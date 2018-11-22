package mainApp.Model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The {@link Zone} Paradox zone.
 * ID is always numeric (1-8 for Evo192)
 * States are taken from cached RAM memory map and parsed.
 *
 * @author Konstantin_Polihronov - Initial contribution
 */
public class Zone extends Entity {
    private boolean isOpened;
    private boolean isTampered;
    private boolean hasLowBattery;

    private static Logger logger = LoggerFactory.getLogger(Zone.class);

    public Zone(int id, String label) {
        super(id, label);
    }

    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(boolean isOpened) {
        this.isOpened = isOpened;
    }

    public boolean isTampered() {
        return isTampered;
    }

    public void setTampered(boolean isTampered) {
        this.isTampered = isTampered;
    }

    public boolean hasLowBattery() {
        return hasLowBattery;
    }

    public void setHasLowBattery(boolean hasLowBattery) {
        this.hasLowBattery = hasLowBattery;
    }
}