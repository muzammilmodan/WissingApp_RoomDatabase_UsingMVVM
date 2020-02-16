package com.wission.appUtils;

import android.content.Context;
import android.content.SharedPreferences;


public class UserPrefManager {
    SharedPreferences app_pref, user_pref;
    SharedPreferences.Editor app_editor, user_editer;
    Context _context;

    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "WissionApp";
    private static final String USER_DATA = "user_data";

    public UserPrefManager(Context context) {
        this._context = context;
        app_pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        user_pref = _context.getSharedPreferences(USER_DATA, PRIVATE_MODE);
        app_editor = app_pref.edit();
        user_editer = user_pref.edit();

    }

    public SharedPreferences getUser_pref() {
        return user_pref;
    }



}
