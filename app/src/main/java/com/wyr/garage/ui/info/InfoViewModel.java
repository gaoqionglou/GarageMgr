package com.wyr.garage.ui.info;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.wyr.garage.data.model.Info;
import com.wyr.garage.db.AppDatabase;

import java.util.List;

public class InfoViewModel extends ViewModel {
    private MutableLiveData<List<Info>> mInfos;

    public InfoViewModel() {
        this.mInfos = new MutableLiveData<List<Info>>();
        List<Info> value = AppDatabase.getInstance().infoDao().getInfoList();
        mInfos.setValue(value);
    }

    public MutableLiveData<List<Info>> getInfoList() {
        return mInfos;
    }


}
