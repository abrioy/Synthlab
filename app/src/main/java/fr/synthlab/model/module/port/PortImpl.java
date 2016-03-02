package fr.synthlab.model.module.port;

import fr.synthlab.model.module.Module;

import java.util.logging.Logger;

/**
 *
 */
public abstract class PortImpl implements Port {
    private static final Logger LOGGER
            = Logger.getLogger(PortImpl.class.getName());

    /**
     * the name of the port.
     */
    private String name;

    /**
     * the port connected to this one.
     */
    private Port port = null;

    /**
     * the module which contains this port.
     */
    private Module module;

    /**
     * constructor.
     *
     * @param nameInit name of the port
     * @param moduleInit    the module which contains this port
     */
    public PortImpl(final String nameInit, final Module moduleInit) {
        name = nameInit;
        module = moduleInit;
    }

    @Override
    public final String getName() {
        return name;
    }

    @Override
    public final boolean isConnected() {
        return port != null;
    }

    @Override
    public void connect(final Port portConnected) {
        port = portConnected;
        module.update();
        port.setPort(this);
        port.getModule().update();
    }

    @Override
    public void disconnect() {
        if (port != null) {
            port.setPort(null);
            port.getModule().update();
            port = null;
            module.update();
        }
    }

    @Override
    public final Port getConnected() {
        return port;
    }

    @Override
    public final Module getModule() {
        return module;
    }

    @Override
    public final void setPort(final Port newPort) {
        port = newPort;
    }
}
