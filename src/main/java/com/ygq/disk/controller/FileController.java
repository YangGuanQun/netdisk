package com.ygq.disk.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@ResponseBody 
@RequestMapping("file")
public class FileController {
	
	private Logger logger = LoggerFactory.getLogger(FileController.class);
	
	private final static String rootPath = "/file_repo/";
	
	static {
		File dir = new File(rootPath);
		if (!dir.exists()) {
			dir.mkdirs();
		}
	}

	@RequestMapping("upload")
	public void upload(MultipartFile file) throws IllegalStateException, IOException {
		String fileName = file.getOriginalFilename();
		File dest = new File(rootPath + fileName);
		logger.info("create file:{}", dest.getAbsolutePath());
		file.transferTo(dest);
	}
	
	@RequestMapping("download")
	public void download(HttpServletResponse rsp, String file) throws IOException {
		FileInputStream fis = new FileInputStream(rootPath + file);
		ServletOutputStream outputStream = rsp.getOutputStream();
		byte[] buff = new byte[1024];
		int length;
		while((length = fis.read(buff)) > 0) {
			outputStream.write(buff, 0, length);
		}
		fis.close();
		outputStream.close();
	}
}
