package fr.synthlab.model.module.vca;

import com.jsyn.Synthesizer;
import fr.synthlab.model.filter.FilterAttenuator;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
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
    private static final Logger LOGGER
            = Logger.getLogger(ModuleVCA.class.getName());

    /**
     * The list of port of the VCA Module.
     */
    private Collection<Port> ports = new ArrayList<>();

    /**
     * inputs ports.
     */
    private InputPort inputPort, inputPortAm;
    /**
     * output port.
     */
    private OutputPort outputPort;

    /**
     * attenuator filter.
     */
    private FilterAttenuator filterAttenuator = new FilterAttenuator();

    /**
     * Attenuation modulator.
     */
    private FilterVCAam filterVCAam;

    private double attenuation = 0.0;

    /**
     * Constructor.
     * @param synthesizer Synthesizer
     */
    public ModuleVCA(final Synthesizer synthesizer) {
        filterVCAam = new FilterVCAam(filterAttenuator.output);

        synthesizer.add(filterAttenuator);
        synthesizer.add(filterVCAam);

        inputPort = new InputPort("in", this, filterAttenuator.input);
        inputPortAm = new InputPort("am", this, filterVCAam.input);
        outputPort = new OutputPort("out", this, filterVCAam.output);

        ports.add(inputPort);
        ports.add(inputPortAm);
        ports.add(outputPort);
    }

    /**
     * @return the attenuation in DB
     */
    public final double getAttenuation() {
        return attenuation;
    }

    /**
     * set the attenuation in DB.
     * @param newAttenuation attenuation new attenuation
     */
    public final void setAttenuation(final double newAttenuation) {
        attenuation = newAttenuation;
        filterAttenuator.setAttenuation(attenuation);
    }

    /**
     * get the list of ports of the VCA module.
     * @return VCA ports
     */
    @Override
    public final Collection<Port> getPorts() {
        return ports;
    }

    /**
     * start the VCA.
     */
    @Override
    public final void start() {
        filterAttenuator.start();
        filterVCAam.start();
    }

    /**
     * Stop the VCA.
     */
    @Override
    public final void stop() {
        filterAttenuator.stop();
        filterVCAam.stop();
    }

    /**
     *
     */
    @Override
    public void update() {
    }

    /**
     * @return the type of the module
     */
    @Override
    public final ModuleType getType() {
        return ModuleType.VCA;
    }
}
