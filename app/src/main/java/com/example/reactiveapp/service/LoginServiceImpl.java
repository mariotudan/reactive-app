package com.example.reactiveapp.service;

import com.example.reactiveapp.model.LoginRequestModel;
import com.example.reactiveapp.model.ResponseModel;
import com.example.reactiveapp.util.RetrofitUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.http.Body;

/**
 * Created by mario on 25.6.2017..
 */

public class LoginServiceImpl implements LoginService {

    @Override
    public Observable<ResponseModel> login(LoginRequestModel loginRequestModel) {
        LoginService service = RetrofitUtils.createRetrofitService(LoginService.class);
        return service.login(loginRequestModel)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends ResponseModel>>() {
                    @Override
                    public ObservableSource<? extends ResponseModel> apply(@NonNull Throwable throwable) throws Exception {
                        return Observable.just(new ResponseModel(false, throwable.getMessage()));
                    }
                });

    }
}
