package com.wyr.garage.ui.info;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.wyr.garage.data.model.Info;
import com.wyr.garage.databinding.FragmentInfoBinding;

import java.util.List;


public class InfoFragment extends Fragment {

    private InfoViewModel mViewModel;

    private FragmentInfoBinding mViewBinding;
    private InfoRecyclerViewAdapter mAdapter;

    public static InfoFragment newInstance() {
        return new InfoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewBinding = FragmentInfoBinding.inflate(LayoutInflater.from(getContext()));

        mViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);
        mAdapter = new InfoRecyclerViewAdapter(getContext(), null);

        mViewBinding.infoRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mViewBinding.infoRecyclerView.setAdapter(mAdapter);
        mViewModel.getInfoList().observe(getViewLifecycleOwner(), new Observer<List<Info>>() {
            @Override
            public void onChanged(List<Info> infoList) {
                mAdapter.setData(infoList);
            }
        });
        return mViewBinding.getRoot();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(InfoViewModel.class);
        // TODO: Use the ViewModel
    }

}
