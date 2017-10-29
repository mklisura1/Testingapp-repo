package testingapp.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

import testingapp.model.User;

public interface UserService {
	
	public void addUser(User user);
	public void updateUser(User user);
	public User getUser(Integer user_id);
	public void deleteUser(Integer user_id);
	public List<User> getUsers();
	public User getUserByUsername(String user_name);
	public User getUserByUsernameRegistration(String user_name);
	// enkripcija
	public String getSalt() throws NoSuchAlgorithmException;
	public String generatePasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
	public String toHex(byte[] array) throws NoSuchAlgorithmException;
	public boolean validatePassword(String inputPassword, String hashedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException;
	public byte[] fromHex(String hex) throws NoSuchAlgorithmException;
}
