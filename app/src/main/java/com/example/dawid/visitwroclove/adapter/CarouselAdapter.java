package com.example.dawid.visitwroclove.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dawid.visitwroclove.DAO.implementation.EventDAOImpl;
import com.example.dawid.visitwroclove.DAO.implementation.ObjectDAOImpl;
import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.BaseDTO;
import com.example.dawid.visitwroclove.model.PointDTO;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.ViewHolder> {
    private ClickListener clickListener;
    private Context context;
    private List<PointDTO> list;
    private ObjectDAOImpl mRepo;
    private EventDAOImpl mRepoEvents;


    public CarouselAdapter(Context context, ObjectDAOImpl mRepo, EventDAOImpl mRepoEvents) {
        this.context = context;
        this.mRepo = mRepo;
        this.mRepoEvents = mRepoEvents;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.cc_im_photo)
        public ImageView itemImage;
        @BindView(R.id.cc_tv_name)
        public TextView itemName;
        @BindView(R.id.cc_cv_card)
        public CardView itemCard;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            if (adapterPosition >= 0) {
                clickListener.onItemClick(adapterPosition, view);
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_carousel, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        BaseDTO baseDTO;
        if (list.get(position).isEvent()) {
            baseDTO = mRepoEvents.getById(list.get(position).getObjectId());
        } else {
            baseDTO = mRepo.getById(list.get(position).getObjectId());
        }

        Glide.with(context)
                .load(baseDTO.getImage())
                .placeholder(R.drawable.placeholder)
                .dontAnimate()
                .centerCrop()
                .into(holder.itemImage);
        holder.itemName.setText(baseDTO.getName());
    }

    public void setOnClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setData(List list) {
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ClickListener {
        void onItemClick(int position, View view);
    }
}
