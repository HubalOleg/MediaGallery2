package com.oleg.hubal.mediagallery2;

import android.app.Application;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by User on 15.11.2016.
 */

public class MediaGalleryApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        DisplayImageOptions mOptions = new DisplayImageOptions.Builder()
                .imageScaleType(ImageScaleType.EXACTLY)
                .showImageOnLoading(android.R.drawable.screen_background_light)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();

        ImageLoaderConfiguration defaultConfiguration
                = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(mOptions)
                .threadPoolSize(5)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .diskCacheExtraOptions(480, 480, null)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .build();

        ImageLoader.getInstance().init(defaultConfiguration);
    }

}
