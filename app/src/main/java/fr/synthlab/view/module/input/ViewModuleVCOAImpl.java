package fr.synthlab.view.module.input;

import fr.synthlab.model.module.vcoa.ShapeVCOA;
import fr.synthlab.view.controller.workbench.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.component.Plug;
import fr.synthlab.view.module.ViewModule;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * Implementation module VCOA.
 */
public class ViewModuleVCOAImpl extends ViewModule implements ViewModuleVCOA {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(ViewModuleVCOAImpl.class.getName());
    /**
     * frequency knob.
     */
    @FXML
    private Knob freq;
    /**
     * modulation frequency knob.
     */
    @FXML
    private Knob freqLine;
    /**
     * form of audio.
     */
    @FXML
    private Knob picker;
    /**
     * plug in.
     */
    @FXML
    private Plug in;
    /**
     * plug out.
     */
    @FXML
    private Plug out;
    /**
     * Label frequency.
     */
    @FXML
    private Label frequencyLabel;

    /**
     * command execute on change on knob picker.
     */
    private Runnable changeShapeCommand;
    /**
     * command execute on change frequency.
     */
    private Runnable changeFreqCommand;

    /**
     * constructor.
     * @param workbench current workbench
     */
    public ViewModuleVCOAImpl(final Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleVCO.fxml");
        this.setId("pane");
    }

    @Override
    public final void initialize(
            final URL url, final ResourceBundle resourceBundle) {
        freq.valueProperty().addListener(event -> {
            updateFrequency();
        });

        freqLine.valueProperty().addListener(event -> {
            updateFrequency();
        });

        picker.valueProperty().addListener(event -> {
            changeShapeCommand.run();
        });
        frequencyLabel.setText(getFreq() + " Hz");
    }

    /**
     * update frequency.
     */
    private void updateFrequency() {
        changeFreqCommand.run();
        frequencyLabel.setText(getFreq() + " Hz");
    }

    @Override
    public final void setChangeShapeCommand(final Runnable changeShape) {
        this.changeShapeCommand = changeShape;
        changeShapeCommand.run();
    }

    @Override
    public final void setChangeFreqCommand(final Runnable changeFreq) {
        this.changeFreqCommand = changeFreq;
        changeFreqCommand.run();
    }

    @Override
    public final double getFreq() {
        double f = freq.getValue() + freqLine.getValue();
        if (f < 0) {
            f = 0;
        } else if (f > 22000) {
            f = 22000;
        }
        f = Math.round((f * 10.0d)) / 10.0d;
        return f;
    }

    @Override
    public final ShapeVCOA getSelectedShape() {
        switch ((int) picker.getValue()) {
            case 0:
                return ShapeVCOA.TRIANGLE;
            case 1:
                return ShapeVCOA.SQUARE;
            case 2:
                return ShapeVCOA.SAWTOOTH;
            default:
                return ShapeVCOA.SINE;
        }
    }

    @Override
    public final void writeObject(final ObjectOutputStream o)
            throws IOException {
        o.writeDouble(freq.getValue());
        o.writeDouble(freqLine.getValue());
        o.writeDouble(picker.getValue());
    }

    @Override
    public final void readObject(final ObjectInputStream o)
            throws IOException, ClassNotFoundException {
        freq.setValue(o.readDouble());
        freqLine.setValue(o.readDouble());
        picker.setValue(o.readDouble());
    }
}
