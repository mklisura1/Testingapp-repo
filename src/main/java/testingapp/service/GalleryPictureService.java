package testingapp.service;

import java.util.List;

import testingapp.model.GalleryPicture;

public interface GalleryPictureService {
	
	public void addGalleryPicture(GalleryPicture galleryPicture);
	public void updateGalleryPicture(GalleryPicture galleryPicture);
	public GalleryPicture getGalleryPicture(Integer galleryPicture_id);
	public void deleteGalleryPicture(Integer galleryPicture_id);
	public List<GalleryPicture> getGalleryPictures();
}
