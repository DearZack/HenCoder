package io.github.dearzack.hencoder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.dearzack.hencoder.adapter.MainAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.main_list);
        MainAdapter mainAdapter = new MainAdapter(this, data, new MainAdapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                gotoNewActivity(position);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(mainAdapter);
    }

    private void initData() {
        data = new ArrayList<>();
        data.add("绘制基础");
    }

    private void gotoNewActivity(int position) {
        Intent intent = null;
        switch (position) {
            case 0:

                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
