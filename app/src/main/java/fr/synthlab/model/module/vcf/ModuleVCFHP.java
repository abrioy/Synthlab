package fr.synthlab.model.module.vcf;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;


public interface ModuleVCFHP extends Module {
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
     * we connect his output to the HighPass filter
     * when the fm input port is disconnected,
     * we set the frequency f0 to the HighPass filter.
     */
    void update();

    /**
     * @return the type of the module
     */
    ModuleType getType();
}
