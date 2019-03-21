package application;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AES
{
	public static String encrypt(String plainText, String mode, String padding, String key) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException
	{
		SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
		Cipher cipher = Cipher.getInstance("AES/"+mode+"/"+padding);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] cipherbytes = cipher.doFinal(plainText.getBytes());
		return Base64.getEncoder().encodeToString(cipherbytes);
		//return new String(cipherbytes);
	}
	
	public static String decrypt(String cipherText, String mode, String padding, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
	{
		SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "AES");
		Cipher cipher = Cipher.getInstance("AES/"+mode+"/"+padding);
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] cipherbytes = cipher.doFinal(Base64.getDecoder().decode(cipherText));
		return new String(cipherbytes);
	}
}
