package fr.synthlab.view;

/**
 * Enum for skin.
 */
public enum Skin {
    /**
     * skin default.
     */
    Default("Default"),
    /**
     * skin Black.
     */
    Black("Black"),
    /**
     * skin Carbon.
     */
    Carbon("Carbon"),
    /**
     * skin Aluminum.
     */
    Aluminum("Aluminum"),
    /**
     * skin Wood.
     */
    Wood("Wood"),
    /**
     * skin Colorful.
     */
    Colorful("Colorful");

    /**
     * serial.
     */
    private static final long serialVersionUID = 1L;

    /**
     * skin name.
     */
    private String name;

    /**
     * constructor.
     * @param newName skin name
     */
    Skin(final String newName) {
        name = newName;
    }

    /**
     * @return skin name
     */
    public String getName() {
        return name;
    }

    /**
     * @return path to skin css
     */
    public String getPath() {
        return "/gui/fxml/style/skin/" + this.name + ".css";
    }

    @Override
    public String toString() {
        return name;
    }
}
