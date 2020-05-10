package com.wyr.garage.ui.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.wyr.garage.databinding.ActivityRegisterBinding;
import com.wyr.garage.databinding.ActivityUpdateUserBinding;
import com.wyr.garage.db.AppDatabase;

public class UpdateUserInfoActivity extends AppCompatActivity {
    private ActivityUpdateUserBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUpdateUserBinding.inflate(LayoutInflater.from(this));
        setContentView(binding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.update_personal_info);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final int userId = LoginRepository.getInstance(null).getLoggedUserId();
        LoggedInUser user = AppDatabase.getInstance().userDao().getUserById(userId);
        binding.usernameET.setText(user.getDisplayName());
        binding.genderRG.check("男".equals(user.getGender()) ? R.id.male : R.id.female);
        binding.name.setText(user.getName());
        binding.personalId.setText(user.getPersonalId());
        binding.phoneNum.setText(user.getPhoneNumber());
        binding.update.setOnClickListener(view -> update(userId));

        binding.updatePwd.setOnClickListener(view -> {
            Intent intent = new Intent(UpdateUserInfoActivity.this, UpdatePwdActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void update(int userId) {
        LoggedInUser user = AppDatabase.getInstance().userDao().getUserById(userId);
        user.setUserId(userId);
        user.setDisplayName(binding.usernameET.getText().toString());
        int genderButtonId = binding.genderRG.getCheckedRadioButtonId();
        user.setGender(genderButtonId == R.id.male ? "男" : "女");
        user.setName(binding.name.getText().toString());
        user.setPersonalId(binding.personalId.getText().toString());
        user.setPhoneNumber(binding.phoneNum.getText().toString());
        user.setRFINumber("U11111111");

        if (user.hasEmptyItem(false)) {
            Toast.makeText(this, "存在未填写项，请补全", Toast.LENGTH_LONG).show();
            return;
        }

        AppDatabase.getInstance().userDao().updateUser(user);
        Toast.makeText(this, "修改成功", Toast.LENGTH_LONG).show();
        finish();
    }
}
