package com.orz.im.globallib.common.loader

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView

/**
 * Created by Orz on .
 * Image loader interface. There are predefined GlideEngine
 * and PicassoEngine.
 */
interface ImageEngine {
    /**
     * Load thumbnail of a static image resource.
     *
     * @param context     Context
     * @param resize      Desired size of the origin image
     * @param placeholder Placeholder drawable when image is not loaded yet
     * @param imageView   ImageView widget
     * @param uri         Uri of the loaded image
     */
    fun loadThumbnail(
        context: Context,
        resize: Int,
        placeholder: Drawable,
        imageView: ImageView,
        uri: Uri
    )

    /**
     * Load thumbnail of a gif image resource. You don't have to load an animated gif when it's only
     * a thumbnail tile.
     *
     * @param context     Context
     * @param resize      Desired size of the origin image
     * @param placeholder Placeholder drawable when image is not loaded yet
     * @param imageView   ImageView widget
     * @param uri         Uri of the loaded image
     */
    fun loadGifThumbnail(
        context: Context,
        resize: Int,
        placeholder: Drawable,
        imageView: ImageView,
        uri: Uri
    )

    /**
     * Load a static image resource.
     *
     * @param context   Context
     * @param resizeX   Desired x-size of the origin image
     * @param resizeY   Desired y-size of the origin image
     * @param imageView ImageView widget
     * @param uri       Uri of the loaded image
     */
    fun loadImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri)

    /**
     * Load a gif image resource.
     *
     * @param context   Context
     * @param resizeX   Desired x-size of the origin image
     * @param resizeY   Desired y-size of the origin image
     * @param imageView ImageView widget
     * @param uri       Uri of the loaded image
     */
    fun loadGifImage(context: Context, resizeX: Int, resizeY: Int, imageView: ImageView, uri: Uri)

    /**
     * Whether this implementation supports animated gif.
     * Just knowledge of it, convenient for users.
     *
     * @return true support animated gif, false do not support animated gif.
     */
    fun supportAnimatedGif(): Boolean
}
