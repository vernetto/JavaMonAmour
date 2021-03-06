import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class ClasspathCrawler {

	List<String> effectiveClasspath = new ArrayList<String>();
	List<CPEntry> entries = new ArrayList<CPEntry>();
	
	boolean VERBOSE = false; 

	public static void main(String[] args) throws Exception {
		ClasspathCrawler cpc = new ClasspathCrawler();
		String cp = System.getProperty("java.class.path");
		String cpseparator = File.pathSeparator;
		cpc.crawl("", cp, cpseparator, "ROOT");
		System.out.println("effective classpath = " + cpc.effectiveClasspath);
		System.out.println("entries = " + cpc.entries.toString().replaceAll(",", "\n"));
	}

	private void crawl(String basepath, String cp, String cpseparator, String referencedBy) throws Exception {
		log("classpath=" + cp);
		for (String cpentry : cp.split(cpseparator)) {
			cpentry = cpentry.trim();
			if (basepath != null && basepath.length() > 0) {
				if (VERBOSE) log("normalizing " + cpentry + " using basepath " + basepath);
				cpentry = new File(basepath + "/" + cpentry).getCanonicalFile().getAbsolutePath();
			}
			if (VERBOSE) log("entry=" + cpentry);
			File cpentryFile = new File(cpentry);
			if (cpentryFile.exists()) {
				if (effectiveClasspath.contains(cpentry)) {
					log("WARNING: " + cpentry + " already in effective classpath..... possible circular reference");
				}
				else {
					if (VERBOSE) log("adding to effectiveClasspath " + cpentry);
					effectiveClasspath.add(cpentry);
					entries.add(new CPEntry(cpentry, referencedBy));
					if (VERBOSE) log("content of effectiveClasspath is " + effectiveClasspath);
					if (cpentryFile.isFile()) {
						ZipFile zipFile = new ZipFile(cpentryFile);
						ZipEntry zipEntry = zipFile.getEntry("META-INF/MANIFEST.MF");
						if (zipEntry != null) {
							String content = readZipEntry(zipFile, zipEntry);
							if (VERBOSE) log(content);
							String cpInManifest = extractCp(content);
							if (cpInManifest != null && cpInManifest.length() > 0) {
								if (VERBOSE) log("cpInManifest=" + cpInManifest);
								crawl(cpentryFile.getParentFile().getAbsolutePath(), cpInManifest, " ", cpentry);
							}
						}
					}
					else {
						log("INFO: skipping directory " + cpentry);
					}
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

		return result.toString().replace("Class-Path: ", "").replace("\r ", "").replace("\n ", "");
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



class CPEntry {
	String file;
	String addedBy;
	public CPEntry(String file, String addedBy) {
		this.file = file;
		this.addedBy = addedBy;
	}
	public String toString() {
		return "file " + this.file + " referenced by " + this.addedBy; 
	}
}
