package com.wyr.garage.data.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "car")
public class Car {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "car_id")
    private int carId = 0;

    @ColumnInfo(name = "car_number")
    private String carNumber;

    @ColumnInfo(name = "car_type")
    private String carType;

    public int getCarId() {
        return carId;
    }

    public void setCarId(int carId) {
        this.carId = carId;
    }

    public String getCarNumber() {
        return carNumber;
    }

    public void setCarNumber(String carNumber) {
        this.carNumber = carNumber;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    @Override
    public String toString() {
        return "Car{" +
                "carId=" + carId +
                ", carNumber='" + carNumber + '\'' +
                ", carType='" + carType + '\'' +
                '}';
    }
}
