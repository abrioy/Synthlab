package fr.synthlab.view.component;

import javafx.scene.control.Button;

/**
 * Created by pollt on 2/9/16.
 */
public class MuteButton extends Button {

    public MuteButton(){
        this.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Module.css").toExternalForm());
        this.getStyleClass().add("button-release");
    }
    public void setToggle(boolean toggle) {
        if (toggle){
            this.getStyleClass().remove("button-release");
            this.getStyleClass().add("button-pressed");

        }
        else {
            this.getStyleClass().remove("button-pressed");
            this.getStyleClass().add("button-release");
        }
    }

}
