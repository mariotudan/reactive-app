package com.example.reactiveapp.service;

import com.example.reactiveapp.model.LoginRequestModel;
import com.example.reactiveapp.model.NewsModel;
import com.example.reactiveapp.model.RecipeModel;
import com.example.reactiveapp.model.ResponseModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by mario on 24.6.2017..
 */

public class MockService implements LoginService {

    public static Flowable<NewsModel> getNewsItems() {

        final List<NewsModel> list = new ArrayList<>();
        for (int i = 1; i <= 30; i++) {
            list.add(new NewsModel(Integer.toString(i), "Title", "Content"));
        }

        return Flowable.fromIterable(list);
    }

    public static Flowable<RecipeModel> getRecipeItems() {

        final List<RecipeModel> list = new ArrayList<>();
        final List<String> ingredients = new ArrayList<>();
        ingredients.add("Ingredient 1");
        ingredients.add("Ingredient 2");
        final List<String> steps = new ArrayList<>();
        steps.add("Step 1");
        steps.add("Step 2");
        for (int i = 1; i <= 30; i++) {
            list.add(new RecipeModel("Recipe: " + Integer.toString(i), "Recipe description for this awesome food shown in picture", ingredients, steps, "no_picture_url", "no source"));
        }

        return Flowable.fromIterable(list);
    }

    @Override
    public Single<ResponseModel> login(final LoginRequestModel loginModel) {
        final String[] users = new String[]{"mario:1234", "user:1234"};
        return Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .flatMap(new Function<Long, ObservableSource<String>>() {
                    @Override
                    public ObservableSource<String> apply(@NonNull Long ignored) throws Exception {
                        return Observable.fromArray(users);
                    }
                })
                .filter(new Predicate<String>() {
                    @Override
                    public boolean test(@NonNull String s) throws Exception {
                        String[] user = s.split(":");
                        return user[0].equals(loginModel.getUsername()) && user[1].equals(loginModel.getPassword());
                    }
                })
                .map(new Function<String, ResponseModel>() {
                    @Override
                    public ResponseModel apply(@NonNull String ignored) throws Exception {
                        return new ResponseModel(true, "Successful login");
                    }
                })
                .firstOrError()
                .onErrorResumeNext(new Function<Throwable, SingleSource<? extends ResponseModel>>() {
                    @Override
                    public SingleSource<? extends ResponseModel> apply(@NonNull Throwable throwable) throws Exception {
                        return Single.just(new ResponseModel(false, "Login unsuccessful, invalid credentials"));
                    }
                });
    }
}
