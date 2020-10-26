package com.tsmc.RilaGet;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FileController {
	@RequestMapping(value = "/{file_folder}/{file_name}", method = RequestMethod.GET)
	public void getFile(@PathVariable("file_folder") String fileFolder, @PathVariable("file_name") String fileName,
			HttpServletResponse response) {
		System.out.println("Start download " + fileName + " in " + fileFolder);
		try {
			// String destLocation = "d:\\";
			// String src = destLocation.concat("\\" + fileName);
			Path currentPath = Paths.get(System.getProperty("user.dir"));
			Path filePath = Paths.get(currentPath.toString(), fileFolder, fileName);
			System.out.println(filePath.toString());

			// get your file as InputStream
			System.out.println("Full Path = " + filePath);
			InputStream is = new FileInputStream(filePath.toString());

			// Try to determine file's content type
			// response.setContentType("application/jar");

			// copy it to response's OutputStream
			IOUtils.copy(is, response.getOutputStream());
			response.flushBuffer();
		} catch (IOException ex) {
			System.out.println("Error writing file to output stream. Filename was " + fileName + ", but occured " + ex);
			throw new RuntimeException("IOError writing file to output stream");
		}
	}
}
