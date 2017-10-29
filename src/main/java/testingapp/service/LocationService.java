package testingapp.service;

import java.util.List;

import testingapp.model.Location;

public interface LocationService {
	
	public void addLocation(Location location);
	public void updateLocation(Location location);
	public Location getLocation(Integer location_id);
	public void deleteLocation(Integer location_id);
	public List<Location> getLocations();
}
