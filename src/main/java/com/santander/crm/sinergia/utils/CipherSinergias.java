package com.santander.crm.sinergia.utils;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component("cipherSinergias")
public class CipherSinergias {

	private static Logger LOGGER = LoggerFactory.getLogger(CipherSinergias.class);

	public static String cipher(String texto, String clave) throws Exception {
		String secretKey = "rtcJupiterkey";
		if (clave != null && !"".equals(clave)) {
			secretKey = clave;
		}
		String base64EncryptedString = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);

			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher cipher = Cipher.getInstance("DESede");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			
			byte[] plainTextBytes = texto.getBytes("utf-8");
			byte[] buf = cipher.doFinal(plainTextBytes);
			byte[] base64Bytes = Base64.encodeBase64(buf);
			base64EncryptedString = new String(base64Bytes);

		} catch (Exception ex) {
			LOGGER.error(ex.toString());
			throw new Exception("Error al encriptar");
		}

	    return base64EncryptedString;
	}

	public static String desCipher(String textoEncriptado, String clave) throws Exception {
		String secretKey = "rtcJupiterkey";
		if (clave != null && !"".equals(clave)) {
			secretKey = clave;
		}
		String base64EncryptedString = "";
		try {
			byte[] message = Base64.decodeBase64(textoEncriptado.getBytes("utf-8"));
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
			byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
			
			SecretKey key = new SecretKeySpec(keyBytes, "DESede");
			Cipher decipher = Cipher.getInstance("DESede");
			decipher.init(2, key);

			byte[] plainText = decipher.doFinal(message);

			base64EncryptedString = new String(plainText, "UTF-8");
			
		} catch (Exception ex) {
			LOGGER.error("CipherJupiter.desCipher() " + ex.getMessage());
			throw new Exception("Desde la aplicación que deseas acceder no tiene acceso");
		}
		return base64EncryptedString;
	}
	
	
	/* Neo cambio la forma de encriptar y desencriptar a tipo url */
	public static String cipherURL(String texto, String clave)
		    throws Exception
		  {
		    String secretKey = "rtcJupiterkey";
		      //String CVE_NEO = "rtcJupiterkey";
		    if ((clave != null) && (!"".equals(clave))) {
		      secretKey = clave;
		    }
		    String base64EncryptedString = "";
		    try
		    {
		      MessageDigest md = MessageDigest.getInstance("MD5");
		      byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
		      byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		      
		      SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		      Cipher cipher = Cipher.getInstance("DESede");
		      cipher.init(Cipher.ENCRYPT_MODE, key);
		      
		         // String encriptado ="oH53qTsoiUiAVbyUqQBcoIjs4dbP%2FWffV%2BumVtXzC9SBTRe6vwUctwFERmzEQGGsD7UOxT7uJ3Y%3D";
		         // String desencriptado = CipherJupiter.desCipher(URLDecoder.decode(encriptado,"UTF-8"),CVE_NEO);
		      byte[] plainTextBytes = texto.getBytes("utf-8");
		      byte[] buf = cipher.doFinal(plainTextBytes);
		      byte[] base64Bytes = Base64.encodeBase64(buf);
		      base64EncryptedString = new String(base64Bytes);
		    }
		    catch (Exception ex)
		    {
		      LOGGER.error(ex.toString());
		      throw new Exception("Desde la aplicación que deseas acceder no tiene acceso");
		    }
		    return URLEncoder.encode(base64EncryptedString, "UTF-8");
		  }
	
	public static String desCipherURL(String textoEncriptado, String clave)
		    throws Exception
		  {
		    String secretKey = "rtcJupiterkey";
		    if ((clave != null) && (!"".equals(clave))) {
		      secretKey = clave;
		    }
		    String base64EncryptedString = "";
		    try
		    {
		      byte[] message = Base64.decodeBase64(URLDecoder.decode(textoEncriptado, "UTF-8").getBytes("utf-8"));
		      MessageDigest md = MessageDigest.getInstance("MD5");
		      byte[] digestOfPassword = md.digest(secretKey.getBytes("utf-8"));
		      byte[] keyBytes = Arrays.copyOf(digestOfPassword, 24);
		      SecretKey key = new SecretKeySpec(keyBytes, "DESede");
		      
		      Cipher decipher = Cipher.getInstance("DESede");
		      decipher.init(2, key);
		      
		      byte[] plainText = decipher.doFinal(message);
		      
		      base64EncryptedString = new String(plainText, "UTF-8");
		    }
		    catch (Exception ex)
		    {
		    	LOGGER.error("CipherJupiter.desCipher() " + ex.getMessage());
		    	throw new Exception("Desde la aplicación que deseas acceder no tiene acceso");
		    }
		    return base64EncryptedString;
		  }

}
