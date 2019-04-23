package main.java.web.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UiFive {

    private static final Logger logger = LoggerFactory.getLogger(UiFive.class);

    @RequestMapping(value = "/ui", method = RequestMethod.GET)
    public ModelAndView showIndex() {
        return new ModelAndView("index");
    }


}
