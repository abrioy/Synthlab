package fr.synthlab.model.module;

import fr.synthlab.model.module.port.Port;

import java.util.Collection;

/**
 * Module interface.
 */
public interface Module {
    /**
     * get all port.
     * @return port collection.
     */
    Collection<Port> getPorts();

    /**
     * get one port by his name.
     * @param name port search
     * @return port call name
     */
    default Port getPort(String name) {
        for (Port p : getPorts()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * start module.
     */
    void start();

    /**
     * stop module.
     */
    void stop();

    /**
     * update module on disconnect port.
     */
    void update();

    /**
     * return type module.
     * @return module type
     */
    ModuleType getType();

}
