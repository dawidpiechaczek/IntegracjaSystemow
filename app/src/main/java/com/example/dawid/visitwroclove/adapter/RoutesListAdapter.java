package com.example.dawid.visitwroclove.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dawid.visitwroclove.R;
import com.example.dawid.visitwroclove.model.RouteDTO;
import com.example.dawid.visitwroclove.utils.FontManager;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RoutesListAdapter extends RecyclerView.Adapter<RoutesListAdapter.ViewHolder> {
    private Context context;
    private RoutesListAdapter.ClickListener clickListener;
    private List<RouteDTO> list;

    public RoutesListAdapter(Context context) {
        this.context = context;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.rle_tv_name)
        public TextView itemName;
        @BindView(R.id.rle_tv_amout)
        public TextView itemAmount;
        @BindView(R.id.rle_tv_time)
        public TextView itemTime;
        @BindView(R.id.rle_tv_placesImage)
        public TextView placesImage;
        @BindView(R.id.rle_tv_timeImage)
        public TextView timeImage;
        @BindView(R.id.rle_tv_description)
        public TextView description;

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
    public RoutesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.routelist_element, parent, false);
        return new RoutesListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemName.setText(list.get(position).getName());
        holder.itemAmount.setText(list.get(position).getPoints().size()+"");
        holder.itemTime.setText(list.get(position).getLength()+" km");
        holder.placesImage.setTypeface(FontManager.getIcons(context));
        holder.timeImage.setTypeface(FontManager.getIcons(context));
        holder.description.setText(list.get(position).getDescription());
    }

    public void setOnClickListener(RoutesListAdapter.ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List list){
        this.list = list;
    }

    public interface ClickListener {
        void onItemClick(int position, View view);
        void onEditItemClick(int position);
    }
}

