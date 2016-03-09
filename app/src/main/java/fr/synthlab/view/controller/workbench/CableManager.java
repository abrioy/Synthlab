package fr.synthlab.view.controller.workbench;

import fr.synthlab.view.component.Cable;
import fr.synthlab.view.component.Plug;

import java.util.logging.Logger;

/**
 * Cable Manager.
 */
public class CableManager {
    private static final Logger LOGGER
            = Logger.getLogger(CableManager.class.getName());

    private Workbench workbench;
    public Cable draggedCable;

    public CableManager(final Workbench workbenchParam) {
        this.workbench = workbenchParam;
    }

    /**
     * Handling event when plug is clicked.
     *
     * @param plug Plug clicked
     */
    public final void plugClicked(final Plug plug) {
        Cable connectedCable = plug.getCable();
        if (draggedCable == null) {
            if (connectedCable != null) {
                // We drag the cable that was connected to the plug
                connectedCable.disconnectPlug(plug);
                draggedCable = connectedCable;
            } else {
                // We create a new cable to drag
                draggedCable = new Cable(workbench, plug);
                workbench.getChildren().add(draggedCable);
                draggedCable.update();
            }
        } else {
            if (connectedCable != null) {
                if (draggedCable.getConnectedPlug() != plug) {
                    // Switching dragged cable
                    connectedCable.disconnectPlug(plug);
                    draggedCable.connectPlug(plug);

                    draggedCable.update();
                    draggedCable = connectedCable;
                    // FIXME: Update connectedCable ?
                } else {
                    // Dropping the cable because we clicked twice on the plug
                    dropCable();
                }
            } else {
                draggedCable.connectPlug(plug);
                draggedCable.update();
                draggedCable = null;
            }
        }
    }


    public final void updateCables() {
        workbench.getCables().forEach(Cable::updateCircles);

        workbench.getCables().forEach(cable -> {
            if (draggedCable != cable) {
                cable.update();
            }
        });
    }

    public final void removeCable(final Cable cable) {
        cable.dispose();
        workbench.getChildren().remove(cable);
    }

    public final void dropCable() {
        if (draggedCable != null) {
            removeCable(draggedCable);
            draggedCable = null;
        }
    }
}
