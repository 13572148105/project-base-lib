package com.orz.im.globallib.common.transformer;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

/**
 * Created by Orz on .
 * 设置viewpager2页面透明的变化
 */
public class AlphaVpPageTransformer implements ViewPager2.PageTransformer {
    private static final float DEFAULT_MIN_ALPHA = 0.5f;
    private float mMinAlpha = DEFAULT_MIN_ALPHA;

    public AlphaVpPageTransformer() {
    }

    public AlphaVpPageTransformer(float minAlpha) {
        mMinAlpha = minAlpha;
    }

    @Override
    public void transformPage(@NonNull View view, float position) {
        view.setScaleX(0.999f);//hack

        if (position < -1) { // [-Infinity,-1)
            view.setAlpha(mMinAlpha);
        } else if (position <= 1) { // [-1,1]
            //[0，-1]
            if (position < 0) {
                //[1,min]
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 + position);
                view.setAlpha(factor);
            } else {//[1，0]
                //[min,1]
                float factor = mMinAlpha + (1 - mMinAlpha) * (1 - position);
                view.setAlpha(factor);
            }
        } else { // (1,+Infinity]
            view.setAlpha(mMinAlpha);
        }
    }
}
