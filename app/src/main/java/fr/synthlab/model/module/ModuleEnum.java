package fr.synthlab.model.module;

public enum ModuleEnum {

    VCOA("VCOA", "VCO Type A"),
    VCA("VCA", "VCA"),
    OUT("OUT", "Output"),
    SCOP("SCOP", "Oscilloscope"),
    REP("REP", "Repeater"),
    EG("EG", "Envelope Generator");

    private String moduleName;
    private String moduleLongName;

    ModuleEnum(String name, String longName) {
        this.moduleName = name;
		this.moduleLongName = longName;
    }

	public String getLongName() {
		return this.moduleLongName;
	}

    @Override
    public String toString() {
        return moduleName;
    }
}
