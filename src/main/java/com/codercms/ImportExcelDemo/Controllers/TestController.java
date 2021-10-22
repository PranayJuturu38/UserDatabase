package com.codercms.ImportExcelDemo.Controllers;

import com.codercms.ImportExcelDemo.Entities.UserEntity;
import com.codercms.ImportExcelDemo.Exceptions.UserException;
import com.codercms.ImportExcelDemo.Models.Message;
import com.codercms.ImportExcelDemo.Models.User;
import com.codercms.ImportExcelDemo.Repositories.UserRepository;
import com.codercms.ImportExcelDemo.serviceImpl.UserData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.codercms.ImportExcelDemo.serviceImpl.UserData.hm;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserData userService;


    @PostMapping("/import-order-excel")
    public ResponseEntity<Message> importExcelFile(@RequestParam("file") MultipartFile files) throws IOException {

        String message = "" + hm;

        try {
            List<User> users = userService.getDataFromExcel(files);
            try {
                userService.storeInDb(users);
            } catch (UserException exception) {
                message = exception.getMessage();
                return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new Message(message));

            } catch (Exception e) {
                message = e.getMessage();
                return ResponseEntity.status(HttpStatus.ALREADY_REPORTED).body(new Message(message));

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        message = "Database updated";
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(new Message(message));

    }
}


