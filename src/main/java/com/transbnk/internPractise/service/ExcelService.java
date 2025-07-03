package com.transbnk.internPractise.service;

import com.transbnk.internPractise.entity.User;
import com.transbnk.internPractise.entity.UserExcel;
import com.transbnk.internPractise.helper.ExcelToDbHelper;
import com.transbnk.internPractise.helper.DbToExcelHelper;
import com.transbnk.internPractise.repository.UserExcelRepo;
import com.transbnk.internPractise.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class ExcelService {

    private final UserRepo userRepo;
    private final UserExcelRepo userExcelRepo;

    // DB to Excel
    public ByteArrayInputStream getactualData(){
        List<User> all = userRepo.findAll();
        return DbToExcelHelper.dataToExcel(all);
    }

    // Excel to DB
    public void save(MultipartFile file){
        try {
            List<UserExcel> excelList = ExcelToDbHelper.convertExcelToListUser(file.getInputStream());
            this.userExcelRepo.saveAll(excelList);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<UserExcel> getAllExcelUsers(){
     return this.userExcelRepo.findAll();
    }
}
