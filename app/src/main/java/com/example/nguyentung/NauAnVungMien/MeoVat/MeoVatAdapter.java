package com.example.nguyentung.NauAnVungMien.MeoVat;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguyentung.NauAnVungMien.R;
import com.example.nguyentung.NauAnVungMien.db.ThongTinMeoVat;

import java.util.ArrayList;

/**
 * Created by Nguyen Tung on 11/23/2016.
 */

public class MeoVatAdapter extends RecyclerView.Adapter<MeoVatAdapter.MyViewHolder> {

    ArrayList<ThongTinMeoVat> lsMeoVat;
    Context mcontext;

    public MeoVatAdapter(ArrayList<ThongTinMeoVat> lsMeoVat, Context mcontext) {
        this.lsMeoVat = lsMeoVat;
        this.mcontext = mcontext;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.meovat_item, null);
        RecyclerView.LayoutParams pr = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pr.setMargins(0,3,0,0);
        view.setLayoutParams(pr);
        return new MeoVatAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.imgMeoVat.setImageBitmap(lsMeoVat.get(position).getBitmap());
        holder.txtName.setText(lsMeoVat.get(position).getName());
        holder.linear_meovat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_chi_tiet = new Intent(mcontext, ChiTietMeoVatActivity.class);
                intent_chi_tiet.putExtra("tenmeovat", lsMeoVat.get(position).getName());
                intent_chi_tiet.putExtra("anhmeovat", lsMeoVat.get(position).getBitmap());
                intent_chi_tiet.putExtra("chitiet", lsMeoVat.get(position).getDetail());
                ((Activity) mcontext).startActivityForResult(intent_chi_tiet, 100);
            }
        });
    }

    @Override
    public int getItemCount() {
        return lsMeoVat.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgMeoVat;
        TextView txtName;
        LinearLayout linear_meovat;
        public MyViewHolder(View itemView) {
            super(itemView);
            linear_meovat = (LinearLayout) itemView.findViewById(R.id.linear_meovat);
            imgMeoVat = (ImageView) itemView.findViewById(R.id.img_meovat);
            txtName = (TextView) itemView.findViewById(R.id.txtTenMeoVat);
        }
    }
}
