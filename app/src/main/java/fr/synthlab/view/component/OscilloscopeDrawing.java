package fr.synthlab.view.component;

import fr.synthlab.model.module.oscilloscope.ModuleSCOP;
import javafx.embed.swing.SwingNode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.logging.Logger;

/**
 * Draw oscilloscope.
 */
public class OscilloscopeDrawing extends Pane {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(OscilloscopeDrawing.class.getName());

    /**
     * model module.
     */
    private ModuleSCOP osc;
    /**
     * swing node.
     */
    private SwingNode swingNode;

    /*
    public OscilloscopeDrawing(ModuleSCOP osc) {
        super();
        init();
        setModuleOscilloscope(osc);
    }
    */

    /**
     * constructor.
     */
    public OscilloscopeDrawing() {
        super();
        init();
    }

    /**
     * init view.
     */
    private void init() {
        swingNode = new SwingNode();
        StackPane pane = new StackPane();
        pane.getChildren().add(swingNode);
        this.getChildren().add(pane);
    }

    /**
     * setter on model module.
     * @param oscilloscope to set
     */
    public final void setModuleOscilloscope(final ModuleSCOP oscilloscope) {
        osc = oscilloscope;
        swingNode.setContent(osc.getOscillatorJComponent());
        swingNode.setMouseTransparent(true);
        //swingNode.getContent().revalidate();
        //swingNode.getContent().repaint();
    }
}
