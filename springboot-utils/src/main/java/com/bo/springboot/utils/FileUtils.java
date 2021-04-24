package com.bo.springboot.utils;

import cn.hutool.core.util.ZipUtil;

import java.io.File;

/**
 * @auther: bo
 * @Date: 2021/4/24 13:36
 * @version:
 * @description:
 */
public class FileUtils {
    public static final String ORIGIN_IMAGE_PATH = "/Users/bo/Documents/work/test/2/2021-31.zip";
    public static final String AFTER = "/Users/bo/Documents/work/test/2/test";

    public static void main(String[] args) {
        File unzip = ZipUtil.unzip(ORIGIN_IMAGE_PATH, AFTER);
        System.out.println(getFiles(unzip.getAbsolutePath()));

    }



    /**
     * 递归获取某路径下的所有文件，文件夹，并输出
     */

    public static String getFiles(String path) {
        File file = new File(path);
        // 如果这个路径是文件夹
        if (file.isDirectory()) {
            // 获取路径下的所有文件
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                // 如果还是文件夹 递归获取里面的文件 文件夹
                if (files[i].isDirectory()) {
                    System.out.println("目录：" + files[i].getPath());
                    getFiles(files[i].getPath());
                } else {
                    System.out.println("文件：" + files[i].getPath());
                    return files[i].getPath();
                }

            }
        } else {
            System.out.println("文件：" + file.getPath());
            return file.getPath();
        }
        return path;
    }
}
