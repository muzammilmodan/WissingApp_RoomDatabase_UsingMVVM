package com.wission.Model;

import android.util.Patterns;

public class UserData {

    String userId;
    private String strUserName;
    private String strUserEmail;
    private String strUserPhone;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }


    public  UserData(){

    }
    public UserData(String userId, String userName, String userEmail, String userPhone) {
        this.userId=userId;
        this.strUserName=userName;
        this.strUserEmail=userEmail;
        this.strUserPhone=userPhone;

    }

    public UserData(String strUserName, String strUserEmail, String strUserPhone) {
        this.strUserName = strUserName;
        this.strUserEmail = strUserEmail;
        this.strUserPhone = strUserPhone;
    }

    public String getStrUserName() {
        return strUserName;
    }

    public void setStrUserName(String strUserName) {
        this.strUserName = strUserName;
    }

    public String getStrUserEmail() {
        return strUserEmail;
    }

    public void setStrUserEmail(String strUserEmail) {
        this.strUserEmail = strUserEmail;
    }

    public String getStrUserPhone() {
        return strUserPhone;
    }

    public void setStrUserPhone(String strUserPhone) {
        this.strUserPhone = strUserPhone;
    }

    public boolean isEmailValid() {
        return Patterns.EMAIL_ADDRESS.matcher(getStrUserEmail()).matches();
    }



}
