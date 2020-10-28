package com.bo.springboot.service;

import com.bo.springboot.constants.pdfConstants;
import com.bo.springboot.entity.SysUser;
import com.bo.springboot.utils.PdfUtils;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.ietf.jgss.Oid;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @auther: bo
 * @Date: 2020/10/28 15:03
 * @version:
 * @description:
 */
@Service
public class ExportPdfService {

    public void exportPdf() throws IOException, DocumentException {
        List<SysUser> sysUsers = creatData();
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        OutputStream out = Response(servletRequestAttributes);
        // 1.新建document对象
        Rectangle rectPageSize = new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth());
        rectPageSize.rotate();
        //边距
        Document document = new Document(rectPageSize);
        // 2.建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中。
        // 创建 PdfWriter 对象 第一个参数是对文档对象的引用，第二个参数是文件的实际名称，在该名称中还会给出其输出路径。
        PdfWriter writer = PdfWriter.getInstance(document, out);
        writer.setViewerPreferences(PdfWriter.PageModeUseThumbs);
//        //添加水印和页码
//        PDFBuilder builder = new PDFBuilder();
//        writer.setPageEvent(builder);
        // 3.打开文档
        document.open();
        //创建一个表格
        PdfPTable table = PdfUtils.creatTable(pdfConstants.EXPORT_PDF_TITLE.length);
        //设置表格宽度
        PdfUtils.setColumnWidths(table, pdfConstants.EXPORT_LOG_PDF_WIDTH);
        table = PdfUtils.addValue(sysUsers, table, pdfConstants.EXPORT_PDF_TITLE);
//        // 4.添加一个内容段落 这里是头
        document.add(table);
        // 5.关闭文档
        document.close();
        //关闭书写器
        writer.close();
    }

    private OutputStream Response(ServletRequestAttributes servletRequestAttributes) throws IOException {
        HttpServletResponse response = servletRequestAttributes.getResponse();
        response.setContentType("application/pdf");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        String title = "pdf";
        response.setHeader("Content-disposition", "attachment; filename=".concat(String.valueOf(URLEncoder.encode(title + ".pdf", "UTF-8"))));
        return response.getOutputStream();
    }
    private List<SysUser> creatData(){
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<SysUser> sysUsers = numbers.stream().map(id -> {
            SysUser sysUser = new SysUser();
            sysUser.setId(id);
            sysUser.setName("test");
            sysUser.setPassword("test");
            return sysUser;
        }).collect(Collectors.toList());
        return sysUsers;
    }
}
