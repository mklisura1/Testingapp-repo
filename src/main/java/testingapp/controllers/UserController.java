package testingapp.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import testingapp.helper.EmailValidator;
import testingapp.helper.PasswordValidator;
import testingapp.model.Gallery;
import testingapp.model.GalleryPicture;
import testingapp.model.Location;
import testingapp.model.User;
import testingapp.model.UserDetails;
import testingapp.service.LocationService;
import testingapp.service.UserDetailsService;
import testingapp.service.UserService;


@Controller
@RequestMapping(value = "users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private LocationService locationService;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	/*
	 * GET metoda za vracanje JSON lokacija korisnika
	 */
	@RequestMapping(value = "/{user_id}/get/locations", method = RequestMethod.GET)
	@ResponseBody
	public String getProductinJSON(@PathVariable Integer user_id, HttpServletRequest request) 
	{
		User user = userService.getUser(user_id);
		List<Location> locations = new ArrayList<Location>();
		locations.addAll(user.getLocations());
		
		ObjectMapper objectMapper = new ObjectMapper();
		String jsonInString = "";
		try {
			jsonInString = objectMapper.writeValueAsString(locations);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println(jsonInString);
		
		return jsonInString;
	}
	
	/*
	 * POST metoda za snimanje trenutne lokacije korisnika
	 */
	@RequestMapping(value="/save/location",method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody void saveLocationOfUser(@RequestBody Location location, HttpServletRequest request)
    {
		User user = (User) request.getSession().getAttribute("user");
		Date date = Calendar.getInstance().getTime();
		
		location.setUser(user);
		location.setDate_time(date);
		
		locationService.addLocation(location);
		user.getLocations().add(location);
		
		userService.updateUser(user);

        System.out.println("lat: " + location.getLatitude());
        System.out.println("lng: " + location.getLongitude());
    }
	
	/* 
	 * GET metoda za pregled prijatelja 
	 */
	@RequestMapping(value = "/view/friend/{user_id}", method = RequestMethod.GET)
	public ModelAndView viewFriend(@PathVariable Integer user_id, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		User friend = userService.getUser(user_id);
		
		ModelAndView modelAndView = new ModelAndView("friendprofileview");
		modelAndView.addObject("user", friend);
		modelAndView.addObject("userDetails", friend.getUserDetails());
		modelAndView.addObject("friends_size", friend.getFriends().size());
		
		List<GalleryPicture> pictures = new ArrayList<GalleryPicture>();
		for(Gallery item: friend.getGalleries()) pictures.addAll(item.getGalleryPictures());
		modelAndView.addObject("pictures", pictures);
		modelAndView.addObject("photos_size", pictures.size());
		
		return modelAndView;
	}
	
	/* 
	 * GET metoda za listu prijatelja 
	 */
	@RequestMapping(value = "/friends/list", method = RequestMethod.GET)
	public ModelAndView listOfMyFriends(HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		List<User> friends = new ArrayList<User>();
		
		friends.addAll(user.getFriends());
		
		ModelAndView modelAndView = new ModelAndView("friendslistview");
		modelAndView.addObject("friends", friends);
		
		return modelAndView;
	}
	
	/* 
	 * POST metoda za brisanje usera iz prijatelja
	 */
	@RequestMapping(value = "/remove/friend/{user_id}")
	public ModelAndView removeFriendOfUser(@PathVariable Integer user_id, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		
		User parent = (User) request.getSession().getAttribute("user");
		User friend = userService.getUser(user_id);
		
		List<User> old_friends = new ArrayList<User>();
		List<User> new_friends = new ArrayList<User>();
		old_friends.addAll(parent.getFriends());
		parent.getFriends().clear();
		
		for(User item: old_friends)
		{
			if(!item.getUser_id().equals(friend.getUser_id()))
			{
				new_friends.add(item);
			}
		}
		
		parent.getFriends().addAll(new_friends);
		userService.updateUser(parent);
		
		return new ModelAndView("redirect:/users/friends/list");
	}
	
	/* 
	 * POST metoda za dodavanje usera u prijatelje
	 */
	@RequestMapping(value = "/add/friend/{user_id}")
	public ModelAndView addFriendToUser(@PathVariable Integer user_id, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		
		User parent = (User) request.getSession().getAttribute("user");
		User friend = userService.getUser(user_id);
		
		parent.getFriends().add(friend);
		friend.getFriends_to().add(parent);
		
		userService.updateUser(parent);
		//userService.updateUser(friend);
		
		return new ModelAndView("redirect:/users/profile");
	}
	
	/* 
	 * GET metoda za search prijatelja 
	 */
	@RequestMapping(value = "/find/friends", method = RequestMethod.GET)
	public ModelAndView findFriends(HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		
		ModelAndView modelAndView = new ModelAndView("searchfriendsview");
		
		return modelAndView;
	}
	
	/* 
	 * POST metoda za search prijatelja 
	 */
	@RequestMapping(value = "/find/friends", method = RequestMethod.POST)
	public @ResponseBody ModelAndView findFriends(@RequestParam("query") String query, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		
		//System.out.println("query je: " + query);
		
		ModelAndView modelAndView = new ModelAndView("searchresultview");
		
		List<User> users = userService.getUsers();
		List<UserDetails> users_details = userDetailsService.getUserDetailss();
		List<User> users_result = new ArrayList<User>();
		
		//Izbrisem iz liste usera koji je logovan jer logicno ne mozes sam sebe dodati u prijatelje
		users.remove(user);
		
		for(User item_in_list: users)
		{
			for(UserDetails item_in_second_list: users_details)
			{
				//System.out.println("id1: " + item_in_list.getUserDetails().getUser_details_id());
				//System.out.println("id2: " + item_in_second_list.getUser_details_id());
				
				//Nasao sam user Details koji pripada korisniku, jos ga trebam provjeriti ime i prezime da li im se podudara
				if(item_in_list.getUserDetails().getUser_details_id().equals(item_in_second_list.getUser_details_id()))
				{
					//System.out.println("Ime: " + item_in_second_list.getFirst_name());
					//System.out.println("Prezime: " + item_in_second_list.getLast_name());
					
					//Ovdje mu provjerim ime i prezime da li se podudara
					if(item_in_second_list.getFirst_name().equals(query) || item_in_second_list.getLast_name().equals(query))
					{
						//System.out.println("Usao");
						users_result.add(item_in_list);
					}
				}
			}
		}
		
		//System.out.println("result: " + users_result.size());
		
		modelAndView.addObject("users_result", users_result);
		return modelAndView;
	}
	
	/* 
	 * GET metoda za dobavljanje profila usera 
	 */
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public ModelAndView userProfile(HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		
		ModelAndView modelAndView = new ModelAndView("userprofileview");
		modelAndView.addObject("user", userService.getUser(user.getUser_id()));
		modelAndView.addObject("userDetails", user.getUserDetails());
		modelAndView.addObject("friends_size", user.getFriends().size());
		
		List<GalleryPicture> pictures = new ArrayList<GalleryPicture>();
		for(Gallery item: user.getGalleries()) pictures.addAll(item.getGalleryPictures());
		modelAndView.addObject("pictures", pictures);
		modelAndView.addObject("photos_size", pictures.size());
		
		return modelAndView;
	}
	
	/* 
	 * POST metoda za dobavljanje profila usera 
	 */
	@RequestMapping(value = "/profile/update", method = RequestMethod.POST)
	public ModelAndView updateUserProfile(@Valid @ModelAttribute("userDetails") UserDetails userDetails, BindingResult result, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		
		if(result.hasErrors())
		{
			ModelAndView modelAndView = new ModelAndView("userprofileview");
			modelAndView.addObject("user", userService.getUser(user.getUser_id()));
			modelAndView.addObject("userDetails", user.getUserDetails());
			
			return modelAndView;
		}
		UserDetails userDetails_novi = user.getUserDetails();
		
		//Izracunaj godina koliko user ima i postavi ih
		java.time.LocalDate birthdate = userDetails.getBirth_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate date_now = new LocalDate();
		int godina = date_now.getYear() - birthdate.getYear();
		
		userDetails_novi.setAge(godina);
		userDetails_novi.setAddress(userDetails.getAddress());
		userDetails_novi.setBirth_date(userDetails.getBirth_date());
		userDetails_novi.setCountry(userDetails.getCountry());
		userDetails_novi.setEmail(userDetails.getEmail());
		userDetails_novi.setFirst_name(userDetails.getFirst_name());
		userDetails_novi.setLast_name(userDetails.getLast_name());
		userDetails_novi.setMobile_phone(userDetails.getMobile_phone());
		
		userDetailsService.updateUserDetails(userDetails_novi);
		
		return new ModelAndView("redirect:/users/profile");
	}
	
	
	/* 
	 * GET metoda za dobavljanje liste usera 
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView users(HttpServletRequest request,	
			@RequestParam(value="offSet", required=false) String offset) {

		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {
			return new ModelAndView("login");
		}

		ModelAndView modelAndView = new ModelAndView("userlistview");

		
		modelAndView.addObject("users", userService.getUsers());


		return modelAndView;
	}
		
	/*
	 * GET metoda za registraciju korisnika
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.GET)
	public ModelAndView registrationPage() {
		return new ModelAndView("userregistration");
	}

	/*
	 * POST metoda za pohranu podataka o registraciji korisnika
	 */
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public @ResponseBody ModelAndView registration(
			@RequestParam("username") String username, @RequestParam("password") String password, 
			@RequestParam("rePassword") String rePassword, @RequestParam("mobilePhone") String mobilePhone,
			@RequestParam("email") String email) {

		ModelAndView modelAndView = null;

		User user = userService.getUserByUsernameRegistration(username);

		// Ako postoji vratiti ponovo na stranicu registration
		if (user != null) {
			modelAndView = new ModelAndView("userregistration");
			modelAndView.addObject("errorUsername", " * Username already exists!");
			return modelAndView;
		}

		if (username.equals("") || password.equals("") || rePassword.equals("") || email.equals("") || mobilePhone.equals("")) {

			modelAndView = new ModelAndView("userregistration");

			if (username.equals(""))
				modelAndView.addObject("errorUsername", " * Username required!");
			else
				modelAndView.addObject("username", username);

			if (password.equals(""))
				modelAndView.addObject("error", " * Password required!");

			if (rePassword.equals(""))
				modelAndView.addObject("errorRePass", " * Retype password required!");
			
			if (mobilePhone.equals(""))
				modelAndView.addObject("errorMobilePhone", " * Mobile phone required!");
			
			if (email.equals(""))
				modelAndView.addObject("errorEmail", " * Email required!");

			return modelAndView;
		}

		// Funkcija koja vraca validator (tj. regex uslov za provjeru passworda)
		PasswordValidator validator = new PasswordValidator();
		boolean passwordOK = validator.validate(password);
		
		// Funkcija koja vraca validator (tj. regex uslov za provjeru emaila)
		EmailValidator validator_email = new EmailValidator();
		boolean emailOK = validator_email.validate(email);

		// Ako password ili email nije u ispravnom formatu vraca ponovo na stranicu za
		// registraciju
		if (!passwordOK || !emailOK) {
			modelAndView = new ModelAndView("userregistration");
			modelAndView.addObject("error", "Password must have:");
			modelAndView.addObject("errorLength", " * 6 or more characters");
			modelAndView.addObject("errorLetters", " * Upper & lower case letters");
			modelAndView.addObject("errorNumbers", " * At least one number");
			modelAndView.addObject("errorSpecialCharacters", " * One of these special characters (@#$%)");
			modelAndView.addObject("errorEmail", " * Email must be in right format");
			modelAndView.addObject("username", username);
			return modelAndView;
		}
		
		//Ako ponovljeni password nije isti ponovo vraca na stranicu za registraciju
		if (!rePassword.equals(password)) {
			modelAndView = new ModelAndView("userregistration");
			modelAndView.addObject("error", "Passwords don't match!");
			modelAndView.addObject("errorRePass", "Passwords don't match!");
			modelAndView.addObject("username", username);
			return modelAndView;
		}

		// Generisanje salta i hasha za korisnika
		String user_password_salt = null;
		String user_password_hash = null;

		try {
			user_password_salt = userService.getSalt();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		try {
			user_password_hash = userService.generatePasswordHash(password);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}

		Date date = Calendar.getInstance().getTime();
		
		User novi = new User();
		UserDetails userDetails = new UserDetails();
		
		novi.setUser_name(username);
		novi.setUser_password_salt(user_password_salt);
		novi.setUser_password_hash(user_password_hash);
		novi.setEmail(email);
		novi.setStart_date(date);
		novi.setMobile_phone(mobilePhone);
		
		userDetails.setAddress("Your address");
		userDetails.setCountry("Your country");
		userDetails.setFirst_name("First name");
		userDetails.setMobile_phone("38762123456");
		userDetails.setLast_name("Last name");
		userDetails.setEmail("example@example.com");
		
		novi.setUserDetails(userDetails);
		userService.addUser(novi);

		boolean ok = true;

		modelAndView = new ModelAndView("login");
		modelAndView.addObject("ok", ok);

		return modelAndView;
	}
	
	/*
	 * GET metoda za brisanje usera
	 */
	@RequestMapping(value = "/delete/{user_id}")
	public ModelAndView deleteUser(@PathVariable Integer user_id, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		
		userService.deleteUser(user_id);
		
		return new ModelAndView("redirect:/users/list");
	}
}
