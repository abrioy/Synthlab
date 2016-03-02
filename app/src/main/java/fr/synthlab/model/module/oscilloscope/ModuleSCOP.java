package fr.synthlab.model.module.oscilloscope;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;

import javax.swing.*;
import java.util.Collection;


public interface ModuleSCOP extends Module {
    /**
     * Getter on ports input and output.
     *
     * @return Scope port
     */
    @Override
    Collection<Port> getPorts();

    /**
     * Start scope.
     */
    @Override
    void start();

    /**
     * Stop scope.
     */
    @Override
    void stop();

    /**
     * Inherit method.
     */
    @Override
    void update();

    /**
     * Changes the scale (i.e. the zoom) of the graphical output of the module.
     *
     * @param scale new scale to display the oscilloscope
     */
    void setScale(int scale);

    int getScale();

    @Override
    ModuleType getType();

    /**
     * Getter on the Scope panel.
     *
     * @return JComponent that displays the scope
     */
    JComponent getOscillatorJComponent();
}
