package com.wyr.garage.ui.reserve;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wyr.garage.R;
import com.wyr.garage.data.model.Car;
import com.wyr.garage.data.model.Garage;

import java.util.List;

public class ReserveSpinnerAdapter<T> extends BaseAdapter {
    private List<T> mData;

    public void setData(List<T> data) {
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public T getItem(int position) {
        return mData == null ? null : mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mData == null) {
            return -1;
        }
        T t = mData.get(position);
        if (t instanceof Garage) {
            return ((Garage) t).getGarageId();
        } else if (t instanceof Car) {
            return ((Car) t).getCarId();
        }
        return 0;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        T t = mData.get(position);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserve_spinner_item_dropdown, parent, false);
        TextView titleView = view.findViewById(android.R.id.text1);
        if (t instanceof Garage) {
            titleView.setText(((Garage) t).getName());
        } else if (t instanceof Car) {
            titleView.setText(((Car) t).getCarNumber());
        }
        return view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        T t = mData.get(position);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reserve_spinner_item, parent, false);
        TextView titleView = view.findViewById(android.R.id.text1);
        if (t instanceof Garage) {
            titleView.setText(((Garage) t).getName());
        } else if (t instanceof Car) {
            titleView.setText(((Car) t).getCarNumber());
        }
        return view;
    }
}
