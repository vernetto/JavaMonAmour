import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class ClasspathCrawler {
	public static void main(String[] args) throws Exception {
		ClasspathCrawler cpc = new ClasspathCrawler();
		cpc.crawl();
		
	}

	private void crawl() throws Exception {
		String cp = System.getProperty("java.class.path");
		String cpseparator = File.pathSeparator;
		log("classpath=" + cp);
		for (String cpentry : cp.split(cpseparator)) {
			log("entry=" + cpentry);
			File cpentryFile = new File(cpentry);
			if (cpentryFile.exists() ) {
				if (cpentryFile.isFile()) {
					ZipFile zipFile = new ZipFile(cpentryFile);
//					Enumeration<? extends ZipEntry> entries = zipFile.entries();
//					while (entries.hasMoreElements()) {
//						ZipEntry element = entries.nextElement();
//						log("element = " + element.getName());
//					}
					
					ZipEntry zipEntry = zipFile.getEntry("META-INF/MANIFEST.MF");
					if (zipEntry != null) {
						String content = readZipEntry(zipFile, zipEntry);
						log(content);
						String cpInManifest = extractCp(content);
						log("cpInManifest=" + cpInManifest);
					}
				}
				else {
					log("INFO: skipping directory " + cpentry );
				}
				
			}
			else {
				log("WARNING: " + cpentry + " doesn't exist");
			}
		}
		
	}

	private String extractCp(String content) {
		boolean capture = false;
		StringBuffer result = new StringBuffer();
		for (String line : content.split("\n")) {
			if (line.startsWith("Class-Path:")) {
				capture = true;
			}
			else if (capture && !line.startsWith(" ")) {
				capture = false;
			}
			if (capture) {
				result.append(line);
			}
		}
		
		return result.toString().replace("Class-Path: ", "").replace("\r ", "");
	}

	private String readZipEntry(ZipFile zipFile, ZipEntry zipEntry) throws IOException, UnsupportedEncodingException {
		InputStream inputStream = zipFile.getInputStream(zipEntry);
		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
		    result.write(buffer, 0, length);
		}
		String content = result.toString("UTF-8");
		return content;
	}
	
	
	
	public void log(String message) {
		System.out.println(message);
	}
}
