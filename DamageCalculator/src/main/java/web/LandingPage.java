package main.java.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class LandingPage {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView showIndex() {
        return new ModelAndView("index");
    }
}
