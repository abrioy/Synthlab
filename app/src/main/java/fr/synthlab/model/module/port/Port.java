package fr.synthlab.model.module.port;

import fr.synthlab.model.module.Module;

import java.util.logging.Logger;

/**
 *
 */
public class Port {
    private static final Logger LOGGER = Logger.getLogger(Port.class.getName());

    /**
     * the name of the port
     */
    private String name;

    /**
     * the port connected to this one
     */
    private Port port = null;

    /**
     * the module which contains this port
     */
    private Module module;

    /**
     * constructor
     *
     * @param name name of the port
     * @param m    the module which contains this port
     */
    public Port(String name, Module m) {
        this.name = name;
        this.module = m;
    }

    /**
     * @return the name of the port
     */
    public String getName() {
        return name;
    }

    public boolean isConnected() {
        return port != null;
    }

    /**
     * connect this port to another port.
     *
     * @param port Port to connect
     */
    public void connect(Port port) {
        this.port = port;
        module.update();
        port.setPort(this);
        port.getModule().update();
    }

    /**
     * disconnect this port.
     */
    public void disconnect() {
        if (port != null) {
            port.setPort(null);
            port.getModule().update();
            port = null;
            module.update();
        } else {
            LOGGER.warning("Trying to disconnect a port that is not connected to anything.");
        }
    }

    /**
     * @return the port which is connected to this one
     */
    public Port getConnected() {
        return port;
    }

    /**
     * @return the module which contains this port
     */
    public Module getModule() {
        return module;
    }

    /**
     * set the connected port
     *
     * @param port the connected port
     */
    public void setPort(Port port) {
        this.port = port;
    }
}
