package com.wission.ui.addUserDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.wission.Database.DatabaseClient;
import com.wission.Model.UserDetails_Room;
import com.wission.R;

public class AddUser_RoomActivity extends AppCompatActivity {

    private EditText editTextTask, editTextDesc, editTextFinishBy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user__room);

        editTextTask = findViewById(R.id.editTextTask);
        editTextDesc = findViewById(R.id.editTextDesc);
        editTextFinishBy = findViewById(R.id.editTextFinishBy);

        findViewById(R.id.button_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveTask();
            }
        });
    }

    private void saveTask() {
        final String sName = editTextTask.getText().toString().trim();
        final String sPhone= editTextDesc.getText().toString().trim();
        final String sEmail= editTextFinishBy.getText().toString().trim();

        if (sName.isEmpty()) {
            editTextTask.setError("Name required");
            editTextTask.requestFocus();
            return;
        }

        if (sPhone.isEmpty()) {
            editTextDesc.setError("Phone required");
            editTextDesc.requestFocus();
            return;
        }

        if (sEmail.isEmpty()) {
            editTextFinishBy.setError("Email required");
            editTextFinishBy.requestFocus();
            return;
        }

        class SaveTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {

                //creating a user
                UserDetails_Room task = new UserDetails_Room();
                task.setUser_name(sName);
                task.setUser_email(sEmail);
                task.setUser_phone(sPhone);

                //adding to database
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().insert(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(), UserList_RoomActivity.class));
                Toast.makeText(getApplicationContext(), "Saved", Toast.LENGTH_LONG).show();
            }
        }

        SaveTask st = new SaveTask();
        st.execute();
    }
}
