package testingapp.dao;

import java.util.List;

import testingapp.model.GalleryPicture;


public interface GalleryPictureDAO {
	public void addGalleryPicture(GalleryPicture galleryPicture);
	public void updateGalleryPicture(GalleryPicture galleryPicture);
	public GalleryPicture getGalleryPicture(Integer galleryPicture_id);
	public void deleteGalleryPicture(Integer galleryPicture_id);
	public List<GalleryPicture> getGalleryPictures();
}
