

public enum ArmyTier {
    T1,
    T2,
    T3,
    T4,
    T5,
    T6,
    T7,
    T8;

    public int getValue() {
        return ordinal() + 1;
    }
}
