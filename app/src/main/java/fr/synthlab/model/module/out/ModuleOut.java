package fr.synthlab.model.module.out;

import com.jsyn.JSyn;
import com.jsyn.Synthesizer;
import com.jsyn.unitgen.LineOut;
import com.jsyn.unitgen.LinearRamp;
import com.jsyn.unitgen.SawtoothOscillatorBL;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by johan on 01/02/16.
 */
public class ModuleOut implements Module{

    private final LineOut lineOut;

    private final OutputPort out;

    public ModuleOut(){
        lineOut = new LineOut();
        out = new OutputPort("out");
    }

    public void start(){
        lineOut.start();
    }

    @Override
    public Collection<Port> getPorts() {
        ArrayList<Port> res = new ArrayList<>();
        res.add(out);
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
        // Connect the oscillator to both left and right output.
        osc.output.connect( 0, a.input, 0 );
        osc.output.connect( 0, a.input, 1 );
        //b.start();
        // Set the minimum, current and maximum values for the port.
        lag.output.connect( osc.amplitude );
        lag.input.setup( 0.0, 0.5, 1.0 );
        lag.time.set(  0.2 );

        // Arrange the faders in a stack



        osc.frequency.setup( 50.0, 300.0, 10000.0 );
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {

        }
    }
}
