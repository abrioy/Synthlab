package fr.synthlab.view.module;

import fr.synthlab.model.Command;
import fr.synthlab.model.module.keyboard.Note;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.KEYBKey;
import fr.synthlab.view.component.Knob;
import javafx.beans.property.IntegerProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
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

    @FXML
    Knob octavePicker;

    @FXML
    Label octaveLabel;

    Command keyPressedCommand;
    Command keyReleasedCommand;
    Command octaveChangeCommand;
    Note lastKeyPressed;

    public ViewModuleKEYB(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleKEYB.fxml");
        this.setId("pane");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /*
            Mouse pressed events
         */
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

        Collection<KEYBKey> keysColl = new ArrayList<KEYBKey>();
        keysColl.add(CKey);
        keysColl.add(CSharpKey);
        keysColl.add(DKey);
        keysColl.add(DSharpKey);
        keysColl.add(EKey);
        keysColl.add(FKey);
        keysColl.add(FSharpKey);
        keysColl.add(GKey);
        keysColl.add(GSharpKey);
        keysColl.add(AKey);
        keysColl.add(ASharpKey);
        keysColl.add(BKey);
        keysColl.add(CNextOctKey);

        for (KEYBKey key : keysColl) {
            key.setOnMouseReleased(event -> {
                keyReleasedCommand.execute();
            });

            key.setOnMouseExited(event -> {
                keyReleasedCommand.execute();
            });
        }

        octavePicker.valueProperty().addListener(event -> {
            octaveChangeCommand.execute();
            updateOctave();
        });

        octaveLabel.setText(getOctave() + "");

    }

    private void updateOctave() {
        octaveLabel.setText(getOctave() + "");
    }

    public int getOctave() {
        return (int) octavePicker.getValue();
    }

    public void setKeyPressedCommand(Command command) {
        this.keyPressedCommand = command;
    }

    public void setKeyReleasedCommand(Command command) {
        this.keyReleasedCommand = command;
    }

    public void setOctaveChangeCommand(Command command) {
        this.octaveChangeCommand = command;
    }

    public Note getNotePressed() {
        return lastKeyPressed;
    }
}
