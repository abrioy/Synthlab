package fr.synthlab.model.module.port;

import fr.synthlab.model.module.Module;

import java.util.logging.Logger;


public class Port {
	private static final Logger logger = Logger.getLogger(Port.class.getName());

	private String name;
    private Port port;

    private Module module;

    public Port(String name, Module m) {
        this.name = name;
        this.module = m;
    }

    public String getName(){
        return name;
    }

    public void connect(Port port){
        this.port = port;
        module.update();
        port.setPort(this);
        port.getModule().update();
    }

    public void disconnect(){
        port = null;
        module.update();
        port.setPort(null);
        port.getModule().update();
    }

    public Port getConnected(){
        return port;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public void setPort(Port port) {
        this.port = port;
    }
}
