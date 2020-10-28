package com.bo.springboot.controller;

import com.bo.springboot.service.ExportPdfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @auther: bo
 * @Date: 2020/10/28 15:02
 * @version:
 * @description:
 */
@RestController
@RequestMapping
public class ExportPdfController {
    @Autowired
    ExportPdfService exportPdfService;

    @GetMapping
    public void exportPdf() throws IOException {
        exportPdfService.exportPdf();
    }
}
