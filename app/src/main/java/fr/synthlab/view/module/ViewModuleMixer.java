package fr.synthlab.view.module;

import fr.synthlab.view.Workbench;
import fr.synthlab.view.component.Knob;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Logger;

public class ViewModuleMixer extends ViewModule implements Initializable{
    private static final Logger logger = Logger.getLogger(ViewModuleMixer.class.getName());

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
    public ViewModuleMixer(Workbench workbench) {
        super(workbench);
        this.loadFXML("/gui/fxml/module/ViewModuleMixer.fxml");
        this.setId("pane");
    }

    /**
     * getter on value attenution of input 1.
     *
     * @return value
     */
    public double getAttenuator1() {
        return attenuator1.getValue();
    }

    /**
     * getter on value attenution of input 2.
     *
     * @return value
     */
    public double getAttenuator2() {
        return attenuator2.getValue();
    }

    /**
     * getter on value attenution of input 3.
     *
     * @return value
     */
    public double getAttenuator3() {
        return attenuator3.getValue();
    }

    /**
     * getter on value attenution of input 4.
     *
     * @return value
     */
    public double getAttenuator4() {
        return attenuator4.getValue();
    }

    /**
     * setter command on change attenuation input 1.
     *
     * @param attenuator1Cmd command
     */
    public void setAttenuator1Cmd(Runnable attenuator1Cmd) {
        this.attenuator1Cmd = attenuator1Cmd;
    }

    /**
     * setter command on change attenuation input 2.
     *
     * @param attenuator2Cmd command
     */
    public void setAttenuator2Cmd(Runnable attenuator2Cmd) {
        this.attenuator2Cmd = attenuator2Cmd;
    }

    /**
     * setter command on change attenuation input 3.
     *
     * @param attenuator3Cmd command
     */
    public void setAttenuator3Cmd(Runnable attenuator3Cmd) {
        this.attenuator3Cmd = attenuator3Cmd;
    }

    /**
     * setter command on change attenuation input 4.
     *
     * @param attenuator4Cmd command
     */
    public void setAttenuator4Cmd(Runnable attenuator4Cmd) {
        this.attenuator4Cmd = attenuator4Cmd;
    }

    /**
     * initialise command
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
	public void writeObject(ObjectOutputStream o) throws IOException {
		o.writeDouble(attenuator1.getValue());
		o.writeDouble(attenuator2.getValue());
		o.writeDouble(attenuator3.getValue());
		o.writeDouble(attenuator4.getValue());
	}

	@Override
	public void readObject(ObjectInputStream o) throws IOException, ClassNotFoundException {
		attenuator1.setValue(o.readDouble());
		attenuator2.setValue(o.readDouble());
		attenuator3.setValue(o.readDouble());
		attenuator4.setValue(o.readDouble());
	}
}
