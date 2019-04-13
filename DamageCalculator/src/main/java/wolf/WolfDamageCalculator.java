package main.java.wolf;

import java.io.IOException;
import java.text.DecimalFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import main.java.config.UserInputParameters;


public class WolfDamageCalculator extends DamageCalculator {

    Logger logger = LoggerFactory.getLogger(WolfDamageCalculator.class);

    public WolfDamageCalculator(UserInputParameters parameters) throws IOException {
        super(parameters);
    }

    @Override
    public String totalDamageToString() {
        return "Total damage:\t" + new DecimalFormat("#").format((totalArmyDamage / 1000) * 30) + "\n";
    }
}
