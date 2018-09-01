package com.example.wuqi.hw9;

import android.content.Context;
import android.media.Image;
import android.media.ImageReader;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;

import static android.os.Build.VERSION_CODES.N;

public class imageListAdapter extends BaseAdapter {
    private ImageLoader mImageLoader;
    private Context mContext;
    private List<String> listURL;
    public imageListAdapter(Context context, List<String> list){
        mContext=context;
        listURL=list;
        mImageLoader=VolleyRequest.getInstance(context).getImageLoader();

    }

    @Override
    public int getCount() {
       return listURL.size();
    }

    @Override
    public Object getItem(int position) {
        return listURL.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= View.inflate(mContext,R.layout.photo_layout,null);
        NetworkImageView img= (NetworkImageView) v.findViewById(R.id.photo_img_request);
        mImageLoader.get(listURL.get(position), ImageLoader.getImageListener(img,R.drawable.search,R.mipmap.ic_launcher));



       img.setImageUrl(listURL.get(position),mImageLoader);
        return v;
    }
}
