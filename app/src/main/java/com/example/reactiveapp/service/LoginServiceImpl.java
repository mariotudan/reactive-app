package com.example.reactiveapp.service;

import com.example.reactiveapp.model.LoginRequestModel;
import com.example.reactiveapp.model.ResponseModel;
import com.example.reactiveapp.util.RetrofitUtils;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mario on 25.6.2017..
 */

public class LoginServiceImpl implements LoginService {

    @Override
    public Single<ResponseModel> login(LoginRequestModel loginRequestModel) {
        LoginService service = RetrofitUtils.createRetrofitService(LoginService.class);
        return service.login(loginRequestModel)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new Function<Throwable, SingleSource<? extends ResponseModel>>() {
                    @Override
                    public SingleSource<? extends ResponseModel> apply(@NonNull Throwable throwable) throws Exception {
                        return Single.just(new ResponseModel(false, throwable.getMessage()));
                    }
                });
    }
}
