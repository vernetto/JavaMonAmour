package com.pierre.ssltests;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;

public class CreatingaCertificationPath {
	public static void main(String[] argv) throws Exception {
		FileInputStream is = new FileInputStream(Constants.JKSKEYSTORE);

		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		keystore.load(is, Constants.JKSKEYSTOREPASSWORD.toCharArray());

		String alias = Constants.JKSCERTACLIAS;
		Certificate cert = keystore.getCertificate(alias);

		CertificateFactory certFact = CertificateFactory.getInstance("X.509");
		CertPath path = certFact.generateCertPath(Arrays.asList(new Certificate[] { cert }));
		System.out.println(path);

	}
}
