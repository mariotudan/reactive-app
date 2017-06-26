package com.example.reactiveapp.service;

import com.example.reactiveapp.model.LoginRequestModel;
import com.example.reactiveapp.model.ResponseModel;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by mario on 25.6.2017..
 */

public interface LoginService {

    @POST("login")
    Single<ResponseModel> login(@Body LoginRequestModel loginRequestModel);
}
