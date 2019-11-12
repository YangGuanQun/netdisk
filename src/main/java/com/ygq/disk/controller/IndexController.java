package com.ygq.disk.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/list")
public class IndexController {

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);

    @Value("${disk.dir}")
    private String diskDir;

    @RequestMapping("/file")
    public String fileList(ModelMap map) throws Exception {
        Map<String, List<String>> fileList = fetchFileName(diskDir + "file");
        map.put("fileMap", fileList);
        return "fileList";
    }

    @RequestMapping("/picture")
    public String pictureList(ModelMap map) throws Exception {
        Map<String, List<String>> fileList = fetchFileName(diskDir + "picture");
        map.put("fileMap", fileList);
        return "pictureList";
    }

    private Map<String, List<String>> fetchFileName(String dir) throws Exception {
        Map<String, List<String>> fileMap = new TreeMap<>(Comparator.reverseOrder());
        File dirFile = new File(dir);
        File[] files = dirFile.listFiles();
        if (null == files) {
            return fileMap;
        }
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        for (File file : files) {
            Date date = new Date(file.lastModified());
            String dateStr = df.format(date);
            if (fileMap.containsKey(dateStr)){
                fileMap.get(dateStr).add(file.getName());
            } else {
                List<String> list = new ArrayList<>();
                list.add(file.getName());
                fileMap.put(dateStr, list);
            }
        }
        logger.info("fileMap:{}", fileMap);
        return fileMap;

        /*String osName = System.getProperty("os.name");
        String cmd;
        String charset;
        if (osName.contains("Windows")) {
            cmd = "cmd.exe /C dir " + dir;
            charset = "GBK";
        } else {
            cmd = "ls -1 " + dir;
            charset = "UTF-8";
        }
        logger.info("exec:{}", cmd);
        Process proc = Runtime.getRuntime().exec(cmd);
        InputStream inputStream = proc.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset));
        String line;
        while ((line = reader.readLine()) != null) {
            if ("picture".equals(line)) {
                continue;
            }
            fileList.add(line);
        }*/
    }


}
