package fr.synthlab.view.module;

import fr.synthlab.model.module.keyboard.NoteKEYB;
import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.KEYBKey;
import fr.synthlab.view.component.Knob;
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
        CKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.C;
            keyPressedCommand.run();
        });
        CSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.CSharp;
            keyPressedCommand.run();
        });
        DKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.D;
            keyPressedCommand.run();
        });
        DSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.DSharp;
            keyPressedCommand.run();
        });
        EKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.E;
            keyPressedCommand.run();
        });
        FKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.F;
            keyPressedCommand.run();
        });
        FSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.FSharp;
            keyPressedCommand.run();
        });
        GKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.G;
            keyPressedCommand.run();
        });
        GSharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.GSharp;
            keyPressedCommand.run();
        });
        AKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.A;
            keyPressedCommand.run();
        });
        ASharpKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.ASharp;
            keyPressedCommand.run();
        });
        BKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.B;
            keyPressedCommand.run();
        });
        CNextOctKey.setOnMousePressed(event -> {
            lastKeyPressed = NoteKEYB.C2;
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

    public NoteKEYB getNotePressed() {
        return lastKeyPressed;
    }

	@Override
	public void writeObject(ObjectOutputStream o) throws IOException {

	}

	@Override
	public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {

	}
}
