package com.example.reactiveapp.view.home;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reactiveapp.R;
import com.example.reactiveapp.service.MockService;
import com.example.reactiveapp.util.ViewUtils;
import com.jakewharton.rxbinding2.view.RxView;

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
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by mario on 26.6.2017..
 */

public class HomeFragment extends Fragment {

    @BindView(R.id.fragment_home)
    View mHomeView;

    @BindView(R.id.home_recycler)
    RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    List<String> items = new ArrayList<>();
    Subscription itemsSubscription;

    CompositeDisposable viewDisposables;
    CompositeDisposable setupUIDisposables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, rootView);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new HomeListAdapter(items);
        MockService.getStringItems()
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {
                        itemsSubscription = s;
                        s.request(Math.max(MockService.LOADED_ITEMS, 4));
                    }

                    @Override
                    public void onNext(String s) {
                        items.add(s);
                        MockService.LOADED_ITEMS = items.size();
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
        //setupUIDisposables = ViewUtils.setupUI(mHomeView, getActivity());
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
                        itemsSubscription.request(4);
                    }
                }));
    }

    @Override
    public void onStop() {
        super.onStop();
        viewDisposables.dispose();
    }
}