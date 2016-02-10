package fr.synthlab.model.module.keyboard;

public enum Note {
    C(-9),
    CSharp(-8),
    D(-7),
    DSharp(-6),
    E(-5),
    F(-4),
    FSharp(-3),
    G(-2),
    GSharp(-1),
    A(0),
    ASharp(1),
    B(2),
    C2(3);

    private final int value;

    private Note(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
