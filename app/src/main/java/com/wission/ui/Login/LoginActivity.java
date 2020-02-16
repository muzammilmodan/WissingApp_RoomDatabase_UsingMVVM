package com.wission.ui.Login;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.lifecycle.ViewModelStoreOwner;

import com.wission.Model.LoginModel;
import com.wission.R;
import com.wission.ViewModel.LoginViewModel;
import com.wission.appUtils.GlobalData;
import com.wission.appUtils.GlobalMethod;
import com.wission.ui.UserList.MainActivity;
import com.wission.view_model_response.APIResponse;
import com.wission.web_services.WebFields;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private LoginModel loginModel;
    Response<ResponseBody> responseBody;

    EditText edtNationaId, edtPassword;
    TextView tvLogin,tvLoginR;

    private String strDeviceId = "ddfdddd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getIds();
        setListner();
        setData();
    }

    private void setData() {
        loginViewModel = ViewModelProviders.of(LoginActivity.this).get(LoginViewModel.class);

    }


    private void setListner() {
        tvLogin.setOnClickListener(onClickListener);
        tvLoginR.setOnClickListener(onClickListener);
    }

    private View.OnClickListener onClickListener = (view) -> {
        switch (view.getId()) {
            case R.id.tvLogin:
                loginViewModel.liveDataApiResponse().observe(this, response ->logInResponce(response));
                checkLoginValidation();
                break;

            case R.id.tvLoginR:
                loginViewModel.liveDataApiResponse().observe(this, response ->logInResponceBodyResponce(response));

              otherDemo();
                break;

        }
    };

    private void otherDemo() {
        strEmail = edtNationaId.getText().toString();
        strPassword = edtPassword.getText().toString();

        Map<String, String> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put(WebFields.REQUEST_NATIONAL_ID, strEmail);
        stringObjectHashMap.put(WebFields.REQUEST_PASSWORD, strPassword);
        stringObjectHashMap.put(WebFields.REQUEST_DEVICE_ID, strDeviceId);
        loginViewModel.callLoginResponceBody(WebFields.LOGIN_URL, LoginActivity.this, stringObjectHashMap);
    }

    private void logInResponceBodyResponce(APIResponse apiResponse) {
        switch (apiResponse.strCurrentStatus) {
            case GlobalData.LOADING:
                if (!GlobalMethod.isProgressDialogShowing(LoginActivity.this)) {
                    GlobalMethod.showProgressDialog(LoginActivity.this);
                }
                break;
            case GlobalData.SUCCESS:
                if (GlobalMethod.isProgressDialogShowing(LoginActivity.this)) {
                    GlobalMethod.hideProgressDialog(LoginActivity.this);
                }
                switch (apiResponse.strApiName) {
                    case WebFields.LOGIN_URL:
                        APIResponse<Response<ResponseBody>> loginModelAPIResponse = apiResponse;
                        responseBody = loginModelAPIResponse.liveData;
                        String responce = responseBody.toString();
                        Log.e("responce==>","::::"+responce);

                        if (responseBody != null) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            GlobalMethod.showToast(LoginActivity.this, "Login Failed");
                        }
                        break;


                }
                break;
            case GlobalData.ERROR:
                GlobalMethod.hideProgressDialog(LoginActivity.this);
                GlobalMethod.showToast(LoginActivity.this, apiResponse.error.getMessage());
                break;
        }

    }

    String strEmail,strPassword;
    private void checkLoginValidation() {
        strEmail = edtNationaId.getText().toString();
        strPassword = edtPassword.getText().toString();

        Map<String, String> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put(WebFields.REQUEST_NATIONAL_ID, strEmail);
        stringObjectHashMap.put(WebFields.REQUEST_PASSWORD, strPassword);
        stringObjectHashMap.put(WebFields.REQUEST_DEVICE_ID, strDeviceId);
        loginViewModel.callLogin(WebFields.LOGIN_URL, LoginActivity.this, stringObjectHashMap);
    }

    private void logInResponce(APIResponse apiResponse) {
        switch (apiResponse.strCurrentStatus) {
            case GlobalData.LOADING:
                if (!GlobalMethod.isProgressDialogShowing(LoginActivity.this)) {
                    GlobalMethod.showProgressDialog(LoginActivity.this);
                }
                break;
            case GlobalData.SUCCESS:
                if (GlobalMethod.isProgressDialogShowing(LoginActivity.this)) {
                    GlobalMethod.hideProgressDialog(LoginActivity.this);
                }
                switch (apiResponse.strApiName) {
                    case WebFields.LOGIN_URL:
                        APIResponse<LoginModel> loginModelAPIResponse = apiResponse;
                        loginModel = loginModelAPIResponse.liveData;
                        if (loginModel.getData() != null) {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            GlobalMethod.showToast(LoginActivity.this, "Login Failed");
                        }
                        break;


                }
                break;
            case GlobalData.ERROR:
                GlobalMethod.hideProgressDialog(LoginActivity.this);
                GlobalMethod.showToast(LoginActivity.this, apiResponse.error.getMessage());
                break;
        }
    }


    private void getIds() {
        edtNationaId = findViewById(R.id.edtNationaId);
        edtPassword = findViewById(R.id.edtPassword);
        tvLogin = findViewById(R.id.tvLogin);
        tvLoginR = findViewById(R.id.tvLoginR);
    }
}
