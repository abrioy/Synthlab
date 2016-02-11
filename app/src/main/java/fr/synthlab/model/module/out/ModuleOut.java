package fr.synthlab.model.module.out;

import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitOutputPort;
import com.jsyn.unitgen.LineOut;
import com.jsyn.util.WaveRecorder;
import fr.synthlab.model.filter.FilterAttenuator;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * out module to play sound on sound card.
 * @author johan
 * @see Module
 */
public class ModuleOut implements Module{
    private static final Logger logger = Logger.getLogger(ModuleOut.class.getName());

    /**
     * audio stereo left exit.
     */
    private final LineOut lineOutLeft;

    /**
     * audio stereo right exit.
     */
    private final LineOut lineOutRight;

    private final OutputPort interOut;

    private final OutputPort interOutLeft;

    private final OutputPort interOutRight;

    /**
     * left attenuator stereo.
     */
    private final FilterAttenuator attenuatorLeft;

    /**
     * right attenuator stereo.
     */
    private final FilterAttenuator attenuatorRight;

    /**
     * synthesizer.
     */
    private final Synthesizer syn;

    /**
     * audio output mono.
     */
    private final LineOut lineOut;

    /**
     * attenuator filter mono.
     */
    private final FilterAttenuator attenuator;

    /**
     * list of ports.
     * contain in, inLeft, inRight.
     */
    private final ArrayList<Port> ports = new ArrayList<>();

    /**
     * if audio is mute.
     */
    private boolean mute = false;

    private boolean recording = false;

    private WaveRecorder waveRecorder;

    /**
     * constructor.
     * @param synthesizer where we get sound
     */
    public ModuleOut(Synthesizer synthesizer){
        lineOutLeft = new LineOut();
        lineOutRight = new LineOut();
        lineOut = new LineOut();
        attenuator = new FilterAttenuator();
        attenuatorLeft = new FilterAttenuator();
        attenuatorRight = new FilterAttenuator();
        synthesizer.add(attenuatorLeft);
        synthesizer.add(attenuatorRight);
        synthesizer.add(attenuator);
        synthesizer.add(lineOutLeft);
        synthesizer.add(lineOutRight);
        synthesizer.add(lineOut);
        InputPort in = new InputPort("in", this, attenuator.input);
        InputPort inLeft = new InputPort("Left", this, attenuatorLeft.input);
        InputPort inRight = new InputPort("Right", this, attenuatorRight.input);
        interOut = new OutputPort("out", this, attenuator.output);
        interOutLeft = new OutputPort("outLeft", this, attenuatorLeft.output);
        interOutRight = new OutputPort("outRight", this, attenuatorRight.output);
        new InputPort("inLeft", this, lineOutLeft.input.getConnectablePart(0)).connect(interOutLeft);
        new InputPort("inRight", this, lineOutRight.input.getConnectablePart(1)).connect(interOutRight);
        new InputPort("in0", this, lineOut.input.getConnectablePart(0)).connect(interOut);
        new InputPort("in1", this, lineOut.input.getConnectablePart(1)).connect(interOut);
        syn = synthesizer;

        ports.add(in);
        ports.add(inLeft);
        ports.add(inRight);
    }

    /**
     * getter on mute.
     * @return true if we play sound
     */
    public boolean isMute() {
        return mute;
    }

    /**
     * setter on mute start or stop play audio.
     * @param mute true if play sound
     */
    public void setMute(boolean mute) {
        this.mute = mute;
        if (isMute()) {
            stop();
        } else {
            start();
        }
    }

    public void setRecording(boolean recording, File pickedFile) {
        this.recording = recording;

        try {
            if (recording) {
                pickedFile.createNewFile();
                waveRecorder = new WaveRecorder(syn, pickedFile);
                ((UnitOutputPort) interOut.getOutput()).connect(0, waveRecorder.getInput(), 0);
                ((UnitOutputPort) interOut.getOutput()).connect(0, waveRecorder.getInput(), 1);
                ((UnitOutputPort) interOutLeft.getOutput()).connect(0, waveRecorder.getInput(), 0);
                ((UnitOutputPort) interOutRight.getOutput()).connect(0, waveRecorder.getInput(), 1);

                waveRecorder.start();
            } else if (waveRecorder != null) {
                waveRecorder.stop();

                interOut.getOutput().disconnect(waveRecorder.getInput());
                interOutLeft.getOutput().disconnect(waveRecorder.getInput());
                interOutRight.getOutput().disconnect(waveRecorder.getInput());

                waveRecorder.close();

                waveRecorder = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * getter on synthesizer.
     * @return synthesizer
     */
    public Synthesizer getSyn() {
        return syn;
    }

    /**
     * start play audio.
     */
    @Override
    public void start() {
        if (!isMute()) {
            lineOut.start();
            lineOutLeft.start();
            lineOutRight.start();
            attenuator.start();
            attenuatorLeft.start();
            attenuatorRight.start();
        }
    }

    /**
     * stop play audio.
     */
    @Override
    public void stop() {
        lineOut.stop();
        lineOutLeft.stop();
        lineOutRight.stop();
        attenuator.stop();
        attenuatorLeft.stop();
        attenuatorRight.stop();
    }

    /**
     * getter on ports input and output.
     * @return only input port : mono, stereo right and stereo left
     */
    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    /**
     * inherit method.
     * nothing to do in the disconnect of port.
     */
    @Override
    public void update() {
    }

    @Override
    public ModuleEnum getType() {
        return ModuleEnum.OUT;
    }

    /**
     * getter on attenuation.
     * @return attenuation
     */
    public double getAttenuation(){
        return attenuatorLeft.getAttenuation();
    }

    /**
     * setter on attenuation.
     * @param attenuation new attenuation
     */
    public void setAttenuation(double attenuation){
        attenuatorLeft.setAttenuation(attenuation);
        attenuatorRight.setAttenuation(attenuation);
        attenuator.setAttenuation(attenuation);
    }
}
