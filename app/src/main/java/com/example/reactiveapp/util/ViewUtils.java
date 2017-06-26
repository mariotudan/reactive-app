package com.example.reactiveapp.util;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.jakewharton.rxbinding2.view.RxView;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Cancellable;
import io.reactivex.functions.Consumer;

/**
 * Created by mario on 24.6.2017..
 */

public class ViewUtils {
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        View currentFocus = activity.getCurrentFocus();
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    public static CompositeDisposable setupUI(View view, final Activity activity) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        setupUI(view, activity, compositeDisposable);
        return compositeDisposable;
    }

    /**
     * Setups UI to hide keyboard when clicked outside EditText view
     *
     * @param view                View to be used
     * @param activity            Activity containing the view
     * @param compositeDisposable Group of disposables
     */
    private static void setupUI(final View view, final Activity activity, CompositeDisposable compositeDisposable) {
        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText || view instanceof Button || view instanceof ImageButton)) {
            compositeDisposable.add(RxView.touches(view)
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<MotionEvent>() {
                        @Override
                        public void accept(@NonNull MotionEvent motionEvent) throws Exception {
                            hideSoftKeyboard(activity);
                        }
                    }));
            /*compositeDisposable.add(Observable.create(new ObservableOnSubscribe<View>() {
                @Override
                public void subscribe(final ObservableEmitter<View> e) throws Exception {
                    view.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(final View view, MotionEvent motionEvent) {
                            hideSoftKeyboard(activity);
                            e.setDisposable(new Disposable() {
                                @Override
                                public void dispose() {
                                    view.setOnTouchListener(null);
                                }

                                @Override
                                public boolean isDisposed() {
                                    return false;
                                }
                            });
                            e.onNext(view);
                            return false;
                        }
                    });
                }
            }).subscribe());*/
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView, activity, compositeDisposable);
            }
        }
    }
}
