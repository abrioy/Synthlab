package fr.synthlab.model.module.vcf;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;


public interface ModuleVCFLP extends Module {
    /**
     * start the module.
     */
    void start();

    /**
     * stop the module.
     */
    void stop();

    /**
     * set the cut frequency.
     * @param f0 the cut frequency
     */
    void setF0(double f0);

    /**
     * get the cut frequency.
     * @return the cut frequency
     */
    double getF0();

    /**
     * this method is called when we connect or disconnect the fm Input port
     * when the fm input port is connected,
     * we connect his output to the LowPass filter
     * when the fm input port is disconnected,
     * we set the frequency f0 to the LowPass filter.
     */
    void update();

    ModuleType getType();

    double getResonance();

    void setResonance(double value);
}
