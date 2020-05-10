package com.wyr.garage.ui.reserve;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wyr.garage.data.model.Car;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.db.AppDatabase;

import java.util.List;

public class ReserveViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Car>> carList = new MutableLiveData<>();
    private MutableLiveData<List<Garage>> garageList = new MutableLiveData<>();

    public ReserveViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        List<Car> carListRaw = AppDatabase.getInstance().carDao().getCarList();
        carList.setValue(carListRaw);

        List<Garage> garageListRaw = AppDatabase.getInstance().garageDao().getGarages();
        Log.d("wyc", garageListRaw == null ? "garage list is null" : "garage list size = " + garageListRaw.size());
        garageList.setValue(garageListRaw);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<List<Car>> getCarList() {
        return carList;
    }

    public MutableLiveData<List<Garage>> getGarageList() {
        return garageList;
    }
}