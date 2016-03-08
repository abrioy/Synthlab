package fr.synthlab.model.module.mixer;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;

import java.util.Collection;

/**
 * Mix Module.
 * @see Module
 */
public interface ModuleMIX extends Module {
    /**
     * getter on lists port.
     *
     * @return ports can connect to other module
     */
    @Override
    Collection<Port> getPorts();

    /**
     * start module.
     */
    @Override
    void start();

    /**
     * stop module.
     */
    @Override
    void stop();

    /**
     * not use in this module.
     */
    @Override
    void update();

    /**
     * module type.
     *
     * @return ModuleType.MIX
     */
    @Override
    ModuleType getType();

    /**
     * setter on attenuation of input 1.
     *
     * @param attenuation to set
     */
    void setAttenuation1(double attenuation);

    /**
     * setter on attenuation of input 2.
     *
     * @param attenuation to set
     */
    void setAttenuation2(double attenuation);

    /**
     * setter on attenuation of input 3.
     *
     * @param attenuation to set
     */
    void setAttenuation3(double attenuation);

    /**
     * setter on attenuation of input 4.
     *
     * @param attenuation to set
     */
    void setAttenuation4(double attenuation);

    /**
     * getter to attenuation 1.
     * @return attenuation 1
     */
    double getAttenuation1();

    /**
     * getter to attenuation 2.
     * @return attenuation 2
     */
    double getAttenuation2();

    /**
     * getter to attenuation 3.
     * @return attenuation 3
     */
    double getAttenuation3();

    /**
     * getter to attenuation 4.
     * @return attenuation 4
     */
    double getAttenuation4();
}
