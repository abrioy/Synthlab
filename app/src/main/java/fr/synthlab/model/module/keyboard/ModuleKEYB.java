package fr.synthlab.model.module.keyboard;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;

import java.util.Collection;


public interface ModuleKEYB extends Module {
	/**
	 * Getter on ports output.
	 *
	 * @return Keyboard port
	 */
	@Override
	Collection<Port> getPorts();

	/**
	 * Start keyboard.
	 */
	@Override
	void start();

	/**
	 * Stop keyboard.
	 */
	@Override
	void stop();

	/**
	 * Inherit method.
	 */
	@Override
	void update();

	/**
	 * @return Type of this module
	 */
	@Override
	ModuleType getType();

	/**
	 * Change keyboard's octave.
	 *
	 * @param newOctave New octave value
	 */
	void changeOctave(int newOctave);

	/**
	 * Method to compute and send the new frequency in the oscilloscope.
	 *
	 * @param n New note pressed
	 */
	void pressKey(NoteKEYB n);

	/**
	 * Release the currently pressed key.
	 *
	 * @param noteKEYB note release
	 */
	void releaseKey(NoteKEYB noteKEYB);

	int getOctave();
}
