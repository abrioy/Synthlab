package fr.synthlab.model.module;

/**
 * Created by johan on 01/02/16.
 */
public class Port {
    private String name;
    private Port port;

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
