package fr.synthlab.model.module.whiteNoise;

import com.jsyn.Synthesizer;
import com.jsyn.unitgen.WhiteNoise;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
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
public class ModuleBRUI implements Module {
    private static final Logger LOGGER = Logger.getLogger(ModuleBRUI.class.getName());

    /**
     * list of ports.
     */
    private final ArrayList<Port> ports;
    private final WhiteNoise noise;

    public ModuleBRUI(Synthesizer synthesizer) {
        ports = new ArrayList<>();
        noise = new WhiteNoise();
        ports.add(new OutputPort("out", this, noise.output));
        synthesizer.add(noise);
    }

    @Override
    public void start() {
        noise.start();
    }

    @Override
    public void stop() {
        noise.stop();
    }

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
    public ModuleType getType() {
        return ModuleType.BRUI;
    }
}
