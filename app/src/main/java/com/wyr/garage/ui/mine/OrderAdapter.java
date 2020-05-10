package com.wyr.garage.ui.mine;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.wyr.garage.R;
import com.wyr.garage.data.LoginRepository;
import com.wyr.garage.data.model.Car;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.data.model.LoggedInUser;
import com.wyr.garage.data.model.Order;
import com.wyr.garage.data.model.ParkingSpace;
import com.wyr.garage.databinding.OrderRecyclerViewItemBinding;
import com.wyr.garage.db.AppDatabase;
import com.wyr.garage.util.Constants;
import com.wyr.garage.util.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class OrderAdapter extends Adapter<OrderAdapter.OrderVH> {

    private List<Order> mData;


    private boolean isCountDownStatus = false;

    private Context mContext;

    private int entry = 200;

    public OrderAdapter(Context context, int entry) {
        this.mContext = context;
        this.entry = entry;
    }

    public void setData(List<Order> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrderVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_recycler_view_item, parent, false);
        return new OrderVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderVH holder, int position) {
        Order order = mData.get(position);


        if (entry == Constants.ENTRY_PARKING_RECORD) {
            holder.currentOrderTitle.setVisibility(View.GONE);
            holder.garageNameView.setText(getGarageNameByGarageId(order.getGarageId()));
            holder.garageNameView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            holder.garageNameView.setTextSize(20);
            holder.parkingSpaceNumView.setText(getParkingSpaceById(order.getParkingSpaceId()));
            holder.carNumView.setText(getCarNumByCarId(order.getCarId()));

            holder.orderPrice.setVisibility(View.GONE);
            holder.orderStatus.setVisibility(View.VISIBLE);
            holder.llCountDownLayout.setVisibility(View.GONE);
            holder.deleteView.setVisibility(View.GONE);

        } else if (entry == Constants.ENTRY_RESERVATION) {
            holder.currentOrderTitle.setVisibility(View.VISIBLE);
            holder.garageNameView.setText(getGarageNameByGarageId(order.getGarageId()));
            holder.garageNameView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            holder.garageNameView.setTextSize(16);
            holder.parkingSpaceNumView.setText(getParkingSpaceById(order.getParkingSpaceId()));
            holder.carNumView.setText(getCarNumByCarId(order.getCarId()));

            holder.orderPrice.setText("订单价格：" + order.getPrice() + "元");


            holder.orderPrice.setVisibility(View.VISIBLE);
            holder.orderStatus.setVisibility(View.GONE);
            holder.llCountDownLayout.setVisibility(View.VISIBLE);
            holder.deleteView.setVisibility(View.VISIBLE);

        }


        holder.orderTimeView.setText("时间：" + order.getFormatTime());
        holder.orderTimeView.setVisibility(View.VISIBLE);

        holder.orderStatus.setTextColor(Color.RED);
        if (order.getStatus() == Order.ORDER_STATUS_COMPLETED) {

            holder.orderStatus.setText("已完成");

        } else if (order.getStatus() == Order.ORDER_STATUS_RESERVE) {

            holder.orderStatus.setText("已预约");
        }

        holder.deleteView.setOnClickListener(view -> {
            AppDatabase.getInstance().orderDao().delete(order);

            int orderStatus = order.getStatus();
            if (orderStatus == Order.ORDER_STATUS_RESERVE) {
                int parkingSpaceId = order.getParkingSpaceId();
                ParkingSpace parkingSpace = AppDatabase.getInstance().parkingSpaceDao().getParkingSpaceById(parkingSpaceId);
                parkingSpace.setStatus(ParkingSpace.STATUS_REMAINING);
                AppDatabase.getInstance().parkingSpaceDao().updateParkingSpace(parkingSpace);

                int userId = LoginRepository.getInstance(null).getLoggedUserId();
                LoggedInUser user = AppDatabase.getInstance().userDao().getUserById(userId);
                int now = user.getMonkey() + order.getPrice();
                user.setMonkey(now);
                AppDatabase.getInstance().userDao().updateUser(user);
                Log.i("wyc", "已取消预约，已退还" + order.getPrice() + "元到账户余额中");
                Toast.makeText(mContext, "已取消预约，已退还" + order.getPrice() + "元到账户余额中", Toast.LENGTH_SHORT).show();
            }


            refreshData(order);
        });
        holder.chronometer.setTag(isCountDownStatus);
        holder.llCountDownLayout.setOnClickListener(view -> {

            Boolean isCountDown = (Boolean) holder.chronometer.getTag();
            if (!isCountDown) {
                holder.arrivalCountDown.setText("点击离开");
                holder.chronometer.setVisibility(View.VISIBLE);
                holder.chronometer.setFormat("");
                holder.chronometer.setBase(0); // set base value
                holder.chronometer.setTag(true);
                holder.chronometer.start();
            } else {
                Utils.showDB();
                new AlertDialog.Builder(mContext).setTitle("提示")
                        .setMessage("确认离开吗？")
                        .setNegativeButton("再等一会儿", null)
                        .setPositiveButton("好的", (v, var) -> {
                            AppDatabase.getInstance().orderDao().delete(order);
                            order.setStatus(Order.ORDER_STATUS_COMPLETED);
                            AppDatabase.getInstance().orderDao().insert(order);
                            //停止计时
                            holder.chronometer.stop();
                            Toast.makeText(mContext, "订单已完成，可在【停车记录】中查看完成的订单", Toast.LENGTH_SHORT).show();
                            refreshData(order);
                        }).show();

            }
        });
        holder.chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            long value = -1;

            @Override
            public void onChronometerTick(Chronometer cArg) {
                if (value == -1) {
                    // chronometer.setBase(0); // the base time value
                    value = cArg.getBase();
                } else {
                    value++; // timer add
                }
                cArg.setText(getTime(value));
            }
        });

    }

    private void refreshData(Order order) {
        List<Order> orderList = AppDatabase.getInstance().orderDao().getOrderByUserId(order.getUserId());
        List<Order> orders = new ArrayList<>();
        for (Order o : orderList) {
            if (o.getStatus() == Order.ORDER_STATUS_RESERVE) {
                orders.add(o);
            }
        }
        setData(orders);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    static class OrderVH extends RecyclerView.ViewHolder {
        TextView currentOrderTitle;
        TextView garageNameView;
        TextView parkingSpaceNumView;
        TextView orderTimeView;
        TextView carNumView;
        TextView deleteView;
        TextView arrivalCountDown;
        Chronometer chronometer;
        LinearLayout llCountDownLayout;
        TextView orderPrice;
        TextView orderStatus;

        public OrderVH(View itemView) {
            super(itemView);
            OrderRecyclerViewItemBinding binding = OrderRecyclerViewItemBinding.bind(itemView);
            currentOrderTitle = binding.currentOrderTitle;
            garageNameView = binding.orderGarageView;
            parkingSpaceNumView = binding.orderParkingSpaceView;
            orderTimeView = binding.orderTimeView;
            carNumView = binding.orderCar;
            deleteView = binding.deleteOrderView;
            arrivalCountDown = binding.arrivalCountDown;
            chronometer = binding.chronometer;
            llCountDownLayout = binding.llCountDownLayout;
            orderPrice = binding.orderPrice;
            orderStatus = binding.orderStatus;
        }

    }

    private String getCarNumByCarId(int carId) {
        Car car = AppDatabase.getInstance().carDao().getCarById(carId);
        return "车辆：" + car.getCarNumber();
    }

    private String getGarageNameByGarageId(int garageId) {
        Garage garage = AppDatabase.getInstance().garageDao().getGarageById(garageId);
        return "车库：" + garage.getName();
    }

    private String getParkingSpaceById(int parkingSpaceId) {
        ParkingSpace parkingSpace = AppDatabase.getInstance().parkingSpaceDao().getParkingSpaceById(parkingSpaceId);
        return "车位：" + parkingSpace.getParkingSpaceNumber();
    }

    /**
     * 转化时间文本
     *
     * @param m 秒数
     * @return 展示的时间文本
     */
    private String getTime(long m) {

        if (m < 60) {//秒

            return numFormat(0) + "分" + numFormat(m) + "秒";
        }

        if (m < 3600) {//分

            return numFormat(m / 60) + "分" + numFormat(m % 60) + "秒";
        }

        if (m < 3600 * 24) {//时

            return numFormat(m / 60 / 60) + "时" + numFormat(m / 60 % 60) + "分" + numFormat(m % 60) + "秒";
        }

        if (m >= 3600 * 24) {//天

            return numFormat(m / 60 / 60 / 24) + "天" + numFormat(m / 60 / 60 % 24) + "时" + numFormat(m / 60 % 60) + "分" + numFormat(m % 60) + "秒";
        }

        return "--";
    }

    private String numFormat(long i) {
        if (String.valueOf(i).length() < 2) {
            return "0" + i;
        } else {
            return String.valueOf(i);
        }
    }


}
