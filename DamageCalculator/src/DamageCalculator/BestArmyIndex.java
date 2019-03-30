

public class BestArmyIndex {

    private ArmyType type;
    private int index = 0;

    public BestArmyIndex(ArmyType type, int index) {
        if (type == null) {
            throw new IllegalArgumentException("Type null is forbidden");
        }
        this.type = type;
        this.index = index;
    }

    public ArmyType getType() {
        return type;
    }

    public int getIndex() {
        return index;
    }
}
