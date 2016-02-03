package fr.synthlab.model.module.port;

import fr.synthlab.model.module.Module;

import java.util.logging.Logger;

/**
 *
 */
public class Port {
	private static final Logger logger = Logger.getLogger(Port.class.getName());

    /**
     * the name of the port
     */
    private String name;

    /**
     * the port connected to this one
     */
    private Port port;

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
     *
     * @return the name of the port
     */
    public String getName(){
        return name;
    }

    /**
     * connect this port to another port
     * @param port
     */
    public void connect(Port port){
        this.port = port;
        module.update();
        port.setPort(this);
        port.getModule().update();
    }

    /**
     * disconnect this port
     */
    public void disconnect(){
        port.setPort(null);
        port.getModule().update();
        port = null;
        module.update();
    }

    /**
     *
     * @return the port which is connected to this one
     */
    public Port getConnected(){
        return port;
    }

    /**
     *
     * @return the module which contains this port
     */
    public Module getModule() {
        return module;
    }

    /**
     * set the connected port
     * @param port the connected port
     */
    public void setPort(Port port) {
        this.port = port;
    }
}
