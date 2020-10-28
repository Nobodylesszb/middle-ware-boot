package com.bo.springboot.controller;

import com.bo.springboot.service.ExportExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @auther: bo
 * @Date: 2020/10/28 15:53
 * @version:
 * @description:
 */
@RestController
@RequestMapping("/export")
public class ExportExcelController {
    @Autowired
    ExportExcelService exportExcelService;

    @GetMapping
    public void exportExcel() throws IOException {
        exportExcelService.exportExcel();
    }
}
