package com.restaurant.project.restaurantapp.utils.glide;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

/**
 * Glide Implementation.
 */
public class GlideLoaderImpl implements GlideLoader {

    private RequestManager requestManager;

    public GlideLoaderImpl(Context context) {
        this.requestManager = Glide.with(context);
    }

    @Override
    public void load(final ImageView imageView, int url) {
        requestManager.load(url).transition(withCrossFade()).load(url).into(imageView);
    }
}
