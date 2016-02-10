package fr.synthlab.model.module;

import fr.synthlab.model.module.port.Port;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collection;


public interface Module extends Serializable {
    Collection<Port> getPorts();

    default Port getPort(String name) {
        for (Port p : getPorts()) {
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    void start();
    void stop();
    void update();
    ModuleEnum getType();

	void writeObject(ObjectOutputStream o) throws IOException;

	void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException;
}
