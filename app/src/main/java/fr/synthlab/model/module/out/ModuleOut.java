package fr.synthlab.model.module.out;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import fr.synthlab.model.filter.FilterAttenuator;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.Port;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by johan on 01/02/16.
 *
 */
public class ModuleOut implements Module{

    private static final Logger logger = Logger.getLogger(ModuleOut.class);

    private final LineOut lineOut;

    private final InputPort in;
    private final FilterAttenuator attenuator;
    public final Synthesizer syn;

    private boolean mute = true;

    public ModuleOut(Synthesizer synthesizer){
        lineOut = new LineOut();
        in = new InputPort("in", this, lineOut.input);
        attenuator = new FilterAttenuator();
        synthesizer.add(attenuator);
        synthesizer.add(lineOut);
        lineOut.input.connect(attenuator.output);
        syn = synthesizer;
        attenuator.start();
    }

    public static void main(String[] args) {
        Synthesizer synth = JSyn.createSynthesizer();
        SawtoothOscillatorBL osc;
        // Add a tone generator. (band limited sawtooth)
        synth.add(osc = new SawtoothOscillatorBL());
        // Add a lag to smooth out amplitude changes and avoid pops.
        //LinearRamp lag;
        //synth.add( lag = new LinearRamp() );
        // Add an output mixer.
        ModuleOut b =new ModuleOut(synth);
        LineOut a = b.lineOut;
        //synth.add( a );
        synth=b.syn;
        b.syn.start();
        osc.output.connect( 0, b.attenuator.input, 0 );
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
                    if (b.isMute()) {
                        Thread.sleep(300);
                        b.setMute(!b.isMute());
                    } else {
                        Thread.sleep(600);
                        b.setMute(!b.isMute());
                    }
                    i++;
                }
            } catch (InterruptedException ignored) {

            }
        }
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
        if (isMute()) {
            stop();
        } else {
            start();
        }
    }

    public void start(){
        if (!isMute()) {
            lineOut.start();
            attenuator.start();
        }
    }

    public void stop(){
        lineOut.stop();
        attenuator.stop();
    }

    @Override
    public Collection<Port> getPorts() {
        ArrayList<Port> res = new ArrayList<>();
        res.add(in);
        return res;
    }

    @Override
    public void update() {
        //TODO nothink
    }
}
