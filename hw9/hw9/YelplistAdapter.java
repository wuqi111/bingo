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

public class YelplistAdapter extends ArrayAdapter<YelpData> {

    private Context mContext;
    int mresource;
    ImageView newurlyelp;

    public YelplistAdapter( Context context, int resource, ArrayList<YelpData> objects) {
        super(context, resource, objects);
        mContext = context;
        mresource=resource;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        String profile_photo_url_yelp=getItem(position).getProfile_photo_url_yelp();
        String author_name_yelp=getItem(position).getAuthor_name_yelp();
        String rating_yelp=getItem(position).getRating_yelp();
        String time_yelp=getItem(position).getTime_yelp();
        String text_yelp=getItem(position).getText_yelp();

        // Data data=new  Data(icon, name, address);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mresource,parent,false);

        newurlyelp =(ImageView) convertView.findViewById(R.id.profile_url_yelp);
        TextView author_name_yelp1=(TextView) convertView.findViewById(R.id.url_nameyelp);
        TextView rating_yelp1=(TextView) convertView.findViewById(R.id.rating_yelp);
        TextView time_yelp1=(TextView) convertView.findViewById(R.id.time_yelp);
        TextView text_yelp1=(TextView) convertView.findViewById(R.id.text_yelp);


        UrlImageViewHelper.setUrlDrawable(newurlyelp, profile_photo_url_yelp);

        author_name_yelp1.setText(author_name_yelp);
        rating_yelp1.setText(rating_yelp);
        time_yelp1.setText(time_yelp);
        text_yelp1.setText(text_yelp);

        return convertView;
    }
}
