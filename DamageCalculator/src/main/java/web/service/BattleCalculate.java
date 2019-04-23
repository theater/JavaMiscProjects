package main.java.web.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import main.java.web.dto.UserInputParameters;

@RestController
public class BattleCalculate {

    private static Logger logger = LoggerFactory.getLogger(BattleCalculate.class);

	@RequestMapping(value = "/battle", method = RequestMethod.GET)
	public ModelAndView showInputForm() {
		List<UserInputParameters> userInput = new ArrayList<UserInputParameters>();
		UserInputParameters attackerInput = new UserInputParameters();
		attackerInput.setDescription("Attacker army");
		userInput.add(attackerInput);

		UserInputParameters defenderInput = new UserInputParameters();
		defenderInput.setDescription("Defender army");
		userInput.add(defenderInput);
   	    return new ModelAndView("battleCalculateForm", "battleInputData", userInput);
	}

	 @RequestMapping(value = "/battle", method = RequestMethod.POST)
	    public String submit(@Valid @ModelAttribute("userInput") ArrayList<UserInputParameters> userInput,
	            BindingResult result, ModelMap model) throws IOException {
		 	for (UserInputParameters userInputParameters : userInput) {
		 		String description = userInputParameters.getDescription();
				logger.info(description + "Castle level=" + userInputParameters.getCastleLevel());
			}
	        return "OK";
	    }

}
