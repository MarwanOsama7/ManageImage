package com.global.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class RenameImage {

	public List<String> ChangeNameImages(List<MultipartFile> files, String directory, int id, String code)
			throws IOException {
		List<String> newFileNames = new ArrayList<>();
		int photoNumber = 1;

		// Determine if the OS is Windows or not
		boolean isWindows = System.getProperty("os.name").toLowerCase().contains("win");

		// Convert directory to OS-specific format
		if (isWindows) {
			// For Windows, replace forward slashes with backslashes
			directory = directory.replace("/", "\\");
		} else {
			// For Linux/Mac, ensure forward slashes
			directory = directory.replace("\\", "/");
		}

		// Ensure the directory path uses the correct file separator for the OS
		File uploadDir = new File(directory);
		if (!uploadDir.exists()) {
			uploadDir.mkdirs(); // Create directories if needed
		}

		for (MultipartFile file : files) {
			// Format the new file name
			String newFilename = String.format("%d_code%s-pic%d.jpg", id, code, photoNumber);

			// Create the file with the correct path separator
			File destinationFile = new File(uploadDir, newFilename);

			// Transfer the file to the new location
			file.transferTo(destinationFile);

			// Add the new file name to the list
			newFileNames.add(newFilename);

			// Increment the photo number for the next file
			photoNumber++;
		}

		// Return the list of new file names
		return newFileNames;
	}
}
