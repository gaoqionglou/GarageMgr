package com.wyr.garage.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity(tableName = "order")
public class Order {

    public static final int ORDER_STATUS_RESERVE = 1;
    public static final int ORDER_STATUS_COMPLETED = 2;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id")
    private int orderId;

    @ColumnInfo(name = "user_id")
    private int userId;

    @ColumnInfo(name = "car_id")
    private int carId;

    @ColumnInfo(name = "garage_id")
    private int garageId;

    @ColumnInfo(name = "parking_space_id")
    private int parkingSpaceId;

    @ColumnInfo(name = "time")
    private long time;

    @ColumnInfo(name = "price")
    private int price;

    @ColumnInfo(name = "status")
    private int status;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public int getGarageId() {
        return garageId;
    }

    public void setGarageId(int garageId) {
        this.garageId = garageId;
    }

    public long getTime() {
        return time;
    }

    public String getFormatTime() {
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(int parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }


    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", carId=" + carId +
                ", garageId=" + garageId +
                ", parkingSpaceId=" + parkingSpaceId +
                ", time=" + time +
                ", price=" + price +
                ", status=" + status +
                '}';
    }
}
