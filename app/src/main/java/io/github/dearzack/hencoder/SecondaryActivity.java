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

import io.github.dearzack.hencoder.activity2.HTTPSActivity;
import io.github.dearzack.hencoder.activity2.LoadingWaveActivity;
import io.github.dearzack.hencoder.activity2.MiuiWeatherActivity;
import io.github.dearzack.hencoder.activity2.NormalActivity;
import io.github.dearzack.hencoder.activity2.PictureExifActivity;
import io.github.dearzack.hencoder.adapter.MainAdapter;

public class SecondaryActivity extends AppCompatActivity {

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
                Intent intent = new Intent(SecondaryActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void initData() {
        data = new ArrayList<>();
        data.add("HTTPS");
        data.add("normalTest");
        data.add("Wave");
        data.add("MiuiWeather");
        data.add("PicExif");
    }

    private void gotoNewActivity(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent(this, HTTPSActivity.class);
                break;
            case 1:
                intent = new Intent(this, NormalActivity.class);
                break;
            case 2:
                intent = new Intent(this, LoadingWaveActivity.class);
                break;
            case 3:
                intent = new Intent(this, MiuiWeatherActivity.class);
                break;
            case 4:
                intent = new Intent(this, PictureExifActivity.class);
                break;
        }
        if (intent != null) {
            startActivity(intent);
        }
    }
}
