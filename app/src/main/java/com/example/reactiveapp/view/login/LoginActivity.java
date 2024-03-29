package com.example.reactiveapp.view.login;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.reactiveapp.R;
import com.example.reactiveapp.model.LoginRequestModel;
import com.example.reactiveapp.model.ResponseModel;
import com.example.reactiveapp.service.LoginService;
import com.example.reactiveapp.service.LoginServiceImpl;
import com.example.reactiveapp.util.ViewUtils;
import com.example.reactiveapp.view.home.HomeActivity;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * A login screen that offers login via username/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    @BindView(R.id.activity_login)
    View loginView;
    @BindView(R.id.username)
    EditText usernameView;
    @BindView(R.id.password)
    EditText passwordView;
    @BindView(R.id.login_progress)
    View progressView;
    @BindView(R.id.login_form)
    View loginFormView;
    @BindView(R.id.sign_in_button)
    Button signInButton;
    //@BindView(R.id.toolbar_login)
    //Toolbar mToolbar;

    CompositeDisposable viewDisposables;
    CompositeDisposable setupUIDisposables;
    LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginService = new LoginServiceImpl();

        //mToolbar.setTitle("Login");
        //setSupportActionBar(mToolbar);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getColor(R.color.colorPrimary));
        window.setNavigationBarColor(getColor(R.color.colorPrimary));

        // TEMP
        usernameView.setText("mario");
        passwordView.setText("1234");
    }

    @Override
    protected void onStart() {
        super.onStart();
        setupUIDisposables = ViewUtils.setupUI(loginView, this);
        viewDisposables = new CompositeDisposable();

        viewDisposables.add(RxTextView.editorActions(passwordView)
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(@NonNull Integer id) throws Exception {
                        if (id == R.id.login || id == EditorInfo.IME_ACTION_DONE) {
                            attemptLogin();
                        }
                    }
                }));

        viewDisposables.add(RxView.clicks(signInButton)
                .map(new Function<Object, String>() {
                    @Override
                    public String apply(@io.reactivex.annotations.NonNull Object o) throws Exception {
                        return usernameView.getText().toString();
                    }
                })
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull String s) throws Exception {
                        Log.d("LOGIN", s);
                        //Toast.makeText(getApplicationContext(), "Button: " + s, Toast.LENGTH_LONG).show();
                        attemptLogin();
                    }
                }));

        viewDisposables.add(RxTextView.textChanges(usernameView)
                .debounce(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CharSequence>() {
                    @Override
                    public void accept(@io.reactivex.annotations.NonNull CharSequence charSequence) throws Exception {
                        Log.d("LOGIN_TEXT_CHANGES", charSequence.toString());
                        //Toast.makeText(getApplicationContext(), "Text changes: " + charSequence.toString(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    @Override
    protected void onStop() {
        super.onStop();
        viewDisposables.dispose();
        setupUIDisposables.dispose();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        ViewUtils.hideSoftKeyboard(this);

        // Reset errors.
        usernameView.setError(null);
        passwordView.setError(null);

        // Store values at the time of the login attempt.
        String username = usernameView.getText().toString();
        String password = passwordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            usernameView.setError(getString(R.string.error_field_required));
            focusView = usernameView;
            cancel = true;
        } else if (!isUsernameValid(username)) {
            usernameView.setError(getString(R.string.error_invalid_username));
            focusView = usernameView;
            cancel = true;
        }

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            passwordView.setError(getString(R.string.error_field_required));
            focusView = passwordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            focusView = passwordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            loginService.login(new LoginRequestModel(username, password))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ResponseModel>() {
                        @Override
                        public void accept(@io.reactivex.annotations.NonNull ResponseModel responseModel) throws Exception {
                            showProgress(false);
                            if (responseModel.getSuccess()) {
                                goToHomeActivity();
                            } else {
                                Toast.makeText(getApplicationContext(), responseModel.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private boolean isUsernameValid(String username) {
        return username.length() > 3;
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 3;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        loginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                loginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    private void goToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}

