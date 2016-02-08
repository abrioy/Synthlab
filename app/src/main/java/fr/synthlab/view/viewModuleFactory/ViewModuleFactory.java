package fr.synthlab.view.viewModuleFactory;


import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.moduleFactory.ModuleFactory;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.out.ModuleOut;
import fr.synthlab.model.module.port.Port;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.OscilloscopeDrawing;
import fr.synthlab.view.module.*;

import java.util.Collection;
import java.util.logging.Logger;

public class ViewModuleFactory {
	private static final Logger logger = Logger.getLogger(ViewModuleFactory.class.getName());


    public static ViewModule createViewModule(ModuleEnum m, Workbench workbench) {
        ViewModule module = null;
		switch (m) {
            case VCOA:
				module = createViewModuleVCO(workbench);
				break;
            case VCA:
                module = createViewModuleVCA(workbench);
                break;
            case OUT:
                module = createViewModuleOut(workbench);
				break;
            case SCOP:
                module = createViewModuleOscilloscope(workbench);
				break;
        }
		logger.finer("ViewModule created: "+m.toString());
        return module;
    }

    private static ViewModule createViewModuleVCA(Workbench workbench) {
        //todo add modeleModule and decomment command
        Module vca = new Module() {//todo delete this false implementation
            @Override public Collection<Port> getPorts() { return null;}
            @Override public void start() {}
            @Override public void stop() {}
            @Override public void update() {}
            @Override public String getName() {return "VCA";}
        };
        ViewModuleVCA viewVca = new ViewModuleVCA(workbench);
        viewVca.setModule(vca);
        /*viewVca.setChangeAmpliCommand(()->
                ((ModuleVCA) vca).setAmpli(viewVca.getAmpli())
        );*/
        return viewVca;
    }

    /**
     * @return a viewModuleVCO attached to its module
     * @param workbench
     */
    private static ViewModuleVCO createViewModuleVCO(Workbench workbench) {
        Module vco = ModuleFactory.createModule(ModuleEnum.VCOA);
        ViewModuleVCO viewVco = new ViewModuleVCO(workbench);
        viewVco.setModule(vco);

        viewVco.setChangeFreqCommand(() -> {
            ((ModuleVCOA) vco).setFrequency(viewVco.getFreq());
        });

        viewVco.setChangeShapeCommand(() -> {
            ((ModuleVCOA) vco).setShape(viewVco.getSelectedShape());
        });

        return viewVco;
    }

    private static ViewModule createViewModuleOut(Workbench workbench) {
        Module out = ModuleFactory.createModule(ModuleEnum.OUT);
        ViewModuleOUT viewOut = new ViewModuleOUT(workbench);
        viewOut.setModule(out);
        viewOut.setVolumeCommand(() -> ((ModuleOut) out).setAttenuation(viewOut.getPicker().getValue()));
        viewOut.setMuteCommand(() -> ((ModuleOut) out).setMute(viewOut.isMute()));

        return viewOut;
    }

    private static ViewModule createViewModuleOscilloscope(Workbench workbench) {
        Module scop = ModuleFactory.createModule(ModuleEnum.SCOP);
        ViewModuleOscilloscope viewScop = new ViewModuleOscilloscope(workbench);
        viewScop.setModule(scop);
        viewScop.setPickerCommand(() -> {
            ((ModuleOscilloscope) scop).setScale(viewScop.getScale());
        });

		((OscilloscopeDrawing) viewScop.getOscilloscopeDrawing()).setModuleOscilloscope((ModuleOscilloscope) scop);

		return viewScop;
    }


}
