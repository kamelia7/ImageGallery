package com.mycorp.imagegallery.ui;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mycorp.imagegallery.R;
import com.mycorp.imagegallery.model.ItemPicture;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.PictureViewHolder> {

    private Context context;
    private List<ItemPicture> data;

    public PicturesAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(@NonNull List<ItemPicture> newData) {
        data = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_picture, parent, false);
        return  new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        holder.bind(data.get(position));
    }

    @Override
    public int getItemCount() { return  data.size(); }

    class PictureViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.ivPicture) ImageView ivPicture;

        public PictureViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        void bind(ItemPicture itemPicture) {

            Glide.with(context)
                    .load(itemPicture.getPreview())
                    .error(R.drawable.ic_cloud_off)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .skipMemoryCache(true)
                    .into(ivPicture);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                ItemPicture itemPicture = data.get(position);
                Intent intent = new Intent(context, PictureActivity.class);
                intent.putExtra(PictureActivity.EXTRA_ITEM_PICTURE, itemPicture);
                context.startActivity(intent);
            }
        }
    }
}
