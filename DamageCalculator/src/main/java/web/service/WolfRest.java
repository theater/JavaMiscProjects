package main.java.web.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import main.java.web.dto.UserInputParameters;
import main.java.web.dto.WolfArmyResultDto;
import main.java.web.util.DataTransformUtil;
import main.java.wolf.DamageCalculator;
import main.java.wolf.WolfDamageCalculator;

@RestController
public class WolfRest {

    private static final Logger logger = LoggerFactory.getLogger(WolfRest.class);

    @RequestMapping(value = "/rest/wolf/calculate", method = RequestMethod.POST, produces = "application/json")
    public WolfArmyResultDto submit(@RequestBody UserInputParameters inputData,
            BindingResult result, ModelMap model) throws IOException {
        logger.info(inputData.toString());
        DamageCalculator calculator = new WolfDamageCalculator(inputData);
        calculator.calculate();
        return DataTransformUtil.convertWolfDistributionToDTO(calculator.getArmyDistribution());
    }
}
