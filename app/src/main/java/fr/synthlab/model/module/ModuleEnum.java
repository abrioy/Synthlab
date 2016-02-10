package fr.synthlab.model.module;

public enum ModuleEnum {



    VCOA("VCOA", "VCO Type A"),
    VCA("VCA", "VCA"),
    OUT("OUT", "Output"),
    SCOP("SCOP", "Oscilloscope"),
    REP("REP", "Repeater"),
    EG("EG", "Envelope Generator"),
	VCFLP("VCFLP", "VCF (LP)"),
    VCFHP("VCFHP", "VCF (HP)"),
    KEYB("KEYB", "Keyboard"),
    MIX("MIX", "MIX");


    private String moduleName;
    private String moduleLongName;

    ModuleEnum(String name, String longName) {
        this.moduleName = name;
		this.moduleLongName = longName;
    }

	public String getLongName() {
		return this.moduleLongName;
	}

    public static String getNameFromLong(String longName) {
        for (ModuleEnum m : values()) {
            if (m.getLongName().equals(longName)) {
                return m.moduleName;
            }
        }
        return "";
    }

    @Override
    public String toString() {
        return moduleName;
    }
}
