package com.wyr.garage.ui.login;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.wyr.garage.R;
import com.wyr.garage.data.LoginRepository;
import com.wyr.garage.data.model.LoggedInUser;
import com.wyr.garage.databinding.ActivityUpdatePwdBinding;
import com.wyr.garage.db.AppDatabase;

public class UpdatePwdActivity extends AppCompatActivity {
    private ActivityUpdatePwdBinding mViewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewBinding = ActivityUpdatePwdBinding.inflate(LayoutInflater.from(this));
        setContentView(mViewBinding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("修改密码");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mViewBinding.update.setOnClickListener(view -> updatePwd());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updatePwd() {
        int userId = LoginRepository.getInstance(null).getLoggedUserId();
        LoggedInUser user = AppDatabase.getInstance().userDao().getUserById(userId);
        String oldPwd = mViewBinding.oldPwdEditText.getText().toString();
        if (!TextUtils.equals(oldPwd, user.getPassword())) {
            Toast.makeText(this, "原密码不正确，请重新输入", Toast.LENGTH_SHORT).show();
            return;
        }

        String pwd = mViewBinding.password.getText().toString();
        String rePwd = mViewBinding.rePassword.getText().toString();

        if (!TextUtils.equals(pwd, rePwd)) {
            Toast.makeText(this, "两次新密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }

        user.setPassword(pwd);
        user.setUserId(userId);

        AppDatabase.getInstance().userDao().updateUser(user);
        finish();
        Toast.makeText(this, "密码设置成功", Toast.LENGTH_SHORT).show();
    }
}
