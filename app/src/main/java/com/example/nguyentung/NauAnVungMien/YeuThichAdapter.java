package com.example.nguyentung.NauAnVungMien;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Nguyen Tung on 11/5/2016.
 */

public class YeuThichAdapter extends RecyclerView.Adapter<YeuThichAdapter.MyViewHolder> {

    ArrayList<ThongTinMonAn> lsMonAn;
    Context mcontext;

    public YeuThichAdapter(ArrayList<ThongTinMonAn> lsMonAn, Context context) {
        this.lsMonAn = lsMonAn;
        this.mcontext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.yeuthich_item, null);
        RecyclerView.LayoutParams pr = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        pr.setMargins(0,3,0,0);
        view.setLayoutParams(pr);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.imgMonAn.setImageBitmap(lsMonAn.get(position).getBm());
        holder.txtName.setText(lsMonAn.get(position).getName());
        holder.relative_yeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_chi_tiet = new Intent(mcontext, ChiTietMonAnActivity.class);
                intent_chi_tiet.putExtra("tenmonan", lsMonAn.get(position).getName());
                intent_chi_tiet.putExtra("nguyenlieumonan", lsMonAn.get(position).getMaterial());
                intent_chi_tiet.putExtra("conthucmonan", lsMonAn.get(position).getProcess());
                intent_chi_tiet.putExtra("anhmonan", lsMonAn.get(position).getBm());
                intent_chi_tiet.putExtra("yeuthich", lsMonAn.get(position).getThich());
                ((Activity) mcontext).startActivityForResult(intent_chi_tiet, 100);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lsMonAn.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView imgMonAn;
        TextView txtName;
        RelativeLayout relative_yeuthich;
        public MyViewHolder(View itemView) {
            super(itemView);
            relative_yeuthich = (RelativeLayout) itemView.findViewById(R.id.relative_yeuthich);
            imgMonAn = (ImageView) itemView.findViewById(R.id.img_monan_yeuthich);
            txtName = (TextView) itemView.findViewById(R.id.txt_monan_yeuthich);
        }
    }

}

