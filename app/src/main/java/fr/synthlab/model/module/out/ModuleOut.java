package fr.synthlab.model.module.out;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import fr.synthlab.model.filter.FilterAttenuator;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

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

    /**
     * input port mono.
     */
    private final InputPort in;

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
     * input stereo Left.
     */
    private final InputPort inLeft;

    /**
     * input stereo right.
     */
    private final InputPort inRight;

    /**
     * audio output mono.
     */
    private final LineOut lineOut;

    /**
     * attenuator filter mono.
     */
    private final FilterAttenuator attenuator;

    /**
     * if audio is mute.
     */
    private boolean mute = false;

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
        in = new InputPort("in", this, attenuator.input);
        inLeft = new InputPort("inLeft", this, attenuatorLeft.input);
        inRight = new InputPort("inRight", this, attenuatorRight.input);
        OutputPort interOut = new OutputPort("out",this, attenuator.output);
        OutputPort interOutLeft = new OutputPort("outLeft",this, attenuatorLeft.output);
        OutputPort interOutRight = new OutputPort("outRight",this, attenuatorRight.output);
        new InputPort("inLeft", this, lineOutLeft.input.getConnectablePart(0)).connect(interOutLeft);
        new InputPort("inRight", this, lineOutRight.input.getConnectablePart(1)).connect(interOutRight);
        new InputPort("in0", this, lineOut.input.getConnectablePart(0)).connect(interOut);
        new InputPort("in1", this, lineOut.input.getConnectablePart(1)).connect(interOut);
        syn = synthesizer;
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
     * @return only input port : mono, stero right and stereo left
     */
    @Override
    public Collection<Port> getPorts() {
        ArrayList<Port> res = new ArrayList<>();
        res.add(in);
        res.add(inLeft);
        res.add(inRight);
        return res;
    }

    /**
     * inherit method.
     * nothing to do in the disconnect of port.
     */
    @Override
    public void update() {}

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
