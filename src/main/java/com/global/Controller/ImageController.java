package com.global.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.global.services.RenameImage;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/v2")
@RequiredArgsConstructor
public class ImageController {

	private final RenameImage renameImage;

	@PostMapping("/uploadimages")
	public ResponseEntity<List<String>> uploadImages(@RequestParam("files") List<MultipartFile> files,
			@RequestParam("directory") String directory, @RequestParam("id") int id,
			@RequestParam("code") String code) {
		try {
			// Replace backslashes with forward slashes
			directory = directory.replace("\\", "/");

			List<String> newFileNames = renameImage.ChangeNameImages(files, directory, id, code);
			return ResponseEntity.ok(newFileNames);
		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(List.of("Error uploading files: " + e.getMessage()));
		}
	}

}
