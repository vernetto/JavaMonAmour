package com.pierre.filecopy;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Date;

public class FileCopy
{
    public static void main(final String... args)
        throws IOException
    {
    	System.out.println("BEGIN " + new Date());
        Files.copy(Paths.get("D:\\pierre\\emule\\archive\\Lisa Dagli Occhi Blu 1969 (Mario Tessuto - Silvia Dioniso - Bice Valori - Peppino De Filippo - Erminio Macario - Franco Franchi - Ciccio Ingrassia - Gino Bramie.avi"),
            Paths.get("F:\\pippo.avi"), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("END " + new Date());
    }
}