package fr.synthlab.view.module.filter;

import fr.synthlab.view.controller.Workbench;
import fr.synthlab.view.component.Knob;
import fr.synthlab.view.module.ViewModule;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleMIX extends ViewModule implements Initializable {
    private static final Logger LOGGER
            = Logger.getLogger(ViewModuleMIX.class.getName());

    /**
     * attenuator input 1.
     */
    @FXML
    private Knob attenuator1;

    /**
     * attenuator input 2.
     */
    @FXML
    private Knob attenuator2;

    /**
     * attenuator input 3.
     */
    @FXML
    private Knob attenuator3;

    /**
     * attenuator input 4.
     */
    @FXML
    private Knob attenuator4;
    /**
     * command execute on change attenuation input 1.
     */
    private Runnable attenuator1Cmd;
    /**
     * command execute on change attenuation input 2.
     */
    private Runnable attenuator2Cmd;
    /**
     * command execute on change attenuation input 3.
     */
    private Runnable attenuator3Cmd;
    /**
     * command execute on change attenuation input 4.
     */
    private Runnable attenuator4Cmd;

    /**
     * constructor.
     *
     * @param workbench the workbench
     */
    public ViewModuleMIX(final Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleMixer.fxml");
        this.setId("pane");
    }

    /**
     * getter on value attenution of input 1.
     *
     * @return value
     */
    public final double getAttenuator1() {
        return attenuator1.getValue();
    }

    /**
     * getter on value attenution of input 2.
     *
     * @return value
     */
    public final double getAttenuator2() {
        return attenuator2.getValue();
    }

    /**
     * getter on value attenution of input 3.
     *
     * @return value
     */
    public final double getAttenuator3() {
        return attenuator3.getValue();
    }

    /**
     * getter on value attenution of input 4.
     *
     * @return value
     */
    public final double getAttenuator4() {
        return attenuator4.getValue();
    }

    /**
     * setter command on change attenuation input 1.
     *
     * @param newAttenuator1Cmd command
     */
    public final void setAttenuator1Cmd(final Runnable newAttenuator1Cmd) {
        attenuator1Cmd = newAttenuator1Cmd;
        attenuator1Cmd.run();
    }

    /**
     * setter command on change attenuation input 2.
     *
     * @param newAttenuator2Cmd command
     */
    public final void setAttenuator2Cmd(final Runnable newAttenuator2Cmd) {
        attenuator2Cmd = newAttenuator2Cmd;
        attenuator2Cmd.run();
    }

    /**
     * setter command on change attenuation input 3.
     *
     * @param newAttenuator3Cmd command
     */
    public final void setAttenuator3Cmd(final Runnable newAttenuator3Cmd) {
        attenuator3Cmd = newAttenuator3Cmd;
        attenuator3Cmd.run();
    }

    /**
     * setter command on change attenuation input 4.
     *
     * @param newAttenuator4Cmd command
     */
    public final void setAttenuator4Cmd(final Runnable newAttenuator4Cmd) {
        attenuator4Cmd = newAttenuator4Cmd;
        attenuator4Cmd.run();
    }

    /**
     * initialise command.
     *
     * @param location  URL
     * @param resources ResourceBundle
     */
    @Override
    public final void initialize(
            final URL location, final ResourceBundle resources) {
        attenuator1.valueProperty().addListener(event -> {
            attenuator1Cmd.run();
        });
        attenuator2.valueProperty().addListener(event -> {
            attenuator2Cmd.run();
        });
        attenuator3.valueProperty().addListener(event -> {
            attenuator3Cmd.run();
        });
        attenuator4.valueProperty().addListener(event -> {
            attenuator4Cmd.run();
        });
    }

    @Override
    public final void writeObject(final ObjectOutputStream o)
            throws IOException {
        o.writeDouble(attenuator1.getValue());
        o.writeDouble(attenuator2.getValue());
        o.writeDouble(attenuator3.getValue());
        o.writeDouble(attenuator4.getValue());
    }

    @Override
    public final void readObject(final ObjectInputStream o)
            throws IOException, ClassNotFoundException {
        attenuator1.setValue(o.readDouble());
        attenuator2.setValue(o.readDouble());
        attenuator3.setValue(o.readDouble());
        attenuator4.setValue(o.readDouble());
    }
}
