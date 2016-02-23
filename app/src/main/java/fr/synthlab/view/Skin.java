package fr.synthlab.view;

/**
 * Created by miow on 2/12/16.
 */
public enum Skin {
    Default("Default"),
    Black("Black"),
    Carbon("Carbon"),
    Aluminum("Aluminum"),
    Wood("Wood"),
    Colorful("Colorful");


    private static final long serialVersionUID = 1L;

    private String name;

    Skin(String newName) {
        name = newName;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return "/gui/fxml/style/skin/" + this.name + ".css";
    }

    @Override
    public String toString() {
        return name;
    }
}
