package com.example.wuqi.hw9;

import android.content.ContentProvider;
import android.content.SharedPreferences;
import android.graphics.drawable.Icon;
import android.media.Image;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.toolbox.ImageLoader;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.squareup.picasso.Picasso;


import org.w3c.dom.Text;

import java.util.List;
import java.util.ArrayList;

public class DatalistAdapter extends ArrayAdapter<Data> {

    private static final String TAG="DataListAdapter";

    private Context  mContext;
    int mresource;
    ImageView newicon;
    int i=0;

    public DatalistAdapter( Context context, int resource, ArrayList<Data> objects) {
        super(context, resource, objects);
        mContext = context;
        mresource=resource;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       // setupImageLoader();

         final String icon=getItem(position).getIcon();
         final String name=getItem(position).getName();
         final String address=getItem(position).getAddress();

        Data data=new  Data(icon, name, address);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mresource,parent,false);

       newicon =(ImageView) convertView.findViewById(R.id.icon);
       // TextView newicon =(TextView) convertView.findViewById(R.id.icon);
        TextView newname=(TextView) convertView.findViewById(R.id.reuqestname);
        TextView newaddress=(TextView) convertView.findViewById(R.id.requestaddress);
        final ImageView heart=(ImageView) convertView.findViewById(R.id.heart);

        heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




               heart.setImageDrawable(getContext().getDrawable((R.drawable.heart_fill_red)));

                Toast.makeText(getContext(),name+"was added to favorites ", Toast.LENGTH_SHORT).show();

                SharedPreferences sharedPreferences=getContext().getSharedPreferences("data", getContext().MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("ImageView",icon);
                editor.putString("newname", name);
                editor.putString("newaddress",address);

                Log.d("slmjnfd", String.valueOf(editor));

                editor.commit();
            }
        });


      //  ImageLoader imageLoader= ImageLoader.getInstance();
       //  loadImageFromUrl(icon);

//        int defualtImage=mContext.getResources().getIdentifier("icon/failed", null,mContext.getPackageName());
//        DisplayImageOptions options=new DisplayImageOptions.Builder().cacheInMemory(true)
//                .cacheOnDisc(true).resetViewBeforeLoading(true)
//                .showImageForEmptyUri(defualtImage)
//                .showImageOnFail(defualtImage)
//                .showImageOnLoading(defualtImage).build();


       // newicon.setImageIcon();
     //   imageLoader.displayImage(icon,newicon,options);
        UrlImageViewHelper.setUrlDrawable(newicon, icon);

        newname.setText(name);
        newaddress.setText(address);
        heart.setImageDrawable(getContext().getDrawable((R.drawable.heart_out_black)));

        return convertView;
    }
//
//    private void loadImageFromUrl(String icon) {
//        Picasso.with(getContext()).load(icon).placeholder(R.mipmap.ic_launcher)
//                .error(R.mipmap.ic_launcher)
//                .into(newicon,new com.squareup.picasso.Callback(){
//
//
//                    @Override
//                    public void onSuccess() {
//
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });
//
//
//    }


//    private void setupImageLoader(){
//        DisplayImageOptions defualtOptions=new DisplayImageOptions.Builder()
//                .cacheOnDisc(true).cacheInMemory(true)
//                .imageScaleType(ImageScaleType.EXACTLY)
//                .displayer(new FadeInBitmapDisplayer(300)).build();
//
//        ImageLoaderConfiguration config=new ImageLoaderConfiguration.Builder(mContext)
//                .defaultDisplayImageOptions(defualtOptions)
//                .memoryCache(new WeakMemoryCache())
//                .discCacheSize(100*1024*1024).build();
//
//        ImageLoader.getInstance().init(config);
//    }
}
