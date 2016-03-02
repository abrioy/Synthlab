package fr.synthlab.view.module.output;

import fr.synthlab.view.controller.workbench.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.OscilloscopeDrawing;
import fr.synthlab.view.module.ViewModule;
import javafx.fxml.FXML;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleSCOPImpl extends ViewModule implements ViewModuleSCOP {
    private static final Logger LOGGER
            = Logger.getLogger(ViewModuleSCOPImpl.class.getName());

    @FXML
    private OscilloscopeDrawing oscilloscopeDrawing;

    @FXML
    private Knob picker;

    private Runnable pickerCmd;
    private Workbench w;

    public ViewModuleSCOPImpl(final Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleOscilloscope.fxml");
        w = workbench;
    }

    @Override
    public final void initialize(
            final URL url, final ResourceBundle resourceBundle) {
        picker.valueProperty().addListener(event -> {
            pickerCmd.run();
        });
    }

    @Override
    public final void setPickerCommand(final Runnable newPickerCmd) {
        pickerCmd = newPickerCmd;
        pickerCmd.run();
    }

    @Override
    public final int getScale() {
        return (int) picker.getValue();
    }

    @Override
    public final OscilloscopeDrawing getOscilloscopeDrawing() {
        return oscilloscopeDrawing;
    }

    @Override
    public final void writeObject(final ObjectOutputStream o)
            throws IOException {
        o.writeDouble(picker.getValue());
    }

    @Override
    public final void readObject(final ObjectInputStream o)
            throws IOException, ClassNotFoundException {
        picker.setValue(o.readDouble());
    }
}
