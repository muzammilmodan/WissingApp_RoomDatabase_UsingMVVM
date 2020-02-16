package com.wission.ui.addUserDetails;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.EditText;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.wission.Database.DatabaseClient;
import com.wission.Model.UserDetails_Room;
import com.wission.R;

public class UpdateTaskActivity extends AppCompatActivity {

    private EditText edtName, edtPhone, edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_task);

        edtName = findViewById(R.id.editTextTask);
        edtPhone = findViewById(R.id.editTextDesc);
        edtEmail= findViewById(R.id.editTextFinishBy);

        final UserDetails_Room task = (UserDetails_Room) getIntent().getSerializableExtra("userDetails");

        loadTask(task);

        findViewById(R.id.button_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Clicked", Toast.LENGTH_LONG).show();
                updateTask(task);
            }
        });

        findViewById(R.id.button_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateTaskActivity.this);
                builder.setTitle("Are you sure?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTask(task);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog ad = builder.create();
                ad.show();
            }
        });
    }

    private void loadTask(UserDetails_Room task) {
        edtName.setText(task.getUser_name());
        edtPhone.setText(task.getUser_phone());
        edtEmail.setText(task.getUser_email());
        //checkBoxFinished.setChecked(task.isFinished());
    }

    private void updateTask(final UserDetails_Room task) {
        final String sTask = edtName.getText().toString().trim();
        final String sDesc = edtPhone.getText().toString().trim();
        final String sFinishBy = edtEmail.getText().toString().trim();

        if (sTask.isEmpty()) {
            edtName.setError("Enter Name");
            edtName.requestFocus();
            return;
        }

        if (sDesc.isEmpty()) {
            edtPhone.setError("Enter Phone");
            edtPhone.requestFocus();
            return;
        }

        if (sFinishBy.isEmpty()) {
            edtEmail.setError("Enter Email");
            edtEmail.requestFocus();
            return;
        }

        class UpdateTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                task.setUser_name(sTask);
                task.setUser_phone(sDesc);
                task.setUser_email(sFinishBy);

                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .update(task);

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, UserList_RoomActivity.class));
            }
        }

        UpdateTask ut = new UpdateTask();
        ut.execute();
    }


    private void deleteTask(final UserDetails_Room task) {
        class DeleteTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                DatabaseClient.getInstance(getApplicationContext()).getAppDatabase()
                        .taskDao()
                        .delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                Toast.makeText(getApplicationContext(), "Deleted", Toast.LENGTH_LONG).show();
                finish();
                startActivity(new Intent(UpdateTaskActivity.this, UserList_RoomActivity.class));
            }
        }

        DeleteTask dt = new DeleteTask();
        dt.execute();

    }

}
