package fr.synthlab.view.module.output;

import fr.synthlab.view.component.Knob;
import javafx.css.Styleable;
import javafx.event.EventTarget;
import javafx.fxml.Initializable;

import java.io.*;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by miow on 3/1/16.
 */
public interface ViewModuleOUT
        extends EventTarget, Styleable, Serializable, Initializable {
   Knob getPicker();

   void setVolumeCommand(Runnable newVolume);

   void setMuteCommand(Runnable mute);

   boolean isMute();

   void setRecordCommand(Runnable record);

   boolean isRecording();

   void setIsRecording(boolean value);

   File getRecordingFile();

   @Override
   void initialize(
         URL location, ResourceBundle resources);

   void writeObject(ObjectOutputStream o)
               throws IOException;

   void readObject(ObjectInputStream o)
           throws IOException, ClassNotFoundException;
}
