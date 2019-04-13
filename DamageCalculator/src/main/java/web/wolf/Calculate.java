package main.java.web.wolf;

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

import main.java.config.UserInputParameters;
import main.java.wolf.DamageCalculator;
import main.java.wolf.WolfDamageCalculator;

@RestController
public class Calculate {

    private static Logger logger = LoggerFactory.getLogger(Calculate.class);

    @RequestMapping(value = "/wolf/calculate", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("calculateForm", "inputData", new UserInputParameters());
    }

    @RequestMapping(value = "/wolf/calculate", method = RequestMethod.POST)
    public String submit(@Valid @ModelAttribute("inputData") UserInputParameters inputData,
            BindingResult result, ModelMap model) throws IOException {
        DamageCalculator calculator = new WolfDamageCalculator(inputData).calculate();
        return calculator.printToHTMLTable();
    }
}