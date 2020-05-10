package com.wyr.garage.ui.mine;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wyr.garage.data.LoginRepository;
import com.wyr.garage.data.model.Order;
import com.wyr.garage.databinding.ActivityOrderBinding;
import com.wyr.garage.db.AppDatabase;
import com.wyr.garage.ui.GarageBaseActivity;
import com.wyr.garage.util.Constants;
import com.wyr.garage.util.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends GarageBaseActivity {
    private ActivityOrderBinding mViewBinding;

    private int entry = Constants.ENTRY_PARKING_RECORD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        entry = getIntent().getIntExtra("entry", Constants.ENTRY_PARKING_RECORD);
        if (entry == Constants.ENTRY_RESERVATION) {
            setActionBarTitle("预约订单信息");
        } else if (entry == Constants.ENTRY_PARKING_RECORD) {
            setActionBarTitle("停车记录");
        }
        mViewBinding = ActivityOrderBinding.inflate(LayoutInflater.from(this));
        setContentView(mViewBinding.getRoot());


        OrderAdapter adapter = new OrderAdapter(this, entry);
        mViewBinding.orderRecyclerView.addItemDecoration(new SpaceItemDecoration());
        mViewBinding.orderRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mViewBinding.orderRecyclerView.setAdapter(adapter);


        int userId = LoginRepository.getInstance(null).getLoggedUserId();
        List<Order> orderList = AppDatabase.getInstance().orderDao().getOrderByUserId(userId);
        List<Order> reservedOrders = new ArrayList<>();
        for (Order o : orderList) {
            if (o.getStatus() == Order.ORDER_STATUS_RESERVE) {
                reservedOrders.add(o);
            }
        }
        if (entry == Constants.ENTRY_RESERVATION) {

            adapter.setData(reservedOrders);
        } else if (entry == Constants.ENTRY_PARKING_RECORD) {

            adapter.setData(orderList);
        }


    }
}
