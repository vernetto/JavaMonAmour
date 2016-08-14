package com.pierre.ssltests;
import java.io.FileInputStream;
import java.security.KeyStore;

public class RetrievingaCertificatefromaKeyStore {


	
	  public static void main(String[] argv) throws Exception {
	    FileInputStream is = new FileInputStream(Constants.JKSKEYSTORE);

	    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
	    keystore.load(is, Constants.JKSKEYSTOREPASSWORD.toCharArray());

	    // Get certificate
	    java.security.cert.Certificate cert = keystore.getCertificate(Constants.JKSCERTACLIAS);
	    System.out.println("cert classname=" + cert.getClass().getName());
	    System.out.println("cert type=" + cert.getType());
	    System.out.println("cert algorithm=" + cert.getPublicKey().getAlgorithm());
	    System.out.println(cert);
	    
	  }
	

}
