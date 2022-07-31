package com.ericcode.wanandroiddemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.ericcode.wanandroiddemo.adapter.MainActivityAdapter;
import com.ericcode.wanandroiddemo.api.WanAndroidRepo;
import com.ericcode.wanandroiddemo.bean.Banner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private MainActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layout);
        adapter = new MainActivityAdapter();
        adapter.setContext(this);
        recyclerView.setAdapter(adapter);

        loadNetData();
    }

    private void loadNetData() {
        WanAndroidRepo.getApi().getBanner()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Banner>() {
                    @Override
                    public void accept(Banner banner) throws Exception {
                        Log.d(TAG, banner.toString());
                        adapter.setData(banner.getData());
                        adapter.notifyDataSetChanged();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showNetErrorDialog();
                    }
                });
    }

    public void showNetErrorDialog() {
        new AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog)
                .setTitle("网络异常")
                .setMessage("网路发生异常：")
                .setNegativeButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}