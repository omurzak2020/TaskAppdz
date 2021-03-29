package com.example.taskapp.ui.board;

import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator;

public class BoardFragment extends Fragment implements BoardAdapter.ImgClick, BoardAdapter.BtnClick {

    private ViewPager2 viewPager;
    private BoardAdapter adapter;
    private Button button;
    private SpringDotsIndicator dotsIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_board, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById(R.id.viewPager);
        dotsIndicator = view.findViewById(R.id.spring_dots_indicator);
        initView();
        requireActivity().getOnBackPressedDispatcher().addCallback(
                getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                requireActivity().finish();
            }
        });
    }

    private void initView() {
        adapter = new BoardAdapter();
        viewPager.setAdapter(adapter);
        adapter.setClick(this);
        dotsIndicator.setViewPager2(viewPager);
    }

    @Override
    public void onImgClick() {
        ((MainActivity)requireActivity()).closeFragment();
        Log.e("TAG", "onImgClick: clicked x" );
    }

    @Override
    public void onbtnClick() {
        ((MainActivity)requireActivity()).closeFragment();
    }
}