package com.example.wuqi.hw9;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

public class FavoritelistAdapter  extends ArrayAdapter<FavoriteData> {

    private Context mContext;
    int mresource;
    ImageView newicon1;

    public FavoritelistAdapter( Context context, int resource, ArrayList<FavoriteData> objects) {
        super(context, resource, objects);
        mContext = context;
        mresource=resource;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View getView(int position, View convertView, ViewGroup parent) {
        // setupImageLoader();

        String icon_favorite=getItem(position).getIcon_favorite();
        String name_favorite=getItem(position).getName_favorite();
        String vicinity_favorite=getItem(position).getAddress_favorite();


        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mresource,parent,false);


       newicon1 =(ImageView) convertView.findViewById(R.id.icon_favorite);

        TextView newname1=(TextView) convertView.findViewById(R.id.reuqestname_favorite);
        TextView newaddress1=(TextView) convertView.findViewById(R.id.requestaddress_favorite);
        final ImageView heart1=(ImageView)  convertView.findViewById(R.id.heart_favorite);


        UrlImageViewHelper.setUrlDrawable(newicon1, icon_favorite);

        newname1.setText(name_favorite);
        newaddress1.setText(vicinity_favorite);
        heart1.setImageDrawable(getContext().getDrawable((R.drawable.heart_out_black)));

        newicon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        return convertView;
    }

}
