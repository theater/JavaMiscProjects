package main.java.web;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import main.java.calculator.Army;
import main.java.calculator.DamageCalculator;
import main.java.calculator.WolfDamageCalculator;

@RestController
public class CalculationResult {

    private static final String LINE_SEPARATOR = "<br>";
    private static String userDataFileLocation = "resources\\InputFile.json";
    private static String configFileLocation = "resources\\Configuration.json";

    @RequestMapping("/result")
    public String index() throws IOException {
        DamageCalculator calculator = new WolfDamageCalculator(userDataFileLocation, configFileLocation).calculate();

        StringBuilder sb = new StringBuilder();
        sb.append("<HTML>");
        sb.append("Initial capacity: " + calculator.getInputParameters().troopsAmount + LINE_SEPARATOR);
        sb.append("Calculated capacity: " + calculator.getCalculatedMarchCapacity() + LINE_SEPARATOR);

        sb.append("<TABLE>");
        sb.append("<TR>");
        sb.append("<TH>Army type</TH>");
        sb.append("<TH>Troops to send</TH>");
        sb.append("</TR>");
        for (Army army : calculator.getArmyDistribution()) {
            sb.append("<TR>");
            sb.append("<TD>").append(army.toString()).append("</TD>");
            sb.append("<TD>").append(army.getTroopsNumber()).append("</TD>");
            sb.append("</TR>");
        }
        sb.append("</TABLE>");
        sb.append(calculator.totalDamageToString());

        sb.append("</HTML>");
        return sb.toString();
    }
}