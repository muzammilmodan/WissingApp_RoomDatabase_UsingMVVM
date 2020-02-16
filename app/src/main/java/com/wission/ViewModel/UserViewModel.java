package com.wission.ViewModel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wission.Model.UserData;

public class UserViewModel extends ViewModel {

    private MutableLiveData<UserData> userMutableLiveData;

    //Todo: After using mutable Live data get user details.

    public MutableLiveData<UserData> getUser() {

        if (userMutableLiveData == null) {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;

    }

    public void callAddUser(String userName, String userEmail, String userPhone) {
        UserData userData = new UserData(userName,userEmail,userPhone);

        userMutableLiveData.setValue(userData);
    }

    public void callEditUser(String userName, String userEmail, String userPhone) {
        UserData userData = new UserData(userName,userEmail,userPhone);

        userMutableLiveData.setValue(userData);
    }
}
