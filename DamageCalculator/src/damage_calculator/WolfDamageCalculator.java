package damage_calculator;

import java.text.DecimalFormat;

import org.apache.log4j.Logger;

public class WolfDamageCalculator extends DamageCalculator {

    Logger logger = Logger.getLogger(WolfDamageCalculator.class);

    public WolfDamageCalculator(InputParameters inputParameters) {
        super(inputParameters);
    }

    @Override
    protected void printTotalDamage() {
        logger.info("Total damage:\t" + new DecimalFormat("#").format((totalArmyDamage / 1000) * 30));
    }
}
