package com.example.taskapp.ui.board;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.taskapp.MainActivity;
import com.example.taskapp.R;
import com.example.taskapp.interfaces.CloseFragment;

import java.util.ArrayList;

public class BoardAdapter extends RecyclerView.Adapter<BoardAdapter.ViewHolder>{

    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> descr = new ArrayList<>();
    private ArrayList<Integer> image = new ArrayList<>();
    public ImgClick imgClick;
    public BtnClick btnClick;
    private ImageView imageX;
    private CloseFragment closeFragment;

    public BoardAdapter() {
        titles.add("First");
        titles.add("Second");
        titles.add("Thouth");

        descr.add("Description Description Description Description Description Description Description Description");
        descr.add("Description Description Description Description Description Description Description Description");
        descr.add("Description Description Description Description Description Description Description Description");

        image.add(R.drawable.ic_add);
        image.add(R.drawable.ic_add);
        image.add(R.drawable.ic_add);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pager_board,parent,false);
        return new ViewHolder(view,imgClick);
    }

public void setClick(ImgClick imgClick){
        this.imgClick = imgClick;
}

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView textTitle;
        private TextView textDesc;
        private ImageView imageView;
        private Button button;
        private ImgClick imgClick;

        public ViewHolder(@NonNull View itemView,ImgClick imgClick) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
            imageView = itemView.findViewById(R.id.image_board);
            button = itemView.findViewById(R.id.btnNext);
            imageX = itemView.findViewById(R.id.imageX);

            this.imgClick = imgClick;

            imageX.setOnClickListener(v -> {
                imgClick.onImgClick();
            });
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnClick.onbtnClick();
                }
            });

        }

        public void bind(int position) {
            textTitle.setText(titles.get(position));
            textDesc.setText(descr.get(position));
            imageView.setImageResource(image.get(position));
            if (position == 2)
                button.setVisibility(View.VISIBLE);
            else button.setVisibility(View.GONE);
            //    clickListener();

        }

/*        private void clickListener() {
            button.setOnClickListener(v -> {
                closeFragment.toclosefragment();
            });
        }*/
    }

   public interface ImgClick{
        void onImgClick();
    }

    public  interface BtnClick{
        void onbtnClick();
    }

}
