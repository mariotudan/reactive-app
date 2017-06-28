package com.example.reactiveapp.service;

import android.util.Log;

import com.example.reactiveapp.model.RecipeModel;
import com.example.reactiveapp.util.RetrofitUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.LongConsumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by mario on 27.6.2017..
 */

public class RecipeService {
    private RecipeService() {
    }

    private static RecipeService instance;

    public static RecipeService getInstance() {
        if (instance == null) {
            instance = new RecipeService();
        }
        return instance;
    }

    public static int LOADED_RECIPE_ITEMS = 0;

    private List<RecipeModel> fetchedRecipes = new ArrayList<>();
    private ReplaySubject<RecipeModel> recipesSubject = ReplaySubject.create();
    private boolean fetchedAll = false;

    private RecipeModel activeRecipe;

    public RecipeModel getActiveRecipe() {
        return activeRecipe;
    }

    public void setActiveRecipe(RecipeModel activeRecipe) {
        this.activeRecipe = activeRecipe;
    }

    public void fetchRecipes() {
        RecipeApiService service = RetrofitUtils.createRetrofitService(RecipeApiService.class);
        Log.d("RecipeService", "Fetching recipes, offset: " + fetchedRecipes.size());
        service.getRecipes(fetchedRecipes.size(), 4)
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<RecipeModel>, ObservableSource<RecipeModel>>() {
                    @Override
                    public ObservableSource<RecipeModel> apply(@NonNull List<RecipeModel> recipeModels) throws Exception {
                        if (recipeModels.size() == 0) {
                            fetchedAll = true;
                        }
                        return Observable.fromIterable(recipeModels);
                    }
                })
                .onErrorResumeNext(new Function<Throwable, ObservableSource<? extends RecipeModel>>() {
                    @Override
                    public ObservableSource<? extends RecipeModel> apply(@NonNull Throwable throwable) throws Exception {
                        Log.d("RecipeService", throwable.getMessage() + "\n" + throwable.getCause());
                        return Observable.just(new RecipeModel("Recipe title", "Recipe description", new ArrayList<String>(), new ArrayList<String>(), "http://lorempixel.com/400/300/food/", "Sample"));
                    }
                })
                .subscribe(new Consumer<RecipeModel>() {
                    @Override
                    public void accept(@NonNull RecipeModel recipeModel) throws Exception {
                        Log.d("RecipeService", "Received recipe " + recipeModel.getName());
                        boolean contains = false;
                        for (RecipeModel r : fetchedRecipes) {
                            if (r.getName().equals(recipeModel.getName())) {
                                contains = true;
                            }
                        }
                        if (!contains) {
                            fetchedRecipes.add(recipeModel);
                            recipesSubject.onNext(recipeModel);
                        }
                    }
                });
    }

    public void syncingRecipes() {
        fetchRecipes();
        Observable.interval(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(@NonNull Long aLong) throws Exception {
                        if (fetchedAll) return;
                        Log.d("RecipeService", "recipeSubject.doOnRequest, LOADED_RECIPE_ITEMS: " + LOADED_RECIPE_ITEMS + ", size: " + fetchedRecipes.size()
                                + ", FETCHING: " + (LOADED_RECIPE_ITEMS + 4 >= fetchedRecipes.size()));
                        if (LOADED_RECIPE_ITEMS + 4 > fetchedRecipes.size()) {
                            fetchRecipes();
                        }
                    }
                });
    }

    public Flowable<RecipeModel> getRecipes() {
        syncingRecipes();
        return recipesSubject.toFlowable(BackpressureStrategy.MISSING);
    }
}
