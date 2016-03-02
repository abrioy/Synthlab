package fr.synthlab.model.module.mixer;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;

import java.util.Collection;


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

    double getAttenuation1();

    double getAttenuation2();

    double getAttenuation3();

    double getAttenuation4();
}
