package com.wyr.garage.ui.garage;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wyr.garage.R;
import com.wyr.garage.data.model.Garage;
import com.wyr.garage.databinding.GarageRecyclerViewItemBinding;
import com.wyr.garage.ui.garage.detail.GarageInfoActivity;
import com.wyr.garage.util.Constants;

import java.text.DecimalFormat;
import java.util.List;

public class GarageRecyclerViewAdapter extends RecyclerView.Adapter<GarageRecyclerViewAdapter.GarageViewHolder> {

    private List<Garage> mGarageList;

    public GarageRecyclerViewAdapter(List<Garage> garageList) {
        mGarageList = garageList;
    }

    public void setData(List<Garage> garageList) {
        mGarageList = garageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GarageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.garage_recycler_view_item, parent, false);
        return new GarageViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GarageViewHolder holder, int position) {
        Garage garage = mGarageList.get(position);
        holder.viewBinding.name.setText(garage.getName());
        holder.viewBinding.address.setText(garage.getAddress());
        holder.viewBinding.distance.setText(getDistance(garage.getDistance()));
        holder.viewBinding.getRoot().setOnClickListener(view -> {
            Context context = holder.itemView.getContext();
            Intent intent = new Intent(context, GarageInfoActivity.class);
            intent.putExtra(Constants.EXTRA_GARAGE_ID, garage.getGarageId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mGarageList == null ? 0 : mGarageList.size();
    }


    static class GarageViewHolder extends RecyclerView.ViewHolder {
        GarageRecyclerViewItemBinding viewBinding;


        public GarageViewHolder(@NonNull View itemView) {
            super(itemView);
            viewBinding = GarageRecyclerViewItemBinding.bind(itemView);
        }
    }

    private String getDistance(float distance) {
        DecimalFormat df=new DecimalFormat("#.#");
        if (distance > 1000) {
            return df.format(distance/1000) + "公里";
        } else {
            return df.format(distance) + "米";
        }
    }
}
