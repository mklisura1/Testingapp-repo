package testingapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
@RequestMapping("/map")
public class MapController {
 
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView showMyLocation(){
		ModelAndView modelAndView = new ModelAndView("mapa");
        return modelAndView;
    }
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public ModelAndView locationsOfUser(){
		ModelAndView modelAndView = new ModelAndView("mapview");
        return modelAndView;
    }
	
}