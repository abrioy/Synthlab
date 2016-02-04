package fr.synthlab.view.module;

import fr.synthlab.view.controller.Workbench;

import java.util.logging.Logger;

public class ViewModuleOUT extends ViewModule {
    private static final Logger logger = Logger.getLogger(ViewModuleOUT.class.getName());

    public ViewModuleOUT(Workbench workbench) {
		super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleOUT.fxml");

    }

}
