package testingapp.controllers;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.io.FilenameUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.drew.imaging.ImageMetadataReader;
import com.drew.lang.GeoLocation;
import com.drew.metadata.Metadata;
import com.drew.metadata.exif.GpsDirectory;

import testingapp.model.Gallery;
import testingapp.model.GalleryPicture;
import testingapp.model.User;
import testingapp.service.EncryptionService;
import testingapp.service.GalleryPictureService;
import testingapp.service.GalleryService;
import testingapp.service.UserService;


@Controller
@RequestMapping(value = "gallery")
public class GalleryController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private GalleryService galleryService;
	
	@Autowired
	private GalleryPictureService galleryPictureService;

	@Autowired
	private EncryptionService encryptionService;
	
	
	private static final String STORAGE_PATH = System.getenv("OPENSHIFT_DATA_DIR");
	
	@RequestMapping(value = "/like/image/{image_id}", method = RequestMethod.GET)
	public ModelAndView likeImage(@PathVariable Integer image_id, HttpServletRequest request)  
	{
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		
		GalleryPicture picture = galleryPictureService.getGalleryPicture(image_id);
		
		user.getLikedPhotos().add(picture);
		picture.getPictureLikes().add(user);
		
		userService.updateUser(user);
		
		//galleryPictureService.updateGalleryPicture(picture);
		
		return new ModelAndView("redirect:/users/view/friend/" + user.getUser_id() + "");
	}
	
	/*
	 * GET metoda za prikazivanje slika na mapi
	 */
	@RequestMapping(value = "/show/map", method=RequestMethod.GET)
	public ModelAndView showMap(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		
		ModelAndView modelAndView = new ModelAndView("showpictureonmapview");
		//modelAndView.addObject("gallery", gallery);
		return modelAndView;
	}
	
	/*
	 * GET metoda za vracanje stringa lokacija svih slika
	 */
	@ResponseBody
	@RequestMapping(value = "/get/locations", method=RequestMethod.GET)
	public String getLocations( HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute("user");
		
		System.out.println("Usao sam ");
		
		String locations_start = "";
		String locations_end ="";
		String delimiter = "/";
		
		List<Gallery> galleries = new ArrayList<Gallery>();
		galleries.addAll(user.getGalleries());
		List<GalleryPicture> pictures = new ArrayList<GalleryPicture>();
		
		for(Gallery item: galleries)
		{
			pictures.addAll(item.getGalleryPictures());
		}
		
		for(GalleryPicture item: pictures)
		{
			if(pictures.get(pictures.size()-1) == item)
			{
				String location = "" + item.getPicture_location_lat() + "/" + item.getPicture_location_lon() + "";
				locations_start = locations_start + location;
			}
			else
			{
				String location = "" + item.getPicture_location_lat() + "/" + item.getPicture_location_lon() + "/";
				locations_start = locations_start + location;
			}
		}
		System.out.println("Saljem: " + locations_start + locations_end);
		return locations_start + locations_end;
	}
	
	
	/* 
	 * GET metoda za dodavanje galerije
	 */
	@RequestMapping(value = "/add", method=RequestMethod.GET)
	public ModelAndView addGallery(@ModelAttribute("gallery") Gallery gallery, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		
		ModelAndView modelAndView = new ModelAndView("galleryaddview");
		modelAndView.addObject("gallery", gallery);
		return modelAndView;
	}
	
	/* 
	 * POST metoda za dodavanje galerije
	 */
	@RequestMapping(value = "/add", method=RequestMethod.POST)
	public ModelAndView addGallery(@Valid @ModelAttribute("gallery") Gallery gallery, BindingResult result, HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		
		if(result.hasErrors())
		{
			ModelAndView modelAndView = new ModelAndView("galleryaddview");
			modelAndView.addObject("user", userService.getUser(user.getUser_id()));
			modelAndView.addObject("gallery", gallery);
			
			return modelAndView;
		}
		gallery.setUser(user);
		galleryService.addGallery(gallery);
		
		user.getGalleries().add(gallery);
		
		userService.updateUser(user);
		
		return new ModelAndView("redirect:/users/profile");
	}
	
	//dodavanje slike u galeriju sa mobilne aplikacije
	@RequestMapping(value="/mobile/user/{username}/pass/{password}/add/picture", method=RequestMethod.POST)
	@ResponseBody
	public String checkLogin(@PathVariable String username, @PathVariable String password,
							@RequestParam("file") MultipartFile file, HttpServletRequest request) 
	{
		System.out.println("Usao sam u kontroler za mobilnu aplikaciju");
		System.out.println("FIle koji si poslao: " + file.getOriginalFilename());
		
		System.out.println("Pokusavam dobaviti usera sa username koji si poslao, username: " + username);
		User user = userService.getUserByUsername(username);  
		try
		{
			if (user != null) 
			{
				System.out.println("Dobavio sam usera");
				if (encryptionService.validatePassword(password, user.getUser_password_hash())) 
				{
					//Sad mu treba postaviti sliku u galeriju

					GalleryPicture picture = new GalleryPicture();
					Integer gallery_id = 1;
					
					//Ispis
					System.out.println("Uredu je šifra korisnika");
					System.out.println("Napravio sam sliku za galeriju");
					System.out.println("Galerija je 1");

					try {
						
						System.out.println("Pokusavam postaviti sliku");

						picture.setPicture(file);
						System.out.println("Postavio sam sliku");
						picture.setUpload_date(new Date());
						System.out.println("Postavio sam i datum uploada");

						//Get name and extension of file
						String name = FilenameUtils.getBaseName(file.getOriginalFilename());
						String extension = FilenameUtils.getExtension(file.getOriginalFilename());
						
						System.out.println("Postavio sam i ime fajla i ekstemnziju");

						//Create directory on server
						File dir = new File(STORAGE_PATH + File.separator + "galleries" + File.separator + user.getUser_id() + File.separator + galleryService.getGallery(gallery_id).getGallery_name() + File.separator);
						if (!dir.exists()) dir.mkdirs();
						
						System.out.println("Napravio sam direktorij");
						System.out.println("Pokusaj spremanja fajla na server");

						//Create the file on server
						byte[] bytes = file.getBytes();
						File serverFile = new File(dir.getAbsolutePath() + File.separator + name + "." + extension);
						BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
						System.out.println("Spremam");
						stream.write(bytes);
						stream.close();
						System.out.println("Spremio sam ");
						System.out.println("Vadim lokaciju slike");
						
						//Lokacija slike
						picture.setPicture_server_location(serverFile.getAbsolutePath());

						//Izvlacenje lokacije slike
						// Read all metadata from the image
						Metadata metadata = ImageMetadataReader.readMetadata(serverFile);
						// See whether it has GPS data
						GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
						// Try to read out the location, making sure it's non-zero
						GeoLocation geoLocation = gpsDirectory.getGeoLocation();
						if (geoLocation != null && !geoLocation.isZero()) {
							// Add to our picture
							picture.setPicture_location_lat(Double.toString(geoLocation.getLatitude()));
							picture.setPicture_location_lon(Double.toString(geoLocation.getLongitude()));
							System.out.println("Izvadio sam lokaciju i postavio je");
						}
						else
						{
							//Posto nema lokacije u slici, nedam da se spremi
							System.out.println("Nisam izvadio lokaciju jer je nema");
							return "0";
						}
							
						System.out.println("Spremam sliku u bazu");
						galleryPictureService.addGalleryPicture(picture);
						Gallery gallery = galleryService.getGallery(gallery_id);
						gallery.getGalleryPictures().add(picture);
						galleryService.updateGallery(gallery);
						System.out.println("Zavrsio sam sve, vracam 1");
						
						//Uspio je upload i sve
						return "1";

					} catch (Exception e) {
						
						//Nije uspio upload
						e.printStackTrace();
						return "0";
					}
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

	/* 
	 * POST metoda za dodavanje slike u galeriju
	 */
	@RequestMapping(value = "/{gallery_id}/add/picture", method=RequestMethod.POST)
	public ModelAndView addPictureToGallery(@PathVariable Integer gallery_id,
											@RequestParam("file") MultipartFile file, 
											HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		GalleryPicture picture = new GalleryPicture();
		
		try {
			
			picture.setPicture(file);
	        picture.setUpload_date(new Date());
			
	    	//Get name and extension of file
	    	String name = FilenameUtils.getBaseName(file.getOriginalFilename());
	    	String extension = FilenameUtils.getExtension(file.getOriginalFilename());
	    	
	    	//Create directory on server
			File dir = new File(STORAGE_PATH + File.separator + "galleries" + File.separator + user.getUser_id() + File.separator + galleryService.getGallery(gallery_id).getGallery_name() + File.separator);
			if (!dir.exists()) dir.mkdirs();
			
			//Create the file on server
			byte[] bytes = file.getBytes();
			File serverFile = new File(dir.getAbsolutePath() + File.separator + name + "." + extension);
			BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
			stream.write(bytes);
			stream.close();
	    	
			//Lokacija slike
			picture.setPicture_server_location(serverFile.getAbsolutePath());
			
			//Izvlacenje lokacije slike
			// Read all metadata from the image
			Metadata metadata = ImageMetadataReader.readMetadata(serverFile);
			// See whether it has GPS data
			GpsDirectory gpsDirectory = metadata.getFirstDirectoryOfType(GpsDirectory.class);
			// Try to read out the location, making sure it's non-zero
			GeoLocation geoLocation = gpsDirectory.getGeoLocation();
			if (geoLocation != null && !geoLocation.isZero()) {
				// Add to our picture
				picture.setPicture_location_lat(Double.toString(geoLocation.getLatitude()));
				picture.setPicture_location_lon(Double.toString(geoLocation.getLongitude()));
			}
			
	    	galleryPictureService.addGalleryPicture(picture);
	    	Gallery gallery = galleryService.getGallery(gallery_id);
	    	gallery.getGalleryPictures().add(picture);
	    	galleryService.updateGallery(gallery);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/users/profile");
	}
	
	/* 
	 * GET metoda za pregled galerija usera
	 */
	@RequestMapping(value = "/list", method=RequestMethod.GET)
	public ModelAndView listOfGalleries(HttpServletRequest request) {

		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return new ModelAndView("login");
		}
		List<Gallery> galleries = new ArrayList<Gallery>();
		galleries.addAll(user.getGalleries());
		
		ModelAndView modelAndView = new ModelAndView("gallerylistview");
		modelAndView.addObject("galleries", galleries);
		return modelAndView;
	}
	
	@RequestMapping(value = "/get/image/{image_id}")
	@ResponseBody
	public byte[] getImageById(@PathVariable Integer image_id)  
	{
		GalleryPicture picture = galleryPictureService.getGalleryPicture(image_id);
		byte[] bytes_picture = null;
		try {
			InputStream is = new FileInputStream(picture.getPicture_server_location());
            BufferedImage img = ImageIO.read(is);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(img, "jpg", bos);
            
            bytes_picture = bos.toByteArray();
            
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bytes_picture;
	}
	
	@RequestMapping(value = "/show/image/{image_id}", method = RequestMethod.GET)
	public ModelAndView getShowById(@PathVariable Integer image_id)  
	{
		ModelAndView modelAndView = new ModelAndView("showimageview");
		GalleryPicture picture = galleryPictureService.getGalleryPicture(image_id);
		
		modelAndView.addObject("picture", picture);
		return modelAndView;
	}
}
