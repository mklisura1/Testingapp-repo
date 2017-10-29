package testingapp.service;

import java.util.List;

import testingapp.model.UserDetails;

public interface UserDetailsService {
	
	public void addUserDetails(UserDetails userDetails);
	public void updateUserDetails(UserDetails userDetails);
	public UserDetails getUserDetails(Integer userDetails_id);
	public void deleteUserDetails(Integer userDetails_id);
	public List<UserDetails> getUserDetailss();
}
