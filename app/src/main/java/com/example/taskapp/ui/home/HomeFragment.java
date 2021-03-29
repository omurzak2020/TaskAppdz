package com.example.taskapp.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.RecyclerView;
import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.interfaces.OnClickPositionRecycler;
import com.example.taskapp.models.Note;
import java.util.List;

public class HomeFragment extends Fragment  {

    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private OnClickPositionRecycler onClickPositionRecycler;
    public static final String EDIT_KEY = "edit_key";
    private Note note;

    private boolean isEditing = false;// helps to figure out to detect if we are going to add or edit
    private int posEdited;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         adapter = new TaskAdapter();
         List<Note> list = App.getDataBase().notDao().getAll();
         adapter.addList(list);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.recyclelView);
        initList();
        view.findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isEditing = true;
                NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
                navController.navigate(R.id.action_navigation_home_to_formFragment);
            }
        });
        setFragmentListener();
    }

    private void setFragmentListener() {
        getParentFragmentManager().setFragmentResultListener("rk_task",
                getViewLifecycleOwner(),
                new FragmentResultListener() {
            @Override
            public void onFragmentResult(@NonNull String requestKey, @NonNull Bundle result) {
                 note  = (Note) result.getSerializable("note");
                if (isEditing) {
                    adapter.updateItem(posEdited,note);
                    App.getDataBase().notDao().updateItem(note);
                }else{
                    adapter.addItem(note);
                    App.getDataBase().notDao().insert(note);
                }
            }
        });
    }

    private void initList() {
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL));
        adapter.setOnClickPositionRecycler(new OnClickPositionRecycler() {
            @Override
            public void onClick(int position) {
                posEdited = position;
                Bundle bundle = new Bundle();
                bundle.putSerializable(EDIT_KEY, adapter.getItem(position));
                Navigation.findNavController(getView()).navigate(R.id.action_navigation_home_to_formFragment,bundle);
                isEditing = true;

               // Toast.makeText(requireContext(),"позиция"+ position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(int position) {

                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setIcon(R.drawable.ic_delete);
                builder.setTitle("Delete this dialog! " + position);
                builder.setPositiveButton("yes", (dialog, which) -> {

                    adapter.deleteElement(position,note);
                    App.getDataBase().notDao().delete(note);

                    Toast.makeText(requireContext(), "позиция" + position, Toast.LENGTH_SHORT).show();
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(requireContext(), "позиция" + position, Toast.LENGTH_SHORT).show();
                    }
                });
                builder.create().show();
            }

        });
    }


}