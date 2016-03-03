package fr.synthlab.model.module.keyboard;

/**
 * Enum different note.
 * @see NoteKEYB
 */
public enum NoteKEYBImpl implements NoteKEYB {
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

    NoteKEYBImpl(final int newValue) {
        value = newValue;
    }

    @Override
    public int getValue() {
        return this.value;
    }

    public static NoteKEYB fromValue(final int val) {
        for (NoteKEYB k : NoteKEYBImpl.values()) {
            if (k.getValue() == val) {
                return k;
            }
        }
        return null;
    }

}
