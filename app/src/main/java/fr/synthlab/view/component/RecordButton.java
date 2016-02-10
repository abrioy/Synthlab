package fr.synthlab.view.component;

import javafx.scene.control.Button;

/**
 * Created by pollt on 2/9/16.
 */
public class RecordButton extends Button {

    public RecordButton(){
        this.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Module.css").toExternalForm());
        this.getStyleClass().add("rec-release");
    }
    public void setToggle(boolean toggle) {
        if (toggle){
            this.getStyleClass().remove("rec-release");
            this.getStyleClass().add("rec-pressed");

        }
        else {
            this.getStyleClass().remove("rec-pressed");
            this.getStyleClass().add("rec-release");
        }
    }

}
