package com.example.wuqi.hw9;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class InformationlistAdapter extends ArrayAdapter<Information>{

    private Context mContext;
    int mresource;

    public InformationlistAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Information> objects) {
        super(context, resource, objects);

        mContext=context;
        mresource=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        String information_name=getItem(position).getInformation_name();
        String information_value=getItem(position).getInformation_value();
        Log.d("jafkldsjfrdfdsf",information_value);

        Information information= new Information(information_name,information_value);

        LayoutInflater inflater=LayoutInflater.from(mContext);
        convertView=inflater.inflate(mresource,parent,false);



        TextView getinformation_name=(TextView) convertView.findViewById(R.id.information_name);
        TextView getinformation_value=(TextView) convertView.findViewById(R.id.information_value);



        getinformation_name.setText(information_name);
        getinformation_value.setText(information_value);

        return convertView;

    }
}
