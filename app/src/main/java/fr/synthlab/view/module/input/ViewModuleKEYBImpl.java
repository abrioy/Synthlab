package fr.synthlab.view.module.input;

import fr.synthlab.model.module.keyboard.NoteKEYB;
import fr.synthlab.model.module.keyboard.NoteKEYBImpl;
import fr.synthlab.view.component.KeyboardKey;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.module.ViewModule;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleKEYBImpl extends ViewModule implements ViewModuleKEYB {
    private static final Logger LOGGER
            = Logger.getLogger(ViewModuleKEYBImpl.class.getName());

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

    public ViewModuleKEYBImpl(final Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleKEYB.fxml");
        this.setId("pane");
    }

    @Override
    public final void initialize(
			final URL location, final ResourceBundle resources) {
        /*
            Mouse pressed events
         */
        cKey.setOnMouseDragged(Event::consume);
        cKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.C;
            keyPressedCommand.run();
            event.consume();
        });
        cSharpKey.setOnMouseDragged(Event::consume);
        cSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.CSharp;
            keyPressedCommand.run();
            event.consume();
        });
        dKey.setOnMouseDragged(Event::consume);
        dKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.D;
            keyPressedCommand.run();
            event.consume();
        });
        dSharpKey.setOnMouseDragged(Event::consume);
        dSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.DSharp;
            keyPressedCommand.run();
            event.consume();
        });
        eKey.setOnMouseDragged(Event::consume);
        eKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.E;
            keyPressedCommand.run();
            event.consume();
        });
        fKey.setOnMouseDragged(Event::consume);
        fKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.F;
            keyPressedCommand.run();
            event.consume();
        });
        fSharpKey.setOnMouseDragged(Event::consume);
        fSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.FSharp;
            keyPressedCommand.run();
            event.consume();
        });
        gKey.setOnMouseDragged(Event::consume);
        gKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.G;
            keyPressedCommand.run();
            event.consume();
        });
        gSharpKey.setOnMouseDragged(Event::consume);
        gSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.GSharp;
            keyPressedCommand.run();
            event.consume();
        });
        aKey.setOnMouseDragged(Event::consume);
        aKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.A;
            keyPressedCommand.run();
            event.consume();
        });
        aSharpKey.setOnMouseDragged(Event::consume);
        aSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.ASharp;
            keyPressedCommand.run();
            event.consume();
        });
        bKey.setOnMouseDragged(Event::consume);
        bKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.B;
            keyPressedCommand.run();
            event.consume();
        });
        cNextOctKey.setOnMouseDragged(Event::consume);
        cNextOctKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYBImpl.C2;
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
                lastKeyReleased = NoteKEYBImpl.fromValue(keysColl.indexOf(key) - 9);
                keyReleasedCommand.run();
            });
            key.setOnMouseExited(event -> {
                lastKeyReleased = NoteKEYBImpl.fromValue(keysColl.indexOf(key) - 9);
                keyReleasedCommand.run();
            });
        }

        octavePicker.valueProperty().addListener(event -> {
            octaveChangeCommand.run();
            updateOctave();
        });

        octaveLabel.setText(getOctave() + "");

        setFocusTraversable(true);
        keyborEnventInit();
    }

    private void keyborEnventInit() {
        setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case Q:
                    lastKeyPressed = NoteKEYBImpl.C;
                    keyPressedCommand.run();
                    break;
                case S:
                    lastKeyPressed = NoteKEYBImpl.D;
                    keyPressedCommand.run();
                    break;
                case D:
                    lastKeyPressed = NoteKEYBImpl.E;
                    keyPressedCommand.run();
                    break;
                case F:
                    lastKeyPressed = NoteKEYBImpl.F;
                    keyPressedCommand.run();
                    break;
                case G:
                    lastKeyPressed = NoteKEYBImpl.G;
                    keyPressedCommand.run();
                    break;
                case H:
                    lastKeyPressed = NoteKEYBImpl.A;
                    keyPressedCommand.run();
                    break;
                case J:
                    lastKeyPressed = NoteKEYBImpl.B;
                    keyPressedCommand.run();
                    break;
                case K:
                    lastKeyPressed = NoteKEYBImpl.C2;
                    keyPressedCommand.run();
                    break;
                case Z:
                    lastKeyPressed = NoteKEYBImpl.CSharp;
                    keyPressedCommand.run();
                    break;
                case E:
                    lastKeyPressed = NoteKEYBImpl.DSharp;
                    keyPressedCommand.run();
                    break;
                case T:
                    lastKeyPressed = NoteKEYBImpl.FSharp;
                    keyPressedCommand.run();
                    break;
                case Y:
                    lastKeyPressed = NoteKEYBImpl.GSharp;
                    keyPressedCommand.run();
                    break;
                case U:
                    lastKeyPressed = NoteKEYBImpl.ASharp;
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
                    lastKeyReleased = NoteKEYBImpl.C;
                    keyReleasedCommand.run();
                    break;
                case S:
                    lastKeyReleased = NoteKEYBImpl.D;
                    keyReleasedCommand.run();
                    break;
                case D:
                    lastKeyReleased = NoteKEYBImpl.E;
                    keyReleasedCommand.run();
                    break;
                case F:
                    lastKeyReleased = NoteKEYBImpl.F;
                    keyReleasedCommand.run();
                    break;
                case G:
                    lastKeyReleased = NoteKEYBImpl.G;
                    keyReleasedCommand.run();
                    break;
                case H:
                    lastKeyReleased = NoteKEYBImpl.A;
                    keyReleasedCommand.run();
                    break;
                case J:
                    lastKeyReleased = NoteKEYBImpl.B;
                    keyReleasedCommand.run();
                    break;
                case K:
                    lastKeyReleased = NoteKEYBImpl.C2;
                    keyReleasedCommand.run();
                    break;
                case Z:
                    lastKeyReleased = NoteKEYBImpl.CSharp;
                    keyReleasedCommand.run();
                    break;
                case E:
                    lastKeyReleased = NoteKEYBImpl.DSharp;
                    keyReleasedCommand.run();
                    break;
                case T:
                    lastKeyReleased = NoteKEYBImpl.FSharp;
                    keyReleasedCommand.run();
                    break;
                case Y:
                    lastKeyReleased = NoteKEYBImpl.GSharp;
                    keyReleasedCommand.run();
                    break;
                case U:
                    lastKeyReleased = NoteKEYBImpl.ASharp;
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

    @Override
	public final int getOctave() {
        return (int) octavePicker.getValue();
    }

    @Override
	public final void setKeyPressedCommand(final Runnable command) {
        this.keyPressedCommand = command;
    }

    @Override
	public final void setKeyReleasedCommand(final Runnable command) {
        this.keyReleasedCommand = command;
    }

    @Override
	public final void setOctaveChangeCommand(final Runnable command) {
        this.octaveChangeCommand = command;
        this.octaveChangeCommand.run();
    }

    @Override
	public final NoteKEYB getNotePressed() {
        return lastKeyPressed;
    }

    @Override
	public final NoteKEYB getLastKeyReleased() {
        return lastKeyReleased;
    }

    @Override
    public final void writeObject(final ObjectOutputStream o)
            throws IOException {
        o.writeDouble(this.octavePicker.getValue());
    }

    @Override
    public final void readObject(final ObjectInputStream o)
            throws IOException, ClassNotFoundException {
        this.octavePicker.setValue(o.readDouble());
    }
}
