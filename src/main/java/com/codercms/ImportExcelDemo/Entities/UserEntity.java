package com.codercms.ImportExcelDemo.Entities;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    public String username;

    public String email;

   // public String password;

    public String contact;

    public String uniqueId;
}
