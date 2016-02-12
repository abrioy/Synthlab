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
import java.util.Collection;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleKEYB extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleKEYB.class.getName());

    @FXML
    private KeyboardKey CKey;
    @FXML
	private KeyboardKey CSharpKey;
    @FXML
	private KeyboardKey DKey;
    @FXML
	private KeyboardKey DSharpKey;
    @FXML
	private KeyboardKey EKey;
    @FXML
	private KeyboardKey FKey;
    @FXML
	private KeyboardKey FSharpKey;
    @FXML
	private KeyboardKey GKey;
    @FXML
	private KeyboardKey GSharpKey;
    @FXML
	private KeyboardKey AKey;
    @FXML
	private KeyboardKey ASharpKey;
    @FXML
	private KeyboardKey BKey;
    @FXML
	private KeyboardKey CNextOctKey;

    @FXML
    Knob octavePicker;

    @FXML
    Label octaveLabel;

    Runnable keyPressedCommand;
    Runnable keyReleasedCommand;
    Runnable octaveChangeCommand;
    NoteKEYB lastKeyPressed;

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
        CKey.setOnMouseDragged(Event::consume);
		CKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.C;
            keyPressedCommand.run();
			event.consume();
        });
        CSharpKey.setOnMouseDragged(Event::consume);
		CSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.CSharp;
            keyPressedCommand.run();
			event.consume();
        });
        DKey.setOnMouseDragged(Event::consume);
		DKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.D;
            keyPressedCommand.run();
			event.consume();
        });
        DSharpKey.setOnMouseDragged(Event::consume);
		DSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.DSharp;
            keyPressedCommand.run();
			event.consume();
        });
        EKey.setOnMouseDragged(Event::consume);
		EKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.E;
            keyPressedCommand.run();
			event.consume();
        });
        FKey.setOnMouseDragged(Event::consume);
		FKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.F;
            keyPressedCommand.run();
			event.consume();
        });
        FSharpKey.setOnMouseDragged(Event::consume);
		FSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.FSharp;
            keyPressedCommand.run();
			event.consume();
        });
        GKey.setOnMouseDragged(Event::consume);
		GKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.G;
            keyPressedCommand.run();
			event.consume();
        });
        GSharpKey.setOnMouseDragged(Event::consume);
		GSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.GSharp;
            keyPressedCommand.run();
			event.consume();
        });
        AKey.setOnMouseDragged(Event::consume);
		AKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.A;
            keyPressedCommand.run();
			event.consume();
        });
        ASharpKey.setOnMouseDragged(Event::consume);
		ASharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.ASharp;
            keyPressedCommand.run();
			event.consume();
        });
        BKey.setOnMouseDragged(Event::consume);
		BKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.B;
            keyPressedCommand.run();
			event.consume();
        });
        CNextOctKey.setOnMouseDragged(Event::consume);
		CNextOctKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.C2;
            keyPressedCommand.run();
			event.consume();
        });

        Collection<KeyboardKey> keysColl = new ArrayList<>();
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

        for (KeyboardKey key : keysColl) {
            key.setOnMouseReleased(event -> keyReleasedCommand.run());

            key.setOnMouseExited(event -> keyReleasedCommand.run());
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
                    if(octavePicker.getValue() < 7)
                        octavePicker.setValue(octavePicker.getValue() + 1);
                    break;
                case W:
                    if(octavePicker.getValue() > 0)
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
					event.consume();
                default:
                    break;
            }
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

	@Override
	public void writeObject(ObjectOutputStream o) throws IOException {
		o.writeDouble(this.octavePicker.getValue());
	}

	@Override
	public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
		this.octavePicker.setValue(o.readDouble());
	}
}
