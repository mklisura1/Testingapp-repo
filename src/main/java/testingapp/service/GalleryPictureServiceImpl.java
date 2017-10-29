package testingapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import testingapp.dao.GalleryPictureDAO;
import testingapp.model.GalleryPicture;

@Service
@Transactional
public class GalleryPictureServiceImpl implements GalleryPictureService {
	
	@Autowired
	private GalleryPictureDAO galleryPictureDAO;	

	public void addGalleryPicture(GalleryPicture galleryPicture) {
		galleryPictureDAO.addGalleryPicture(galleryPicture);
	}

	public void updateGalleryPicture(GalleryPicture galleryPicture) {
		galleryPictureDAO.updateGalleryPicture(galleryPicture);
	}

	public GalleryPicture getGalleryPicture(Integer galleryPicture_id) {
		return galleryPictureDAO.getGalleryPicture(galleryPicture_id);		
	}
	
	public void deleteGalleryPicture(Integer galleryPicture_id) {	
		galleryPictureDAO.deleteGalleryPicture(galleryPicture_id);
	}

	@Override
	public List<GalleryPicture> getGalleryPictures() {
		return galleryPictureDAO.getGalleryPictures();
	}
}
