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
import android.widget.TextView;

import com.example.reactiveapp.R;
import com.example.reactiveapp.service.MockService;
import com.example.reactiveapp.util.ViewUtils;

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
                case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    changeFragment(0);
                    return true;
                case R.id.navigation_dashboard:
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
            fragment = new HomeFragment();
        } else if (position == 1) {
            fragment = new TempFragment();
        } else {
            fragment = new TempFragment();
        }
        fragments[position] = fragment;

        getSupportFragmentManager().beginTransaction().replace(
                R.id.content, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        MockService.LOADED_ITEMS = 0;
        super.onBackPressed();
    }
}
