package testingapp.controllers;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import testingapp.model.User;
import testingapp.service.EncryptionService;
import testingapp.service.UserService;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private EncryptionService encryptionService;

	// Logiranje korisnika na sistem
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView loginPage(HttpServletRequest request) {
		
		/*
		 * Dobavi usera
		 * ako nije logovan omoguci mu
		 * ako je vec logovan vrati ga na njegovu stranicu
		 */
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		else 
		{
			return new ModelAndView("redirect:/users/profile");
		}
	}
	
	//Logiranje korisnika na sistem sa mobilne aplikacije
	@RequestMapping(value="/mobile/login/user/{username}/pass/{password}")
	@ResponseBody
	public String checkLogin(@PathVariable String username, @PathVariable String password, HttpServletRequest request) 
	{
		System.out.println("Usao sam u kontroler za mobilnu aplikaciju");
		try 
		{
			System.out.println("Pokusavam dobaviti usera sa username koji si poslao, username: " + username);

			User user = userService.getUserByUsername(username);

			if (user != null) 
			{
				System.out.println("Dobavio sam usera");
				if (encryptionService.validatePassword(password, user.getUser_password_hash())) 
				{
					//Update usera da mu se postavi last login
					
					/*
					 * Date last_login_date = Calendar.getInstance().getTime();
					 
					 * user.setLast_login(last_login_date);
					 * userService.updateUser(user);
					 */
					return "1";

				} 
				else 
				{
					//Ako je pogrijesio password ili username, znaci da nesto nije uredu i vracamo 0
					System.out.println("Nisam dobavio usera jer ne valja password ili username");
					return "0";
				}

			} 
			else 
			{
				//Ako nema tog usera onda vratimo 0
				System.out.println("Nisam dobavio usera jer ga nema");
				return "0";
			}

		} 
		catch (Exception e) 
		{
			System.out.println("Desio se exception");
			e.printStackTrace();
			return "0";
		}
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody ModelAndView home(HttpServletRequest request, @RequestParam("username") String username,
			@RequestParam("password") String password) throws ServletException, IOException {

		ModelAndView modelAndView = null;

		try {

			User user = userService.getUserByUsername(username);

			if (user != null) {
				if (encryptionService.validatePassword(password, user.getUser_password_hash())) {
					
					// Kreiranje sesije za korisnika nakon uspjesnog logiranja
					HttpSession sess = request.getSession(true);
					
					sess.setAttribute("user", user);
					
					//limit trajanja sesije na 3 sata
					sess.setMaxInactiveInterval(10800);
					
					//Update usera da mu se postavi last login
					Date last_login_date = Calendar.getInstance().getTime();
					user.setLast_login(last_login_date);
					userService.updateUser(user);
					
					modelAndView = new ModelAndView("redirect:/users/profile");
					//modelAndView = new ModelAndView("home");
					modelAndView.addObject("user", user);
				} else {
					modelAndView = new ModelAndView("login");
					modelAndView.addObject("error", " * The username or password you entered is incorrect!");
				}

			} else {				
				modelAndView = new ModelAndView("login");
				modelAndView.addObject("error", " * The username or password you entered is incorrect!");
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			modelAndView = new ModelAndView("login");
		}
		 
		return modelAndView;
	}

	// Odjava sa sistema
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logoutPage(HttpServletRequest request) {

		HttpSession sess = request.getSession(true);
		sess.invalidate();

		return new ModelAndView("login");
	}
}
