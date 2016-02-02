package fr.synthlab.model.module.out;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.ports.UnitInputPort;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import fr.synthlab.model.filter.FilterAttenuator;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

/**
 * out module to play sound on sound card
 * @author johan
 * @see Module
 */
public class ModuleOut implements Module{

    /**
     * logger
     */
    private static final Logger logger = Logger.getLogger(ModuleOut.class);

    /**
     * audio exit
     */
    private final LineOut lineOut;

    /**
     * input port
     */
    private final InputPort in;

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
    private boolean mute = true;

    /**
     * constructor
     * @param synthesizer where we get sound
     */
    public ModuleOut(Synthesizer synthesizer){
        lineOut = new LineOut();
        attenuator = new FilterAttenuator();
        synthesizer.add(attenuator);
        synthesizer.add(lineOut);
        InputPort interIn = new InputPort("in", this, lineOut.input);
        in = new InputPort("in", this, attenuator.input);
        OutputPort interOut = new OutputPort("out",this, attenuator.output);
        interIn.connect(interOut);
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
     * @return
     */
    public UnitInputPort getInput(){
        return (UnitInputPort) in.getInput();
    }

    /**
     * start play audio
     */
    public void start(){
        if (!isMute()) {
            lineOut.start();
            attenuator.start();
        }
    }

    /**
     * stop play audio
     */
    public void stop(){
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
     * test main on SOS
     * @param args argument
     */
    public static void main(String[] args) {
        Synthesizer synth = JSyn.createSynthesizer();
        SawtoothOscillatorBL osc;
        // Add a tone generator. (band limited sawtooth)
        synth.add(osc = new SawtoothOscillatorBL());
        // Add an output mixer.
        ModuleOut b =new ModuleOut(synth);
        b.syn.start();
        osc.output.connect( 0, b.getInput(), 0 );
        //osc.output.connect( 0, a.input, 1 );
        b.start();
        int i;
        while (true) {
            try {
                i = 0;
                while (i < 6) {
                    Thread.sleep(300);
                    b.setMute(!b.isMute());
                    i++;
                    b.attenuator.setAttenuation(b.attenuator.getAttenuation()-1);
                    b.stop();
                    b.start();
                }
                while (i < 12) {
                    b.attenuator.setAttenuation(b.attenuator.getAttenuation()-1);
                    if (b.isMute()) {
                        Thread.sleep(300);
                        b.setMute(!b.isMute());
                    } else {
                        Thread.sleep(600);
                        b.setMute(!b.isMute());
                    }
                    i++;
                }
            } catch (InterruptedException ignored) {}
        }
    }
}
