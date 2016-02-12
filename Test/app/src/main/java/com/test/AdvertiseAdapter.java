package com.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by toshiba on 2/12/2016.
 */
public class AdvertiseAdapter extends RecyclerView.Adapter<AdvertiseAdapter.MyViewHolder> {

    private List<Advertise> advertiseList;
    private Context context;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public ImageView iv_image;

        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
        }
    }


    public AdvertiseAdapter(List<Advertise> advertiseList, Context context) {
        this.advertiseList = advertiseList;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Advertise advertise = advertiseList.get(position);
        if (!TextUtils.isEmpty(advertise.getTitle())) {
            holder.iv_image.setVisibility(View.VISIBLE);
            holder.tv_title.setVisibility(View.VISIBLE);
            holder.tv_title.setText(advertise.getTitle());
            Picasso.with(context).load(advertise.getUri()).into(holder.iv_image);
        } else {
            holder.iv_image.setVisibility(View.GONE);
            holder.tv_title.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return advertiseList.size();
    }
}
