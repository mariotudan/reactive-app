package com.example.reactiveapp.view.home.recipe;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.reactiveapp.R;
import com.example.reactiveapp.model.NewsModel;
import com.example.reactiveapp.model.RecipeModel;
import com.example.reactiveapp.service.NewsService;
import com.example.reactiveapp.service.RecipeService;
import com.example.reactiveapp.util.GlideApp;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class RecipeDetailActivity extends AppCompatActivity {

    // UI references.
    @BindView(R.id.activity_recipe_detail)
    View mRecipeDetailView;
    @BindView(R.id.toolbar_recipe)
    Toolbar mToolbar;

    @BindView(R.id.recipe_image)
    ImageView recipeImage;
    @BindView(R.id.recipe_name)
    TextView recipeName;
    @BindView(R.id.recipe_description)
    TextView recipeDescription;
    @BindView(R.id.recipe_ingredients)
    TextView recipeIngredients;
    @BindView(R.id.recipe_steps)
    TextView recipeSteps;

    CompositeDisposable viewDisposables;

    private RecipeModel recipeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);
        ButterKnife.bind(this);

        recipeModel = RecipeService.getInstance().getActiveRecipe();

        mToolbar.setTitle(recipeModel.getName());
        setSupportActionBar(mToolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        recipeName.setText(recipeModel.getName());
        recipeDescription.setText(recipeModel.getDescription());

        GlideApp.with(this)
                .load(recipeModel.getImageUrl())
                .centerCrop()
                .into(recipeImage);

        String ingredients = "";
        for (int i = 0; i < recipeModel.getIngredients().size(); i++) {
            String ingredient = recipeModel.getIngredients().get(i);
            ingredients += ingredient + (i + 1 == recipeModel.getIngredients().size() ? "" : "\n");
        }

        String steps = "";
        for (int i = 0; i < recipeModel.getSteps().size(); i++) {
            String step = recipeModel.getSteps().get(i);
            steps += (i + 1) + ". " + step + (i + 1 == recipeModel.getSteps().size() ? "" : "\n\n");
        }

        recipeIngredients.setText(ingredients);
        recipeSteps.setText(steps);
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

