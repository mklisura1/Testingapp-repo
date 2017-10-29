package testingapp.helper;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import testingapp.model.GalleryPicture;

public class FileValidator implements Validator{
	public boolean supports(Class<?> clazz) {
        return GalleryPicture.class.isAssignableFrom(clazz);
	}

	public void validate(Object obj, Errors errors) {
		GalleryPicture file = (GalleryPicture) obj;
		if (file.getPicture().getSize() == 0) {
			errors.rejectValue("file", "valid.file");
		}
	}

}
