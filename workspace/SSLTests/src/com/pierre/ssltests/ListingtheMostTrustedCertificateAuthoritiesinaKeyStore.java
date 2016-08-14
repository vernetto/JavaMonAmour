package com.pierre.ssltests;

import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Iterator;

public class ListingtheMostTrustedCertificateAuthoritiesinaKeyStore {
	public static void main(String[] argv) throws Exception {

		String filename = System.getProperty("java.home") + "/lib/security/cacerts".replace('/', File.separatorChar);
		FileInputStream is = new FileInputStream(filename);
		KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
		String password = "changeit";
		keystore.load(is, password.toCharArray());

		PKIXParameters params = new PKIXParameters(keystore);

		Iterator it = params.getTrustAnchors().iterator();
		for (; it.hasNext();) {
			TrustAnchor ta = (TrustAnchor) it.next();

			X509Certificate cert = ta.getTrustedCert();
			System.out.println(cert.getSigAlgName());
		}
	}
}
