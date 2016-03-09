package fr.synthlab.model.module.keyboard;

/**
 * Enum different note.
 * @see NoteKEYB
 */
public enum NoteKEYB {
    /**
     * C.
     */
    C(-9),
    /**
     * CSharp.
     */
    CSharp(-8),
    /**
     * D.
     */
    D(-7),
    /**
     * DSharp.
     */
    DSharp(-6),
    /**
     * E.
     */
    E(-5),
    /**
     * F.
     */
    F(-4),
    /**
     * FSharp.
     */
    FSharp(-3),
    /**
     * G.
     */
    G(-2),
    /**
     * GSharp.
     */
    GSharp(-1),
    /**
     * A.
     */
    A(0),
    /**
     * ASharp.
     */
    ASharp(1),
    /**
     * B.
     */
    B(2),
    /**
     * C2.
     */
    C2(3);

    /**
     * value of note.
     */
    private final int value;

    /**
     * constructor.
     * @param newValue value of note
     */
    NoteKEYB(final int newValue) {
        value = newValue;
    }

    /**
     * getter value.
     * @return value of note
     */
    public int getValue() {
        return value;
    }

    /**
     * getter on note by value.
     * @param val search
     * @return note
     */
    public static NoteKEYB fromValue(final int val) {
        for (NoteKEYB k : NoteKEYB.values()) {
            if (k.getValue() == val) {
                return k;
            }
        }
        return null;
    }

}
