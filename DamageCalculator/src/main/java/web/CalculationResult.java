package main.java.web;

import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        sb.append(calculator.printToHTMLTable());
        sb.append("</HTML>");
        return sb.toString();
    }
}