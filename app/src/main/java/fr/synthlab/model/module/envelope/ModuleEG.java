package fr.synthlab.model.module.envelope;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;

import java.util.Collection;

/**
 * Envelope module.
 * @see Module
 */
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

    /**
     * setter on attack.
     * @param attack to set
     */
    void setAttack(double attack);

    /**
     * setter on decay.
     * @param decay to set
     */
    void setDecay(double decay);

    /**
     * setter on sustain.
     * @param sustain to set
     */
    void setSustain(double sustain);

    /**
     * setter on release.
     * @param release to set
     */
    void setRelease(double release);

    /**
     * @return attack
     */
    double getAttack();

    /**
     * @return decay
     */
    double getDecay();

    /**
     * @return sustain
     */
    double getSustain();

    /**
     * @return release
     */
    double getRelease();
}
