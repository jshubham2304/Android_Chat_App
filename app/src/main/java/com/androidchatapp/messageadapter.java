package com.androidchatapp;

import android.content.Context;
import android.media.MediaPlayer;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class messageadapter extends RecyclerView.Adapter {
    List<String> data = new ArrayList<>();
    List<String> time = new ArrayList<>();
    public Context mContext;

    public messageadapter(Context context , List<String> data, List<String> user) {
        this.data = data;
        this.mContext = context;
        this.time = user;
    }
    public static class TextTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txt;
        TextView time;

        public TextTypeViewHolder(View itemView) {
            super(itemView);
            this.txt = (TextView) itemView.findViewById(R.id.textview_message2);
            this.time = (TextView) itemView.findViewById(R.id.time2);
        }
    }

    public static class ImageTypeViewHolder extends RecyclerView.ViewHolder {

        TextView txt;
        TextView time;

        public ImageTypeViewHolder(View itemView) {
            super(itemView);
            this.txt = (TextView) itemView.findViewById(R.id.textview_message);
            this.time = (TextView) itemView.findViewById(R.id.time);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bubble_out2, parent, false);
                return new TextTypeViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bubble_out, parent, false);
                return new ImageTypeViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (time.get(position).equals(UserDetails.username))
        {
            return 0;
        }
        else
        {
            return 1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        if (time.get(listPosition).equals(UserDetails.username)) {
            if (!data.get(listPosition).isEmpty()) {
                ((TextTypeViewHolder) holder).txt.setText(data.get(listPosition).toString());
                ((TextTypeViewHolder) holder).time.setText(time.get(listPosition).toString());
            }
        }
        else {
            if (!data.get(listPosition).isEmpty()) {
                ((ImageTypeViewHolder) holder).txt.setText(data.get(listPosition).toString());
                ((ImageTypeViewHolder) holder).time.setText(time.get(listPosition).toString());
            } else {
                Toast.makeText(mContext, "null value in data", Toast.LENGTH_SHORT).show();
            }
        }
        }

    @Override
    public int getItemCount() {
        return data.size();
    }
}