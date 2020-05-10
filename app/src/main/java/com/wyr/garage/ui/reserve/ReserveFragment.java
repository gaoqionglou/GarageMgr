package com.wyr.garage.ui.reserve;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.wyr.garage.R;
import com.wyr.garage.data.LoginRepository;
import com.wyr.garage.data.model.Car;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.data.model.LoggedInUser;
import com.wyr.garage.data.model.Order;
import com.wyr.garage.data.model.ParkingSpace;
import com.wyr.garage.databinding.FragmentReserveBinding;
import com.wyr.garage.db.AppDatabase;

import java.util.Arrays;
import java.util.List;

public class ReserveFragment extends Fragment {
    private String TAG = "ReserveFragment";
    private ReserveViewModel reserveViewModel;
    private FragmentReserveBinding mViewBinding;

    private String[] timeArrays = new String[24];
    private int time = -1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewBinding = FragmentReserveBinding.inflate(LayoutInflater.from(getContext()));

        reserveViewModel = ViewModelProviders.of(this).get(ReserveViewModel.class);

        mViewBinding.parkingTime.setOnClickListener((this::chooseParkingTime));

        ReserveSpinnerAdapter<Garage> garageSpinnerAdapter = new ReserveSpinnerAdapter<>();
        mViewBinding.chooseGarageSpinner.setAdapter(garageSpinnerAdapter);

        reserveViewModel.getGarageList().observe(getViewLifecycleOwner(), new Observer<List<Garage>>() {
            @Override
            public void onChanged(List<Garage> garages) {
                garageSpinnerAdapter.setData(garages);
            }
        });

        ReserveSpinnerAdapter<Car> carSpinnerAdapter = new ReserveSpinnerAdapter<>();
        mViewBinding.chooseCarNumberSpinner.setAdapter(carSpinnerAdapter);

        reserveViewModel.getCarList().observe(getViewLifecycleOwner(), new Observer<List<Car>>() {
            @Override
            public void onChanged(List<Car> cars) {
                carSpinnerAdapter.setData(cars);
            }
        });

        mViewBinding.reserveTV.setOnClickListener(view -> doReserveAction());

        return mViewBinding.getRoot();
    }


    private void doReserveAction() {
        if (time <= 0) {
            Toast.makeText(getContext(), "请选择停车时间段", Toast.LENGTH_SHORT).show();
            return;
        }
        Garage garage = (Garage) mViewBinding.chooseGarageSpinner.getSelectedItem();
        List<ParkingSpace> parkingSpaceList = AppDatabase.getInstance().parkingSpaceDao().getRemainingParkingSpaces(garage.getGarageId());
        if (parkingSpaceList == null || parkingSpaceList.isEmpty()) {
            Toast.makeText(getContext(), "当前车库已满，请选择其他车库", Toast.LENGTH_SHORT).show();
            return;
        }
        ParkingSpace parkingSpace = parkingSpaceList.get(0);

        Order order = new Order();
        order.setCarId((int) mViewBinding.chooseCarNumberSpinner.getSelectedItemId());
        order.setGarageId(garage.getGarageId());
        order.setUserId(LoginRepository.getInstance(null).getLoggedUserId());
        order.setPrice(garage.getPrice() * time);
        order.setTime(System.currentTimeMillis());
        order.setParkingSpaceId(parkingSpace.getParkingSpaceId());
        order.setStatus(Order.ORDER_STATUS_RESERVE);
        AppDatabase.getInstance().orderDao().insert(order);

        parkingSpace.setStatus(ParkingSpace.STATUS_RESERVED);
        AppDatabase.getInstance().parkingSpaceDao().updateParkingSpace(parkingSpace);

        int userId = LoginRepository.getInstance(null).getLoggedUserId();
        LoggedInUser user = AppDatabase.getInstance().userDao().getUserById(userId);
        int now = user.getMonkey() - order.getPrice();
        if (now <= 0) {
            Toast.makeText(getContext(), "账号余额不足，请去充值", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.d("wyc", "doReserveAction: " + now + ", " + user.getMonkey() + ", " + order.getPrice());
        user.setMonkey(now);
        AppDatabase.getInstance().userDao().updateUser(user);

        new AlertDialog.Builder(getContext())
                .setTitle("预约成功")
                .setMessage("车位：" + parkingSpace.getParkingSpaceNumber())
                .setNegativeButton("确定", (dialog, which) -> {
                })
                .show();

    }


    public void chooseParkingTime(View view) {

        for (int i = 0; i < 24; i++) {
            timeArrays[i] = i + ":00";
        }

        View outerView = LayoutInflater.from(getContext()).inflate(R.layout.wheel_view, null);
        WheelView startTime = (WheelView) outerView.findViewById(R.id.wheel_view_start_time);
        WheelView endTime = (WheelView) outerView.findViewById(R.id.wheel_view_end_time);
        startTime.setOffset(2);
        startTime.setItems(Arrays.asList(timeArrays));
        startTime.setSeletion(3);
        startTime.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });

        endTime.setOffset(2);
        endTime.setItems(Arrays.asList(timeArrays));
        endTime.setSeletion(3);
        endTime.setOnWheelViewListener(new WheelView.OnWheelViewListener() {
            @Override
            public void onSelected(int selectedIndex, String item) {
                Log.d(TAG, "[Dialog]selectedIndex: " + selectedIndex + ", item: " + item);
            }
        });

        new AlertDialog.Builder(getContext())
                .setView(outerView)
                .setPositiveButton("确定", (v, var) -> {
                    int startOffset = startTime.getSeletedIndex();
                    int endOffset = endTime.getSeletedIndex();
                    time = endOffset - startOffset;
                    if (endOffset <= startOffset) {
                        Toast.makeText(getContext(), "你所选的时间段有误，请重新选择", Toast.LENGTH_SHORT).show();
                    } else {
                        mViewBinding.parkingTime.setText(startTime.getSeletedItem() + " ~ " + endTime.getSeletedItem());
                    }
                })
                .show();

    }
}
