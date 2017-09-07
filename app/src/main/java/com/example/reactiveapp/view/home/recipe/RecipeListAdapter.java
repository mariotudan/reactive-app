package com.example.reactiveapp.view.home.recipe;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.reactiveapp.R;
import com.example.reactiveapp.model.RecipeModel;
import com.example.reactiveapp.util.GlideApp;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by mario on 24.6.2017..
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.ViewHolder> {
    private List<RecipeModel> recipes;
    private Fragment fragment;

    private final PublishSubject<RecipeModel> onClickSubject = PublishSubject.create();

    // Provide a reference to the views for each data item
    // Complex data newsItems may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        @BindView(R.id.recipe_image)
        ImageView recipeImage;
        @BindView(R.id.recipe_title)
        TextView recipeTitle;
        @BindView(R.id.recipe_description)
        TextView recipeDescription;
        @BindView(R.id.open_button)
        Button openRecipeButton;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecipeListAdapter(List<RecipeModel> recipes, Fragment fragment) {
        this.recipes = recipes;
        this.fragment = fragment;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RecipeListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_card, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final RecipeModel recipeModel = recipes.get(position);
        String name = recipeModel.getName();
        if(name.length() > 35){
            name = name.substring(0, 32) + "...";
        }
        holder.recipeTitle.setText(name);
        holder.recipeDescription.setText(recipeModel.getDescription());

        GlideApp.with(fragment)
                .load(recipeModel.getImageUrl())
                .centerCrop()
                .into(holder.recipeImage);

        Consumer<Object> clickConsumer = o -> onClickSubject.onNext(recipeModel);
        RxView.clicks(holder.recipeImage).subscribe(clickConsumer);
        RxView.clicks(holder.openRecipeButton).subscribe(clickConsumer);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public Observable<RecipeModel> getPositionClicks() {
        return onClickSubject.hide();
    }
}
