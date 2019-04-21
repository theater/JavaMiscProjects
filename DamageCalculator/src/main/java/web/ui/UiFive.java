package main.java.web.ui;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import main.java.web.dto.UserInputParameters;

@RestController
public class UiFive {

	private static Logger logger = LoggerFactory.getLogger(UiFive.class);

	@RequestMapping(value = "/ui", method = RequestMethod.GET)
	public ModelAndView showIndex() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/rest/wolf/calculate", method = RequestMethod.POST)
    public String submit(@RequestBody UserInputParameters inputData,
            BindingResult result, ModelMap model) {
		logger.info(inputData.toString());
		return "OK";
	}
}
