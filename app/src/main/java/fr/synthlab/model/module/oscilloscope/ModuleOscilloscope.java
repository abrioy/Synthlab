package fr.synthlab.model.module.oscilloscope;

import fr.synthlab.model.module.Module;
import org.apache.log4j.Logger;
import fr.synthlab.model.module.port.Port;

import java.util.Collection;


public class ModuleOscilloscope implements Module {
	private static final Logger logger = Logger.getLogger(ModuleOscilloscope.class);



    @Override
    public Collection<Port> getPorts() {
        return null;
    }
}
