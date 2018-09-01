package com.example.wuqi.hw9;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

public class GoogleDataListAdapter extends ArrayAdapter<GoogleData> {

    private Context mContext;
    int mresource;
    ImageView newurl;
    Float haha;

    public GoogleDataListAdapter( Context context, int resource, ArrayList<GoogleData> objects) {
        super(context, resource, objects);
        mContext = context;
        mresource=resource;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        String profile_photo_url_google=getItem(position).getProfile_photo_url_google();
        String author_name_google=getItem(position).getAuthor_name_google();
        String rating_google=getItem(position).getRating_google();
        String time_google=getItem(position).getTime_google();
        String text_google=getItem(position).getText_google();

        int timeout= Integer.parseInt(time_google);

//
//        for(var i=0;i<$scope.acceptdata.reviews.length;i++){
//            var aa=$scope.acceptdata.reviews[i].time*1000;
//            var day=new Date(aa);
//            var month=day.getMonth()+1;
//            var alltime=day.getFullYear()+"-"+month+"-"+day.getDate()+" "+day.getHours()+":"+day.getMinutes()+":"+day.getSeconds();
//            $scope.acceptdata.reviews[i].time=alltime;
//        }

        haha=Float.parseFloat(rating_google);

       // Data data=new  Data(icon, name, address);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mresource,parent,false);

        newurl =(ImageView) convertView.findViewById(R.id.profile_url);
        TextView author_name_google1=(TextView) convertView.findViewById(R.id.url_name);
        RatingBar rating_google1=(RatingBar) convertView.findViewById(R.id.rating_google);
        TextView time_google1=(TextView) convertView.findViewById(R.id.time_google);
        TextView text_google1=(TextView) convertView.findViewById(R.id.text_google);


        UrlImageViewHelper.setUrlDrawable(newurl, profile_photo_url_google);

        author_name_google1.setText(author_name_google);
        rating_google1.setRating(haha);
        time_google1.setText(time_google);
        text_google1.setText(text_google);

        return convertView;
    }
}
