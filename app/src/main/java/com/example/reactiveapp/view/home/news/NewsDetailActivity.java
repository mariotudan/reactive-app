package com.example.reactiveapp.view.home.news;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.reactiveapp.R;
import com.example.reactiveapp.model.NewsModel;
import com.example.reactiveapp.service.NewsService;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.CompositeDisposable;

public class NewsDetailActivity extends AppCompatActivity {

    // UI references.
    @BindView(R.id.activity_news_detail)
    View mNewsDetailView;
    @BindView(R.id.toolbar_news)
    Toolbar mToolbar;
    @BindView(R.id.textView)
    TextView textView;

    CompositeDisposable viewDisposables;

    private NewsModel newsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        newsModel = NewsService.getInstance().getActiveNews();

        textView.setText(newsModel.getUser());
        mToolbar.setTitle("News Detail: " + newsModel.getUser());
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

