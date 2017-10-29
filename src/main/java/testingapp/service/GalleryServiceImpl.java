package testingapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import testingapp.dao.GalleryDAO;
import testingapp.model.Gallery;

@Service
@Transactional
public class GalleryServiceImpl implements GalleryService {
	
	@Autowired
	private GalleryDAO galleryDAO;	

	public void addGallery(Gallery gallery) {
		galleryDAO.addGallery(gallery);
	}

	public void updateGallery(Gallery gallery) {
		galleryDAO.updateGallery(gallery);
	}

	public Gallery getGallery(Integer gallery_id) {
		return galleryDAO.getGallery(gallery_id);		
	}
	
	public void deleteGallery(Integer gallery_id) {	
		galleryDAO.deleteGallery(gallery_id);
	}

	@Override
	public List<Gallery> getGalleries() {
		return galleryDAO.getGalleries();
	}
}
