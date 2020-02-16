package com.wission.web_services;

import com.wission.Model.LoginModel;

import java.util.Map;

import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {


    @FormUrlEncoded
    @POST(WebFields.LOGIN_URL)
    Single<LoginModel> getLogin(@Body Map<String, String> body);

    @POST(WebFields.LOGIN_URL)
    Single<Response<ResponseBody>> getLoginResponce(@Body Map<String, String> body);

}
