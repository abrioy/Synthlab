package fr.synthlab.view.viewModuleFactory;


import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleEnum;
import fr.synthlab.model.module.moduleFactory.ModuleFactory;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.view.module.ViewModule;
import fr.synthlab.view.module.ViewModuleOUT;
import fr.synthlab.view.module.ViewModuleOscilloscope;
import fr.synthlab.view.module.ViewModuleVCO;

public class ViewModuleFactory {


    public static ViewModule createViewModule(ModuleEnum m) {
        switch (m) {
            case VCOA:
                return createViewModuleVCO();
            case OUT:
                return createViewModuleOut();
            case SCOP:
                return createViewModuleOscilloscope();
        }
        return null;
    }




    /**
     * @return a viewModuleVCO attached to its module
     */
    private static ViewModuleVCO createViewModuleVCO() {
        Module vco = ModuleFactory.createModule(ModuleEnum.VCOA);
        ViewModuleVCO viewVco = new ViewModuleVCO();
        viewVco.setModule(vco);

        viewVco.setChangeFreqCommand(() -> {
            ((ModuleVCOA) vco).setFrequency(viewVco.getFreq() + viewVco.getFreqFin());
        });

        viewVco.setChangeShapeCommand(() -> {
            ((ModuleVCOA) vco).setShape(viewVco.getSelectedShape());
        });

        return viewVco;
    }

    private static ViewModule createViewModuleOut() {
        Module out = ModuleFactory.createModule(ModuleEnum.OUT);
        ViewModuleOUT viewOut = new ViewModuleOUT();
        viewOut.setModule(out);

        //Todo Creat Command


        return viewOut;
    }

    private static ViewModule createViewModuleOscilloscope() {
        Module scop = ModuleFactory.createModule(ModuleEnum.SCOP);
        ViewModuleOscilloscope viewScop = new ViewModuleOscilloscope();
        viewScop.setModule(scop);
        return viewScop;
    }


}
