package com.example.dawid.visitwroclove.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyWindowAdapter implements GoogleMap.InfoWindowAdapter {
    @BindView(R.id.wl_tv_name)
    TextView name;
    @BindView(R.id.wl_iv_image)
    ImageView image;
    @BindView(R.id.wl_btn_longClick)
    Button button;

    private Context context;
    private ObjectDAOImpl mRepo;
    private EventDAOImpl mRepoEvent;
    private Map<Marker, Integer> hashMap;
    private boolean creatorMode;

    public MyWindowAdapter(Context context, ObjectDAOImpl repo, EventDAOImpl repoEvent, Map<Marker, Integer> hashMap) {
        this.context = context;
        this.mRepo = repo;
        this.mRepoEvent = repoEvent;
        this.hashMap = hashMap;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(final Marker marker) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.window_layout, null);
        ButterKnife.bind(this, view);
        int id = hashMap.get(marker);
        BaseDTO baseDTO;
        if (marker.getTag().equals(context.getString(R.string.events))) {
            baseDTO = mRepoEvent.getById(id);
        } else {
            baseDTO = mRepo.getById(id);
        }

        if (creatorMode) {
            button.setVisibility(View.VISIBLE);
        } else {
            button.setVisibility(View.GONE);
        }

        name.setText(baseDTO.getName());
        Glide.with(context)
                .load(baseDTO.getImage())
                .asBitmap()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (!isFromMemoryCache) {
                            marker.showInfoWindow();
                        }
                        return false;
                    }
                })
                .placeholder(R.drawable.placeholder)
                .dontAnimate()
                .centerCrop()
                .into(image);
        return view;
    }

    public void setCreatorMode(boolean mode) {
        creatorMode = mode;
    }
}