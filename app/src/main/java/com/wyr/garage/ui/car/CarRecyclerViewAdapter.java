package com.wyr.garage.ui.car;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wyr.garage.R;
import com.wyr.garage.data.model.Car;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.databinding.CarRecyclerViewItemBinding;
import com.wyr.garage.databinding.GarageRecyclerViewItemBinding;
import com.wyr.garage.db.AppDatabase;

import java.util.List;

public class CarRecyclerViewAdapter extends RecyclerView.Adapter<CarRecyclerViewAdapter.CarViewHolder> {

    private List<Car> mCarList;

    public CarRecyclerViewAdapter(List<Car> carList) {
        mCarList = carList;
    }

    public void setData(List<Car> carList) {
        mCarList = carList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_recycler_view_item, parent, false);
        return new CarViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarViewHolder holder, int position) {
        Car car = mCarList.get(position);
        holder.viewBinding.carNumber.setText(car.getCarNumber());
        holder.viewBinding.cartype.setText(car.getCarType());
        holder.viewBinding.delete.setOnClickListener(view -> deleteCar(car));
    }

    @Override
    public int getItemCount() {
        return mCarList == null ? 0 : mCarList.size();
    }


    static class CarViewHolder extends RecyclerView.ViewHolder {
        CarRecyclerViewItemBinding viewBinding;


        public CarViewHolder(@NonNull View itemView) {
            super(itemView);
            viewBinding = CarRecyclerViewItemBinding.bind(itemView);
        }
    }

    private void deleteCar(Car car) {
        AppDatabase.getInstance().carDao().deleteCar(car);
        List<Car> carList = AppDatabase.getInstance().carDao().getCarList();
        setData(carList);
    }
}
