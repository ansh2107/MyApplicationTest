package com.example.ansh.myapplicationtest;

import android.content.Context;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.cloudinary.Transformation;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private final ArrayList<String> mUrls;
    private final String reso;
    private Context mContext;

    // Constructor
    public ImageAdapter(Context c, ArrayList<String> mUrls,String reso) {
        mContext = c;
        this.reso  = reso;
        this.mUrls = mUrls;
    }

    public int getCount() {
        return mUrls.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        switch (reso) {
            case "max":
                Picasso.with(mContext).load(getUrlForMaxWidth(mUrls.get(position))).into(imageView);
                break;
            case "60x40":
                Picasso.with(mContext).load(getUrlFor60_40(mUrls.get(position))).into(imageView);
                break;
            case "300x250":
                Picasso.with(mContext).load(getUrlFor300_500(mUrls.get(position))).into(imageView);
                break;
            case "1200x100":
                Picasso.with(mContext).load(getUrlFor1200_1000(mUrls.get(position))).into(imageView);
                break;
        }


        return imageView;
    }

    public String getUrlForMaxWidth(String imageId) {
        int width = Utils.getScreenWidth(this);
        return MediaManager.get().getCloudinary().url().transformation(new Transformation().width(width)).generate(imageId);
    }
    public String getUrlFor60_40(String imageId) {
        int width = 60;
        int hieght = 40;
        return MediaManager.get().getCloudinary().url().transformation(new Transformation().width(width).height(hieght)).generate(imageId);
    }
    public String getUrlFor300_500(String imageId) {
        int width = 300;
        int hieght = 500;
        int width = Utils.getScreenWidth(this);
        return MediaManager.get().getCloudinary().url().transformation(new Transformation().width(width).height(hieght)).generate(imageId);
    }
    public String getUrlFor1200_1000(String imageId) {
        int width = 1200;
        int hieght = 1000;
        int width = Utils.getScreenWidth(this);
        return MediaManager.get().getCloudinary().url().transformation(new Transformation().width(width).height(hieght)).generate(imageId);
    }

}
