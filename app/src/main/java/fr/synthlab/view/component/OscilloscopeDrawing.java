package fr.synthlab.view.component;

import fr.synthlab.model.module.oscilloscope.ModuleSCOP;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.logging.Logger;

/**
 * Created by corentin on 01/02/16.
 */
public class OscilloscopeDrawing extends Pane {
    private static final Logger LOGGER = Logger.getLogger(OscilloscopeDrawing.class.getName());

    private ModuleSCOP osc;
    private SwingNode swingNode;

    /*
    public OscilloscopeDrawing(ModuleSCOP osc) {
        super();
        init();
        setModuleOscilloscope(osc);
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

    public final void setModuleOscilloscope(final ModuleSCOP oscilloscope) {
        osc = oscilloscope;
        swingNode.setContent(osc.getOscillatorJComponent());
        swingNode.setMouseTransparent(true);
        //swingNode.getContent().revalidate();
        //swingNode.getContent().repaint();
    }
}
