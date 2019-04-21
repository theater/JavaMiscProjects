package main.java.web.ui;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UiFive {
	@RequestMapping(value = "/ui", method = RequestMethod.GET)
	public ModelAndView showIndex() {
		return new ModelAndView("index");
	}

	@RequestMapping(value = "/ui/getWolfie", method = RequestMethod.GET)
	public ModelAndView getWolfie() {
		return new ModelAndView("index");
	}
}
