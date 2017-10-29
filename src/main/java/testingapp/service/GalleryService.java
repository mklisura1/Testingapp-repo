package testingapp.service;

import java.util.List;

import testingapp.model.Gallery;

public interface GalleryService {
	
	public void addGallery(Gallery gallery);
	public void updateGallery(Gallery gallery);
	public Gallery getGallery(Integer gallery_id);
	public void deleteGallery(Integer gallery_id);
	public List<Gallery> getGalleries();
}
