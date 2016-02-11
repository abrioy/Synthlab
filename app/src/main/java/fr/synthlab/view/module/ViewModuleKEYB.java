package fr.synthlab.view.module;

import fr.synthlab.model.module.keyboard.Note;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.KEYBKey;
import fr.synthlab.view.component.Knob;
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

    Runnable keyPressedCommand;
    Runnable keyReleasedCommand;
    Runnable octaveChangeCommand;
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
            keyPressedCommand.run();
        });
        CSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.CSharp;
            keyPressedCommand.run();
        });
        DKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.D;
            keyPressedCommand.run();
        });
        DSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.DSharp;
            keyPressedCommand.run();
        });
        EKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.E;
            keyPressedCommand.run();
        });
        FKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.F;
            keyPressedCommand.run();
        });
        FSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.FSharp;
            keyPressedCommand.run();
        });
        GKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.G;
            keyPressedCommand.run();
        });
        GSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.GSharp;
            keyPressedCommand.run();
        });
        AKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.A;
            keyPressedCommand.run();
        });
        ASharpKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.ASharp;
            keyPressedCommand.run();
        });
        BKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.B;
            keyPressedCommand.run();
        });
        CNextOctKey.setOnMousePressed(event -> {
            lastKeyPressed = Note.C2;
            keyPressedCommand.run();
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
                keyReleasedCommand.run();
            });

            key.setOnMouseExited(event -> {
                keyReleasedCommand.run();
            });
        }

        octavePicker.valueProperty().addListener(event -> {
            octaveChangeCommand.run();
            updateOctave();
        });

        octaveLabel.setText(getOctave() + "");

        this.setFocusTraversable(true);
        this.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Q:
                    lastKeyPressed = Note.C;
                    keyPressedCommand.run();
                    break;
                case S:
                    lastKeyPressed = Note.D;
                    keyPressedCommand.run();
                    break;
                case D:
                    lastKeyPressed = Note.E;
                    keyPressedCommand.run();
                    break;
                case F:
                    lastKeyPressed = Note.F;
                    keyPressedCommand.run();
                    break;
                case G:
                    lastKeyPressed = Note.G;
                    keyPressedCommand.run();
                    break;
                case H:
                    lastKeyPressed = Note.A;
                    keyPressedCommand.run();
                    break;
                case J:
                    lastKeyPressed = Note.B;
                    keyPressedCommand.run();
                    break;
                case K:
                    lastKeyPressed = Note.C2;
                    keyPressedCommand.run();
                    break;
                case Z:
                    lastKeyPressed = Note.CSharp;
                    keyPressedCommand.run();
                    break;
                case E:
                    lastKeyPressed = Note.DSharp;
                    keyPressedCommand.run();
                    break;
                case T:
                    lastKeyPressed = Note.FSharp;
                    keyPressedCommand.run();
                    break;
                case Y:
                    lastKeyPressed = Note.GSharp;
                    keyPressedCommand.run();
                    break;
                case U:
                    lastKeyPressed = Note.ASharp;
                    keyPressedCommand.run();
                    break;
                case X:
                    octavePicker.setValue(octavePicker.getValue() + 1);
                    break;
                case W:
                    octavePicker.setValue(octavePicker.getValue() - 1);
                    break;
                default:
                    break;
            }
            event.consume();
        });

        this.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case Q:
                case S:
                case D:
                case F:
                case G:
                case H:
                case J:
                case K:
                case Z:
                case E:
                case T:
                case Y:
                case U:
                    keyReleasedCommand.run();
                default:
                    break;
            }
            event.consume();
        });
    }

    private void updateOctave() {
        octaveLabel.setText(getOctave() + "");
    }

    public int getOctave() {
        return (int) octavePicker.getValue();
    }

    public void setKeyPressedCommand(Runnable command) {
        this.keyPressedCommand = command;
    }

    public void setKeyReleasedCommand(Runnable command) {
        this.keyReleasedCommand = command;
    }

    public void setOctaveChangeCommand(Runnable command) {
        this.octaveChangeCommand = command;
    }

    public Note getNotePressed() {
        return lastKeyPressed;
    }
}
