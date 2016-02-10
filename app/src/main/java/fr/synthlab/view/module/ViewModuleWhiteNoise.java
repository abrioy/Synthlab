package fr.synthlab.view.module;

import fr.synthlab.view.Workbench;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleWhiteNoise extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleWhiteNoise.class.getName());


    public ViewModuleWhiteNoise(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleWhiteNoise.fxml");
        this.setId("pane");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
