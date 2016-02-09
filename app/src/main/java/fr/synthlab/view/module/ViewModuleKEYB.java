package fr.synthlab.view.module;

import fr.synthlab.model.Command;
import fr.synthlab.model.module.keyboard.Note;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.KEYBKey;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleKEYB extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleKEYB.class.getName());

    @FXML
    KEYBKey CKey;
    @FXML
    KEYBKey CSharpKey;
    @FXML
    KEYBKey DKey;
    @FXML
    KEYBKey DSharpKey;
    @FXML
    KEYBKey EKey;
    @FXML
    KEYBKey FKey;
    @FXML
    KEYBKey FSharpKey;
    @FXML
    KEYBKey GKey;
    @FXML
    KEYBKey GSharpKey;
    @FXML
    KEYBKey AKey;
    @FXML
    KEYBKey ASharpKey;
    @FXML
    KEYBKey BKey;
    @FXML
    KEYBKey CNextOctKey;

    Command keyPressedCommand;
    Command keyReleasedCommand;
    Note lastKeyPressed;

    public ViewModuleKEYB(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleKEYB.fxml");
        this.setId("pane");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        CKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.C;
            keyPressedCommand.execute();
        });
        CSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.CSharp;
            keyPressedCommand.execute();
        });
        DKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.D;
            keyPressedCommand.execute();
        });
        DSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.DSharp;
            keyPressedCommand.execute();
        });
        EKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.E;
            keyPressedCommand.execute();
        });
        FKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.F;
            keyPressedCommand.execute();
        });
        FSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.FSharp;
            keyPressedCommand.execute();
        });
        GKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.G;
            keyPressedCommand.execute();
        });
        GSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.GSharp;
            keyPressedCommand.execute();
        });
        AKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.A;
            keyPressedCommand.execute();
        });
        ASharpKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.ASharp;
            keyPressedCommand.execute();
        });
        BKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.B;
            keyPressedCommand.execute();
        });
        CNextOctKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.C2;
            keyPressedCommand.execute();
        });


        CKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });
        CSharpKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });
        DKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });
        DSharpKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });
        EKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });
        FKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });
        FSharpKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });
        GKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });
        GSharpKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });
        AKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });
        ASharpKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });
        BKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });
        CNextOctKey.setOnMouseReleased(event -> {
            keyReleasedCommand.execute();
        });

    }

    public void setKeyPressedCommand(Command command) {
        this.keyPressedCommand = command;
    }

    public void setKeyReleasedCommand(Command command) {
        this.keyReleasedCommand = command;
    }

    public Note getNotePressed() {
        return lastKeyPressed;
    }
}
