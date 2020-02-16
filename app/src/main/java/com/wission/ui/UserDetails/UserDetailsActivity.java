package com.wission.ui.UserDetails;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wission.R;

public class UserDetailsActivity extends AppCompatActivity {


    String user_name, user_email, user_phone;

    TextView tvNameAUD, tvEmailAUD, tvPhoneAUD;
    ImageView imgBackAUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        try {
            getIds();
            user_name = getIntent().getStringExtra("name");
            user_email = getIntent().getStringExtra("email");
            user_phone = getIntent().getStringExtra("phone");

            tvNameAUD.setText("Name: "+user_name);
            tvEmailAUD.setText("Email: "+user_email);
            tvPhoneAUD.setText("Phone: "+user_phone);

            imgBackAUD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getIds() {
        try {
            tvNameAUD = findViewById(R.id.tvNameAUD);
            tvEmailAUD = findViewById(R.id.tvEmailAUD);
            tvPhoneAUD = findViewById(R.id.tvPhoneAUD);
            imgBackAUD = findViewById(R.id.imgBackAUD);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
