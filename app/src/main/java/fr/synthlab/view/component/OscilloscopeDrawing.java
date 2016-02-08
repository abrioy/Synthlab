package fr.synthlab.view.component;

import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.logging.Logger;

/**
 * Created by corentin on 01/02/16.
 */
public class OscilloscopeDrawing extends Pane {
	private static final Logger logger = Logger.getLogger(OscilloscopeDrawing.class.getName());

	ModuleOscilloscope osc;
	SwingNode swingNode;

	/*
	public OscilloscopeDrawing(ModuleOscilloscope osc) {
		super();
		init();
		setModuleOscillo(osc);
	}
	*/

	public OscilloscopeDrawing() {
		super();
		init();
	}

	private void init() {
		swingNode = new SwingNode();
		StackPane pane = new StackPane();
		pane.getChildren().add(swingNode);
		this.getChildren().add(pane);
	}

	public void setModuleOscillo(ModuleOscilloscope osc) {
		this.osc = osc;
		swingNode.setContent(osc.getOscillatorJComponent());
		swingNode.setMouseTransparent(true);
		//swingNode.getContent().revalidate();
		//swingNode.getContent().repaint();
	}

}