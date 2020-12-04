package com.example.MollyMoranFYP.Models;

public class Member {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
//    private Integer Age;
//    private Long ph;
//    private Float ht;

    public Member() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String fname) {
        firstName = fname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastname) {
        lastName = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Float getHt() {
//        return ht;
//    }
//
//    public void setHt(Float ht) {
//        this.ht = ht;
//    }
}
