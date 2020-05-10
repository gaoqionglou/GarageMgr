package com.wyr.garage.ui.map;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.model.AMapCarInfo;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.navi.view.RouteOverLay;
import com.wyr.garage.AppApplication;
import com.wyr.garage.R;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.databinding.FragmentMapBinding;
import com.wyr.garage.ui.reserve.ReserveSpinnerAdapter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class GarageMapFragment extends Fragment implements AMap.OnMapTouchListener,
        AMap.OnMyLocationChangeListener {

    private FragmentMapBinding mViewBinding;

    private GarageMapViewModel mViewModel;
    private AMapNavi mAMapNavi;
    private AMap mAmap;

    /**
     * 保存当前算好的路线
     */
    private SparseArray<RouteOverLay> routeOverlays = new SparseArray<RouteOverLay>();
    /**
     * 路线计算成功标志位
     */
    private boolean calculateSuccess = false;
    /**
     * 当前用户选中的路线，在下个页面进行导航
     */
    private int routeIndex;
    private boolean chooseRouteSuccess = false;
    private boolean followMove = true;

    //声明mlocationClient对象
    private AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    private AMapLocationClientOption mLocationOption = null;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mViewModel =
                ViewModelProviders.of(this).get(GarageMapViewModel.class);
//        final TextView textView = root.findViewById(R.id.text_notifications);
//        mViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });
        mViewBinding = FragmentMapBinding.inflate(LayoutInflater.from(getContext()));
        mViewBinding.mapView.onCreate(savedInstanceState);
        mAmap = mViewBinding.mapView.getMap();
        mAmap.setMyLocationEnabled(true);
        mAmap.setOnPolylineClickListener(mOnPolylineClickListener);

        //获取AMapNavi实例
        mAMapNavi = AMapNavi.getInstance(AppApplication.getContext());
        //添加监听回调，用于处理算路成功
        mAMapNavi.addAMapNaviListener(mAMapNaviListener);

        MyLocationStyle myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        mAmap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        mAmap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        mAmap.moveCamera(CameraUpdateFactory.zoomTo(17));
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);

        mAmap.setOnMapTouchListener(this);
        mAmap.setOnMyLocationChangeListener(this);

        ReserveSpinnerAdapter<Garage> garageSpinnerAdapter = new ReserveSpinnerAdapter<>();
        mViewBinding.chooseGarageSpinner.setAdapter(garageSpinnerAdapter);
        mViewBinding.chooseGarageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                startNavigation();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mViewModel.getGarageList().observe(getViewLifecycleOwner(), garageSpinnerAdapter::setData);

        mViewBinding.startNavigationButton.setOnClickListener(view -> {
            Intent gpsintent = new Intent(getContext(), RouteNaviActivity.class);
            gpsintent.putExtra("gps", true);
            startActivity(gpsintent);
        });

        return mViewBinding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        mViewBinding.mapView.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
        mViewBinding.mapView.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mViewBinding.mapView.onDestroy();
        mAMapNavi.removeAMapNaviListener(mAMapNaviListener);
        mAMapNavi.destroy();
    }

    private void startNavigation() {
        clearRoute();
        Garage garage = (Garage) mViewBinding.chooseGarageSpinner.getSelectedItem();

        int strategy=0;
        try {
            strategy = mAMapNavi.strategyConvert(true, false, false, false, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("wyc", "onInitNaviSuccess: " + garage.getLatitude() + ", "
                + garage.getLongitude());
        List<NaviLatLng> end = new ArrayList<>();
        end.add(new NaviLatLng(garage.getLatitude(), garage.getLongitude()));

        List<NaviLatLng> start = new ArrayList<>();
        AMapLocationClient client = new AMapLocationClient(AppApplication.getContext());
        AMapLocation location = client.getLastKnownLocation();
        if (location == null) {
            startLocation(getContext(), end, strategy);
            return;
        }
        start.add(new NaviLatLng(location.getLatitude(), location.getLongitude()));
        mAMapNavi.calculateDriveRoute(start, end, null, strategy);
    }

    public void startLocation(Context context, List<NaviLatLng> end, int strategy) {
        mlocationClient = new AMapLocationClient(context);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(amapLocation -> {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {

                    List<NaviLatLng> start = new ArrayList<>();
                    start.add(new NaviLatLng(amapLocation.getLatitude(), amapLocation.getLongitude()));
                    mAMapNavi.calculateDriveRoute(start, end, null, strategy);

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

    /**
     * 清除当前地图上算好的路线
     */
    private void clearRoute() {
        for (int i = 0; i < routeOverlays.size(); i++) {
            RouteOverLay routeOverlay = routeOverlays.valueAt(i);
            routeOverlay.removeFromMap();
        }
        routeOverlays.clear();
    }

    private void drawRoutes(int routeId, AMapNaviPath path) {
        calculateSuccess = true;
        mAmap.moveCamera(CameraUpdateFactory.changeTilt(0));
        RouteOverLay routeOverLay = new RouteOverLay(mAmap, path, getContext());
        routeOverLay.setTrafficLine(false);
        routeOverLay.addToMap();
        routeOverlays.put(routeId, routeOverLay);
    }

    private AMapNaviListener mAMapNaviListener = new SimpleAMapNaviListener() {

        @Override
        public void onCalculateRouteSuccess(int[] ints) {
            super.onCalculateRouteSuccess(ints);
            Log.d("wyc", "onCalculateRouteSuccess: ");
            //清空上次计算的路径列表。
            routeOverlays.clear();
            HashMap<Integer, AMapNaviPath> paths = mAMapNavi.getNaviPaths();
            for (int i = 0; i < ints.length; i++) {
                AMapNaviPath path = paths.get(ints[i]);
                if (path != null) {
                    drawRoutes(ints[i], path);
                }
            }
        }
    };

    private AMap.OnPolylineClickListener mOnPolylineClickListener = new AMap.OnPolylineClickListener() {
        @Override
        public void onPolylineClick(Polyline polyline) {
            if(routeOverlays != null && routeOverlays.size() == 1){  //路线只有一条，没必要选择路线。
                return;
            }
            List<LatLng> latLngs = polyline.getPoints();
            if (latLngs.size() == 0){//确定获取线段有坐标集合
                return;
            }
            LatLng latLng = latLngs.get(0);//取线段的第一个坐标就好
            outer:
            for (int i = 0; i < routeOverlays.size(); i++) {//遍历路线集合
                int key = routeOverlays.keyAt(i);
                List<NaviLatLng> naviLatLngs = routeOverlays.get(key)
                        .getAMapNaviPath().getCoordList();  //获取路线所有坐标集合
                for (NaviLatLng naviLatLng : naviLatLngs) {//遍历路线的坐标集合
                    if (Math.abs((naviLatLng.getLatitude() - latLng.latitude)) <= 0.000001
                            && Math.abs((naviLatLng.getLongitude()- latLng.longitude)) <= 0.00001){//符合差值范围
                        if (i == routeIndex){   // 已经选过该路线 跳转下一条路线 点击的线段可能是两条路线的重复路段
                            continue outer;
                        }
                        //下面循环方法是绘制没选中路线不高亮
                        for (int j = 0; j < routeOverlays.size(); j++) {
                            if (i == j){    //选中路线 先不用画
                                continue;
                            }
                            int key2 = routeOverlays.keyAt(j);
                            routeOverlays.get(key2).setTransparency(0.4f);
                            routeOverlays.get(key).setZindex(0);
                        }
                        routeOverlays.get(key).setTransparency(1.0f);
                        /**把用户选择的那条路的权值弄高，使路线高亮显示的同时，重合路段不会变的透明**/
                        routeOverlays.get(key).setZindex(1);
                        mAMapNavi.selectRouteId(key);
                        routeIndex = i;
                        chooseRouteSuccess = true;
                        return;
                    }
                }
            }

        }
    };


    @Override
    public void onTouch(MotionEvent motionEvent) {
        mAmap.setMyLocationEnabled(false);
        if (followMove) {
            //用户拖动地图后，不再跟随移动，需要跟随移动时再把这个改成true
            followMove = false;
        }
    }

    @Override
    public void onMyLocationChange(Location location) {
        if (followMove) {
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
            mAmap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }
}
