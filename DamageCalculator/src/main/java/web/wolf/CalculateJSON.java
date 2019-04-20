package main.java.web.wolf;

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

import main.java.web.dto.UserInputParameters;
import main.java.wolf.DamageCalculator;
import main.java.wolf.WolfDamageCalculator;

@RestController
public class CalculateJSON {

    private static Logger logger = LoggerFactory.getLogger(CalculateJSON.class);

    @RequestMapping(value = "/wolf/calculateJSON", method = RequestMethod.GET)
    public ModelAndView showForm() {
        return new ModelAndView("calculateJSON", "jsonContent", new String());
    }

    @PostMapping("/wolf/calculateJSON")
    public String submit(@RequestParam String jsonContent) throws IOException {
        logger.info("JSON Content:" + jsonContent);
        ObjectMapper mapper = new ObjectMapper();
        UserInputParameters userParameters1 = mapper.readValue(jsonContent, UserInputParameters.class);
        DamageCalculator calculator = new WolfDamageCalculator(userParameters1).calculate();




        return calculator.printToHTMLTable();
    }
}
