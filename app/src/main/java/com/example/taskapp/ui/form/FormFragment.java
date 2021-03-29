package com.example.taskapp.ui.form;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.taskapp.App;
import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.example.taskapp.interfaces.OnClickPositionRecycler;
import com.example.taskapp.models.Note;
import com.example.taskapp.ui.home.HomeFragment;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class FormFragment extends Fragment {

    private EditText editText;
    private Button button;
    private Note note;

    private String dateString;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_form, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText = view.findViewById(R.id.editText);
        button = view.findViewById(R.id.btnSave);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        if (getArguments() != null){
            retrieve();
        }
    }

    private void retrieve() {
        note = (Note)getArguments().getSerializable(HomeFragment.EDIT_KEY);
        if (note != null){
            editText.setText(note.getTitle());
        }
    }

    private void save() {
        String text = editText.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm yyyy/MM/dd", Locale.ROOT);
        dateString = dateFormat.format(System.currentTimeMillis());

        Note note = new Note(text,dateString);
        App.getDataBase().notDao().insert(note);

        Bundle bundle = new Bundle();
        bundle.putSerializable("note", note);
        getParentFragmentManager().setFragmentResult("rk_task", bundle);
        ((MainActivity) requireActivity()).closeFragment();
    }


}