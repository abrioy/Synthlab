package fr.synthlab.model.module;

/**
 * Singleton for different module type.
 */
public enum ModuleType {
    VCOA("VCOA", "VCO Type A"),
    VCA("VCA", "VCA"),
    OUT("OUT", "Audio Output"),
    SCOP("SCOP", "Oscilloscope"),
    REP("REP", "Repeater"),
    EG("EG", "Envelope Generator"),
    VCFLP("VCFLP", "VCF (LP)"),
    VCFHP("VCFHP", "VCF (HP)"),
    KEYB("KEYB", "Keyboard"),
    MIX("MIX", "Mixer"),
    BRUI("BRUI", "White Noise"),
    SEQ("SEQ", "Sequencer");

    private static final long serialVersionUID = 1L;

    private String moduleName;
    private String moduleLongName;

    ModuleType(final String name, final String longName) {
        moduleName = name;
        moduleLongName = longName;
    }

    public static String getNameFromLong(final String longName) {
        for (ModuleType m : values()) {
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
