package com.pierre.ssltests;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

public class SignatureTest {
  public static void main(String[] args) {
    try {
      KeyPairGenerator keygen = KeyPairGenerator.getInstance("DSA");
      SecureRandom secrand = new SecureRandom();
      keygen.initialize(512, secrand);

      KeyPair keys1 = keygen.generateKeyPair();
      PublicKey pubkey1 = keys1.getPublic();
      PrivateKey privkey1 = keys1.getPrivate();

      KeyPair keys2 = keygen.generateKeyPair();
      PublicKey pubkey2 = keys2.getPublic();
      PrivateKey privkey2 = keys2.getPrivate();

      Signature signalg = Signature.getInstance("DSA");
      System.out.println("Provider=" + signalg.getProvider());
      signalg.initSign(privkey1);
      String message = "Pay authors a bonus of $20,000.";
      signalg.update(message.getBytes());
      byte[] signature = signalg.sign();
      Signature verifyalg = Signature.getInstance("DSA");
      verifyalg.initVerify(pubkey1);
      verifyalg.update(message.getBytes());
      if (!verifyalg.verify(signature))
        System.out.print("not ");
      System.out.println("signed with private key 1");

      verifyalg.initVerify(pubkey2);
      verifyalg.update(message.getBytes());
      if (!verifyalg.verify(signature))
        System.out.print("not ");
      System.out.println("signed with private key 2");
    } catch (Exception e) {
      System.out.println("Error " + e);
    }
  }
}
