package com.wyr.garage.ui.garage.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.wyr.garage.R;
import com.wyr.garage.data.model.ParkingSpace;
import com.wyr.garage.databinding.ParkingSpaceRecyclerViewItemBinding;
import com.wyr.garage.ui.car.SelectCarActiivty;
import com.wyr.garage.util.Constants;

import java.util.List;

public class ParkingSpaceAdapter extends RecyclerView.Adapter<ParkingSpaceAdapter.ParkingSpaceVH> {

    private List<ParkingSpace> mParkingSpaceList;

    public void setData(List<ParkingSpace> parkingSpaces) {
        mParkingSpaceList = parkingSpaces;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ParkingSpaceVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ParkingSpaceRecyclerViewItemBinding binding = ParkingSpaceRecyclerViewItemBinding
                .inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ParkingSpaceVH(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ParkingSpaceVH holder, int position) {
        ParkingSpace parkingSpace = mParkingSpaceList.get(position);
        String statusInfo = "";
        if (parkingSpace.getStatus() == ParkingSpace.STATUS_REMAINING) {
            holder.mParkingSpaceNumView.setBackgroundResource(R.color.colorPrimary);
            statusInfo = "空";
        } else if (parkingSpace.getStatus() == ParkingSpace.STATUS_RESERVED) {
            holder.mParkingSpaceNumView.setBackgroundColor(Color.YELLOW);
            statusInfo = "已被预订";
        } else if (parkingSpace.getStatus() == ParkingSpace.STATUS_USED) {
            holder.mParkingSpaceNumView.setBackgroundColor(Color.GRAY);
            statusInfo = "已停";
        }
        holder.mParkingSpaceNumView.setText(String.format("%s\n%s", parkingSpace.getParkingSpaceNumber(), statusInfo));
        holder.itemView.setOnClickListener(view -> {
            if (parkingSpace.getStatus() == ParkingSpace.STATUS_REMAINING) {
                Context context = holder.itemView.getContext();
                Intent intent = new Intent(context, SelectCarActiivty.class);
                intent.putExtra(Constants.EXTRA_GARAGE_ID, parkingSpace.getGarageId());
                intent.putExtra(Constants.EXTRA_PARKING_SPACE_ID, parkingSpace.getParkingSpaceId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mParkingSpaceList == null ? 0 : mParkingSpaceList.size();
    }



    static class ParkingSpaceVH extends RecyclerView.ViewHolder{
        private TextView mParkingSpaceNumView;

        ParkingSpaceVH(ParkingSpaceRecyclerViewItemBinding binding) {
            super(binding.getRoot());
            mParkingSpaceNumView = binding.parkingSpaceNumView;
        }

    }
}
