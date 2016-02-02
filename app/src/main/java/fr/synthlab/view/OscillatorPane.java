package fr.synthlab.view;

import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;

/**
 * Created by corentin on 01/02/16.
 */
public class OscillatorPane extends Pane {
    SwingNode swingNode;
	ModuleOscilloscope osc;

    public OscillatorPane(ModuleOscilloscope osc) {
		super();
		swingNode = new SwingNode();
        //swingNode.setContent(osc.getOscillatorJComponent());
        this.getChildren().add(swingNode);
	}
}