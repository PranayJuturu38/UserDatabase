package com.codercms.ImportExcelDemo.serviceImpl;

import com.codercms.ImportExcelDemo.Entities.UserEntity;
import com.codercms.ImportExcelDemo.Exceptions.UserException;
import com.codercms.ImportExcelDemo.Models.User;
import com.codercms.ImportExcelDemo.Repositories.UserRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class UserData {

    @Autowired
    UserRepository userRepository;
    public static HashMap<String, List<String>> hm = new HashMap<>();


    private String getCellValue(Row row, int cellNo) {
        DataFormatter formatter = new DataFormatter();

        Cell cell = row.getCell(cellNo);

        return formatter.formatCellValue(cell);
    }

    private int convertStringToInt(String str) {
        int result = 0;

        if (str == null || str.isEmpty() || str.trim().isEmpty()) {
            return result;
        }

        result = Integer.parseInt(str);

        return result;
    }

    public static boolean isValidValue(String value) {
        return value.length() > 255 ? true: false;
    }
    public  static List<String> isValidate(User user)
    {
        List<String> list = new ArrayList<>();

        try{
            if(isValidValue(user.getUsername())) {

                throw new UserException("Username is not valid");
            }
        }catch(UserException e) {
            System.out.println(e);
            list.add(e.getMessage());
        }
        try{
            if(isValidValue(user.getEmail())) {

                throw new UserException("Email is not valid");
            }
        }catch(UserException e) {
            list.add(e.getMessage());
        }
        try{
            if(isValidValue(user.getContact())) {

                throw new UserException("Contact is not valid");
            }
        }catch(UserException e) {
            list.add(e.getMessage());
        }
        return list;
    }


    public List<User> getDataFromExcel(MultipartFile userFile) throws IOException {

        List<User> users = new ArrayList<>();
        XSSFWorkbook workbook = new XSSFWorkbook(userFile.getInputStream());

        // Read user data form excel file sheet.
        XSSFSheet worksheet = workbook.getSheetAt(0);
        for (int index = 0; index < worksheet.getPhysicalNumberOfRows(); index++) {
            if (index > 0) {
                XSSFRow row = worksheet.getRow(index);

                User user = new User();


                user.username = getCellValue(row, 0);
                user.email = getCellValue(row, 1);
                // user.password = getCellValue(row, 2);
                user.contact = getCellValue(row, 2);
                user.uniqueId = getCellValue(row, 3);
                if (isValidate(user).isEmpty()) {
                    if (user.username != "" && user.email != "" && user.contact != "" && user.uniqueId != "") {
                        users.add(user);
                    }
                } else {
                    List<String> exceptions = isValidate(user);
                    hm.put(user.uniqueId, exceptions);
                    for(int i=0;i< exceptions.size();i++){
                        throw new UserException(exceptions.get(i));
                    }
                }
            }
        }
        return users;
    }

public void storeInDb(List<User> users){
    // take data from db and check values
    List<UserEntity> dbEntities = userRepository.findAll();
    boolean updated = false;
    if (dbEntities.size() == users.size()) {

        for (int i = 0; i < dbEntities.size(); i++) {
            updated = false;

            if (dbEntities.get(i).uniqueId.equals(users.get(i).uniqueId))

                updated = true;
            else{
                throw new UserException("File contains same value"+hm);
            }
        }

    }

    if (!updated) {
        // Save to db.
        List<UserEntity> entities = new ArrayList<>();
        if (users.size() > 0) {
            users.forEach(x -> {
                UserEntity entity = new UserEntity();

                entity.username = x.username;
                entity.email = x.email;
                entity.contact = x.contact;
                entity.uniqueId = x.uniqueId;
                System.out.println(x.uniqueId);
                entities.add(entity);
            });
            userRepository.saveAll(entities);
        }
    }

}

}
