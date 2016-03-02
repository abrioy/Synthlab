package fr.synthlab.model.module.envelope;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;

import java.util.Collection;


public interface ModuleEG extends Module {
    @Override
    Collection<Port> getPorts();

    @Override
    void start();

    @Override
    void stop();

    @Override
    void update();

    @Override
    ModuleType getType();

    void setAttack(double attack);

    void setDecay(double decay);

    void setSustain(double sustain);

    void setRelease(double release);

    double getAttack();

    double getDecay();

    double getSustain();

    double getRelease();
}
