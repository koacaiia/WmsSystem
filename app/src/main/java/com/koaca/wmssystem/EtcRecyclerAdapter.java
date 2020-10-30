package com.koaca.wmssystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EtcRecyclerAdapter extends RecyclerView.Adapter<EtcRecyclerAdapter.EtcRecyclerHolder> {
    ArrayList<EtcList> etc_lists;

    public EtcRecyclerAdapter(ArrayList<EtcList> list) {
        this.etc_lists=list;
    }

    @NonNull
    @Override
    public EtcRecyclerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_etc,parent,false);
        return new EtcRecyclerHolder(view);}

    @Override
    public void onBindViewHolder(@NonNull EtcRecyclerHolder holder, int position) {
        holder.items.setText(etc_lists.get(position).getEtc_list());
    }

    @Override
    public int getItemCount() {
        return etc_lists.size();
    }

    public class EtcRecyclerHolder extends RecyclerView.ViewHolder{
        TextView items;
        public EtcRecyclerHolder(@NonNull View itemView) {
            super(itemView);
            this.items=itemView.findViewById(R.id.list_etc);
        }
    }
}
