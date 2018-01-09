package com.example.kennykanp.bmmbusiness.Model;

/**
 * Created by KennyKanp on 08/01/2018.
 */

public class Driver {

    String Name;
    String LastName;
    String NumberPhone;
    String Email;
    String User;
    String Password;
    String Photo;

    public Driver() {
    }

    public Driver(String name, String lastName, String numberPhone, String email, String user, String password, String photo) {
        Name = name;
        LastName = lastName;
        NumberPhone = numberPhone;
        Email = email;
        User = user;
        Password = password;
        Photo = photo;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getNumberPhone() {
        return NumberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        NumberPhone = numberPhone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }
}
