package com.ygq.disk.controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ygq.disk.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping("/upload/{type}")
	public void upload(MultipartFile file, @PathVariable String type, HttpServletResponse rsp) throws
			IllegalStateException, IOException {
		String fileName = file.getOriginalFilename();
		File dir = new File(diskDir + type);
        if (!dir.exists()) {
            dir.mkdirs();
        }
		File dest = new File(diskDir + type + File.separator + fileName);
		logger.info("create file:{}", dest.getAbsolutePath());
		file.transferTo(dest);
		rsp.sendRedirect("/disk/list/" + type);
	}
	
	@RequestMapping("/download/{type}")
	public void download(HttpServletRequest req, HttpServletResponse rsp, String file, @PathVariable String type)
			throws IOException {
	    logger.info("download file:{}, request from:{}", file, req.getRemoteAddr());
	    rsp.setContentType("multipart/form-data");   
	    rsp.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getBytes(), "ISO-8859-1"));
		FileInputStream fis = new FileInputStream(diskDir + type + File.separator + file);
		ServletOutputStream outputStream = rsp.getOutputStream();
		byte[] buff = new byte[1024];
		int length;
		while((length = fis.read(buff)) > 0) {
			outputStream.write(buff, 0, length);
		}
		fis.close();
		outputStream.close();
	}

	@RequestMapping("thumb")
	public void thumb(HttpServletRequest req, HttpServletResponse rsp, String file) throws IOException {
        logger.info("生成缩略图:{}", file);
		BufferedImage bufferedImage = ImageUtil.thumbnailImage(diskDir + "picture" + File.separator + file,
				200, 200, true);
		rsp.setContentType("multipart/form-data");
		rsp.setHeader("Content-Disposition", "attachment;filename=" + new String(file.getBytes(), "ISO-8859-1"));
		ImageIO.write(bufferedImage, "jpg", rsp.getOutputStream());
	}
	
	@RequestMapping("isExist")
	public boolean isExist(String file) {
	    File localFile = new File(diskDir + file);
	    return localFile.exists();
	}

	@RequestMapping("delete/{type}")
	public void delete(HttpServletRequest req, String file, @PathVariable String type) {
		logger.info("delete file:{}, request from:{}", file, req.getRemoteAddr());
		File localFile = new File(diskDir + type + File.separator +  file);
		if (localFile.exists()) {
			localFile.delete();
		}
	}

}
