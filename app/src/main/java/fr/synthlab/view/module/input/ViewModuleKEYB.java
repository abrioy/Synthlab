package fr.synthlab.view.module.input;

import fr.synthlab.model.module.keyboard.NoteKEYB;
import fr.synthlab.view.component.KeyboardKey;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.module.ViewModule;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleKEYB extends ViewModule implements Initializable {
    private static final Logger LOGGER = Logger.getLogger(ViewModuleKEYB.class.getName());

    @FXML
    private KeyboardKey cKey;
    @FXML
    private KeyboardKey cSharpKey;
    @FXML
    private KeyboardKey dKey;
    @FXML
    private KeyboardKey dSharpKey;
    @FXML
    private KeyboardKey eKey;
    @FXML
    private KeyboardKey fKey;
    @FXML
    private KeyboardKey fSharpKey;
    @FXML
    private KeyboardKey gKey;
    @FXML
    private KeyboardKey gSharpKey;
    @FXML
    private KeyboardKey aKey;
    @FXML
    private KeyboardKey aSharpKey;
    @FXML
    private KeyboardKey bKey;
    @FXML
    private KeyboardKey cNextOctKey;

    @FXML
    private Knob octavePicker;

    @FXML
    private Label octaveLabel;

    private Runnable keyPressedCommand;
    private Runnable keyReleasedCommand;
    private Runnable octaveChangeCommand;
    private NoteKEYB lastKeyPressed, lastKeyReleased;

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
        cKey.setOnMouseDragged(Event::consume);
        cKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.C;
            keyPressedCommand.run();
            event.consume();
        });
        cSharpKey.setOnMouseDragged(Event::consume);
        cSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.CSharp;
            keyPressedCommand.run();
            event.consume();
        });
        dKey.setOnMouseDragged(Event::consume);
        dKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.D;
            keyPressedCommand.run();
            event.consume();
        });
        dSharpKey.setOnMouseDragged(Event::consume);
        dSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.DSharp;
            keyPressedCommand.run();
            event.consume();
        });
        eKey.setOnMouseDragged(Event::consume);
        eKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.E;
            keyPressedCommand.run();
            event.consume();
        });
        fKey.setOnMouseDragged(Event::consume);
        fKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.F;
            keyPressedCommand.run();
            event.consume();
        });
        fSharpKey.setOnMouseDragged(Event::consume);
        fSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.FSharp;
            keyPressedCommand.run();
            event.consume();
        });
        gKey.setOnMouseDragged(Event::consume);
        gKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.G;
            keyPressedCommand.run();
            event.consume();
        });
        gSharpKey.setOnMouseDragged(Event::consume);
        gSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.GSharp;
            keyPressedCommand.run();
            event.consume();
        });
        aKey.setOnMouseDragged(Event::consume);
        aKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.A;
            keyPressedCommand.run();
            event.consume();
        });
        aSharpKey.setOnMouseDragged(Event::consume);
        aSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.ASharp;
            keyPressedCommand.run();
            event.consume();
        });
        bKey.setOnMouseDragged(Event::consume);
        bKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.B;
            keyPressedCommand.run();
            event.consume();
        });
        cNextOctKey.setOnMouseDragged(Event::consume);
        cNextOctKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.C2;
            keyPressedCommand.run();
            event.consume();
        });

        List<KeyboardKey> keysColl = new ArrayList<>();
        keysColl.add(cKey);
        keysColl.add(cSharpKey);
        keysColl.add(dKey);
        keysColl.add(dSharpKey);
        keysColl.add(eKey);
        keysColl.add(fKey);
        keysColl.add(fSharpKey);
        keysColl.add(gKey);
        keysColl.add(gSharpKey);
        keysColl.add(aKey);
        keysColl.add(aSharpKey);
        keysColl.add(bKey);
        keysColl.add(cNextOctKey);

        for (KeyboardKey key : keysColl) {
            // Ugly, but it saves a lot of space
            // The "-9" comes from the fact that NoteKEYB.C has a value of -9.
            key.setOnMouseReleased(event -> {
                lastKeyReleased = NoteKEYB.fromValue(keysColl.indexOf(key) - 9);
                keyReleasedCommand.run();
            });
            key.setOnMouseExited(event -> {
                lastKeyReleased = NoteKEYB.fromValue(keysColl.indexOf(key) - 9);
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
                    lastKeyPressed = NoteKEYB.C;
                    keyPressedCommand.run();
                    break;
                case S:
                    lastKeyPressed = NoteKEYB.D;
                    keyPressedCommand.run();
                    break;
                case D:
                    lastKeyPressed = NoteKEYB.E;
                    keyPressedCommand.run();
                    break;
                case F:
                    lastKeyPressed = NoteKEYB.F;
                    keyPressedCommand.run();
                    break;
                case G:
                    lastKeyPressed = NoteKEYB.G;
                    keyPressedCommand.run();
                    break;
                case H:
                    lastKeyPressed = NoteKEYB.A;
                    keyPressedCommand.run();
                    break;
                case J:
                    lastKeyPressed = NoteKEYB.B;
                    keyPressedCommand.run();
                    break;
                case K:
                    lastKeyPressed = NoteKEYB.C2;
                    keyPressedCommand.run();
                    break;
                case Z:
                    lastKeyPressed = NoteKEYB.CSharp;
                    keyPressedCommand.run();
                    break;
                case E:
                    lastKeyPressed = NoteKEYB.DSharp;
                    keyPressedCommand.run();
                    break;
                case T:
                    lastKeyPressed = NoteKEYB.FSharp;
                    keyPressedCommand.run();
                    break;
                case Y:
                    lastKeyPressed = NoteKEYB.GSharp;
                    keyPressedCommand.run();
                    break;
                case U:
                    lastKeyPressed = NoteKEYB.ASharp;
                    keyPressedCommand.run();
                    break;
                case X:
                    if (octavePicker.getValue() < 7) {
                        octavePicker.setValue(octavePicker.getValue() + 1);
                    }
                    break;
                case W:
                    if (octavePicker.getValue() > 0) {
                        octavePicker.setValue(octavePicker.getValue() - 1);
                    }
                    break;
                default:
                    break;
            }
            event.consume();
        });

        this.setOnKeyReleased(event -> {
            switch (event.getCode()) {
                case Q:
                    lastKeyReleased = NoteKEYB.C;
                    keyReleasedCommand.run();
                    break;
                case S:
                    lastKeyReleased = NoteKEYB.D;
                    keyReleasedCommand.run();
                    break;
                case D:
                    lastKeyReleased = NoteKEYB.E;
                    keyReleasedCommand.run();
                    break;
                case F:
                    lastKeyReleased = NoteKEYB.F;
                    keyReleasedCommand.run();
                    break;
                case G:
                    lastKeyReleased = NoteKEYB.G;
                    keyReleasedCommand.run();
                    break;
                case H:
                    lastKeyReleased = NoteKEYB.A;
                    keyReleasedCommand.run();
                    break;
                case J:
                    lastKeyReleased = NoteKEYB.B;
                    keyReleasedCommand.run();
                    break;
                case K:
                    lastKeyReleased = NoteKEYB.C2;
                    keyReleasedCommand.run();
                    break;
                case Z:
                    lastKeyReleased = NoteKEYB.CSharp;
                    keyReleasedCommand.run();
                    break;
                case E:
                    lastKeyReleased = NoteKEYB.DSharp;
                    keyReleasedCommand.run();
                    break;
                case T:
                    lastKeyReleased = NoteKEYB.FSharp;
                    keyReleasedCommand.run();
                    break;
                case Y:
                    lastKeyReleased = NoteKEYB.GSharp;
                    keyReleasedCommand.run();
                    break;
                case U:
                    lastKeyReleased = NoteKEYB.ASharp;
                    keyReleasedCommand.run();
                    break;
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
        this.octaveChangeCommand.run();
    }

    public NoteKEYB getNotePressed() {
        return lastKeyPressed;
    }

    public NoteKEYB getLastKeyReleased() {
        return lastKeyReleased;
    }


    @Override
    public void writeObject(ObjectOutputStream o) throws IOException {
        o.writeDouble(this.octavePicker.getValue());
    }

    @Override
    public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
        this.octavePicker.setValue(o.readDouble());
    }
}
