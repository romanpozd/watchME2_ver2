package com.watchme.roman.watchme_ver2.Volley;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**********************************************************************************
 * Created by roman on 03/09/2015.
 * This class is a singletone of Volley library that initializes the core objects
 **********************************************************************************/
public class VolleyController extends WatchMeApplication {

    public static final String TAG = VolleyController.class.getSimpleName();

    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static VolleyController mInstance;

    @Override
    public void onCreate(){
        super.onCreate();
        mInstance = this;
    }

    public static synchronized VolleyController getmInstance(){
        return mInstance;
    }
    public RequestQueue getmRequestQueue(){
        if (mRequestQueue == null)
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        return mRequestQueue;
    }

    public ImageLoader getmImageLoader(){
        getmRequestQueue();
        if (mImageLoader == null)
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        return this.mImageLoader;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag){
        // Set default tag if "tag" value is empty
        request.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getmRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request){
        request.setTag(TAG);
        getmRequestQueue().add(request);
    }
    public void cancelPendingRequests(Object tag){
        if (mRequestQueue != null)
            mRequestQueue.cancelAll(tag);
    }
}
