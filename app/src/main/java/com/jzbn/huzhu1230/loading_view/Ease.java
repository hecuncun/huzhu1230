package com.jzbn.huzhu1230.loading_view;

import android.view.animation.Interpolator;

/**
 * Created by ybq.
 */
public class Ease {
    public static Interpolator inOut() {
        return PathInterpolatorCompat.create(0.42f, 0f, 0.58f, 1f);
    }
}
