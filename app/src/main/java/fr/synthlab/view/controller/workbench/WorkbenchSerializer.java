package fr.synthlab.view.controller.workbench;

import fr.synthlab.model.module.Module;
import fr.synthlab.model.module.ModuleType;
import fr.synthlab.model.module.port.Port;
import fr.synthlab.view.component.Cable;
import fr.synthlab.view.component.Plug;
import fr.synthlab.view.module.ViewModule;
import fr.synthlab.view.module.ViewModuleFactory;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Serializer for the workbench.
 */
public class WorkbenchSerializer {
    /**
     * Logger.
     */
    private static final Logger LOGGER
            = Logger.getLogger(WorkbenchSerializer.class.getName());

    /**
     * @param module to hash.
     * @return hash code of module
     */
    private static int serializeModuleToUID(final Module module) {
        return System.identityHashCode(module);
    }

    /**
     * serialize and save view module.
     * @param workbench current workbench
     * @param outputStream file for save
     * @throws IOException write or read error
     */
    public static void serializeViewModules(
            final Workbench workbench,
            final ObjectOutputStream outputStream) throws IOException {
        Collection<ViewModule> modules = workbench.getViewModules();

        // Writing the total number of modules
        outputStream.writeInt(modules.size());

        for (ViewModule viewModule : modules) {
            Module module = viewModule.getModule();

            // Writing module type
            ModuleType type = module.getType();
            LOGGER.fine("Saving module of type \"" + type + "\".");
            outputStream.writeObject(type);

            // Writing viewModule position
            Bounds viewModuleBounds = viewModule.getBoundsInParent();
            LOGGER.fine("\tPosition: (" + viewModuleBounds.getMinX()
                    + ", " + viewModuleBounds.getMinY() + ").");
            outputStream.writeDouble(viewModuleBounds.getMinX());
            outputStream.writeDouble(viewModuleBounds.getMinY());

            // Writing ViewModule data
            LOGGER.fine("\tData.");
            viewModule.writeObject(outputStream);

            // Writing ViewModule connections
            // Writing the UID of this module
            int uid = serializeModuleToUID(viewModule.getModule());
            LOGGER.fine("\tId: " + uid);
            outputStream.writeInt(uid);

            // Writing all the ports
            Collection<Plug> plugs = viewModule.getPlugs();
            // Filtering connected ports
            Collection<Plug> connectedPlugs = plugs.stream()
                    .filter(plug -> plug.getPort().isConnected())
                    .collect(Collectors.toList());

            int nbPlugs = connectedPlugs.size();
            outputStream.writeInt(nbPlugs);
            LOGGER.fine("\tConnections (" + nbPlugs + ")");
            for (Plug plug : connectedPlugs) {
                outputStream.writeObject(plug.getName());
                // Storing the ID of the connected module
                Port connectedPort = plug.getPort().getConnected();
                int targetUID = serializeModuleToUID(connectedPort.getModule());
                outputStream.writeInt(targetUID);
                outputStream.writeObject(connectedPort.getName());
                // Storing the color of the connections
                Color cableColor = plug.getCable().getColor();
                outputStream.writeDouble(cableColor.getRed());
                outputStream.writeDouble(cableColor.getGreen());
                outputStream.writeDouble(cableColor.getBlue());
                LOGGER.fine("\t\t" + plug.getName()
                        + "\t-- " + cableColor + "\t--> "
                        + targetUID + "." + connectedPort.getName());
            }
        }
    }

    /**
     * reload a view module.
     * @param workbench current workbench
     * @param inputStream file where module is safe
     * @throws IOException write or read error
     */
    public static void deSerializeViewModules(
            final Workbench workbench,
            final ObjectInputStream inputStream) throws IOException {
        workbench.removeAllModules();

        Map<Integer, ViewModule> moduleList = new HashMap<>();

        /**
         * class to referenced port.
         */
        class PortReference {
            private int parentUID;
            private String name;
            private int connectedUID;
            private String connectedPortName;
            private Color cableColor;

            /**
             * constructor.
             * @param parentUIDInit parent
             * @param nameInit name
             * @param connectedUIDInit connect
             * @param connectedPortNameInit name port connected
             * @param cableColorInit cable color
             */
            PortReference(final int parentUIDInit, final String nameInit,
                          final int connectedUIDInit,
                          final String connectedPortNameInit,
                          final Color cableColorInit) {
                parentUID = parentUIDInit;
                name = nameInit;
                connectedUID = connectedUIDInit;
                connectedPortName = connectedPortNameInit;
                cableColor = cableColorInit;
            }
        }
        Collection<PortReference> portList = new ArrayList<>();

        // Reading the number of modules
        int nbModules = inputStream.readInt();

        for (int i = 0; i < nbModules; i++) {
            try {
                // Reading type
                ModuleType type = (ModuleType) inputStream.readObject();
                LOGGER.fine("Restoring a module of type \"" + type + "\".");

                // Reading position
                double xPos = inputStream.readDouble();
                double yPos = inputStream.readDouble();
                LOGGER.fine("\tPosition: (" + xPos + ", " + yPos + ").");

                // Creating new ViewModule and feeding it the gathered data
                ViewModule viewModule
                        = ViewModuleFactory.createViewModule(type, workbench);
                viewModule.relocate(xPos, yPos);

                // Feeding data to the Module
                LOGGER.fine("\tData.");
                viewModule.readObject(inputStream);

                // Reading connections
                // Adding this module to the global list
                int moduleUID = inputStream.readInt();
                LOGGER.fine("\tId: " + moduleUID);
                moduleList.put(moduleUID, viewModule);

                // Adding this module's port to the list
                int nbPlugs = inputStream.readInt();
                LOGGER.fine("\tFound " + nbPlugs + " connections");
                for (int j = 0; j < nbPlugs; j++) {
                    String plugName = (String) inputStream.readObject();
                    int connectedModuleId = inputStream.readInt();
                    String connectedPortName
                            = (String) inputStream.readObject();
                    double r = inputStream.readDouble();
                    double g = inputStream.readDouble();
                    double b = inputStream.readDouble();
                    Color cableColor = new Color(r, g, b, 1.0d);
                    portList.add(new PortReference(
                            moduleUID, plugName, connectedModuleId,
                            connectedPortName, cableColor));
                    LOGGER.fine("\t\t" + plugName + "\t-- "
                            + cableColor + "\t--> "
                            + connectedModuleId + "."
                            + connectedPortName);
                }
                workbench.addModule(viewModule);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        // Resolving connections
        LOGGER.fine("Restoring " + portList.size() + " connections");
        for (PortReference portReference : portList) {
            ViewModule viewModule = moduleList.get(portReference.parentUID);
            Plug plug = viewModule.getPlugByName(portReference.name);
            ViewModule connectedViewModule = moduleList.get(
                    portReference.connectedUID);
            Plug connectedPlug = connectedViewModule.getPlugByName(
                    portReference.connectedPortName);

            // Checking this port is not already connected
            if (!plug.getPort().isConnected()) {
                LOGGER.fine("\t" + portReference.parentUID
                        + "." + portReference.name + " -- "
                        + portReference.cableColor + " --> "
                        + portReference.connectedUID + "."
                        + portReference.connectedPortName);
                Cable cable = new Cable(workbench, plug, connectedPlug);
                cable.setColor(portReference.cableColor);
                workbench.getChildren().add(cable);
            }
        }
        // Updating cable positions
        workbench.getCables().forEach(Cable::updateCircles);
        workbench.getCables().forEach(Cable::update);

        inputStream.close();
    }
}
