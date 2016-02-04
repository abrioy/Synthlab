package fr.synthlab.view.viewModuleFactory;


import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.moduleFactory.ModuleFactory;
import fr.synthlab.model.module.vcoa.ModuleVCOA;
import fr.synthlab.view.module.ViewModuleVCO;

public class ViewModuleFactory {

    /**
     * @return a viewModuleVCO attached to its module
     */
    public static ViewModuleVCO creatViewModuleVCO() {
        Module vco = ModuleFactory.createVCO();
        ViewModuleVCO viewVco = new ViewModuleVCO();
        viewVco.setModule(vco);

        viewVco.setChangeFreq(() -> {
            ((ModuleVCOA) vco).setFrequency(viewVco.getFreq() + viewVco.getFreqFin());
        });


        return viewVco;
    }


}
