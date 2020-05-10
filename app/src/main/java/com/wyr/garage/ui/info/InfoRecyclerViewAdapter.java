package com.wyr.garage.ui.info;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.wyr.garage.R;
import com.wyr.garage.data.model.Info;
import com.wyr.garage.ui.web.H5Activity;

import java.util.List;

public class InfoRecyclerViewAdapter extends RecyclerView.Adapter<InfoRecyclerViewAdapter.InfoViewHolder> {

    private List<Info> mInfoList;
    private Context mContext;

    public InfoRecyclerViewAdapter(Context context, List<Info> infoList) {
        mInfoList = infoList;
        mContext = context;
    }

    public void setData(List<Info> infoList) {
        mInfoList = infoList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public InfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.info_recycler_view_item, parent, false);
        return new InfoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoViewHolder holder, int position) {
        Info info = mInfoList.get(position);
        holder.viewBinding.title.setText(info.getTitle());
        holder.viewBinding.content.setText(info.getDesc());
        Glide.with(mContext).load(info.getImageUrl()).into(holder.viewBinding.imageView);

        holder.viewBinding.getRoot().setOnClickListener(view -> {
            Intent intent = new Intent(mContext, H5Activity.class);

            intent.putExtra("url", info.getUrl());
            intent.putExtra("title", info.getTitle());
            mContext.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return mInfoList == null ? 0 : mInfoList.size();
    }


    static class InfoViewHolder extends RecyclerView.ViewHolder {
        com.wyr.garage.databinding.InfoRecyclerViewItemBinding viewBinding;


        public InfoViewHolder(@NonNull View itemView) {
            super(itemView);
            viewBinding = com.wyr.garage.databinding.InfoRecyclerViewItemBinding.bind(itemView);
        }
    }

}
