package com.example.nguyentung.NauAnVungMien.MeoVat;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.example.nguyentung.NauAnVungMien.R;
import com.example.nguyentung.NauAnVungMien.db.MeoVatController;
import com.example.nguyentung.NauAnVungMien.db.ThongTinMeoVat;

import java.util.ArrayList;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;

public class MeoVatActivity extends AppCompatActivity {

    RecyclerView rclmeovat;
    static MeoVatAdapter meoVatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meo_vat);

        //Doi mau status bar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.MainColor));
        }

        //Doi mau action bar
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8F0461")));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_meovat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rclmeovat = (RecyclerView) findViewById(R.id.rclmeovat);
        meoVatAdapter = new MeoVatAdapter(getMeoVat(), this);
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(meoVatAdapter, rclmeovat);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rclmeovat.setLayoutManager(manager);
        rclmeovat.setAdapter(animatorAdapter);
    }

    public ArrayList<ThongTinMeoVat> getMeoVat(){
        return new MeoVatController(MeoVatActivity.this).getMeoVat();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }


}
