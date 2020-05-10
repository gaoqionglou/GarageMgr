package com.wyr.garage.data.model;

import androidx.annotation.NonNull;

public class ParkingSpaceStatusCount {

    private int garageId;
    private int status;
    private int count;

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

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @NonNull
    @Override
    public String toString() {
        return "garageId = " + garageId + ", status = " + status + ", count = " + count;
    }
}
