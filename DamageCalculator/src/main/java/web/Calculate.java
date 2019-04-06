package main.java.web;

import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import main.java.calculator.DamageCalculator;
import main.java.calculator.WolfDamageCalculator;
import main.java.config.InputParams;

@RestController
public class Calculate {

    private static String userDataFileLocation = "resources\\InputFile.json";
    private static String configFileLocation = "resources\\Configuration.json";

    private static Logger logger = LoggerFactory.getLogger(Calculate.class);

    @RequestMapping(value = "/calculate", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("index", "inputData", new InputParams());
    }

    @RequestMapping(value = "/calculate", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("inputData") InputParams inputData,
            BindingResult result, ModelMap model) throws IOException {
        // DamageCalculator calculator = new WolfDamageCalculator(userDataFileLocation, configFileLocation).calculate();
        model.addAttribute("castleLevel", inputData.getCastleLevel());
        model.addAttribute("troopsAmount", inputData.getTroopsAmount());
        return model.toString();
    }

    // OLD stuff
    private static final String LINE_SEPARATOR = "<br>";

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