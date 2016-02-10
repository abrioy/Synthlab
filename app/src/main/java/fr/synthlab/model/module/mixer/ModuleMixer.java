package fr.synthlab.model.module.mixer;

import com.jsyn.Synthesizer;
import fr.synthlab.model.filter.FilterAttenuator;
import fr.synthlab.model.filter.MixFilter;
import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
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
public class ModuleMixer implements Module{
    private static final Logger logger = Logger.getLogger(ModuleMixer.class.getName());
    private final FilterAttenuator attenuator1;
    private final FilterAttenuator attenuator2;
    private final FilterAttenuator attenuator3;
    private final FilterAttenuator attenuator4;

    private MixFilter mix;

    private Collection<Port> ports;

    public ModuleMixer(Synthesizer syn){

        attenuator1 = new FilterAttenuator();
        attenuator2 = new FilterAttenuator();
        attenuator3 = new FilterAttenuator();
        attenuator4 = new FilterAttenuator();

        mix = new MixFilter(this);

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

        attenuator1.getOutput().connect(mix.getInput1().getInput());
        attenuator2.getOutput().connect(mix.getInput2().getInput());
        attenuator3.getOutput().connect(mix.getInput3().getInput());
        attenuator4.getOutput().connect(mix.getInput4().getInput());

    }

    @Override
    public Collection<Port> getPorts() {
        return ports;
    }

    @Override
    public void start() {
        attenuator1.start();
        attenuator2.start();
        attenuator3.start();
        attenuator4.start();
        mix.start();
    }

    @Override
    public void stop() {
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
    public ModuleEnum getType() {
        return ModuleEnum.MIX;
    }

    public double getAttenuation1(){
        return attenuator1.getAttenuation();
    }

    public void setAttenuation1(double attenuation) {
        attenuator1.setAttenuation(attenuation);
    }

    public double getAttenuation2(){
        return attenuator2.getAttenuation();
    }

    public void setAttenuation2(double attenuation){
        attenuator2.setAttenuation(attenuation);
    }

    public double getAttenuation3(){
        return attenuator3.getAttenuation();
    }

    public void setAttenuation3(double attenuation) {
        attenuator3.setAttenuation(attenuation);
    }

    public double getAttenuation4(){
        return attenuator4.getAttenuation();
    }

    public void setAttenuation4(double attenuation){
        attenuator4.setAttenuation(attenuation);
    }

}
