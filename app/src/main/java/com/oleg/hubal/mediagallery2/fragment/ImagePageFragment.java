package com.oleg.hubal.mediagallery2.fragment;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.oleg.hubal.mediagallery2.Constants;
import com.oleg.hubal.mediagallery2.R;
import com.oleg.hubal.mediagallery2.adapter.ThumbnailAdapter;
import com.oleg.hubal.mediagallery2.model.ImageItem;
import com.oleg.hubal.mediagallery2.view.ZoomableImageView;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by User on 15.11.2016.
 */

public class ImagePageFragment extends Fragment implements ThumbnailAdapter.OnThumbnailSelectionListener {

    private ImageLoader mImageLoader;
    private ArrayList<ImageItem> mImageItems;
    private ThumbnailAdapter mThumbnailAdapter;
    private ZoomableImageView mBigImage;

    //      Callback
    private LoaderManager.LoaderCallbacks<Cursor> mCursorLoader =
            new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return createCursorLoader();
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
            parseCursorData(data);

            if (mImageItems != null) {
                mImageItems.get(0).setSelection(true);
                mThumbnailAdapter.setData(mImageItems);
                loadImageWithPosition(0);
            }
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };
    //      Callback end!

    public static ImagePageFragment newInstance() {
        ImagePageFragment imageFragment = new ImagePageFragment();
        return imageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageLoader = ImageLoader.getInstance();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_image, container, false);

        mBigImage = (ZoomableImageView) v.findViewById(R.id.iv_big_size_image);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rv_image_recycler);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), Constants.IMAGE_COUNT);
        recyclerView.setLayoutManager(layoutManager);

        mThumbnailAdapter = new ThumbnailAdapter(ImagePageFragment.this);
        recyclerView.setAdapter(mThumbnailAdapter);

        getLoaderManager().initLoader(0, null, mCursorLoader);

        return v;
    }

    @Override
    public void onThumbnailSelected(int position) {
        mThumbnailAdapter.setSelected(position);

        loadImageWithPosition(position);
    }

    private void loadImageWithPosition(int position) {
        String path = mImageItems.get(position).getPath();
        Uri uri = Uri.fromFile(new File(path));
        mImageLoader.displayImage(uri.toString(), mBigImage);
    }

    private void parseCursorData(Cursor data) {
        ArrayList<ImageItem> imageItems = new ArrayList<>();

        if (data.moveToFirst()) {
            do {
                String path = data.getString(data.getColumnIndex(MediaStore.Images.Media.DATA));
                String date = data.getString(data.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN));
                String mimeType = data.getString(data.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));

                imageItems.add(new ImageItem(path, date, mimeType));
            } while (data.moveToNext());
        }
        mImageItems = imageItems;
    }

    private CursorLoader createCursorLoader() {
        String selection =
                MediaStore.MediaColumns.MIME_TYPE + " = ? OR " +
                        MediaStore.MediaColumns.MIME_TYPE + " = ? OR " +
                        MediaStore.MediaColumns.MIME_TYPE + " = ? OR " +
                        MediaStore.MediaColumns.MIME_TYPE + " = ? OR " +
                        MediaStore.MediaColumns.MIME_TYPE + " = ?";

        String[] selectionArgs = new String[] {
                "image/jpeg",
                "image/bmp",
                "image/gif",
                "image/jpg",
                "image/png"};

        String sortOrder = MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC";

        return new CursorLoader(getContext(),
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                selection,
                selectionArgs,
                sortOrder);
    }
}
