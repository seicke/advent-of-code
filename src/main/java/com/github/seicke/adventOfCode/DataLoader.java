package com.github.seicke.adventOfCode;

import java.io.File;
import java.io.FileInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

public class DataLoader {

	private static File resourcesDirectory = null;

	static {

		try {

			resourcesDirectory = new File(new URI(DataLoader.class.getResource("").toString()
					.replace(DataLoader.class.getPackageName().replace(".", "/"), "").replace("/target/classes/", "")
					.concat("resources")).getPath());

		} catch (Exception exception) {

			exception.printStackTrace();

		}

	}

	public static List<String> getStringLines(int year, int day, boolean useExampleData) {

		String filename = determineDataFile(year, day, useExampleData);

		File dataFile = new File(resourcesDirectory + "/" + filename);

		try (FileInputStream dataFileInputStream = new FileInputStream(dataFile)) {

			return IOUtils.readLines(dataFileInputStream, StandardCharsets.UTF_8);

		} catch (Exception e) {

			e.printStackTrace();
			return new ArrayList<String>();

		}

	}

	public static String getString(int year, int day, boolean useExampleData) {

		return getString(year, day, useExampleData, "");

	}

	public static String getString(int year, int day, boolean useExampleData, String lineDelimiter) {

		List<String> lines = getStringLines(year, day, useExampleData);

		return String.join(lineDelimiter, lines);

	}

	private static String determineDataFile(int year, int day, boolean useExampleData) {

		String filename = String.format("year%4d/day%02d/Day%02d_%sData.txt", year, day, day,
				useExampleData ? "Example" : "");

		return filename;
	}

}
