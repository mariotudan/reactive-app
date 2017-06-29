package com.example.reactiveapp.view.home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Transition;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

import com.example.reactiveapp.R;
import com.example.reactiveapp.service.NewsService;
import com.example.reactiveapp.service.RecipeService;
import com.example.reactiveapp.view.home.news.NewsFragment;
import com.example.reactiveapp.view.home.recipe.RecipesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.activity_home)
    View mMainView;
    @BindView(R.id.toolbar_home)
    Toolbar mToolbar;

    Fragment[] fragments = new Fragment[3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        //set the transition
        Transition ts = new Explode();
        ts.setDuration(5000);
        getWindow().setEnterTransition(ts);
        getWindow().setExitTransition(ts);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        //ViewUtils.setupUI(mMainView, this);
        mToolbar.setTitle("Home");
        setSupportActionBar(mToolbar);

        changeFragment(0);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_recipes:
                    //mTextMessage.setText(R.string.title_home);
                    changeFragment(0);
                    return true;
                case R.id.navigation_news:
                    //mTextMessage.setText(R.string.title_dashboard);
                    changeFragment(1);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    changeFragment(2);
                    return true;
            }
            return false;
        }

    };


    private void changeFragment(int position) {

        Fragment fragment = null;

        if (fragments[position] != null && fragments[position].isAdded()) {
            fragment = fragments[position];
        } else if (position == 0) {
            fragment = new RecipesFragment();
        } else if (position == 1) {
            fragment = new NewsFragment();
        } else {
            fragment = new AboutFragment();
        }
        fragments[position] = fragment;

        switch (position){
            case 0:
                mToolbar.setTitle("Recipes");
                break;
            case 1:
                mToolbar.setTitle("News");
                break;
            case 2:
                mToolbar.setTitle("About");
                break;
        }

        getSupportFragmentManager().beginTransaction().replace(
                R.id.content, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        NewsService.LOADED_NEWS_ITEMS = 0;
        RecipeService.LOADED_RECIPE_ITEMS = 0;
        super.onBackPressed();
    }
}
