package fr.synthlab.model.module.port;

public class Port {
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
