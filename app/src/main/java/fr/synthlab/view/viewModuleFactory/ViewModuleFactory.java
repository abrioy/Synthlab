package fr.synthlab.view.viewModuleFactory;


import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.moduleFactory.ModuleFactory;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.module.ViewModule;
import fr.synthlab.view.module.ViewModuleOUT;
import fr.synthlab.view.module.ViewModuleOscilloscope;
import fr.synthlab.view.module.ViewModuleVCO;

public class ViewModuleFactory {


    public static ViewModule createViewModule(ModuleEnum m,Workbench workbench) {
        switch (m) {
            case VCOA:
                return createViewModuleVCO(workbench);
            case OUT:
                return createViewModuleOut(workbench);
            case SCOP:
                return createViewModuleOscilloscope(workbench);
        }
        return null;
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
            ((ModuleVCOA) vco).setFrequency(viewVco.getFreq() + viewVco.getFreqFin());
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

        //Todo Creat Command


        return viewOut;
    }

    private static ViewModule createViewModuleOscilloscope(Workbench workbench) {
        Module scop = ModuleFactory.createModule(ModuleEnum.SCOP);
        ViewModuleOscilloscope viewScop = new ViewModuleOscilloscope(workbench);
        viewScop.setModule(scop);
        return viewScop;
    }


}
