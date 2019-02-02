package com.ygq.disk.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@ResponseBody 
@RequestMapping("file")
public class FileController {
	
	private Logger logger = LoggerFactory.getLogger(FileController.class);
	
	@Value("${disk.dir}")
	private String diskDir;

	@RequestMapping("upload")
	public void upload(MultipartFile file) throws IllegalStateException, IOException {
		String fileName = file.getOriginalFilename();
		File dir = new File(diskDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
		File dest = new File(diskDir + fileName);
		logger.info("create file:{}", dest.getAbsolutePath());
		file.transferTo(dest);
	}
	
	@RequestMapping("download")
	public void download(HttpServletRequest req, HttpServletResponse rsp, String file) throws IOException {
	    logger.info("download file:{}, request from:{}", file, req.getRemoteAddr());
	    rsp.setContentType("multipart/form-data");   
	    rsp.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getBytes(), "ISO-8859-1"));
		FileInputStream fis = new FileInputStream(diskDir + file);
		ServletOutputStream outputStream = rsp.getOutputStream();
		byte[] buff = new byte[1024];
		int length;
		while((length = fis.read(buff)) > 0) {
			outputStream.write(buff, 0, length);
		}
		fis.close();
		outputStream.close();
	}
	
	@RequestMapping("isExist")
	public boolean isExist(String file) {
	    File localFile = new File(diskDir + file);
	    return localFile.exists();
	}

	@RequestMapping("delete")
	public void delete(HttpServletRequest req, String file) {
		logger.info("delete file:{}, request from:{}", file, req.getRemoteAddr());
		File localFile = new File(diskDir + file);
		if (localFile.exists()) {
			localFile.delete();
		}
	}

}
