package testingapp.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import testingapp.dao.LocationDAO;
import testingapp.model.Location;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {
	
	@Autowired
	private LocationDAO locationDAO;	

	public void addLocation(Location location) {
		locationDAO.addLocation(location);
	}

	public void updateLocation(Location location) {
		locationDAO.updateLocation(location);
	}

	public Location getLocation(Integer location_id) {
		return locationDAO.getLocation(location_id);		
	}
	
	public void deleteLocation(Integer location_id) {	
		locationDAO.deleteLocation(location_id);
	}

	@Override
	public List<Location> getLocations() {
		return locationDAO.getLocations();
	}
}
