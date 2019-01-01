package com.ygq.disk.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    @RequestMapping("/")
    public String home(ModelMap map) throws Exception {
        String cmd = "ipconfig";
        Process proc = Runtime.getRuntime().exec(cmd);
        InputStream inputStream = proc.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> fileList = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null){
            fileList.add(line);
        }
        map.put("fileList", fileList);
        return "index";
    }


}
