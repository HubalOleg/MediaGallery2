package com.oleg.hubal.mediagallery2.fragment;

import android.database.Cursor;
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

import com.oleg.hubal.mediagallery2.Constants;
import com.oleg.hubal.mediagallery2.R;
import com.oleg.hubal.mediagallery2.adapter.ThumbnailAdapter;
import com.oleg.hubal.mediagallery2.model.VideoItem;

import java.util.ArrayList;

/**
 * Created by User on 15.11.2016.
 */

public class VideoPageFragment extends Fragment implements ThumbnailAdapter.OnThumbnailSelectionListener {

    private ArrayList<VideoItem> mVideoItems;
    private ThumbnailAdapter mThumbnailAdapter;


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
            mThumbnailAdapter.setData(mVideoItems);
        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };
    //      Callback end!

    public static VideoPageFragment newInstance() {
        VideoPageFragment videoFragment = new VideoPageFragment();
        return videoFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(0, null, mCursorLoader);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_video, container, false);

        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.rv_video_recycler);
        recyclerView.setHasFixedSize(true);

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), Constants.IMAGE_COUNT);
        recyclerView.setLayoutManager(layoutManager);

        mThumbnailAdapter = new ThumbnailAdapter(VideoPageFragment.this);
        recyclerView.setAdapter(mThumbnailAdapter);

        getLoaderManager().initLoader(0, null, mCursorLoader);

        return v;
    }

    @Override
    public void onThumbnailSelected(int position) {

    }

    private void parseCursorData(Cursor data) {
        ArrayList<VideoItem> videoItems = new ArrayList<>();

        if (data.moveToFirst()) {
            do {
                String path = data.getString(data.getColumnIndex(MediaStore.Video.Media.DATA));
                String date = data.getString(data.getColumnIndex(MediaStore.Video.VideoColumns.DATE_TAKEN));
                String mimeType = data.getString(data.getColumnIndex(MediaStore.MediaColumns.MIME_TYPE));
                String duration = data.getString(data.getColumnIndex(MediaStore.Video.Media.DURATION));

                videoItems.add(new VideoItem(path, date, mimeType, duration));
            } while (data.moveToNext());
        }
        mVideoItems = videoItems;
    }

    private CursorLoader createCursorLoader() {
        String selection =
                MediaStore.MediaColumns.MIME_TYPE + " = ? OR " +
                        MediaStore.MediaColumns.MIME_TYPE + " = ? OR " +
                        MediaStore.MediaColumns.MIME_TYPE + " = ?";

        String[] selectionArgs = new String[] {
                "video/wav",
                "video/mp4",
                "video/mp4"};

        String sortOrder = MediaStore.Video.VideoColumns.DATE_TAKEN + " DESC";

        return new CursorLoader(getContext(),
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null,
                selection,
                selectionArgs,
                sortOrder);
    }
}
