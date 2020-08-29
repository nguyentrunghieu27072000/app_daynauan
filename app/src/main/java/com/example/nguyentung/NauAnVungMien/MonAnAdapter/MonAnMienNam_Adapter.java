package com.example.nguyentung.NauAnVungMien.MonAnAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nguyentung.NauAnVungMien.ChiTietMonAnActivity;
import com.example.nguyentung.NauAnVungMien.R;
import com.example.nguyentung.NauAnVungMien.ThongTinMonAn;

import java.util.ArrayList;

/**
 * Created by Nguyen Tung on 11/5/2016.
 */

public class MonAnMienNam_Adapter extends RecyclerView.Adapter<MonAnMienNam_Adapter.MyViewHolder> {

    private ArrayList<ThongTinMonAn> lsData;
    private Context mContext;

    public MonAnMienNam_Adapter(ArrayList<ThongTinMonAn> lsData, Context context) {
        this.lsData = lsData;
        this.mContext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.monan_item, null);
        MyViewHolder holder = new MyViewHolder(view);
        RecyclerView.LayoutParams pr = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        pr.setMargins(5,5,5,5);
        view.setLayoutParams(pr);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        holder.textView.setText(lsData.get(position).getName());
        holder.textView.setSelected(true);
        holder.imageView.setImageBitmap(lsData.get(position).getBm());
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(v.getContext(), lsData.get(position).getName(), Toast.LENGTH_SHORT).show();
                Intent intent_chi_tiet = new Intent(mContext, ChiTietMonAnActivity.class);
                intent_chi_tiet.putExtra("tenmonan", lsData.get(position).getName());
                intent_chi_tiet.putExtra("nguyenlieumonan", lsData.get(position).getMaterial());
                intent_chi_tiet.putExtra("conthucmonan", lsData.get(position).getProcess());
                Bitmap resized = Bitmap.createScaledBitmap(lsData.get(position).getBm(), 250, 200, true);
                intent_chi_tiet.putExtra("anhmonan", resized);
                intent_chi_tiet.putExtra("yeuthich", lsData.get(position).getThich());
//                Toast.makeText(mContext, lsData.get(position).getThich().toString(), Toast.LENGTH_SHORT).show();
//                intent_chi_tiet.putExtra("vitri", position);
                ((Activity) mContext).startActivityForResult(intent_chi_tiet, 100);
            }
        });
    }



    @Override
    public int getItemCount() {
        return lsData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.img);
            textView = (TextView) itemView.findViewById(R.id.txt);
        }
    }
}