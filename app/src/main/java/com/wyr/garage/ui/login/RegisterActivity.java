package com.wyr.garage.ui.login;

import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.wyr.garage.R;
import com.wyr.garage.data.model.LoggedInUser;
import com.wyr.garage.databinding.ActivityRegisterBinding;
import com.wyr.garage.db.AppDatabase;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.register);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        binding.register.setOnClickListener(view -> register());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void register() {
        LoggedInUser user = new LoggedInUser();
        user.setDisplayName(binding.usernameET.getText().toString());
        user.setPassword(binding.password.getText().toString());
        int genderButtonId = binding.genderRG.getCheckedRadioButtonId();
        user.setGender(genderButtonId == R.id.male ? "男" : "女");

        user.setName(binding.name.getText().toString());
        user.setPersonalId(binding.personalId.getText().toString());
        user.setPhoneNumber(binding.phoneNum.getText().toString());
        user.setRFINumber("U11111111");

        if (user.hasEmptyItem(true)) {
            Toast.makeText(this, "存在未填写项，请补全", Toast.LENGTH_LONG).show();
            return;
        }

        AppDatabase.getInstance().userDao().insertUser(user);
        Toast.makeText(this, "注册成功", Toast.LENGTH_LONG).show();
        finish();
    }
}
