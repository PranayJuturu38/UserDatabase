package com.codercms.ImportExcelDemo.Models;
public class User {

  public String username;

  public String email;

 // public String password;

  public String contact;


  public String uniqueId;
  public User() {
  }

  public String getUniqueId() {
    return uniqueId;
  }

  public void setUniqueId(String uniqueId) {
    this.uniqueId = uniqueId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }



  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }



  public User(String username, String email, String contact) {

    this.username = username;
    this.email = email;
    //this.password = password;
    this.contact = contact;
  }

  @Override
  public String toString() {
    return "User{" +
            "username='" + username + '\'' +
            ", email='" + email + '\'' +
            ", contact='" + contact + '\'' +
            '}';
  }
  /*
	 * public int getAge() { return age; }
	 * 
	 * public void setAge(int age) { this.age = age; }
	 */

}