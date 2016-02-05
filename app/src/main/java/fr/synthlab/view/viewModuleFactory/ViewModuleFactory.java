package fr.synthlab.view.viewModuleFactory;


import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.moduleFactory.ModuleFactory;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.out.ModuleOut;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.OscilloscopeDrawing;
import fr.synthlab.view.module.ViewModule;
import fr.synthlab.view.module.ViewModuleOUT;
import fr.synthlab.view.module.ViewModuleOscilloscope;
import fr.synthlab.view.module.ViewModuleVCO;
import javafx.scene.layout.AnchorPane;

import java.util.logging.Logger;

public class ViewModuleFactory {
	private static final Logger logger = Logger.getLogger(ViewModuleFactory.class.getName());


    public static ViewModule createViewModule(ModuleEnum m, Workbench workbench) {
        ViewModule module = null;
		switch (m) {
            case VCOA:
				module = createViewModuleVCO(workbench);
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
        viewOut.setVolume(() -> ((ModuleOut) out).setAttenuation(viewOut.getPicker().getValue()));
        viewOut.setMute(() -> ((ModuleOut) out).setMute(viewOut.isMute()));

        return viewOut;
    }

    private static ViewModule createViewModuleOscilloscope(Workbench workbench) {
        Module scop = ModuleFactory.createModule(ModuleEnum.SCOP);
        ViewModuleOscilloscope viewScop = new ViewModuleOscilloscope(workbench);
        viewScop.setModule(scop);
        viewScop.setPickerCmd(() -> {
            ((ModuleOscilloscope) scop).setScale(viewScop.getScale());
        });

		// FIXME: Code à Corentin
		((OscilloscopeDrawing) ((AnchorPane) viewScop.getChildren().get(0)).getChildren().get(0)).setModuleOscillo((ModuleOscilloscope)scop);
        return viewScop;
    }


}
