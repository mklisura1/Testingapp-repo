package testingapp.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import testingapp.model.User;

import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	
	@RequestMapping(value="/", method = RequestMethod.GET)
	public ModelAndView indexPage(HttpServletRequest request) {
		
		User user = (User)request.getSession().getAttribute("user");
		if(user == null) {
			return new ModelAndView("login");
		}
		
		return new ModelAndView("redirect:/users/profile");
	}		
	
	
	
}
