package com.bo.springboot.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ReflectionUtils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @auther: bo
 * @Date: 2020/10/27 15:29
 * @version:
 * @description:
 */
@Slf4j
public class PdfUtils {
    /**
     * 创建一个拥有几列的table
     */
    public static PdfPTable creatTable(Integer length) {
        PdfPTable table = new PdfPTable(length);
        table.setWidthPercentage(100); // 宽度100%填充
        table.setSpacingBefore(10f); // 前间距
        table.setSpacingAfter(10f); // 后间距
        return table;

    }

    public static PdfPTable setColumnWidths(PdfPTable table, float[] columnWidths) {
        try {
            table.setWidths(columnWidths);
        } catch (DocumentException e) {
            log.error(e.getMessage());
        }
        return table;
    }

    public static <T> PdfPTable addValue(List<T> list, PdfPTable table, String[] header) {
        List<PdfPRow> pdfPRows = list.stream().map(item -> {
            Class<?> aClass = item.getClass();
            Field[] declaredFields = aClass.getDeclaredFields();
            PdfPCell[] cell = new PdfPCell[header.length];
            PdfPRow row = new PdfPRow(cell);
            for (int i = 0; i < header.length; i++) {
                declaredFields[i].setAccessible(true);
                Object field = ReflectionUtils.getField(declaredFields[i], item);
                if (Objects.isNull(field)) {
                    //处理null值
                    cell[i] = new PdfPCell(new Paragraph());
                    continue;
                }
                cell[i] = new PdfPCell(new Paragraph(field.toString(), getPdfChineseFont(10)));
            }
            return row;
        }).collect(Collectors.toList());
        ArrayList<PdfPRow> listRows = table.getRows();
        //添加头
        PdfPRow headerRow = addHeaders(header);
        listRows.add(headerRow);
        listRows.addAll(pdfPRows);
        return table;
    }

    private static PdfPRow addHeaders(String[] headers) {
        PdfPCell[] cell = new PdfPCell[headers.length];
        PdfPRow row = new PdfPRow(cell);
        for (int i = 0; i < headers.length; i++) {
            cell[i] = new PdfPCell(new Paragraph(headers[i], getPdfChineseFont(12)));
        }
        return row;
    }

    /**
     * 显示中文
     *
     * @return
     */
    public static Font getPdfChineseFont(Integer size) {
        BaseFont bfChinese = null;
        try {
            bfChinese = BaseFont.createFont("STSongStd-Light", "UniGB-UCS2-H",
                    BaseFont.NOT_EMBEDDED);
        } catch (DocumentException | IOException e) {
            log.error(e.getMessage());
        }
        return new Font(bfChinese, size, Font.NORMAL);
    }

    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        Rectangle rectPageSize = new Rectangle(PageSize.A4);// 定义A3页面大小
//        rectPageSize = rectPageSize.rotate(); //横版
        //创建文件
        Document document = new Document(rectPageSize);
        //建立一个书写器
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("test4.pdf"));
        //打开文件
        document.open();
        //添加内容
        document.add(new Paragraph("HD content here"));

        // 3列的表.
        PdfPTable table = new PdfPTable(3);
        table.setWidthPercentage(100); // 宽度100%填充
        table.setSpacingBefore(10f); // 前间距
        table.setSpacingAfter(10f); // 后间距

        List<PdfPRow> listRow = table.getRows();
        //设置列宽
        float[] columnWidths = {1f, 2f, 3f};
        table.setWidths(columnWidths);

        //行1
        PdfPCell[] cells1 = new PdfPCell[3];
        PdfPRow row1 = new PdfPRow(cells1);

        //单元格
        cells1[0] = new PdfPCell(new Paragraph("111"));//单元格内容
        cells1[0].setBorderColor(BaseColor.BLUE);//边框验证
        cells1[0].setPaddingLeft(20);//左填充20
        cells1[0].setHorizontalAlignment(Element.ALIGN_CENTER);//水平居中
        cells1[0].setVerticalAlignment(Element.ALIGN_MIDDLE);//垂直居中

        cells1[1] = new PdfPCell(new Paragraph("222"));
        cells1[2] = new PdfPCell(new Paragraph("333"));

        //行2
        PdfPCell cells2[] = new PdfPCell[3];
        PdfPRow row2 = new PdfPRow(cells2);
        cells2[0] = new PdfPCell(new Paragraph("444"));

        //把第一行添加到集合
        listRow.add(row1);
        listRow.add(row2);
        //把表格添加到文件中
        document.add(table);

        //关闭文档
        document.close();
        //关闭书写器
        writer.close();
    }
}
