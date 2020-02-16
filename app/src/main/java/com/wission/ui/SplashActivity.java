package com.wission.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.wission.R;
import com.wission.ui.Login.LoginActivity;
import com.wission.ui.UserList.MainActivity;
import com.wission.ui.addUserDetails.UserList_RoomActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setSplash();

    }

    private void setSplash() {
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intentLogin;

                    intentLogin = new Intent(SplashActivity.this, LoginActivity.class);
                    intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentLogin);
                    finish();
                }
            }, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
