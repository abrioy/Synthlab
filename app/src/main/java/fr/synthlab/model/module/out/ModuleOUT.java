package fr.synthlab.model.module.out;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;

import java.io.File;
import java.util.Collection;


public interface ModuleOUT extends Module {
    /**
     * getter on mute.
     *
     * @return true if we play sound
     */
    boolean isMute();

    /**
     * setter on mute start or stop play audio.
     *
     * @param newMute true if play sound
     */
    void setMute(boolean newMute);

    void setRecording(
            boolean newRecording, File pickedFile);

    /**
     * start play audio.
     */
    @Override
    void start();

    /**
     * stop play audio.
     */
    @Override
    void stop();

    /**
     * getter on ports input and output.
     *
     * @return only input port : mono, stereo right and stereo left
     */
    @Override
    Collection<Port> getPorts();

    /**
     * inherit method.
     * nothing to do in the disconnect of port.
     */
    @Override
    void update();

    @Override
    ModuleType getType();

    /**
     * getter on attenuation.
     *
     * @return attenuation
     */
    double getAttenuation();

    /**
     * setter on attenuation.
     *
     * @param attenuation new attenuation
     */
    void setAttenuation(double attenuation);

    boolean isRecording();
}
