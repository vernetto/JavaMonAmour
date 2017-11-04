package org.pierre.tools;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Decoder {

    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.out.println("Usage: java EncodeFileWithBASE64 d|e <inputFile> <outputFile>");
            return;
        }
        String op = args[0];
        String inputFile = args[1];
        String outputFile = args[2];


        if (op.equalsIgnoreCase("e")) {
            BASE64Encoder encoder = new BASE64Encoder();
            encoder.encode(
                    new FileInputStream(inputFile),
                    new FileOutputStream(outputFile)
            );
        } else {
            BASE64Decoder decoder = new BASE64Decoder();
            decoder.decodeBuffer(new FileInputStream(inputFile),
                    new FileOutputStream(outputFile));


        }
    }
}




