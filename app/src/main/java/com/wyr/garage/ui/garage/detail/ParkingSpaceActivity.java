package com.wyr.garage.ui.garage.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;

import com.wyr.garage.data.model.Garage;
import com.wyr.garage.data.model.ParkingSpace;
import com.wyr.garage.databinding.ActivityParkingSpaceBinding;
import com.wyr.garage.db.AppDatabase;
import com.wyr.garage.util.Constants;

import java.util.List;

public class ParkingSpaceActivity extends AppCompatActivity {
    private ActivityParkingSpaceBinding mViewBinding;
    private ParkingSpaceViewModel mViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("车位信息");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        mViewBinding = ActivityParkingSpaceBinding.inflate(LayoutInflater.from(this));
        setContentView(mViewBinding.getRoot());

        int garageId = getIntent().getIntExtra(Constants.EXTRA_GARAGE_ID, -1);
        if (garageId == -1) {
            finish();
            return;
        }

        mViewModel =new ViewModelProvider(this, ParkingSpaceViewModel.ParkingSpaceViewModelFactory.getInstance(garageId))
                .get(ParkingSpaceViewModel.class);

        mViewModel.setGarageId(garageId);


        Garage garage = AppDatabase.getInstance().garageDao().getGarageById(garageId);
        mViewBinding.garageNameView.setText(String.format("车库名：%s", garage.getName()));


        ParkingSpaceAdapter adapter = new ParkingSpaceAdapter();
        mViewBinding.parkingSpaceRecyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        mViewBinding.parkingSpaceRecyclerView.setAdapter(adapter);


        mViewModel.getParkingSpaceLiveData().observe(this, adapter::setData);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewModel.loadAllParkingSpace();
    }
}
