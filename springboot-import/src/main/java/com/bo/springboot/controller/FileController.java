package com.bo.springboot.controller;

import cn.hutool.core.util.ZipUtil;
import com.bo.springboot.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.*;

/**
 * @auther: bo
 * @Date: 2020/11/19 15:16
 * @version:
 * @description:
 */
@Controller
public class FileController {
    @Value("${bo.uploadPath}")
    private String uploadPath;

    @GetMapping("/uploadfile")
    public String uploadfile() {
        return "uploadfile";
    }


    /**
     * 单文件
     */
    @PostMapping("/upload")
    @ResponseBody
    Object upload(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map.put("status", 0);
        String fileName;
        fileName = UUID.randomUUID().toString(); //对文件名称重命名

        try {
            FileUtil.uploadFile(file.getBytes(), uploadPath, fileName);
            map.put("filename", fileName);
        } catch (Exception e) {
            map.put("status", -1);
            map.put("message", e.getMessage());

        }


        return map;
    }

    /**
     * 单文件
     */
    @PostMapping("/uploadzip")
    @ResponseBody
    public Object uploadZip(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map.put("status", 0);
        String fileName;
        fileName = UUID.randomUUID().toString(); //对文件名称重命名

        try {
            File convFile = new File("/Users/bo/Documents/work/test/2/upload/"+Objects.requireNonNull(file.getOriginalFilename()));
            file.transferTo(convFile);
            File unzip = ZipUtil.unzip(convFile, new File("/Users/bo/Documents/work/test/2/test/"));
            map.put("filename", unzip.getAbsolutePath());
            FileUtil.deleteFile(convFile.getAbsolutePath());
        } catch (Exception e) {
            map.put("status", -1);
            map.put("message", e.getMessage());

        }

        return map;
    }

    /**
     * 多文件
     */
    @PostMapping("/uploads")
    @ResponseBody
    Object uploads(@RequestParam("files") MultipartFile[] files, HttpServletRequest request) {
        Map<String, Object> map = new HashMap();
        map.put("status", 0);
        List<String> filenames = new ArrayList<>();
        for (MultipartFile file : files
        ) {
            String ext = Objects.requireNonNull(file.getOriginalFilename()).split("\\.")[1];
            String fileName = file.getOriginalFilename();
            //fileName = UUID.randomUUID().toString()+"."+ext; //对文件名称重命名

            try {
                FileUtil.uploadFile(file.getBytes(), uploadPath, fileName);
                filenames.add(fileName);
            } catch (Exception e) {
                map.put("status", -1);
                map.put("message", e.getMessage());
                return map;

            }
        }

        map.put("filename", filenames);
        return map;
    }
}
