package com.wyr.garage.ui.car;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wyr.garage.data.model.Car;
import com.wyr.garage.databinding.ActivityCarBinding;
import com.wyr.garage.db.AppDatabase;

import java.util.List;

public class CarActivity extends AppCompatActivity {
    private ActivityCarBinding mViewBinding;
    CarRecyclerViewAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityCarBinding.inflate(LayoutInflater.from(this));
        setContentView(mViewBinding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("车辆管理");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mAdapter = new CarRecyclerViewAdapter(null);
        mViewBinding.carRecyclerView.addItemDecoration(new SpaceItemDecoration(10));
        mViewBinding.carRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mViewBinding.carRecyclerView.setAdapter(mAdapter);

        mViewBinding.addCarBtn.setOnClickListener(view -> {
            Intent intent = new Intent(CarActivity.this, NewCarActivity.class);
            startActivity(intent);
        });
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
        List<Car> carList = AppDatabase.getInstance().carDao().getCarList();
        mAdapter.setData(carList);
    }
}
