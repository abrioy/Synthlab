package fr.synthlab.view.module;

import fr.synthlab.model.module.oscilloscope.ModuleOscilloscope;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.StackPane;

import java.util.logging.Logger;

/**
 * Created by corentin on 01/02/16.
 */
public class ViewModuleOscillator extends ViewModule {
	private static final Logger logger = Logger.getLogger(ViewModuleOscillator.class.getName());

	ModuleOscilloscope osc;
	SwingNode swingNode;

	public ViewModuleOscillator(ModuleOscilloscope osc) {
		super();

		this.osc = osc;
		swingNode = new SwingNode();
		swingNode.setContent(osc.getOscillatorJComponent());
		swingNode.setMouseTransparent(true);
		StackPane pane = new StackPane();
		pane.getChildren().add(swingNode);
		this.getChildren().add(pane);
	}
}