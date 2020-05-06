package com.orz.im.globallib.widget.title;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.hjq.bar.SelectorDrawable;
import com.hjq.bar.style.BaseTitleBarStyle;
import com.orz.im.globallib.R;

/**
 * com.yj.lottery.common.TitleBarDefaultStyle
 * Created by cxd on 2020/3/27
 * Describe: titlebar默认统一样式
 */
public class TitleBarDefaultStyle extends BaseTitleBarStyle {

    public TitleBarDefaultStyle(Context context) {
        super(context);
    }

    @Override
    public Drawable getBackground() {
        return getDrawable(R.drawable.shape_title_bar_default);//new ColorDrawable(0xFFFFFFFF);
    }

    @Override
    public Drawable getBackIcon() {
        return getDrawable(R.mipmap.bar_icon_back_black);
    }

    @Override
    public int getLeftColor() {
        return 0xFF666666;
    }

    @Override
    public int getTitleColor() {
        return 0xFF222222;
    }

    @Override
    public int getRightColor() {
        return 0xFFA4A4A4;
    }

    @Override
    public boolean isLineVisible() {
        return true;
    }

    @Override
    public Drawable getLineDrawable() {
        return new ColorDrawable(0xFFECECEC);
    }

    @Override
    public Drawable getLeftBackground() {
        return new SelectorDrawable.Builder()
                .setDefault(new ColorDrawable(0x00000000))
                .setFocused(new ColorDrawable(0x0C000000))
                .setPressed(new ColorDrawable(0x0C000000))
                .builder();
    }

    @Override
    public Drawable getRightBackground() {
        return getLeftBackground();
    }

}
