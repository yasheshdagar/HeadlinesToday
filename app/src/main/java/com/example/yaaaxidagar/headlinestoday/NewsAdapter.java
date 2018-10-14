package com.example.yaaaxidagar.headlinestoday;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yaaaxi Dagar on 11/4/2017.
 */

public class NewsAdapter extends ArrayAdapter<NewsData> {

    int resourceColor;
    int resourceColorBackground;

    public NewsAdapter(Context context,int resource,ArrayList<NewsData> al,int resourceColor,int resourceColorBackground) {
        super(context, resource,al);
        this.resourceColor=resourceColor;
        this.resourceColorBackground=resourceColorBackground;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView=LayoutInflater.from(getContext()).inflate(R.layout.single_view_layout,parent,false);
        }

        NewsData data=getItem(position);

        int color= ContextCompat.getColor(getContext(),resourceColor);
        int colorBackground=ContextCompat.getColor(getContext(),resourceColorBackground);

        LinearLayout linearLayout=(LinearLayout)convertView.findViewById(R.id.linearLayout);
        linearLayout.setBackgroundColor(colorBackground);

        TextView textView=convertView.findViewById(R.id.textview1);
        textView.setText(data.getTitle());
        textView.setBackgroundColor(color);

        TextView content=convertView.findViewById(R.id.textview2);
        content.setText(data.getContent());

        String dateJson=data.getDate();
        String splitted[]=dateJson.split("T");

        TextView date=convertView.findViewById(R.id.textview3);
        TextView time=convertView.findViewById(R.id.textview4);

        if(dateJson.equals("null")){
            date.setText("Date: "+"Unknown");
            time.setText("Time: "+"Unknown");
        }

        else {
            date.setText("Date: "+splitted[0]);
            time.setText("Time: "+new StringBuilder(splitted[1]).deleteCharAt(splitted[1].length()-1).toString());
        }

        return convertView;

    }
}
