package com.gapli.gapli.Model;

public class User {
    String id;
    String name;
    String eMail;
    String pass;

    public User(String id, String name, String eMail, String pass) {
        this.id = id;
        this.name = name;
        this.eMail = eMail;
        this.pass = pass;
    }

    public User() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }
}
