package fr.synthlab.view;

/**
 * Created by miow on 2/12/16.
 */
public enum Skin {
	Default("Default"),
	Fire("Fire");


	private static final long serialVersionUID = 1L;

	private String name;

	Skin(String name) {
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public String getPath(){
		return "/gui/fxml/style/skin/"+this.name+".css";
	}

	@Override
	public String toString() {
		return name;
	}
}
