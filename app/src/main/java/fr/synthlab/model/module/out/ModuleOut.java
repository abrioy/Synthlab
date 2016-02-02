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
 * out module to play sound on sound card
 * @author johan
 * @see Module
 */
public class ModuleOut implements Module{
    private static final Logger logger = Logger.getLogger(ModuleOut.class.getName());

    /**
     * audio exit
     */
    private final LineOut lineOut;

    /**
     * input port
     */
    private final InputPort in;

    //todo outputPort

    /**
     * attenuator
     */
    private final FilterAttenuator attenuator;

    /**
     * synthesizer
     */
    private final Synthesizer syn;

    /**
     * if audio is mute
     */
    private boolean mute = false;

    /**
     * constructor
     * @param synthesizer where we get sound
     */
    public ModuleOut(Synthesizer synthesizer){
        lineOut = new LineOut();
        attenuator = new FilterAttenuator();
        synthesizer.add(attenuator);
        synthesizer.add(lineOut);
        in = new InputPort("in", this, attenuator.input);
        OutputPort interOut = new OutputPort("out",this, attenuator.output);
        new InputPort("in", this, lineOut.input.getConnectablePart(0)).connect(interOut);
        new InputPort("in", this, lineOut.input.getConnectablePart(1)).connect(interOut);
        syn = synthesizer;
        attenuator.start();
    }

    /**
     * getter on mute
     * @return true if we play sound
     */
    public boolean isMute() {
        return mute;
    }

    /**
     * setter on mute start or stop play audio
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
     * getter on synthesizer
     * @return synthesizer
     */
    public Synthesizer getSyn() {
        return syn;
    }

    /**
     * getter on input
     * @return InputPort
     */
    public InputPort getInput() {
        return in;
    }

    /**
     * start play audio
     */
    @Override
    public void start() {
        if (!isMute()) {
            lineOut.start();
            attenuator.start();
        }
    }

    /**
     * stop play audio
     */
    @Override
    public void stop() {
        lineOut.stop();
        attenuator.stop();
    }

    /**
     * getter on ports input and output
     * @return only input port
     */
    @Override
    public Collection<Port> getPorts() {
        ArrayList<Port> res = new ArrayList<>();
        res.add(in);
        return res;
    }

    /**
     * inherit method
     */
    @Override
    public void update() {
        //TODO nothink
    }

    /**
     * getter on attenuation
     * @return attenuation
     */
    public double getAttenuation(){
        return attenuator.getAttenuation();
    }

    /**
     * setter on attenuation
     * @param attenuation new attenuation
     */
    public void setAttenuation(double attenuation){
        attenuator.setAttenuation(attenuation);
    }
}