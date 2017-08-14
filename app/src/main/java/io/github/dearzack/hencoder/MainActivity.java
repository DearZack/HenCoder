package io.github.dearzack.hencoder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import io.github.dearzack.hencoder.activity.HenCoderActivity1_1;
import io.github.dearzack.hencoder.activity.HenCoderActivity1_2;
import io.github.dearzack.hencoder.activity.HenCoderActivity1_3;
import io.github.dearzack.hencoder.activity.HenCoderActivity1_4;
import io.github.dearzack.hencoder.activity.HenCoderActivity1_5;
import io.github.dearzack.hencoder.adapter.MainAdapter;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private List<String> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fab = (FloatingActionButton) findViewById(R.id.fab);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initData() {
        data = new ArrayList<>();
        data.add("绘制基础");
        data.add("Paint 详解");
        data.add("文字的绘制");
        data.add("Canvas 对绘制的辅助");
        data.add("绘制顺序");
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
            case 3:
                intent = new Intent(this, HenCoderActivity1_4.class);
                break;
            case 4:
                intent = new Intent(this, HenCoderActivity1_5.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
