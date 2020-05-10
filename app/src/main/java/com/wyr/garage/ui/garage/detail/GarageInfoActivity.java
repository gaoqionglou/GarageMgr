package com.wyr.garage.ui.garage.detail;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.wyr.garage.R;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.data.model.ParkingSpaceStatusCount;
import com.wyr.garage.databinding.ActivityGarageInfoBinding;
import com.wyr.garage.db.AppDatabase;
import com.wyr.garage.util.Constants;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GarageInfoActivity extends AppCompatActivity {

    private ActivityGarageInfoBinding mViewBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("车库信息");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mViewBinding = ActivityGarageInfoBinding.inflate(LayoutInflater.from(this));
        setContentView(mViewBinding.getRoot());

        int garageId = getIntent().getIntExtra(Constants.EXTRA_GARAGE_ID, -1);
        if (garageId == -1) {
            finish();
            return;
        }

        Garage garage = AppDatabase.getInstance().garageDao().getGarageById(garageId);
        List<ParkingSpaceStatusCount> parkingSpaceStatusCounts
                = AppDatabase.getInstance().parkingSpaceDao().getParkingSpaceStatusCountByGarageId(garageId);

        Map<Integer, Integer> map = new HashMap<>();
        if (parkingSpaceStatusCounts != null) {
            parkingSpaceStatusCounts.forEach(parkingSpaceStatusCount -> Log.d("wyc", parkingSpaceStatusCount.toString()));
            parkingSpaceStatusCounts.forEach(parkingSpaceStatusCount -> {
                map.put(parkingSpaceStatusCount.getStatus(), parkingSpaceStatusCount.getCount());
            });

        } else {
            Log.d("wyc", "onCreate: xxxxxx");
        }

        int count = 0;
        for (Integer v : map.values()) {
            count += v;
        }
        mViewBinding.garageNameView.setText(String.format("车库名：%s", garage.getName()));
        mViewBinding.parkingSpaceCountView.setText("车位总量：" + count);
        mViewBinding.remainingParkingSpaceView.setText("剩余车位：" + map.get(0));
        mViewBinding.parkingSpceUsageView.setText("已停：" + map.get(2));
        mViewBinding.reserveParkingSpaceView.setText("预定：" + map.get(1));

        mViewBinding.addressView.setText(String.format("地址：%s", garage.getAddress()));
        mViewBinding.priceView.setText(String.format("价格：%s元/小时", garage.getPrice()));

        mViewBinding.showParkingSpaceView.setOnClickListener(view -> {
            Intent intent = new Intent(GarageInfoActivity.this, ParkingSpaceActivity.class);
            intent.putExtra(Constants.EXTRA_GARAGE_ID, garageId);
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
}
