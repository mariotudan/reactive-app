package com.example.reactiveapp.view.home.recipe;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.reactiveapp.R;
import com.example.reactiveapp.model.NewsModel;
import com.example.reactiveapp.model.RecipeModel;
import com.example.reactiveapp.service.NewsService;
import com.example.reactiveapp.service.RecipeService;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class RecipeDetailActivity extends AppCompatActivity {

    // UI references.
    @BindView(R.id.activity_recipe_detail)
    View mRecipeDetailView;
    @BindView(R.id.toolbar_recipe)
    Toolbar mToolbar;
    @BindView(R.id.textView)
    TextView textView;

    CompositeDisposable viewDisposables;

    private RecipeModel recipeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        recipeModel = RecipeService.getInstance().getActiveRecipe();

        textView.setText(recipeModel.getName());
        mToolbar.setTitle(recipeModel.getName());
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewDisposables = new CompositeDisposable();
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewDisposables.dispose();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

