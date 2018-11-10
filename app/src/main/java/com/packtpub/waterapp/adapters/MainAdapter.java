package com.packtpub.waterapp.adapters;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

    public MainAdapter(Context context, ArrayList<Drink> drinks) {
        mDrinks = drinks;
        mContext = context;
    }

    @Override
    public MainAdapter.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(
                parent.getContext()).inflate(
                R.layout.adapter_main_card_view, parent, false);
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
//        if (currentDrink.imageUri != null) {
//            holder.mImageView.setImageURI(Uri.parse(currentDrink.imageUri));
//        } // why the heck was this here?

        // add work to display thumbnail images
        if (currentDrink.imageUri != null) {
            Bitmap bitmap = getBitmapFromUri(Uri.parse(currentDrink.imageUri));
            holder.mImageView.setImageBitmap(bitmap);
        }

    }

    //create getBitmapFromUri() method if Uri is known for the item then display item
    // thumbnail
    public Bitmap getBitmapFromUri(Uri uri) {
        mContext.getContentResolver().notifyChange(uri, null);
        ContentResolver cr = mContext.getContentResolver();
        try {
            Bitmap bitmap = android.provider.MediaStore.Images.Media.getBitmap(cr, uri);
            return bitmap;
        }
        catch (Exception e) {
            Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

}
