package com.data.extract.generator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ResultAdapter extends RecyclerView.Adapter<ResultAdapter.set> {

    ArrayList<String> resultList;
    Context context;


    public ResultAdapter(ArrayList<String> emailItems, Context context) {
        this.resultList = emailItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultAdapter.set onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view1= LayoutInflater.from(context).inflate(R.layout.item_text,parent,false);
        return new set(view1);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultAdapter.set holder, int position) {
        String email = resultList.get(position);
        holder.tv.setText(email);
    }

    @Override
    public int getItemCount() {
       return resultList.size();
    }

    public  class set extends RecyclerView.ViewHolder {
        TextView tv;

        public set(@NonNull View itemView) {
            super(itemView);
            tv=itemView.findViewById(R.id.tv);
        }
    }
}
