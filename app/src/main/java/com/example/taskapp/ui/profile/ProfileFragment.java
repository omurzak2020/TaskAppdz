package com.example.taskapp.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.taskapp.R;

import javax.xml.transform.Result;

public class ProfileFragment extends Fragment {

    private ImageView imageView;
    public final String REQUEST_CODE = "request_code";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.image_view);
        imageView.setOnClickListener(v -> {
            activityResultLauncher.launch("image/*");
        });
    }

    public ActivityResultLauncher<String>
    activityResultLauncher = registerForActivityResult(new
            ActivityResultContracts.GetContent(),


            result -> Glide
                    .with(getContext())
                    .load(result)
                    .into(imageView));
}