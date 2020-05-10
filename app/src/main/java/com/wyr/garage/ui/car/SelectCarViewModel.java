package com.wyr.garage.ui.car;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wyr.garage.data.model.Car;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.db.AppDatabase;

import java.util.List;

public class SelectCarViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<List<Car>> carList = new MutableLiveData<>();

    public SelectCarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

        List<Car> carListRaw = AppDatabase.getInstance().carDao().getCarList();
        carList.setValue(carListRaw);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public MutableLiveData<List<Car>> getCarList() {
        return carList;
    }

}