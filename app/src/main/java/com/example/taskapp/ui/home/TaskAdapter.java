  package com.example.taskapp.ui.home;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.App;
import com.example.taskapp.R;
import com.example.taskapp.interfaces.OnClickPositionRecycler;
import com.example.taskapp.models.Note;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


  public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder>{

    private ArrayList<Note> list = new ArrayList<>() ;
    private OnClickPositionRecycler onClickPost;

      @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.list_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addList(List<Note> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(Note note) {
        list.add(0, note);
        notifyItemInserted(list.indexOf(note));
        notifyDataSetChanged();
    }

      public void deleteElement(int pos, Note note) {
          App.getDataBase().notDao().delete(note);
          list.set(pos,note);
          list.remove(note);
//          notifyItemRemoved(pos);
          notifyDataSetChanged();
      }



      public void updateItem(int pos, Note note) {
        list.set(pos,note);
        notifyItemChanged(pos);
        notifyDataSetChanged();
      }
      public void setOnClickPositionRecycler(OnClickPositionRecycler onClickPositionRecycler) {
          this.onClickPost = onClickPositionRecycler;
      }

      public Note getItem(int position) {
          Log.e(TAG, "getItem: "+ position);
          return list.get(position);
      }


      public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textView;
        private TextView timeview;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            timeview = itemView.findViewById(R.id.timeView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickPost.onClick(getAdapterPosition());
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    onClickPost.onLongClick(getAdapterPosition());
                    return true;
                }
            });
        }

        public void bind(Note note) {
            textView.setText(note.getTitle());
            timeview.setText(note.getCreatedAt());
            if (getAdapterPosition() % 2 == 0)
                itemView.setBackgroundColor(itemView.getResources().getColor(R.color.design_bottom_navigation_shadow_color));
            else
                itemView.setBackgroundColor(itemView.getResources().getColor(R.color.design_default_color_secondary ));
        }
    }
}
