package com.pierre.googletranslate;

import java.io.BufferedWriter;

import java.io.File;

import java.nio.file.Files;

import java.nio.file.Path;

import java.nio.file.Paths;

import java.util.ArrayList;

import java.util.Enumeration;

import java.util.List;

import java.util.zip.ZipEntry;

import java.util.zip.ZipFile;

public class FindClasses {

	static boolean DEBUG = true;

	static String rootFolder = "C:\\Apps\\Pippo\\";

	static List<File> jarFiles = new ArrayList<File>();

	public static void main(String[] args) throws Throwable {

		Path outputFilePath = Paths.get("allfilesPippo.out");

		BufferedWriter writer = Files.newBufferedWriter(outputFilePath);

		File rootFolderDir = new File(rootFolder);

		scan(rootFolderDir);

		System.out.println("END SCAN");

		if (DEBUG) {

			for (File jar : jarFiles) {

				System.out.println(jar.getAbsolutePath());

			}

		}

		for (File jar : jarFiles) {

			writer.write("BEGIN JAR: " + jar.getAbsolutePath() + "\n");

			ZipFile zipFile = new ZipFile(jar);

			Enumeration<? extends ZipEntry> entries = zipFile.entries();

			while (entries.hasMoreElements()) {

				ZipEntry entry = entries.nextElement();

				writer.write(entry.getName() + "\n");

			}

			zipFile.close();

			writer.write("END JAR: " + jar.getAbsolutePath() + "\n\n");

		}

		writer.close();

	}

	private static void scan(File folderToScan) {

		if (DEBUG)

			System.out.println("scanning " + folderToScan.getAbsolutePath());

		for (File file : folderToScan.listFiles()) {

			if (file.isDirectory()) {

				scan(file);

			} else {

				if (file.getName().toLowerCase().endsWith(".jar")) {

					jarFiles.add(file);

				}

			}

		}

	}

}