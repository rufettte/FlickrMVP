package com.example.flickr_mvp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.flickr_mvp.Objects.Image;
import com.example.flickr_mvp.R;
import com.example.flickr_mvp.Activities.ImageViewerActivity;
import org.jetbrains.annotations.NotNull;
import java.util.List;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final List<Image> images;
    private final Context context;

    public ImageAdapter(List<Image> images, Context context) {
        this.images = images;
        this.context = context;
    }

    public void addAll(List<Image> packOfImages) {
        images.addAll(packOfImages);
        notifyItemRangeChanged(images.size()-1, packOfImages.size());
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView imageView;
        public TextView titleView;

        public ViewHolder(View v) {
            super(v);
            imageView = v.findViewById(R.id.image);
            titleView = v.findViewById(R.id.title);
        }
    }

    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {

        LayoutInflater inflater = LayoutInflater.from(
                parent.getContext());
        View v =
                inflater.inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        Glide.with(viewHolder.itemView)
                .load(images.get(position).getUrl())
                .centerCrop()
                .into(viewHolder.imageView);

        viewHolder.titleView.setText(images.get(position).getTitle());

        viewHolder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ImageViewerActivity.class);
            intent.putExtra(context.getString(R.string.img_url), images.get(position).getUrl());
            context.startActivity(intent);
        });
    }
}
