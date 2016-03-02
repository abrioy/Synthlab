package fr.synthlab.model.module.port;

import fr.synthlab.model.module.Module;


public interface Port {
    /**
     * @return the name of the port
     */
    String getName();

    boolean isConnected();

    /**
     * connect this port to another port.
     *
     * @param portConnected Port to connect
     */
    void connect(Port portConnected);

    /**
     * disconnect this port.
     */
    void disconnect();

    /**
     * @return the port which is connected to this one
     */
    Port getConnected();

    /**
     * @return the module which contains this port
     */
    Module getModule();

    /**
     * set the connected port.
     *
     * @param newPort the connected port
     */
    void setPort(Port newPort);
}
