package com.example.wuqi.hw9;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

public class DatasecondlistAdapter extends ArrayAdapter<Datasecond> {

    private static final String TAG="DataListAdapter";

    private Context mContext;
    int mresource;
    ImageView newicon;

    public DatasecondlistAdapter( Context context, int resource, ArrayList<Datasecond> objects) {
        super(context, resource, objects);
        mContext = context;
        mresource=resource;
    }



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // setupImageLoader();

        String icon=getItem(position).getNexticon();
        String name=getItem(position).getNextname();
        String address=getItem(position).getNextvicinity();

        Datasecond data=new  Datasecond(icon, name, address);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mresource,parent,false);

        newicon =(ImageView) convertView.findViewById(R.id.icon);
        // TextView newicon =(TextView) convertView.findViewById(R.id.icon);
        TextView newname=(TextView) convertView.findViewById(R.id.reuqestname);
        TextView newaddress=(TextView) convertView.findViewById(R.id.requestaddress);
        final ImageView heart=(ImageView) convertView.findViewById(R.id.heart);

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
}
