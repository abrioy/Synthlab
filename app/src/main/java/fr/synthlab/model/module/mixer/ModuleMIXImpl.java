package fr.synthlab.model.module.mixer;

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
 * out module to play sound on sound card.
 * @author johan
 * @see Module
 */
public class ModuleMIXImpl implements ModuleMIX {
    private static final Logger LOGGER
            = Logger.getLogger(ModuleMIXImpl.class.getName());

    /**
     * attenuator on input 1.
     */
    private final FilterAttenuator attenuator1;

    /**
     * attenuator on input 2.
     */
    private final FilterAttenuator attenuator2;

    /**
     * attenuator on input 3.
     */
    private final FilterAttenuator attenuator3;

    /**
     * attenuator on input 4.
     */
    private final FilterAttenuator attenuator4;

    /**
     * filter who unit the 4 inputs.
     */
    private FilterMIXImpl mix;

    /**
     * return list ports.
     */
    private Collection<Port> ports;

    /**
     * constructor.
     *
     * @param syn synthesizer
     */
    public ModuleMIXImpl(final Synthesizer syn) {
        attenuator1 = new FilterAttenuator();
        attenuator2 = new FilterAttenuator();
        attenuator3 = new FilterAttenuator();
        attenuator4 = new FilterAttenuator();

        mix = new FilterMIXImpl();

        ports = new ArrayList<>();
        ports.add(new InputPort("in1", this, attenuator1.input));
        ports.add(new InputPort("in2", this, attenuator2.input));
        ports.add(new InputPort("in3", this, attenuator3.input));
        ports.add(new InputPort("in4", this, attenuator4.input));
        ports.add(new OutputPort("out", this, mix.getOutput()));

        syn.add(attenuator1);
        syn.add(attenuator2);
        syn.add(attenuator3);
        syn.add(attenuator4);
        syn.add(mix);

        attenuator1.getOutput().connect(mix.getInput1());
        attenuator2.getOutput().connect(mix.getInput2());
        attenuator3.getOutput().connect(mix.getInput3());
        attenuator4.getOutput().connect(mix.getInput4());
    }

    @Override
    public final Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public final void start() {
        attenuator1.start();
        attenuator2.start();
        attenuator3.start();
        attenuator4.start();
        mix.start();
    }

    @Override
    public final void stop() {
        attenuator1.stop();
        attenuator2.stop();
        attenuator3.stop();
        attenuator4.stop();
        mix.stop();
    }

    @Override
    public void update() {
    }

    @Override
    public final ModuleType getType() {
        return ModuleType.MIX;
    }

    @Override
	public final void setAttenuation1(final double attenuation) {
        attenuator1.setAttenuation(attenuation);
    }

    @Override
	public final void setAttenuation2(final double attenuation) {
        attenuator2.setAttenuation(attenuation);
    }

    @Override
	public final void setAttenuation3(final double attenuation) {
        attenuator3.setAttenuation(attenuation);
    }

    @Override
	public final void setAttenuation4(final double attenuation) {
        attenuator4.setAttenuation(attenuation);
    }

    @Override
	public final double getAttenuation1() {
        return attenuator1.getAttenuation();
    }

    @Override
	public final double getAttenuation2() {
        return attenuator2.getAttenuation();
    }

    @Override
	public final double getAttenuation3() {
        return attenuator3.getAttenuation();
    }

    @Override
	public final double getAttenuation4() {
        return attenuator4.getAttenuation();
    }
}
