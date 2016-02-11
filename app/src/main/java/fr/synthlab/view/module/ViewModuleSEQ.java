package fr.synthlab.view.module;

import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.component.ResetButton;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleSEQ extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleSEQ.class.getName());

    @FXML
    private ResetButton resetButton;

    private Runnable resetCommand;


    public ViewModuleSEQ(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleSEQ.fxml");
        this.setId("pane");
        //resetButton.setPrefSize(30,30);

    }

    public void setResetCommand(Runnable reset) {
        this.resetCommand = reset;
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        resetButton.setOnAction(event -> {
            resetCommand.run();
        });
    }

    @Override
    public void writeObject(ObjectOutputStream o) throws IOException {

    }

    @Override
    public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {

    }
}
