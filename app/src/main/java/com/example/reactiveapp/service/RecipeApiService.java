package com.example.reactiveapp.service;

import com.example.reactiveapp.model.RecipeModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by mario on 28.6.2017..
 */

public interface RecipeApiService {

    @GET("recipesLazy/{offset}/{limit}")
    Observable<List<RecipeModel>> getRecipes(@Path("offset") Integer offset, @Path("limit") Integer limit);
}
