package com.wyr.garage.ui.mine;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wyr.garage.data.LoginRepository;
import com.wyr.garage.data.model.Car;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.data.model.LoggedInUser;
import com.wyr.garage.data.model.Order;
import com.wyr.garage.data.model.ParkingSpace;
import com.wyr.garage.databinding.FragmentMineBinding;
import com.wyr.garage.db.AppDatabase;
import com.wyr.garage.ui.login.UpdateUserInfoActivity;
import com.wyr.garage.ui.car.CarActivity;
import com.wyr.garage.ui.car.NewCarActivity;
import com.wyr.garage.util.Constants;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MineFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MineFragment extends Fragment {

    private FragmentMineBinding mViewBinding;

    public MineFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MineFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mViewBinding = FragmentMineBinding.inflate(inflater);

        mViewBinding.updatePersonalInfoTV.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), UpdateUserInfoActivity.class);
            startActivity(intent);
        });

        mViewBinding.carInfoTV.setOnClickListener(view -> {

            int carCount = AppDatabase.getInstance().carDao().getCarCount();
            Intent intent = new Intent();
            intent.setClass(getContext(), carCount > 0 ? CarActivity.class : NewCarActivity.class);
            startActivity(intent);
        });

        mViewBinding.orderView.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), OrderActivity.class);
            intent.putExtra("entry", Constants.ENTRY_RESERVATION);
            startActivity(intent);
        });

        mViewBinding.payView.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), OrderActivity.class);
            intent.putExtra("entry", Constants.ENTRY_PARKING_RECORD);
            startActivity(intent);
        });


        mViewBinding.rechargeView.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), RechargeActivity.class);
            startActivity(intent);
        });

        return mViewBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        bindView();
    }

    private void bindView() {
        final int userId = LoginRepository.getInstance(null).getLoggedUserId();
        LoggedInUser user = AppDatabase.getInstance().userDao().getUserById(userId);
        Log.d("wyc", "bindviewwwwwwww" + user.getDisplayName());
        mViewBinding.accountNameTV.setText("账号：" + user.getDisplayName());
        mViewBinding.nameTV.setText("姓名：" + user.getName());
        mViewBinding.genderTV.setText("性别：" + user.getGender());
        mViewBinding.personalIdTV.setText("身份证号：" + user.getPersonalId());
        mViewBinding.phoneNumberTV.setText("手机号码：" + user.getPhoneNumber());
        mViewBinding.rfiNumberTV.setText("RFI卡号：" + user.getRFINumber());


//        Order order = AppDatabase.getInstance().orderDao().getOrderByUserId(userId);
//        if (order == null) {
//            mViewBinding.revertOrderLayout.setVisibility(View.GONE);
//        } else {
//            Garage garage = AppDatabase.getInstance().garageDao().getGarageById(order.getGarageId());
//            Car car = AppDatabase.getInstance().carDao().getCarById(order.getCarId());
//
//            mViewBinding.orderGarageView.setText("预定车库：" + garage.getName());
//            mViewBinding.orderParkingSpaceView.setText("预定车位：" + "100001");
//            mViewBinding.orderTimeView.setText("预定时间：" + order.getFormatTime());
//            mViewBinding.orderCar.setText("预定车辆：" + car.getCarNumber());
//
//            mViewBinding.deleteOrderView.setTag(order);
//        }

    }

}
