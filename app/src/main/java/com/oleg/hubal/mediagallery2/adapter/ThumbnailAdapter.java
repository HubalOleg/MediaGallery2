package com.oleg.hubal.mediagallery2.adapter;

import android.graphics.Color;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.makeramen.roundedimageview.RoundedImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.oleg.hubal.mediagallery2.R;
import com.oleg.hubal.mediagallery2.model.ThumbnailItem;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by User on 15.11.2016.
 */

public class ThumbnailAdapter extends RecyclerView.Adapter<ThumbnailAdapter.ViewHolder> {

    private ArrayList<? extends ThumbnailItem> mThumbnailItems;
    private OnThumbnailSelectionListener mSelectionListener;
    private ImageLoader mImageLoader;

    private RoundedImageView mActiveView;
    private RoundedImageView mSelectedView;
    private int mActivePosition = 0;

    public ThumbnailAdapter(OnThumbnailSelectionListener selectionListener) {
        mSelectionListener = selectionListener;
        mImageLoader = ImageLoader.getInstance();
    }

    @Override
    public void onBindViewHolder(ThumbnailAdapter.ViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_thumbnail, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public int getItemCount() {
        if (mThumbnailItems != null)
            return mThumbnailItems.size();
        else
            return 0;
    }

    public void setSelected(int position) {
        mThumbnailItems.get(mActivePosition).setSelection(false);
        mActiveView.setBorderColor(Color.TRANSPARENT);

        mSelectedView.setBorderColor(Color.BLUE);
        mActiveView = mSelectedView;
        mThumbnailItems.get(position).setSelection(true);
        mActivePosition = position;
    }

    public void setData(ArrayList<? extends ThumbnailItem> thumbnailItems) {
        mThumbnailItems = thumbnailItems;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView rivThumbnail;

        public ViewHolder(View itemView) {
            super(itemView);
            rivThumbnail = (RoundedImageView) itemView.findViewById(R.id.riv_thumbnail_card);
        }

        public void onBind(final int position) {
            if (mThumbnailItems.get(position).getSelection()) {
                rivThumbnail.setBorderColor(Color.BLUE);
                mActiveView = rivThumbnail;
            } else {
                rivThumbnail.setBorderColor(Color.TRANSPARENT);
            }

            String path = mThumbnailItems.get(position).getPath();
            Uri uri = Uri.fromFile(new File(path));
            mImageLoader.displayImage(uri.toString(), rivThumbnail);

            rivThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mSelectedView = rivThumbnail;
                    mSelectionListener.onThumbnailSelected(position);
                }
            });
        }
    }

    public interface OnThumbnailSelectionListener {
        public void onThumbnailSelected(int position);
    }
}
