package com.example.reactiveapp.view.home.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reactiveapp.R;
import com.example.reactiveapp.model.NewsModel;
import com.example.reactiveapp.service.MockService;
import com.example.reactiveapp.service.NewsService;
import com.example.reactiveapp.view.common.EndlessRecyclerOnScrollListener;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by mario on 26.6.2017..
 */

public class NewsFragment extends Fragment {

    @BindView(R.id.fragment_news)
    View mNewsView;

    @BindView(R.id.news_recycler)
    RecyclerView mRecyclerView;
    private NewsListAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    List<NewsModel> newsItems = new ArrayList<>();
    Subscription newsItemsSubscription;

    CompositeDisposable viewDisposables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new NewsListAdapter(newsItems);
        MockService.getNewsItems()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsModel>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        newsItemsSubscription = s;
                        s.request(Math.max(NewsService.LOADED_NEWS_ITEMS, 4));
                    }

                    @Override
                    public void onNext(NewsModel newsModel) {
                        newsItems.add(newsModel);
                        NewsService.LOADED_NEWS_ITEMS = newsItems.size();
                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable t) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });

        mRecyclerView.setAdapter(mAdapter);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewDisposables = new CompositeDisposable();

        viewDisposables.add(Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Object> e) throws Exception {
                final EndlessRecyclerOnScrollListener listener = new EndlessRecyclerOnScrollListener(mLayoutManager) {
                    @Override
                    public void onLoadMore(int current_page) {
                        e.onNext(current_page);
                    }
                };
                mRecyclerView.addOnScrollListener(listener);
                e.setDisposable(new Disposable() {
                    @Override
                    public void dispose() {
                        mRecyclerView.removeOnScrollListener(listener);
                    }

                    @Override
                    public boolean isDisposed() {
                        return false;
                    }
                });
            }
        }).debounce(300, TimeUnit.MILLISECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(@NonNull Object o) throws Exception {
                        newsItemsSubscription.request(4);
                    }
                }));

        viewDisposables.add(mAdapter.getPositionClicks().subscribe(new Consumer<NewsModel>() {
            @Override
            public void accept(@NonNull NewsModel newsModel) throws Exception {
                goToNewsDetails(newsModel);
            }
        }));
    }

    @Override
    public void onStop() {
        super.onStop();
        viewDisposables.dispose();
    }

    private void goToNewsDetails(NewsModel newsModel) {
        NewsService.getInstance().setActiveNews(newsModel);
        Intent intent = new Intent(getActivity(), NewsDetailActivity.class);
        startActivity(intent);
    }
}