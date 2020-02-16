package com.wission.ui.UserList;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.wission.Model.UserData;
import com.wission.Model.User_Constant;
import com.wission.R;
import com.wission.ViewModel.UserViewModel;
import com.wission.appUtils.EmailValidation;
import com.wission.appUtils.KeyboardUtility;
import com.wission.appUtils.UserPrefManager;
import com.wission.interfaces.RecyclerViewItemClicked;
import com.wission.ui.addUserDetails.UserList_RoomActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserViewModel loginViewModel;

    private ProgressDialog pDialog;
    FirebaseStorage storage;
    StorageReference storageReference;
    SharedPreferences user_pref;
    UserData sendUserNode;
    private DatabaseReference mDatabase;
    List<UserData> userList;
    UserListAdapter userAdapter;
    RecyclerView rVwUserListAM;
    TextView tvUserDataNotFound;
    FloatingActionButton fltBtnRoomDatabase;
    Context context;
    ImageView imgVwAddAM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        getIds();
        setListners();

        loginViewModel = ViewModelProviders.of(MainActivity.this).get(UserViewModel.class);
        loginViewModel.getUser().observe(this, response -> userDetailsResponce(response));

        try {

            pDialog = new ProgressDialog(this);
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            storage = FirebaseStorage.getInstance();
            storageReference = storage.getReference();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            linearLayoutManager.setSmoothScrollbarEnabled(false);
            rVwUserListAM.setLayoutManager(linearLayoutManager);
            initUserData();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    String  userName, userEmail, userPhone;

    private void initUserData() {
        try {

            user_pref = new UserPrefManager(MainActivity.this).getUser_pref();
            sendUserNode = new UserData();
            sendUserNode.setUserId(user_pref.getString(User_Constant.user_id, "") + "");
            sendUserNode.setStrUserName(userName + "");
            sendUserNode.setStrUserEmail(userEmail + "");
            sendUserNode.setStrUserPhone(userPhone + "");
            mDatabase = FirebaseDatabase.getInstance().getReference("USER_DETAILS");
            userList = new ArrayList<>();
            mDatabase.addValueEventListener(valueEventListener());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Todo: Get Fcm dataSnapShot data
    ValueEventListener valueEventListener() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    if (dataSnapshot.hasChildren()) {

                        Iterable<DataSnapshot> messageChildren = dataSnapshot.getChildren();
                        userList.clear();

                        for (DataSnapshot message : messageChildren) {

                            String userId = "";
                            String userName = "";
                            String userEmail = "";
                            String userPhone = "";

                            String temp = message.getValue().toString();
                            String[] stringArray = temp.split(",");

                            for (String value : stringArray) {
                                value = value.replace("{", "");
                                value = value.replace("}", "");
                                if (value.contains(User_Constant.user_id))
                                    userId = value.substring(value.indexOf("=") + 1);
                                if (value.contains(User_Constant.user_name))
                                    userName = value.substring(value.indexOf("=") + 1);
                                if (value.contains(User_Constant.user_email))
                                    userEmail = value.substring(value.indexOf("=") + 1);
                                if (value.contains(User_Constant.user_phone))
                                    userPhone = value.substring(value.indexOf("=") + 1);

                            }

                            userList.add(new UserData(userId, userName, userEmail, userPhone));
                        }

                        if (userList.size() > 0) {
                            rVwUserListAM.setVisibility(View.VISIBLE);
                            tvUserDataNotFound.setVisibility(View.GONE);
                            bindData();
                        } else {
                            rVwUserListAM.setVisibility(View.GONE);
                            tvUserDataNotFound.setVisibility(View.VISIBLE);
                        }

                    }else{
                        rVwUserListAM.setVisibility(View.GONE);
                        tvUserDataNotFound.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        };
        return valueEventListener;
    }

    //Todo: After get data using FCM real time database show in listview
    private void bindData() {
        try {


            RecyclerViewItemClicked mListener = new RecyclerViewItemClicked() {

                @Override
                public void onItemClickListener(View view, int position) {

                    String username = userList.get(position).getStrUserName();
                    String userEmail= userList.get(position).getStrUserEmail();
                    String userPhone = userList.get(position).getStrUserPhone();

                    //dialogEditUser(username,userEmail,userPhone);
                }
            };

            userAdapter = new UserListAdapter(userList, MainActivity.this,mListener);
            rVwUserListAM.setAdapter(userAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setListners() {
        imgVwAddAM.setOnClickListener(onClickListener);
        fltBtnRoomDatabase.setOnClickListener(onClickListener);
    }

    //Todo: Clicked listener
    private View.OnClickListener onClickListener = (view) -> {
        switch (view.getId()) {
            case R.id.imgVwAddAM:
                dialogAddUser();
                break;

            case R.id.fltBtnRoomDatabase:
                startActivity(new Intent(MainActivity.this, UserList_RoomActivity.class));
                break;
        }
    };


    Button btnSave;


    //Todo: Open Add User Details Dialog
    private void dialogAddUser() {
        try {
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_user_data);

            EditText etUserName =  dialog.findViewById(R.id.etUserNameDUD);
            EditText etUserEmail =dialog.findViewById(R.id.etUserEmailDUD);
            EditText etUserPhone =  dialog.findViewById(R.id.etUserPhoneDUD);
            ImageView imgCancelDUD = dialog.findViewById(R.id.imgCancelDUD);
            btnSave = dialog.findViewById(R.id.btnSaveDUD);

            imgCancelDUD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userName = etUserName.getText().toString();
                    userEmail = etUserEmail.getText().toString();
                    userPhone = etUserPhone.getText().toString();

                    //Todo: Check validation(Empty or not)
                    if (userName.isEmpty()){
                        Toast.makeText(context, getResources().getString(R.string.empty_user_name), Toast.LENGTH_SHORT).show();
                        etUserName.setFocusable(true);
                    }else if (userEmail.isEmpty()){
                        Toast.makeText(context, getResources().getString(R.string.empty_user_email), Toast.LENGTH_SHORT).show();
                        etUserEmail.setFocusable(true);
                    }else if (!EmailValidation.checkEmailIsCorrect(userEmail)){
                        Toast.makeText(context, getResources().getString(R.string.empty_valid_email), Toast.LENGTH_SHORT).show();
                        etUserEmail.setFocusable(true);
                    }else if (userPhone.isEmpty()){
                        Toast.makeText(context, getResources().getString(R.string.empty_user_phone), Toast.LENGTH_SHORT).show();
                        etUserPhone.setFocusable(true);
                    }else if (userPhone.length()<10){
                        Toast.makeText(context, getResources().getString(R.string.empty_valid_phone), Toast.LENGTH_SHORT).show();
                        etUserPhone.setFocusable(true);
                    }else {
                        loginViewModel.callAddUser(userName, userEmail, userPhone);
                        dialog.dismiss();
                    }
                }
            });

            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getIds() {

        imgVwAddAM = findViewById(R.id.imgVwAddAM);
        rVwUserListAM = findViewById(R.id.rVwUserListAM);
        tvUserDataNotFound = findViewById(R.id.tvUserDataNotFound);
        fltBtnRoomDatabase = findViewById(R.id.fltBtnRoomDatabase);
    }

    //Todo: Get User Details as per Using ViewModel class
    private void userDetailsResponce(UserData response) {

        Toast.makeText(context, "Data Added.", Toast.LENGTH_SHORT).show();
        Log.e("res name", "===>" + response.getStrUserName());
        Log.e("res phone", "===>" + response.getStrUserPhone());
        Log.e("res email", "===>" + response.getStrUserEmail());

        try {
            KeyboardUtility.hideKeyboard(context, btnSave);

            HashMap<String, String> userDetails = null;
            userDetails = createUserNode(response);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = database.getReference("USER_DETAILS");
            databaseReference.push().setValue(userDetails);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //Todo: Create User Node and After add user details stored FCM realtime database.
    private HashMap<String, String> createUserNode(UserData response) {
        HashMap<String, String> hashMap = new HashMap<>();
        try {

            hashMap.put(User_Constant.user_id, System.currentTimeMillis() / 1000 + "");
            hashMap.put(User_Constant.user_name, response.getStrUserName());
            hashMap.put(User_Constant.user_phone, response.getStrUserPhone());

            hashMap.put(User_Constant.user_email, response.getStrUserEmail());
            // hashMap.put(User_Constant.time, System.currentTimeMillis() / 1000 + "");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }





    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false);
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(getString(R.string.exit_from_app_message));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                MainActivity.super.onBackPressed();
                MainActivity.this.finishAffinity();
            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
