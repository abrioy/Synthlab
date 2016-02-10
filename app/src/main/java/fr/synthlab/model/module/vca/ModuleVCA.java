package fr.synthlab.model.module.vca;

import com.jsyn.Synthesizer;
import fr.synthlab.model.filter.FilterAttenuator;
import fr.synthlab.model.filter.VcaAM;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.port.InputPort;
import fr.synthlab.model.module.port.OutputPort;
import fr.synthlab.model.module.port.Port;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 * VCA module (Voltage Control Amplifier) to control the amplitude of a signal
 * with an other modular signal.
 */
public class ModuleVCA implements Module {
    private static final Logger logger = Logger.getLogger(ModuleVCA.class.getName());

    /**
     * The list of port of the VCA Module
     */
    private Collection<Port> ports = new ArrayList<>();

    /**
     * inputs ports
     */
    private InputPort inputPort, inputPortAm;
    /**
     * output port
     */
    private OutputPort outputPort;

    /**
     * attenuator filter
     */
    private FilterAttenuator filterAttenuator = new FilterAttenuator();

    /**
     * Attenuation modulator
     */
    private VcaAM vcaAM;

    private double attenuation = 0.0;

    /**
     * Constructor
     * @param synthesizer
     */
    public ModuleVCA(Synthesizer synthesizer) {
        vcaAM = new VcaAM(filterAttenuator.output);

        synthesizer.add(filterAttenuator);
        synthesizer.add(vcaAM);

        inputPort = new InputPort("in", this, filterAttenuator.input);
        inputPortAm = new InputPort("am", this, vcaAM.input);
        outputPort = new OutputPort("out", this, vcaAM.output);

        ports.add(inputPort);
        ports.add(inputPortAm);
        ports.add(outputPort);
    }

    /**
     * set the attenuation in DB
     * @param attenuation
     */
    public void setAttenuation(double attenuation) {
        this.attenuation = attenuation;
        filterAttenuator.setAttenuation(attenuation);
    }

    /**
     * get the list of ports of the VCA module
     * @return
     */
    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    /**
     * start the VCA
     */
    @Override
    public void start() {
        filterAttenuator.start();
        vcaAM.start();
    }

    /**
     * Stop the VCA
     */
    @Override
    public void stop() {
        filterAttenuator.stop();
        vcaAM.stop();
    }

    /**
     *
     */
    @Override
    public void update() {

    }

    /**
     *
     * @return the type of the module
     */
    @Override
    public ModuleEnum getType() {
        return ModuleEnum.VCA;
    }
}
