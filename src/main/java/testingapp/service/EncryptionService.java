package testingapp.service;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface EncryptionService {
		
		public String getSalt() throws NoSuchAlgorithmException;
		public String generatePasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException;
		public String toHex(byte[] array) throws NoSuchAlgorithmException;
		public boolean validatePassword(String inputPassword, String hashedPassword) throws NoSuchAlgorithmException, InvalidKeySpecException;
		public byte[] fromHex(String hex) throws NoSuchAlgorithmException;
}
