package org.pierre.tools;
import java.io.File;
import java.io.IOException;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagConstant;
import org.farng.mp3.TagException;
import org.farng.mp3.TagOptionSingleton;

public class BatchRename {
	public static void main(String[] args) throws IOException, TagException {
		File root = new File("D:\\pierre\\emule\\Incoming\\battiato\\");
		TagOptionSingleton.getInstance().setDefaultSaveMode(TagConstant.MP3_FILE_SAVE_OVERWRITE);
		changeTag(root);
	}
	
	private static void renameAll(File dir) {
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.getName().endsWith("mp3") && !f.getName().startsWith("battiato")) {
				File newFile = new File(f.getParent(), "battiato" + f.getName());
				boolean result = f.renameTo(newFile);
				System.out.println(result + " " + newFile);
			}
			else if (f.isDirectory()) {
				renameAll(f);
			}
		}
	}
	
	private static void changeTag(File dir) throws IOException, TagException {
		for (File f : dir.listFiles()) {
			if (f.isFile() && f.getName().endsWith("mp3") && f.getName().startsWith("battiato")) {
				MP3File mp3file = new MP3File(f);
				if (mp3file.getID3v1Tag() != null) {
					mp3file.getID3v1Tag().setSongTitle(f.getName());
				}
				else {
					System.out.println("getID3v1Tag is null for " + f.getName());
				}
				
			}
			else if (f.isDirectory()) {
				changeTag(f);
			}
		}
	}
}
