package fr.synthlab.model.module.sequencer;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;

import java.util.Collection;
import java.util.Observer;


public interface ModuleSEQ extends Module {
    /**
     * Getter on ports input and output.
     *
     * @return Scope port
     */
    @Override
    Collection<Port> getPorts();

    /**
     * Inherit method.
     */
    @Override
    void start();

    /**
     * Inherit method.
     */
    @Override
    void stop();

    /**
     * Inherit method.
     */
    @Override
    void update();

    @Override
    ModuleType getType();

    void setStepValue(int step, double value);

    void addObserver(Observer obs);

    void removeObserver(Observer obs);

    void updateObs();

    int getCurrent();

    void reset();
}
