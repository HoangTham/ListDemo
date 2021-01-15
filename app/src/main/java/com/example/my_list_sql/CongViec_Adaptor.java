package com.example.my_list_sql;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class CongViec_Adaptor extends BaseAdapter {
    private MainActivity context;
    private int layuot;
    private List<CongViec> listCV;

    public CongViec_Adaptor(MainActivity context, int layuot, List<CongViec> listCV) {
        this.context = context;
        this.layuot = layuot;
        this.listCV = listCV;
    }

    @Override
    public int getCount() {
        return listCV.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    private class ViewHolder{
        TextView txtTen;
        ImageView imgEdit, imgDelete;
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layuot, null);
            holder.txtTen   = (TextView) convertView.findViewById(R.id.layuot_txt_name);
            holder.imgEdit  = (ImageView) convertView.findViewById(R.id.layuot_img_edit);
            holder.imgDelete= (ImageView) convertView.findViewById(R.id.layuot_img_delete);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        final CongViec congViec = listCV.get(position);
        holder.txtTen.setText(congViec.getTen());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.DialogSuaCV(congViec.getTen(), congViec.getId());
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                context.DialogXoaCV(congViec.getTen(), congViec.getId());
            }
        });
        return convertView;

    }
}
