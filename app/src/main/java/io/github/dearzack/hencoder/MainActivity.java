package io.github.dearzack.hencoder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.dearzack.hencoder.activity.HenCoderActivity1_1;
import io.github.dearzack.hencoder.activity.HenCoderActivity1_2;
import io.github.dearzack.hencoder.activity.HenCoderActivity1_3;
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
        data.add("Paint 详解");
        data.add("文字的绘制");
    }

    private void gotoNewActivity(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, HenCoderActivity1_1.class);
                break;
            case 1:
                intent = new Intent(this, HenCoderActivity1_2.class);
                break;
            case 2:
                intent = new Intent(this, HenCoderActivity1_3.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
