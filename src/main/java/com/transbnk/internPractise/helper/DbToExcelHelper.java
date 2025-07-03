package com.transbnk.internPractise.helper;

import com.transbnk.internPractise.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Slf4j
public class DbToExcelHelper {

    public static final String[] HEADERS = {
            "id",
            "username",
            "role",
            "email"
    };

    public static final String SHEET_NAME = "User_Data";

    public static ByteArrayInputStream dataToExcel(List<User> list) {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream())
        {
            Sheet sheet = workbook.createSheet(SHEET_NAME);

            // Header Row
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < HEADERS.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(HEADERS[i]);
            }

            // Data Rows
            int rowIndex = 1;
            for (User user : list) {
                Row row = sheet.createRow(rowIndex++);
                row.createCell(0).setCellValue(user.getId());               // id
                row.createCell(1).setCellValue(user.getUsername());         // username
                row.createCell(2).setCellValue(user.getRoles());            // role
                row.createCell(3).setCellValue(user.getEmail());            // email
            }

            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            log.error("Exception while exporting data to Excel: {}", e.getMessage(), e);
            return null;
        }
    }
}
