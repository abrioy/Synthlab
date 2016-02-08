package fr.synthlab.view.viewModuleFactory;


import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.envelope.ModuleEG;
import fr.synthlab.model.module.moduleFactory.ModuleFactory;
import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import fr.synthlab.model.module.out.ModuleOut;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.OscilloscopeDrawing;
import fr.synthlab.view.module.*;

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
            case REP:
                module = createViewModuleREP(workbench);
                break;
            case EG:
                module = createViewModuleEG(workbench);
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

    private static ViewModule createViewModuleREP(Workbench workbench) {
        Module rep = ModuleFactory.createModule(ModuleEnum.REP);
        ViewModuleREP viewREP = new ViewModuleREP(workbench);
        viewREP.setModule(rep);

        return viewREP;
    }

    private static ViewModule createViewModuleEG(Workbench workbench) {
        Module eg = ModuleFactory.createModule(ModuleEnum.EG);
        ViewModuleEG viewEG = new ViewModuleEG(workbench);
        viewEG.setModule(eg);

        viewEG.setChangeAttackCommand(() -> {
            ((ModuleEG) eg).setAttack(viewEG.getAttack());
        });

        viewEG.setChangeDecayCommand(() -> {
            ((ModuleEG) eg).setDecay(viewEG.getDecay());
        });

        viewEG.setChangeSustainCommand(() -> {
            ((ModuleEG) eg).setSustain(viewEG.getSustain());
        });

        viewEG.setChangeReleaseCommand(() -> {
            ((ModuleEG) eg).setRelease(viewEG.getRelease());
        });

        return viewEG;
    }

}
