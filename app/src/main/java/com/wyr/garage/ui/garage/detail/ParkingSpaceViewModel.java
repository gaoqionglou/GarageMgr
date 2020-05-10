package com.wyr.garage.ui.garage.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.wyr.garage.data.model.ParkingSpace;
import com.wyr.garage.db.AppDatabase;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

public class ParkingSpaceViewModel extends ViewModel {
    private  int mGarageId;
    MutableLiveData<List<ParkingSpace>> mParkingSpaceLiveData = new MutableLiveData<>();

    public ParkingSpaceViewModel(int garageId) {
        mGarageId = garageId;
        loadAllParkingSpace();
    }

    public ParkingSpaceViewModel setGarageId(int garageId){
        this.mGarageId = garageId;
        return  this;
    }

    MutableLiveData<List<ParkingSpace>> getParkingSpaceLiveData() {
        return mParkingSpaceLiveData;
    }

    void loadAllParkingSpace() {
        List<ParkingSpace> list = AppDatabase.getInstance().parkingSpaceDao().getParkingSpaceListByGarageId(mGarageId);
        mParkingSpaceLiveData.setValue(list);
    }

    void loadRemainingParkingSpace() {
        List<ParkingSpace> list = AppDatabase.getInstance().parkingSpaceDao().getRemainingParkingSpaces(mGarageId);
        mParkingSpaceLiveData.setValue(list);
    }


    public static class ParkingSpaceViewModelFactory implements ViewModelProvider.Factory {

        private static ParkingSpaceViewModelFactory mInstance;
        int garageId;

        public static ParkingSpaceViewModelFactory getInstance(int garageId) {
            if (mInstance == null) {
                mInstance = new ParkingSpaceViewModelFactory(garageId);
            }
            return mInstance;
        }

        private ParkingSpaceViewModelFactory(int garageId) {
            this.garageId = garageId;
        }


        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            try {
                return modelClass.getConstructor(int.class).newInstance(garageId);
            } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
