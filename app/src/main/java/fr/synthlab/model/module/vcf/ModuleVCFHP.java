package fr.synthlab.model.module.vcf;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.FilterHighPass;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ModuleVCFHP extends ModuleVCF {

    /**
     * JSyn highPass Filter
     */
    private FilterHighPass hpFilter = new FilterHighPass();

    public ModuleVCFHP(Synthesizer synthesizer) {
        super(synthesizer);
        synthesizer.add(hpFilter);
        input = new InputPort("in", this, hpFilter.input);
        output = new OutputPort("out", this, hpFilter.output);

        ports.add(input);
        ports.add(output);
        setF0(f0);
    }

    /**
     * start the module
     */
    @Override
    public void start() {
        super.start();
        hpFilter.start();
    }

    /**
     * stop the module
     */
    @Override
    public void stop() {
        super.stop();
        hpFilter.stop();
    }

    /**
     * set the cut frequency
     * @param f0 the cut frequency
     */
    @Override
    public void setF0(double f0) {
        super.setF0(f0);
        if (fmInput.getConnected() == null) {
            hpFilter.frequency.set(f0);
        }
    }

    /**
     * this method is called when we connect or disconnect the fm Input port
     * when the fm input port is connected, we connect his output to the HighPass filter
     * when the fm input port is disconnected, we set the frequency f0 to the HighPass filter
     */
    @Override
    public void update() {
        if (fmInput.getConnected() == null) {
            fmFilter.output.disconnectAll();
            hpFilter.frequency.set(f0);
        } else {
            fmFilter.output.connect(hpFilter.frequency);
        }
    }

    /**
     *
     * @return the type of the module
     */
    @Override
    public ModuleEnum getType() {
        return ModuleEnum.VCFHP;
    }

}
