package com.packtpub.waterapp.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.packtpub.waterapp.R;
import com.packtpub.waterapp.models.Drink;

import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private ArrayList<Drink> mDrinks;
    private Context mContext;
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mCommentTextView;
        public TextView mDateTimeTextView;
        public ImageView mImageView;
        public ViewHolder (View v) {
            super(v);
        }
    }

    public MainAdapter.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.adapter_main, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.mDateTimeTextView = (TextView) v.findViewById(R.id.main_date_time_textview);
        viewHolder.mCommentTextView = (TextView) v.findViewById(R.id.main_comment_textview);
        viewHolder.mImageView = (ImageView) v.findViewById(R.id.main_image_view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mDrinks.size();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Drink currentDrink = mDrinks.get(position);
        holder.mCommentTextView.setText(currentDrink.comments);
        holder.mDateTimeTextView.setText(currentDrink.dateAndTime.toString());
        if (currentDrink.imageUri != null) {
            holder.mImageView.setImageURI(Uri.parse(currentDrink.imageUri));
        }
    }

}
