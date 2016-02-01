package fr.synthlab.model.module.port;

import org.apache.log4j.Logger;


public class Port {
	private static final Logger logger = Logger.getLogger(Port.class);

	private String name;
    private Port port;

    public Port(String name) {
        this.name = name;
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
}
