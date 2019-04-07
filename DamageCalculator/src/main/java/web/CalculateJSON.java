package main.java.web;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;

import main.java.calculator.DamageCalculator;
import main.java.calculator.WolfDamageCalculator;
import main.java.config.UserInputParameters;

@RestController
public class CalculateJSON {

    private static Logger logger = LoggerFactory.getLogger(CalculateJSON.class);

    @RequestMapping(value = "/calculateJSON", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("calculateJSON", "jsonContent", new String());
    }

    @PostMapping("/calculateJSON")
    public String submit(@RequestParam String jsonContent) throws IOException {
        logger.info("JSON Content:" + jsonContent);
        ObjectMapper mapper = new ObjectMapper();
        UserInputParameters userParameters = mapper.readValue(jsonContent, UserInputParameters.class);
        DamageCalculator calculator = new WolfDamageCalculator(userParameters).calculate();
        return calculator.printToHTMLTable();
    }
}
