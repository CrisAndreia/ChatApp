package com.crispereira.projectchatapp.Model;

public class User /*extends JSONObject*/ {
    private String name;
    //private int status;

    public User(String name){ this.name = name; }

    public void setName(String name) {
        this.name = name;
    }

    //public void setStatus(int status) { this.status = status; }

    public String getName(){
        return name;
    }

    //public int getStatus(){ return status; }

}
