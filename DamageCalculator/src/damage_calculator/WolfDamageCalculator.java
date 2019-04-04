package damage_calculator;

import java.io.IOException;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class WolfDamageCalculator extends DamageCalculator {

    Logger logger = LoggerFactory.getLogger(WolfDamageCalculator.class);


    public WolfDamageCalculator(String userInputFilePath, String configurationFilePath) throws IOException {
        super(userInputFilePath, configurationFilePath);
    }

    @Override
    protected void printTotalDamage() {
        logger.info("Total damage:\t" + new DecimalFormat("#").format((totalArmyDamage / 1000) * 30));
    }
}
