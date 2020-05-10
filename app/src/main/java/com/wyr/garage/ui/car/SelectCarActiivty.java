package com.wyr.garage.ui.car;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.wyr.garage.R;
import com.wyr.garage.data.LoginRepository;
import com.wyr.garage.data.model.Car;
import com.wyr.garage.data.model.Order;
import com.wyr.garage.data.model.ParkingSpace;
import com.wyr.garage.databinding.ActivitySelectCarBinding;
import com.wyr.garage.db.AppDatabase;
import com.wyr.garage.ui.reserve.ReserveSpinnerAdapter;
import com.wyr.garage.ui.reserve.WheelView;
import com.wyr.garage.util.Constants;

import java.util.Arrays;

public class SelectCarActiivty extends AppCompatActivity {

    private SelectCarViewModel mViewModel;
    private ActivitySelectCarBinding mViewBinding;
    private int time = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("选择车辆");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mViewBinding = ActivitySelectCarBinding.inflate(LayoutInflater.from(this));
        setContentView(mViewBinding.getRoot());

        mViewModel = new ViewModelProvider(this).get(SelectCarViewModel.class);

        ReserveSpinnerAdapter<Car> carSpinnerAdapter = new ReserveSpinnerAdapter<>();
        mViewBinding.chooseCarNumberSpinner.setAdapter(carSpinnerAdapter);

        mViewModel.getCarList().observe(this, carSpinnerAdapter::setData);

        mViewBinding.reserveTV.setOnClickListener(view -> doReserveAction());
    }

    public void chooseParkingTime(View view) {

        String[] timeArrays = new String[24];
        for (int i = 0; i < 24; i++) {
            timeArrays[i] = i + ":00";
        }

        View outerView = LayoutInflater.from(this).inflate(R.layout.wheel_view, null);
        WheelView startTime = (WheelView) outerView.findViewById(R.id.wheel_view_start_time);
        WheelView endTime = (WheelView) outerView.findViewById(R.id.wheel_view_end_time);
        startTime.setOffset(2);
        startTime.setItems(Arrays.asList(timeArrays));
        startTime.setSeletion(3);
        startTime.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {

            }
        });

        endTime.setOffset(2);
        endTime.setItems(Arrays.asList(timeArrays));
        endTime.setSeletion(3);
        endTime.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {

            }
        });

        new AlertDialog.Builder(this)
                .setView(outerView)
                .setPositiveButton("确定", (v, var) -> {
                    int startOffset = startTime.getSeletedIndex();
                    int endOffset = endTime.getSeletedIndex();
                    time = endOffset - startOffset;
                    if (endOffset <= startOffset) {
                        Toast.makeText(this, "你所选的时间段有误，请重新选择", Toast.LENGTH_SHORT).show();
                    } else {
                        mViewBinding.chooseParkingTime.setText(startTime.getSeletedItem() + " ~ " + endTime.getSeletedItem());
                    }
                })
                .show();

    }

    private void doReserveAction() {
        if (time <= 0  ) {
            Toast.makeText(this, "请选择停车时间段", Toast.LENGTH_SHORT).show();
            return;
        }
        final int parkingSpaceId = getIntent().getIntExtra(Constants.EXTRA_PARKING_SPACE_ID, -1);

        Order order = new Order();
        order.setCarId((int) mViewBinding.chooseCarNumberSpinner.getSelectedItemId());
        order.setGarageId(getIntent().getIntExtra(Constants.EXTRA_GARAGE_ID, -1));
        order.setUserId(LoginRepository.getInstance(null).getLoggedUserId());
        order.setParkingSpaceId(parkingSpaceId);
        order.setTime(System.currentTimeMillis());
        order.setStatus(Order.ORDER_STATUS_RESERVE);
        AppDatabase.getInstance().orderDao().insert(order);

        ParkingSpace parkingSpace = AppDatabase.getInstance().parkingSpaceDao().getParkingSpaceById(parkingSpaceId);
        parkingSpace.setStatus(ParkingSpace.STATUS_RESERVED);
        AppDatabase.getInstance().parkingSpaceDao().updateParkingSpace(parkingSpace);

        new AlertDialog.Builder(this)
                .setTitle("预定成功")
                .setMessage("车位：" + parkingSpace.getParkingSpaceNumber())
                .setNegativeButton("确定", (dialog, which) -> {
                    finish();
                })
                .show();
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
