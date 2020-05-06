package com.orz.im.globallib.common.loader.impl;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestListener;
import com.orz.im.globallib.R;
import com.orz.im.globallib.common.loader.ImageEngine;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * Glide图片加载引擎
 */
public class GlideEngine implements ImageEngine {


    /**
     * 加载圆角图片
     * @param imageView
     * @param filePath
     * @param listener
     * @param radius
     */
    public static void loadCornerImage(Context context,ImageView imageView, String filePath, RequestListener listener, float radius) {
        CornerTransform transform = new CornerTransform(context, radius);
        ColorDrawable drawable = new ColorDrawable(Color.GRAY);
        Glide.with(context)
                .load(filePath)
                .centerCrop()
                .placeholder(drawable)
                .error(R.drawable.picture_image_placeholder)
                .transform(transform)
                .listener(listener)
                .into(imageView);
    }

    /**
     * 加载头像图片===>urlPath
     * @param imageView
     * @param urlPath
     */
    public static void loadAvatar(Context context,ImageView imageView, String urlPath) {
        Glide.with(context)
                .load(urlPath)
                .error(R.drawable.picture_image_placeholder)
                .into(imageView);
    }

    /**
     * 加载头像图片===>obj
     * @param imageView
     * @param obj
     */
    public static void loadAvatar(Context context,ImageView imageView, Object obj) {
        if (obj == null) {
            return;
        }
        Glide.with(context)
                .load(obj)
                .error(R.drawable.picture_image_placeholder)
                .into(imageView);
    }


    /**
     * 加载头像图片带监听器
     * @param imageView
     * @param filePath
     * @param listener
     */
    public static void loadAvatar(Context context,ImageView imageView, String filePath, RequestListener listener) {
        Glide.with(context)
                .load(filePath)
                .error(R.drawable.picture_image_placeholder)
                .listener(listener)
                .into(imageView);
    }
    /**
     * 加载图片===>uri
     * @param imageView
     * @param uri
     */
    public static void loadImage(Context context,ImageView imageView, Uri uri) {
        if (uri == null) {
            return;
        }
        Glide.with(context)
                .load(uri)
                .error(R.drawable.picture_image_placeholder)
                .into(imageView);
    }

    /**
     * 加载图片===>obj
     * @param imageView
     * @param obj
     */
    public static void loadImage(Context context,ImageView imageView, Object obj) {
        if (obj == null) {
            return;
        }
        Glide.with(context)
                .load(obj)
                .error(R.drawable.picture_image_placeholder)
                .into(imageView);
    }

    /**
     * 加载===>指定尺寸的图片
     * @param context   Context
     * @param resizeX   Desired x-size of the origin image
     * @param resizeY   Desired y-size of the origin image
     * @param imageView ImageView widget
     * @param uri       Uri of the loaded image
     */
    @Override
    public void loadImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .load(uri)
                .override(resizeX, resizeY)
                .priority(Priority.HIGH)
                .error(R.drawable.picture_image_placeholder)
                .fitCenter()
                .into(imageView);
    }


    /**
     * 获取File
     * @param filePath
     * @param url
     */
    public static void loadFile(Context context,String filePath, String url) {
        try {
            File file = Glide.with(context).asFile().load(url).submit().get();
            File destFile = new File(filePath);
            file.renameTo(destFile);
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取Bitmap
     * @param imageUrl
     * @param targetImageSize
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static Bitmap loadBitmap(Context context,Object imageUrl, int targetImageSize) throws InterruptedException, ExecutionException {
        if (imageUrl == null) {
            return null;
        }
        return Glide.with(context).asBitmap()
                .load(imageUrl)
                .error(R.drawable.picture_image_placeholder)
                .submit(targetImageSize,targetImageSize)
                .get();
    }
    /**
     * 获取Bitmap
     * @param imageUrl
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static Bitmap loadBitmap(Context context,Object imageUrl) throws InterruptedException, ExecutionException {
        if (imageUrl == null) {
            return null;
        }
        return Glide.with(context).asBitmap()
                .load(imageUrl)
                .error(R.drawable.picture_image_placeholder)
                .submit()
                .get();
    }
    /**
     * 加载缩略图
     * @param context     Context
     * @param resize      Desired size of the origin image
     * @param placeholder Placeholder drawable when image is not loaded yet
     * @param imageView   ImageView widget
     * @param uri         Uri of the loaded image
     */
    @Override
    public void loadThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView, Uri uri) {
        Glide.with(context)
                .asBitmap() // some .jpeg files are actually gif
                .load(uri)
                .override(resize, resize)
                .placeholder(placeholder)
                .centerCrop()
                .into(imageView);
    }

    /**
     * 加载Gif缩略图
     * @param context     Context
     * @param resize      Desired size of the origin image
     * @param placeholder Placeholder drawable when image is not loaded yet
     * @param imageView   ImageView widget
     * @param uri         Uri of the loaded image
     */
    @Override
    public void loadGifThumbnail(Context context, int resize, Drawable placeholder, ImageView imageView,
                                 Uri uri) {
        Glide.with(context)
                .asBitmap() // some .jpeg files are actually gif
                .load(uri)
                .override(resize, resize)
                .placeholder(placeholder)
                .centerCrop()
                .into(imageView);
    }


    /**
     * 加载指定尺寸的Gif图片
     * @param context   Context
     * @param resizeX   Desired x-size of the origin image
     * @param resizeY   Desired y-size of the origin image
     * @param imageView ImageView widget
     * @param uri       Uri of the loaded image
     */
    @Override
    public void loadGifImage(Context context, int resizeX, int resizeY, ImageView imageView, Uri uri) {
        Glide.with(context)
                .asGif()
                .load(uri)
                .override(resizeX,resizeY)
                .priority(Priority.HIGH)
                .fitCenter()
                .into(imageView);
    }

    public static void clear(Context context,ImageView imageView) {
        Glide.with(context).clear(imageView);
    }

    @Override
    public boolean supportAnimatedGif() {
        return true;
    }

}
