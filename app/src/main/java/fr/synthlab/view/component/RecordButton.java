package fr.synthlab.view.component;

import javafx.scene.control.Button;

/**
 * Created by pollt on 2/9/16.
 */
public class RecordButton extends Button {

    public RecordButton() {
        this.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Module.css").toExternalForm());
        this.getStyleClass().add("record-release");
    }

    public void setToggle(boolean toggle) {
        if (toggle) {
            this.getStyleClass().remove("record-release");
            this.getStyleClass().add("record-pressed");
        } else {
            this.getStyleClass().remove("record-pressed");
            this.getStyleClass().add("record-release");
        }
    }
}
