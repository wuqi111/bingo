package com.example.wuqi.hw9;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.session.PlaybackState;
import android.util.LruCache;

import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;

public class VolleyRequest {

    private static VolleyRequest mInstance;
    private static Context mContext;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;

    private VolleyRequest(Context context){
        mContext=context;
        mRequestQueue=getRequestQueue();
        mImageLoader=new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private final LruCache<String,Bitmap> cache=new LruCache<>(20);
            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
            cache.put(url, bitmap);
            }
        });
    }

    public RequestQueue getRequestQueue() {
        if(mRequestQueue==null){
            Cache cache= new DiskBasedCache(mContext.getCacheDir(),10*1024*1024);
            Network network=new BasicNetwork(new HurlStack());
            mRequestQueue=new RequestQueue(cache,network);

            mRequestQueue.start();
        }
        return mRequestQueue;
    }

    public ImageLoader getImageLoader(){
            return mImageLoader;
    }

    public static synchronized VolleyRequest getInstance(Context context){
        if (mInstance==null){
            mInstance=new VolleyRequest(context);
        }
        return mInstance;
    }
}
