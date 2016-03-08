package fr.synthlab.model.module.vcoa;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;

import java.util.Collection;

/**
 * Module VCOA.
 */
public interface ModuleVCOA extends Module {
    /**
     * @return the list of port of the VCO
     */
    @Override
    Collection<Port> getPorts();

    /**
     * Start the VCO.
     */
    @Override
    void start();

    /**
     * Stop the VCO.
     */
    @Override
    void stop();

    /**
     * @return the frequency f0 of the VCO
     */
    double getFrequency();

    /**
     * set the frequency of the VCO.
     *
     * @param newFrequency new frequency
     */
    void setFrequency(double newFrequency);

    /**
     * This method is called by the input port fm of the VCO
     * when its state has changed
     * When nothing is connected to the input port of fm,
     * the 4 oscillators has the same frequency f0
     * When something is connected to the input port of fm,
     * we connect the output port of fm filter
     * to input port of each oscillator.
     */
    @Override
    void update();

    @Override
    ModuleType getType();

    /**
     * @return the form.
     */
    ShapeVCOA getShape();

    /**
     * setter of type
     * @param newShape form oscillation.
     */
    void setShape(ShapeVCOA newShape);
}
