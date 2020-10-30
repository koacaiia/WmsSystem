package com.koaca.wmssystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class RecyclerViewActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EtcRecyclerAdapter adapter;
    MainActivity mainActivity;
    ArrayList<EtcList> list;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        Intent intent=getIntent();
        String [] items_etc=intent.getStringArrayExtra("des");

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        list =new ArrayList<>();
        adapter = new EtcRecyclerAdapter(list);

        int itemsLength=items_etc.length;
        for(int i=0;i<itemsLength;i++){
            ArrayList<String> arrayData=new ArrayList<>(Arrays.asList(items_etc));
            String addData= arrayData.get(i);
        EtcList data=new EtcList(addData);
        list.add(data);
        }
        recyclerView.setAdapter(adapter);


    }
}