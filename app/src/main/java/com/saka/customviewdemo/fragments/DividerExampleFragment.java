package com.saka.customviewdemo.fragments;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saka.customviewdemo.CustomDivider;
import com.saka.customviewdemo.DividerAdapter;
import com.saka.customviewdemo.R;
import com.saka.customviewdemo.databinding.FragmentDividerExampleBinding;
import com.saka.customviewdemo.model.DividerModel;

import java.util.ArrayList;
import java.util.List;


public class DividerExampleFragment extends Fragment {

    private FragmentDividerExampleBinding binding;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List<DividerModel> data = new ArrayList<>();

    public DividerExampleFragment() {
        // Required empty public constructor
    }


    public static DividerExampleFragment newInstance(String param1, String param2) {
        DividerExampleFragment fragment = new DividerExampleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_divider_example, container, false);
        init();
        return binding.getRoot();
    }

    private void init() {
        data.add(new DividerModel("标题1", "今天建党节"));
        data.add(new DividerModel("标题2", "今天建党节"));
        data.add(new DividerModel("标题3", "今天建党节"));
        data.add(new DividerModel("标题4", "今天建党节"));
        data.add(new DividerModel("标题5", "今天建党节"));
        data.add(new DividerModel("标题6", "今天建党节"));
        data.add(new DividerModel("标题7", "今天建党节"));
        data.add(new DividerModel("标题8", "今天建党节"));
        data.add(new DividerModel("标题9", "今天建党节"));
        data.add(new DividerModel("标题10", "今天建党节"));
        data.add(new DividerModel("标题11", "今天建党节"));
        data.add(new DividerModel("标题12", "今天建党节"));
        data.add(new DividerModel("标题13", "今天建党节"));
        data.add(new DividerModel("标题14", "今天建党节"));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.rv.setAdapter(new DividerAdapter(data));
        binding.rv.setLayoutManager(layoutManager);
        binding.rv.addItemDecoration(new CustomDivider(
                getResources().getDrawable(R.drawable.ic_camera_alt_black_24dp)));
    }

}
