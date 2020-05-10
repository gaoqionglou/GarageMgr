package com.wyr.garage.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.wyr.garage.data.LoginRepository;
import com.wyr.garage.data.model.LoggedInUser;
import com.wyr.garage.databinding.ActivityRechargeBinding;
import com.wyr.garage.db.AppDatabase;
import com.wyr.garage.ui.GarageBaseActivity;

public class RechargeActivity extends GarageBaseActivity {
    private ActivityRechargeBinding mViewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionBarTitle("充值");

        mViewBinding = ActivityRechargeBinding.inflate(LayoutInflater.from(this));
        setContentView(mViewBinding.getRoot());

        int userId = LoginRepository.getInstance(null).getLoggedUserId();
        bindView(userId);

        mViewBinding.rechargeView.setOnClickListener(view -> {
            LoggedInUser user = AppDatabase.getInstance().userDao().getUserById(userId);
            String now = mViewBinding.rechageLimitView.getText().toString();
            user.setMonkey(user.getMonkey() + Integer.parseInt(now));
            AppDatabase.getInstance().userDao().updateUser(user);
            bindView(userId);
            mViewBinding.rechageLimitView.setText("");

        });

    }

    private void bindView(int userId) {
        LoggedInUser user = AppDatabase.getInstance().userDao().getUserById(userId);
        mViewBinding.currentMoney.setText("当前额度：" + user.getMonkey());
    }
}
