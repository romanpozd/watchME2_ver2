package com.watchme.roman.watchme_ver2.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;


public class RoundedNetworkImageView extends RoundedImageView {

    private String mUrl;
    private int mDefaultImageId;
    private int mErrorImageId;
    private ImageLoader mImageLoader;
    private ImageLoader.ImageContainer mImageContainer;

    public RoundedNetworkImageView(Context context) {
        this(context, (AttributeSet)null);
    }

    public RoundedNetworkImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RoundedNetworkImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void setImageUrl(String url, ImageLoader imageLoader) {
        this.mUrl = url;
        this.mImageLoader = imageLoader;
        this.loadImageIfNecessary(false);
    }

    public void setDefaultImageResId(int defaultImage) {
        this.mDefaultImageId = defaultImage;
    }

    public void setErrorImageResId(int errorImage) {
        this.mErrorImageId = errorImage;
    }

    private void loadImageIfNecessary(final boolean isInLayoutPass) {
        int width = this.getWidth();
        int height = this.getHeight();
        boolean isFullyWrapContent = this.getLayoutParams() != null && this.getLayoutParams().height == -2 && this.getLayoutParams().width == -2;
        if(width != 0 || height != 0 || isFullyWrapContent) {
            if(TextUtils.isEmpty(this.mUrl)) {
                if(this.mImageContainer != null) {
                    this.mImageContainer.cancelRequest();
                    this.mImageContainer = null;
                }

                this.setDefaultImageOrNull();
            } else {
                if(this.mImageContainer != null && this.mImageContainer.getRequestUrl() != null) {
                    if(this.mImageContainer.getRequestUrl().equals(this.mUrl)) {
                        return;
                    }

                    this.mImageContainer.cancelRequest();
                    this.setDefaultImageOrNull();
                }

                ImageLoader.ImageContainer newContainer = this.mImageLoader.get(this.mUrl, new ImageLoader.ImageListener() {
                    public void onErrorResponse(VolleyError error) {
                        if(mErrorImageId != 0) {
                            setImageResource(mErrorImageId);
                        }

                    }

                    public void onResponse(final ImageLoader.ImageContainer response, boolean isImmediate) {
                        if(isImmediate && isInLayoutPass) {
                            post(new Runnable() {
                                public void run() {
                                    onResponse(response, false);
                                }
                            });
                        } else {
                            if(response.getBitmap() != null) {
                                setImageBitmap(response.getBitmap());
                            } else if(mDefaultImageId != 0) {
                                setImageResource(mDefaultImageId);
                            }

                        }
                    }
                });
                this.mImageContainer = newContainer;
            }
        }
    }

    private void setDefaultImageOrNull() {
        if(this.mDefaultImageId != 0) {
            this.setImageResource(this.mDefaultImageId);
        } else {
            this.setImageBitmap((Bitmap)null);
        }

    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.loadImageIfNecessary(true);
    }

    protected void onDetachedFromWindow() {
        if(this.mImageContainer != null) {
            this.mImageContainer.cancelRequest();
            this.setImageBitmap((Bitmap)null);
            this.mImageContainer = null;
        }

        super.onDetachedFromWindow();
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        this.invalidate();
    }
}
