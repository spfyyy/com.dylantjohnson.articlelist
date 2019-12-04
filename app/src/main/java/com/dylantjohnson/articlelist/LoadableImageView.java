package com.dylantjohnson.articlelist;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import java.io.IOException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

class LoadableImageView extends FrameLayout {
    private static final String TAG = LoadableImageView.class.getCanonicalName();
    private static Executor mExecutor = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(), Runtime.getRuntime().availableProcessors(),
            0, TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    private ProgressBar mLoader;
    private ImageView mImage;

    LoadableImageView(Context context) {
        super(context);

        mLoader = new ProgressBar(context);
        mLoader.setId(generateViewId());
        mLoader.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        mImage = new ImageView(context);
        mImage.setId(generateViewId());
        mImage.setVisibility(INVISIBLE);
        mImage.setAdjustViewBounds(true);
        mImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
        mImage.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        this.addView(mLoader);
        this.addView(mImage);
    }

    void loadImage(URL url) {
        mExecutor.execute(() -> {
            try {
                mImage.post(() -> {
                    mImage.setVisibility(INVISIBLE);
                    mLoader.setVisibility(VISIBLE);
                });
                Bitmap bitmap = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                mImage.post(() -> {
                    mImage.setImageBitmap(bitmap);
                    mImage.setVisibility(VISIBLE);
                    mLoader.setVisibility(INVISIBLE);
                });
            } catch (IOException ex) {
                Log.w(TAG, ex);
            }
        });
    }
}
