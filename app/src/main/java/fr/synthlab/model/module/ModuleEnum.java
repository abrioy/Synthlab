package fr.synthlab.model.module;

public enum ModuleEnum {

    VCOA("VCOA"),
    OUT("OUT"),
    SCOP("SCOP"),
    REP("REP");

    private String moduleName;

    ModuleEnum(String name) {
        this.moduleName = name;
    }

    @Override
    public String toString() {
        return moduleName;
    }
}
