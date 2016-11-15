package com.oleg.hubal.mediagallery2.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oleg.hubal.mediagallery2.fragment.ImagePageFragment;
import com.oleg.hubal.mediagallery2.fragment.VideoPageFragment;

/**
 * Created by User on 15.11.2016.
 */

public class MediaPagerAdapter extends FragmentPagerAdapter {

    public static final int PAGE_COUNT = 2;
    public static final int IMAGE_PAGE_POSITION = 0;
    public static final int VIDEO_PAGE_POSITION = 1;
    public static final String IMAGE_PAGE_TITLE = "Image";
    public static final String VIDEO_PAGE_TITLE = "Video";

    public MediaPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case IMAGE_PAGE_POSITION:
                return ImagePageFragment.newInstance();
            case VIDEO_PAGE_POSITION:
                return VideoPageFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case IMAGE_PAGE_POSITION:
                return IMAGE_PAGE_TITLE;
            case VIDEO_PAGE_POSITION:
                return VIDEO_PAGE_TITLE;
            default:
                return null;
        }
    }
}
