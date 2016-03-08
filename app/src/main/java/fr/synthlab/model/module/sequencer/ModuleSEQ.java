package fr.synthlab.model.module.sequencer;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;

import java.util.Collection;
import java.util.Observer;

/**
 * module sequence.
 */
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

    /**
     * set value of sequence.
     * @param step position in the sequence
     * @param value new value
     */
    void setStepValue(int step, double value);

    /**
     * add observer of current sequence.
     * @param obs observer
     */
    void addObserver(Observer obs);

    /**
     * remove observer of current sequence.
     * @param obs observer
     */
    void removeObserver(Observer obs);

    /**
     * notify all observer.
     */
    void updateObs();

    /**
     * getter of position in the sequence.
     * @return current sequence
     */
    int getCurrent();

    /**
     * restart sequence.
     */
    void reset();
}
