package com.wyr.garage.ui.car;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.wyr.garage.data.model.Car;
import com.wyr.garage.databinding.ActivityNewCarBinding;
import com.wyr.garage.db.AppDatabase;

public class NewCarActivity extends AppCompatActivity {
    private ActivityNewCarBinding mViewBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewBinding = ActivityNewCarBinding.inflate(LayoutInflater.from(this));
        setContentView(mViewBinding.getRoot());

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("添加车辆");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        mViewBinding.addCarBtn.setOnClickListener(view -> addCar());

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addCar() {
        Car car = new Car();
        car.setCarNumber(mViewBinding.carNumber.getText().toString());
        car.setCarType(mViewBinding.carType.getText().toString());
        AppDatabase.getInstance().carDao().insertCar(car);
        Toast.makeText(this, "添加成功", Toast.LENGTH_LONG).show();
        finish();
    }
}
