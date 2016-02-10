package fr.synthlab.view.module;

import fr.synthlab.view.Workbench;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleMixer extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleMixer.class.getName());

    public ViewModuleMixer(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleMixer.fxml");
        this.setId("pane");
        //todo add listener on mute
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*picker.valueProperty().addListener(event -> {
            volume.execute();
        });
        muteButton.setOnAction(event -> {
            mute = !mute;
            muteCommand.execute();
        });*/
    }
}
