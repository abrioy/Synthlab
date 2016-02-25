package fr.synthlab.view.component;

import javafx.scene.control.Button;

/**
 * Created by pollt on 2/9/16.
 */
public class RecordButton extends Button {

    public RecordButton() {
        this.getStylesheets().add(
                getClass().getResource("/gui/fxml/style/Module.css")
                        .toExternalForm());
        this.getStyleClass().add("record-release");
    }

    public final void setToggle(final boolean toggle) {
        if (toggle) {
            this.getStyleClass().clear();
            this.getStyleClass().add("record-pressed");
        } else {
            this.getStyleClass().clear();
            this.getStyleClass().add("record-release");
        }
    }
}
