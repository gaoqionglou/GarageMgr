package com.wyr.garage.ui.garage;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.wyr.garage.R;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.databinding.FragmentGarageBinding;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class GarageFragment extends Fragment {

    private GarageViewModel garageViewModel;
    private FragmentGarageBinding mViewBinding;
    private GarageRecyclerViewAdapter mAdapter;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        garageViewModel =
                ViewModelProviders.of(this).get(GarageViewModel.class);
        mViewBinding = FragmentGarageBinding.inflate(LayoutInflater.from(getContext()));
        mAdapter = new GarageRecyclerViewAdapter(null);
        DividerItemDecoration dec = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        dec.setDrawable(new ColorDrawable(ContextCompat.getColor(getContext(), R.color.divider_line_color)));

        mViewBinding.garageRecyclerView.addItemDecoration(dec);
        mViewBinding.garageRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewBinding.garageRecyclerView.setAdapter(mAdapter);
        garageViewModel.getGarageList().observe(getViewLifecycleOwner(), new Observer<List<Garage>>() {
            @Override
            public void onChanged(List<Garage> garageList) {
                mAdapter.setData(garageList);
            }
        });

        garageViewModel.startLocation(getContext());
        return mViewBinding.getRoot();
    }

}
