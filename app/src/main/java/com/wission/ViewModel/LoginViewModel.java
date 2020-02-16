package com.wission.ViewModel;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wission.Model.LoginModel;
import com.wission.appUtils.GlobalData;
import com.wission.view_model_response.APIResponse;
import com.wission.web_services.ApiClient;
import com.wission.web_services.ApiService;

import java.io.IOException;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<APIResponse> apiRespnse = new MutableLiveData<>();

    private ApiService apiService;

    @Override
    protected void onCleared() {
        disposables.clear();
    }

    public LiveData<APIResponse> liveDataApiResponse() {
        return apiRespnse;
    }

    public LiveData<APIResponse> callLogin(String strAPIName, Context context,  Map<String, String> loginParam) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
        disposables.add(apiService.getLogin(loginParam)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(__ -> apiRespnse.setValue(APIResponse.loading(GlobalData.LOADING, strAPIName, 0)))
                        .subscribeWith(new DisposableSingleObserver<LoginModel>() {
                            @Override
                            public void onSuccess(LoginModel loginModel) {
                                Log.e("==> ", " LoginResponse " + loginModel);
                                apiRespnse.setValue(APIResponse.success(GlobalData.SUCCESS, loginModel, strAPIName, 0));
                                return;
                            }
                            @Override
                            public void onError(Throwable e) {
                                apiRespnse.setValue(APIResponse.error(GlobalData.ERROR, e, strAPIName));
                                Log.e("==>", "onError: " + e.getMessage());
                            }
                        }));
        return apiRespnse;
    }

    public LiveData<APIResponse> callLoginResponceBody(String strAPIName, Context context,  Map<String, String> loginParam) {
        apiService = ApiClient.getClient(context).create(ApiService.class);
        disposables.add(apiService.getLoginResponce(loginParam)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(__ -> apiRespnse.setValue(APIResponse.loading(GlobalData.LOADING, strAPIName, 0)))
                .subscribeWith(new DisposableSingleObserver<Response<ResponseBody>>() {
                    @Override
                    public void onSuccess(Response<ResponseBody> loginModel) {

                        Log.e("==> MM ", " LoginResponse body:  " + loginModel.body());
                        Log.e("==>MM  ", " LoginResponse message:  " + loginModel.message());
                       /* try {

                            Log.e("==> ", " LoginResponse ddd:  " +   loginModel.body());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*/
                        apiRespnse.setValue(APIResponse.success(GlobalData.SUCCESS, loginModel, strAPIName, 0));
                        return;
                    }
                    @Override
                    public void onError(Throwable e) {
                        apiRespnse.setValue(APIResponse.error(GlobalData.ERROR, e, strAPIName));
                        Log.e("==>", "onError: " + e.getMessage());
                    }
                }));
        return apiRespnse;
    }

}
