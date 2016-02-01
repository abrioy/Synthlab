package fr.synthlab.model.module.port;

import fr.synthlab.model.module.Module;
import org.apache.log4j.Logger;


public class Port {
	private static final Logger logger = Logger.getLogger(Port.class);

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
        // TODO
    }

    public void disconnect(){
        //TODO
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
}
