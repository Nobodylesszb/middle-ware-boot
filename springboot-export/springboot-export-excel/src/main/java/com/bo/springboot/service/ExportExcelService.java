package com.bo.springboot.service;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.bo.springboot.constants.ExcelConstants;
import com.bo.springboot.entity.SysUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @auther: bo
 * @Date: 2020/10/28 15:54
 * @version:
 * @description:
 */
@Service
@Slf4j
public class ExportExcelService {
    public void exportExcel() throws IOException {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = response(servletRequestAttributes);
        List<SysUser> sysUsers = creatData();
        ExcelWriter writer = ExcelUtil.getBigWriter();
        writer.writeHeadRow(Arrays.asList(ExcelConstants.EXPORT_EXCEL_TITLE));
        writer.write(sysUsers);
        ServletOutputStream out = null;
//        writer.flush(response.getOutputStream(), true);
//        writer.close();
        try {
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            log.warn(e.getMessage());
        } finally {
            /* 关闭writer，释放内存 */
            writer.close();
        }
        /* 此处记得关闭输出Servlet流 */
        IoUtil.close(out);
    }

    private HttpServletResponse response(ServletRequestAttributes servletRequestAttributes) throws IOException {
        HttpServletResponse response = servletRequestAttributes.getResponse();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        String title = "test";
        response.setHeader("Content-disposition", "attachment; filename=".concat(String.valueOf(URLEncoder.encode(title + ".xlsx", "UTF-8"))));
        return response;
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
