package com.pierre.ssltests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;

public class AddingaCertificatetoaKeyStore {
	public static void main(String[] argv) throws Exception {
		FileInputStream is = new FileInputStream(Constants.JKSKEYSTORE);

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(is, Constants.JKSKEYSTOREPASSWORD.toCharArray());

		String alias = Constants.JKSCERTACLIAS;
		char[] password = Constants.JKSKEYSTOREPASSWORD.toCharArray();

		Certificate cert = keystore.getCertificate(alias);

		File keystoreFile = new File(Constants.JKSKEYSTORE);
		// Load the keystore contents
		FileInputStream in = new FileInputStream(keystoreFile);
		keystore.load(in, password);
		in.close();

		// Add the certificate
		keystore.setCertificateEntry(alias, cert);

		// Save the new keystore contents
		FileOutputStream out = new FileOutputStream(keystoreFile);
		keystore.store(out, password);
		out.close();

	}
}
