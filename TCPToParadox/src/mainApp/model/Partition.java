package mainApp.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link Partition} Paradox partition states. Retrieved and parsed from RAM memory responses.
 *
 * @author Konstantin_Polihronov - Initial contribution
 */
/**
 * The {@link Partition} Paradox partition.
 * ID is always numeric (1-8 for Evo192)
 *
 * @author Konstantin_Polihronov - Initial contribution
 */
public class Partition extends Entity {

    private static Logger logger = LoggerFactory.getLogger(Partition.class);

    private PartitionState state = new PartitionState();

    public Partition(int id, String label) {
        super(id, label);
    }

    public PartitionState getState() {
        return state;
    }

    public Partition setState(PartitionState state) {
        this.state = state;
        return this;
    }
}
