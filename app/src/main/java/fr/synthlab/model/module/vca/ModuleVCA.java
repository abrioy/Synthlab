package fr.synthlab.model.module.vca;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;

import java.util.Collection;


public interface ModuleVCA extends Module {
	/**
	 * @return the attenuation in DB
	 */
	double getAttenuation();

	/**
	 * set the attenuation in DB.
	 * @param newAttenuation attenuation new attenuation
	 */
	void setAttenuation(double newAttenuation);

	/**
	 * get the list of ports of the VCA module.
	 * @return VCA ports
	 */
	@Override
	Collection<Port> getPorts();

	/**
	 * start the VCA.
	 */
	@Override
	void start();

	/**
	 * Stop the VCA.
	 */
	@Override
	void stop();

	/**
	 *
	 */
	@Override
	void update();

	/**
	 * @return the type of the module
	 */
	@Override
	ModuleType getType();
}
