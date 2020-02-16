package com.wission.ui.addUserDetails;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.wission.Database.DatabaseClient;
import com.wission.Model.UserDetails_Room;
import com.wission.R;

import java.util.List;

public class UserList_RoomActivity extends AppCompatActivity {

    private FloatingActionButton buttonAddTask;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list__room);

        recyclerView = findViewById(R.id.recyclerview_tasks);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        buttonAddTask = findViewById(R.id.fltAddUser);
        buttonAddTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UserList_RoomActivity.this, AddUser_RoomActivity.class);
                startActivity(intent);
            }
        });


        getTasks();

    }


    private void getTasks() {
        class GetTasks extends AsyncTask<Void, Void, List<UserDetails_Room>> {

            @Override
            protected List<UserDetails_Room> doInBackground(Void... voids) {

                List<UserDetails_Room> taskList = DatabaseClient.getInstance(getApplicationContext()).getAppDatabase().taskDao().getAll();

                return taskList;
            }

            @Override
            protected void onPostExecute(List<UserDetails_Room> tasks) {
                super.onPostExecute(tasks);
                if (tasks.size()>0) {
                    UserListAdapter adapter = new UserListAdapter(UserList_RoomActivity.this, tasks);
                    recyclerView.setAdapter(adapter);
                }else {
                    Toast.makeText(UserList_RoomActivity.this, "No Data Found.", Toast.LENGTH_SHORT).show();
                }
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }


}
