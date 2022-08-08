package iss.team5.vms.helper;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.jboss.logging.Logger;

import iss.team5.vms.Team5VmsApplication;

public class HashStringGenerator {
	private static final Logger LOGGER = Logger.getLogger(Team5VmsApplication.class.getName());
	
	public static byte[] getHash(String username, String password) {
		byte[] hash = null;
		String combinedString = username + password;
		
		try {
			MessageDigest mg = MessageDigest.getInstance("SHA-256");
			hash = mg.digest(combinedString.getBytes(StandardCharsets.UTF_8));
		}
		catch(NoSuchAlgorithmException e) {
			LOGGER.error("No SHA-256 algorithm");
			LOGGER.error(e);
			
		}
		
		return hash;
	}
	
	public static String convertByteToHex(byte[] hash) {
		StringBuilder hexDisplay = new StringBuilder(2 * hash.length);
		
		for(int i = 0; i < hash.length; i++) {
			String hex = Integer.toHexString(0xff & hash[i]);
			if(hex.length() == 1) {
				hexDisplay.append('0');
			}
			hexDisplay.append(hex);
		}
		
		return hexDisplay.toString();
	}
}
