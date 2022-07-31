package com.ericcode.wanandroiddemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.ericcode.wanandroiddemo.MainHandlerManager;
import com.ericcode.wanandroiddemo.R;
import com.ericcode.wanandroiddemo.WebViewActivity;
import com.ericcode.wanandroiddemo.bean.Banner;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {

    private List<Banner.DataDTO> data;
    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Banner.DataDTO dataDTO = data.get(position);
        holder.title.setText(dataDTO.getTitle());
        holder.subTitle.setText(dataDTO.getDesc());
        ImageView imageView = holder.imageView;
        final Context context = imageView.getContext();
        Glide
                .with(context)
                .load(dataDTO.getImagePath())
                .apply(RequestOptions
                        .placeholderOf(R.mipmap.ic_launcher_round)
                        .centerCrop())
                .into(imageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("click", "click:" + position);
                Log.d("click", "click:" + dataDTO.getUrl());

                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", dataDTO.getUrl());
                context.startActivity(intent);
                Message msg = new Message();
                msg.what=1;
                new MyHandler().sendMessageDelayed(msg, 1500);
            }
        });

        Handler handler = MainHandlerManager.getIns().getHandler();

    }


    class MyHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", "http://www.baidu.com");
                context.startActivity(intent);
            }
        }
    }
    @Override
    public int getItemCount() {
        if (data == null) {
            return 0;
        }
        return data.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setData(List<Banner.DataDTO> data) {
        this.data = data;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView title;
        final TextView subTitle;
        final ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            subTitle = itemView.findViewById(R.id.tvSubTitle);
            imageView = itemView.findViewById(R.id.ivImage);
        }
    }
}
