package damage_calculator;

import java.io.IOException;
import java.text.DecimalFormat;

import org.apache.log4j.Logger;

public class WolfDamageCalculator extends DamageCalculator {

    Logger logger = Logger.getLogger(WolfDamageCalculator.class);


    public WolfDamageCalculator(String userInputFilePath, String configurationFilePath) throws IOException {
        super(userInputFilePath, configurationFilePath);
    }

    @Override
    protected void printTotalDamage() {
        logger.info("Total damage:\t" + new DecimalFormat("#").format((totalArmyDamage / 1000) * 30));
    }
}
