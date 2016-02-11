package fr.synthlab.model.module;

public enum ModuleTypes {
    VCOA("VCOA", "VCO Type A"),
    VCA("VCA", "VCA"),
    OUT("OUT", "Output"),
    SCOP("SCOP", "Oscilloscope"),
    REP("REP", "Repeater"),
    EG("EG", "Envelope Generator"),
	VCFLP("VCFLP", "VCF (LP)"),
    VCFHP("VCFHP", "VCF (HP)"),
    KEYB("KEYB", "Keyboard"),
    MIX("MIX", "MIX"),
    BRUI("BRUI","White Noise" );

	private static final long serialVersionUID = 1L;

    private String moduleName;
	private String moduleLongName;

    ModuleTypes(String name, String longName) {
        this.moduleName = name;
		this.moduleLongName = longName;
    }

    public static String getNameFromLong(String longName) {
        for (ModuleTypes m : values()) {
            if (m.getLongName().equals(longName)) {
                return m.moduleName;
            }
        }
        return "";
    }

	public String getLongName() {
		return this.moduleLongName;
	}

    @Override
    public String toString() {
        return moduleName;
    }
}
