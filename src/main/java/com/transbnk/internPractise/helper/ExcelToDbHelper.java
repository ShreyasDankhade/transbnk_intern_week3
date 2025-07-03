package com.transbnk.internPractise.helper;

import com.transbnk.internPractise.entity.UserExcel;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


@Slf4j
public class ExcelToDbHelper {

    // Check the file is it excel of not
//    public static boolean checkExcelFormat(MultipartFile file) {
//        String contentType = file.getContentType();
//        assert contentType != null;
//        return contentType.equals("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//    }
//
//    //Converts excel to list of users
//
//    public static List<UserExcel> convertExcelToListUser(InputStream inputStream) {
//        List<UserExcel> list = new ArrayList<>();
//        try {
//
//            XSSFWorkbook sheet = new XSSFWorkbook(inputStream);
//            XSSFSheet data = sheet.getSheetAt(0);
//
//            int rowNumber = 0;
//
//
//            for (Row row : data) {
//                if (rowNumber == 0) {
//                    rowNumber++;
//                    continue;
//                }
//                UserExcel userEx = getUserExcel(row);
//                list.add(userEx);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }
//
//    private static UserExcel getUserExcel(Row row) {
//        int cellId = 0;
//        UserExcel userEx = new UserExcel();
//
//        for (Cell cell : row) {
//            switch (cellId) {
//                case 0 -> userEx.setId((long) cell.getNumericCellValue());
//                case 1 -> userEx.setUsername(cell.getStringCellValue());
//                case 2 -> userEx.setRole(cell.getStringCellValue());
//                case 3 -> userEx.setEmail(cell.getStringCellValue());
//            }
//            cellId++;
//        }
//        return userEx;
//    }

    // The below is the Optimized Code

    // Checks if the file is in Excel format (XLSX)
    public static boolean checkExcelFormat(MultipartFile file) {
        return "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"
                .equalsIgnoreCase(file.getContentType());
    }

    // Converts Excel input stream to list of UserExcel objects
    public static List<UserExcel> convertExcelToListUser(InputStream inputStream) {
        List<UserExcel> users = new ArrayList<>();

        try (Workbook workbook = new XSSFWorkbook(inputStream)) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rows = sheet.iterator();

            if (rows.hasNext()) rows.next(); // Skip header row

            while (rows.hasNext()) {
                Row row = rows.next();
                users.add(parseUserFromRow(row));
            }

        } catch (Exception e) {
            // Log error (in real applications, use a logger)
            log.error(e.getMessage());
        }
        return users;
    }

    private static UserExcel parseUserFromRow(Row row) {
        UserExcel user = new UserExcel();

        Cell idCell = row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        Cell usernameCell = row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        Cell roleCell = row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
        Cell emailCell = row.getCell(3, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);

        if (idCell.getCellType() == CellType.NUMERIC) {
            user.setId((long) idCell.getNumericCellValue());
        }

        user.setUsername(usernameCell.toString().trim());
        user.setRole(roleCell.toString().trim());
        user.setEmail(emailCell.toString().trim());

        return user;
    }
}