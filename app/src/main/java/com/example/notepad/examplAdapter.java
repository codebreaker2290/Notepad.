package com.example.notepad;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.notepad.MainActivity.EXTRA_ETEXT;
import static com.example.notepad.MainActivity.EXTRA_TEXTV;

public class examplAdapter extends RecyclerView.Adapter<examplAdapter.ExampleViewHolder> {
    private ArrayList<ExampleItem> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener{
        void onDeleteClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class ExampleViewHolder extends RecyclerView.ViewHolder{

        public EditText mText1;
        public EditText mText2;
        public RelativeLayout layout;
        public ImageView mdelete;

        public ExampleViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            mText1 = itemView.findViewById(R.id.textView);
            mText2 = itemView.findViewById(R.id.editText);
            mdelete = itemView.findViewById(R.id.image_delete);
            layout = itemView.findViewById(R.id.rlayout);

            mdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onDeleteClick(position);
                        }
                    }
                }
            });
        }
    }

    public examplAdapter(ArrayList<ExampleItem> examplelist ){
        mExampleList = examplelist;
    }

    @NonNull
    @Override
    public ExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.example_item,parent,false);
        ExampleViewHolder evh = new ExampleViewHolder(v,mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull ExampleViewHolder holder, int position) {

        ExampleItem currentItem = mExampleList.get(position);

        holder.mText1.setText(currentItem.getText1());
        holder.mText2.setText(currentItem.getText2());
    }
    @Override
    public int getItemCount() {

        return mExampleList.size();
    }
}