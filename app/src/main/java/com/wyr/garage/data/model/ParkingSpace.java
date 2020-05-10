package com.wyr.garage.data.model;

import androidx.annotation.IntDef;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "parking_space")
public class ParkingSpace {
    public static final int STATUS_REMAINING = 0;
    public static final int STATUS_RESERVED = 1;
    public static final int STATUS_USED = 2;


    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "parking_space_id")
    int parkingSpaceId;

    @ColumnInfo(name = "garage_id")
    int garageId;

    @ColumnInfo(name = "status")
    int status;

    @ColumnInfo(name = "parking_space_num")
    String parkingSpaceNumber;

    public int getParkingSpaceId() {
        return parkingSpaceId;
    }

    public void setParkingSpaceId(int parkingSpaceId) {
        this.parkingSpaceId = parkingSpaceId;
    }

    public int getGarageId() {
        return garageId;
    }

    public void setGarageId(int garageId) {
        this.garageId = garageId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getParkingSpaceNumber() {
        return parkingSpaceNumber;
    }

    public void setParkingSpaceNumber(String parkingSpaceNumber) {
        this.parkingSpaceNumber = parkingSpaceNumber;
    }

    @Override
    public String toString() {
        return "ParkingSpace{" +
                "parkingSpaceId=" + parkingSpaceId +
                ", garageId=" + garageId +
                ", status=" + status +
                ", parkingSpaceNumber='" + parkingSpaceNumber + '\'' +
                '}';
    }
}
