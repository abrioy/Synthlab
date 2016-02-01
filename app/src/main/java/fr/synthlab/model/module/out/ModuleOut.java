package fr.synthlab.model.module.out;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by johan on 01/02/16.
 */
public class ModuleOut implements Module{

    private final LineOut lineOut;

    private final InputPort in;

    public boolean isMute() {
        return mute;
    }

    private boolean mute = true;

    public ModuleOut(){
        lineOut = new LineOut();
        in = new InputPort("in");
    }

    public void start(){
        if (!isMute()) {
            lineOut.start();
        }
    }


    public void stop(){
        lineOut.stop();
    }

    public void setMute(boolean mute) {
        this.mute = mute;
        if (isMute()) {
                stop();
        } else {
            start();
        }
    }

    @Override
    public Collection<Port> getPorts() {
        ArrayList<Port> res = new ArrayList<>();
        res.add(in);
        return res;
    }

    public static void main(String[] args){
        Synthesizer synth = JSyn.createSynthesizer();
        SawtoothOscillatorBL osc;
        // Add a tone generator. (band limited sawtooth)
        synth.add( osc = new SawtoothOscillatorBL() );
        // Add a lag to smooth out amplitude changes and avoid pops.
        LinearRamp lag;
        synth.add( lag = new LinearRamp() );
        // Add an output mixer.
        ModuleOut b =new ModuleOut();
        LineOut a = b.lineOut;
        synth.add( a );
        synth.start();
        osc.output.connect( 0, a.input, 0 );
        osc.output.connect( 0, a.input, 1 );
        b.start();
        lag.output.connect( osc.amplitude );
        lag.input.setup( 0.0, 0.5, 1.0 );
        lag.time.set(  0.2 );
        osc.frequency.setup( 50.0, 300.0, 10000.0 );
        int i;
        while (true) {
            try {
                i = 0;
                while(i<6) {
                    Thread.sleep(300);
                    b.setMute(!b.isMute());
                    i++;
                }
                while(i<12){
                    if (b.isMute()){
                        Thread.sleep(300);
                        b.setMute(!b.isMute());
                    }
                    else {
                        Thread.sleep(600);
                        b.setMute(!b.isMute());
                    }
                    i++;
                }
            } catch (InterruptedException ignored) {

            }
        }
    }
}
