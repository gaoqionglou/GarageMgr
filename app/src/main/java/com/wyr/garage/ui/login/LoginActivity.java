package com.wyr.garage.ui.login;

import android.Manifest;
import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.wyr.garage.MainActivity;
import com.wyr.garage.R;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.data.model.LoggedInUser;
import com.wyr.garage.data.model.ParkingSpace;
import com.wyr.garage.databinding.ActionBarLayoutBinding;
import com.wyr.garage.databinding.ActivityLoginBinding;
import com.wyr.garage.db.AppDatabase;
import com.wyr.garage.ui.login.LoginViewModel;
import com.wyr.garage.ui.login.LoginViewModelFactory;
import com.wyr.garage.util.Utils;

import java.util.List;

public class LoginActivity extends AppCompatActivity {


    private String[] premissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private int REQUEST_PERMISSION_EXTERNAL_STORAGE_CODE = 200;
    private LoginViewModel loginViewModel;
    private ActivityLoginBinding activityLoginBinding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLoginBinding = ActivityLoginBinding.inflate(LayoutInflater.from(this));
        setContentView(activityLoginBinding.getRoot());
        //判断当前系统是否高于或等于6.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //当前系统大于等于6.0
            if (ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            ) {
                //造数据
                Utils.fakeData();
            } else {

                ActivityCompat.requestPermissions(LoginActivity.this, premissions, REQUEST_PERMISSION_EXTERNAL_STORAGE_CODE);
            }
        } else {
            //当前系统小于6.0，直接调用拍照
            //造数据
            Utils.fakeData();
        }

        setCustomActionBar();
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        loginViewModel.getLoginFormState().observe(this, loginFormState -> {
            if (loginFormState == null) {
                return;
            }
            activityLoginBinding.login.setEnabled(loginFormState.isDataValid());
            if (loginFormState.getUsernameError() != null) {
                activityLoginBinding.username.setError(getString(loginFormState.getUsernameError()));
            }
            if (loginFormState.getPasswordError() != null) {
                activityLoginBinding.password.setError(getString(loginFormState.getPasswordError()));
            }
        });

        loginViewModel.getLoginResult().observe(this, loginResult -> {

            if (loginResult == null) {
                return;
            }
            activityLoginBinding.loading.setVisibility(View.GONE);
            if (loginResult.getError() != null) {
                showLoginFailed(loginResult.getError());
                return;
            }
            if (loginResult.getSuccess() != null) {
                updateUiWithUser(loginResult.getSuccess());
            }
            setResult(Activity.RESULT_OK);

            //Complete and destroy login activity once successful
            finish();
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(activityLoginBinding.username.getText().toString(),
                        activityLoginBinding.password.getText().toString());
            }
        };
        activityLoginBinding.username.addTextChangedListener(afterTextChangedListener);
        activityLoginBinding.password.addTextChangedListener(afterTextChangedListener);
        activityLoginBinding.password.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                loginViewModel.login(activityLoginBinding.username.getText().toString(),
                        activityLoginBinding.password.getText().toString());
            }
            return false;
        });

        activityLoginBinding.login.setOnClickListener(view -> {
            activityLoginBinding.loading.setVisibility(View.VISIBLE);
            loginViewModel.login(activityLoginBinding.username.getText().toString(),
                    activityLoginBinding.password.getText().toString());
        });

        activityLoginBinding.register.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


        List<LoggedInUser> user = AppDatabase.getInstance().userDao().getUsers();
        if (user != null) {
            user.forEach(user1 -> Log.d("wyc", user1.toString()));
        } else {
            Log.d("wyc", "user is empty");
        }

        activityLoginBinding.fakeData.setOnClickListener(view -> Utils.fakeData());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_EXTERNAL_STORAGE_CODE) {
            int count = 0;
            for (int j = 0; j < grantResults.length; j++) {
                if (grantResults[j] == PackageManager.PERMISSION_GRANTED) {
                    count++;
                }
            }
            if (count == premissions.length) {
                Toast.makeText(this, "赋予权限成功", Toast.LENGTH_SHORT).show();
                Utils.fakeData();
            }else {
                Toast.makeText(this, "请赋予权限", Toast.LENGTH_SHORT).show();
            }


        }
    }

    private void setCustomActionBar() {
        ActionBarLayoutBinding actionBarViewBinding = ActionBarLayoutBinding.inflate(LayoutInflater.from(this));
        ActionBar.LayoutParams lp = new ActionBar.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT, Gravity.CENTER);
        View mActionBarView = actionBarViewBinding.getRoot();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setCustomView(mActionBarView, lp);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayShowTitleEnabled(false);

        actionBarViewBinding.title.setText(R.string.app_name);
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }


}
