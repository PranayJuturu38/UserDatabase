package com.codercms.ImportExcelDemo.Models;

import java.util.HashMap;
import java.util.List;

public class Message {

    private String message;
    private HashMap<String,List<String>> hm;
    private Exception e;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Message(String message, HashMap<String, List<String>> hm) {
        this.message = message;
        this.hm = hm;
    }

}