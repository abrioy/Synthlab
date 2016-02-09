package fr.synthlab.model.module.keyboard;

/**
 * Created by corentin on 09/02/16.
 */
public enum Note {
    C(3),
    CSharp(4),
    D(5),
    DSharp(6),
    E(7),
    F(8),
    FSharp(9),
    G(10),
    GSharp(11),
    A(0),
    ASharp(1),
    B(2),

    C2(12),
    INCOCT(13),
    DECOCT(14);

    private final int value;

    private Note(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

}
