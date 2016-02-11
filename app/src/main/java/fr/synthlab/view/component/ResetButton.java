package fr.synthlab.view.component;

import javafx.scene.control.Button;

/**
 * Created by pollt on 2/9/16.
 */
public class ResetButton extends Button {

    public ResetButton(){
        this.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Module.css").toExternalForm());
        this.getStyleClass().add("reset-button");
    }

}
