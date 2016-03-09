package fr.synthlab.model.module;

/**
 * Singleton for different module type.
 */
public enum ModuleType {
    /**
     * VCOA.
     */
    VCOA("VCOA", "VCO Type A"),
    /**
     * VCA.
     */
    VCA("VCA", "VCA"),
    /**
     * Out.
     */
    OUT("OUT", "Audio Output"),
    /**
     * Scop.
     */
    SCOP("SCOP", "Oscilloscope"),
    /**
     * Rep.
     */
    REP("REP", "Repeater"),
    /**
     * EG.
     */
    EG("EG", "Envelope Generator"),
    /**
     * VCFLP.
     */
    VCFLP("VCFLP", "VCF (LP)"),
    /**
     * VCFHP.
     */
    VCFHP("VCFHP", "VCF (HP)"),
    /**
     * Keyboard.
     */
    KEYB("KEYB", "Keyboard"),
    /**
     * mix.
     */
    MIX("MIX", "Mixer"),
    /**
     * white noise.
     */
    BRUI("BRUI", "White Noise"),
    /**
     * Sequencer.
     */
    SEQ("SEQ", "Sequencer");

    /**
     * serial.
     */
    private static final long serialVersionUID = 1L;

    /**
     * name of module.
     */
    private String moduleName;
    /**
     * complete name of module.
     */
    private String moduleLongName;

    /**
     * constructor.
     * @param name of module
     * @param longName complete name
     */
    ModuleType(final String name, final String longName) {
        moduleName = name;
        moduleLongName = longName;
    }

    /**
     * getter by long name.
     * @param longName module to found
     * @return module type
     */
    public static String getNameFromLong(final String longName) {
        for (ModuleType m : values()) {
            if (m.getLongName().equals(longName)) {
                return m.moduleName;
            }
        }
        return "";
    }

    /**
     * @return the complete name
     */
    public String getLongName() {
        return this.moduleLongName;
    }

    /**
     * transform in string.
     * @return it is the name of module
     */
    @Override
    public String toString() {
        return moduleName;
    }
}
