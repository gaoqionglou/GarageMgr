package com.wyr.garage.ui.garage;

import android.content.Context;
import android.util.Log;

import androidx.arch.core.executor.ArchTaskExecutor;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.LatLng;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.db.AppDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GarageViewModel extends ViewModel {

    private MutableLiveData<List<Garage>> mGarages;
    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;

    public GarageViewModel() {
        mGarages = new MutableLiveData<>();
        List<Garage> garages = AppDatabase.getInstance().garageDao().getGarages();
        mGarages.setValue(garages);
    }



    public MutableLiveData<List<Garage>> getGarageList() {
        return mGarages;
    }

    public void startLocation(Context context) {
        mlocationClient = new AMapLocationClient(context);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(amapLocation -> {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息

                    calculateDistance(amapLocation.getLatitude(), amapLocation.getLongitude());
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        });

        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }


    private void calculateDistance(double lat, double lng) {
        LatLng currentLocation = new LatLng(lat, lng);
        List<Garage> list = mGarages.getValue();
        if (list != null) {
            list.forEach(garage -> {
                LatLng garageLocation = new LatLng(garage.getLatitude(), garage.getLongitude());
                float distance = AMapUtils.calculateLineDistance(currentLocation,garageLocation);
                garage.setDistance(distance);
            });
        }
        mGarages.setValue(list);
    }
}