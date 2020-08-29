package com.example.nguyentung.NauAnVungMien;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyentung.NauAnVungMien.MeoVat.MeoVatActivity;
import com.example.nguyentung.NauAnVungMien.db.MonAnController;
import com.facebook.ads.AdSettings;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;

import java.util.ArrayList;
import java.util.Random;

import static com.example.nguyentung.NauAnVungMien.YeuThichActivity.yeuThichAdapter;

public class ChiTietMonAnActivity extends AppCompatActivity {

    private ImageView play, yeuthich, imgaudio, imgmonan;
    private TextView txtTenMonAn, txtNguyenLieu, txtCachCheBien;
    public static int ok_change=0;
    private int thich, length=0, current=0, firstVisit;
    private Bitmap bm;
    private boolean audio=true;
    private String tenmonan, nguyenlieu, cachchebien;
    private MediaPlayer song;
    private ThongTinMonAn monan;
    private ArrayList<Integer> Songs = new ArrayList<>();
    private AdView adView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);
        AdSettings.addTestDevice("ede675e65be87983189c63f45edad63d");
        firstVisit=1;
        //Doi mau status bar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.MainColor));
        }
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8F0461")));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_chi_tiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View action_bar_view = getSupportActionBar().getCustomView();

        play = (ImageView) action_bar_view.findViewById(R.id.imgplay);
        yeuthich = (ImageView) action_bar_view.findViewById(R.id.imgyeuthich);
        imgaudio = (ImageView) action_bar_view.findViewById(R.id.imgaudio);
        txtTenMonAn = (TextView) findViewById(R.id.idTenMonAn);
        txtNguyenLieu = (TextView) findViewById(R.id.txtNguyenLieu);
        txtCachCheBien = (TextView) findViewById(R.id.txtCachCheBien);
        imgmonan = (ImageView) findViewById(R.id.imgmonan);

        RelativeLayout adViewContainer = (RelativeLayout) findViewById(R.id.adChiTietMonAn);
        adView = new AdView(this, "1017140735063948_1023186121126076", AdSize.BANNER_320_50);
        adViewContainer.addView(adView);
        adView.loadAd();

        yeuthich.setOnClickListener(onclick_yeuthich);
        play.setOnClickListener(onclick_play);
        imgaudio.setOnClickListener(onclick_audio);

        bm = getIntent().getParcelableExtra("anhmonan");
        tenmonan = getIntent().getStringExtra("tenmonan");
        nguyenlieu = getIntent().getStringExtra("nguyenlieumonan");
        cachchebien = getIntent().getStringExtra("conthucmonan");
        thich = getIntent().getIntExtra("yeuthich",0);
        imgmonan.setImageBitmap(bm);
        txtTenMonAn.setText(tenmonan);
        txtTenMonAn.setSelected(true);
        txtNguyenLieu.setText(nguyenlieu);
        txtCachCheBien.setText(cachchebien);

        monan = new ThongTinMonAn(tenmonan, bm, nguyenlieu, cachchebien, thich);

        Songs.add(R.raw.song1);
        Songs.add(R.raw.song2);
        Songs.add(R.raw.song3);
        Songs.add(R.raw.song4);
        Songs.add(R.raw.song5);
        Songs.add(R.raw.song6);
        Songs.add(R.raw.song7);
        Songs.add(R.raw.song8);
        Songs.add(R.raw.song9);
        Songs.add(R.raw.song10);
        Random random = new Random();
        current = random.nextInt(Songs.size());
        song = MediaPlayer.create(ChiTietMonAnActivity.this, Songs.get(current));
        song.start();
        setAnhYeuThich();



    }

    private void setAnhYeuThich(){
        ArrayList<ThongTinMonAn> lsAll = getMonAn();
        for (ThongTinMonAn ma: lsAll) {
            if(monan.getName().equalsIgnoreCase(ma.getName())&&ma.getThich()==0){
                monan.setThich(ma.getThich());
                yeuthich.setImageResource(R.mipmap.white_heart);
            }
            if(monan.getName().equalsIgnoreCase(ma.getName())&&ma.getThich()==1){
                monan.setThich(ma.getThich());
                yeuthich.setImageResource(R.mipmap.heart_red);
            }
        }
    }

    private ArrayList<ThongTinMonAn> getMonAn(){
        ArrayList<ThongTinMonAn> lsMA = new ArrayList<>();
        lsMA.addAll(new MonAnController(this).getMonAn());
        return lsMA;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        song.stop();

    }

    @Override
    protected void onPause() {
        super.onPause();
        song.pause();
        length = song.getCurrentPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(firstVisit==1){
            firstVisit=0;
        }else{
            if(audio==true){
                song = MediaPlayer.create(ChiTietMonAnActivity.this, Songs.get(current));
                song.seekTo(length);
                song.start();
            }
        }

        if(ok_change==1){
            yeuThichAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chitietmonan_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.hom_nay_an_gi:
                Intent homnayangi = new Intent(ChiTietMonAnActivity.this,HomNayAnGiActivity.class);
                startActivity(homnayangi);
                break;
            case R.id.mon_an_yeu_thich:
                Intent monanyeuthich = new Intent(ChiTietMonAnActivity.this,YeuThichActivity.class);
                startActivity(monanyeuthich);
                break;
            case R.id.meo_vat:
                Intent meovat = new Intent(ChiTietMonAnActivity.this,MeoVatActivity.class);
                startActivity(meovat);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener onclick_audio = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(audio==true){
                audio=false;
                imgaudio.setImageResource(R.mipmap.mute);
                song.pause();
                length = song.getCurrentPosition();
            }else{
                audio=true;
                imgaudio.setImageResource(R.mipmap.audio);
                song.seekTo(length);
                song.start();
            }
        }
    };

    private View.OnClickListener onclick_yeuthich = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(monan.getThich()==0){
                monan.setThich(1);
                boolean kq = new MonAnController(ChiTietMonAnActivity.this).updateMonAn(monan);
                if(kq==true){
                    yeuthich.setImageResource(R.mipmap.heart_red);
                    Toast.makeText(ChiTietMonAnActivity.this, "Đã thêm vào danh sách yêu thích", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(ChiTietMonAnActivity.this, "Co loi xay ra", Toast.LENGTH_SHORT).show();
                }

            }else{
                monan.setThich(0);
                boolean kq = new MonAnController(ChiTietMonAnActivity.this).updateMonAn(monan);
                if (kq==true) {
                    yeuthich.setImageResource(R.mipmap.white_heart);
                    Toast.makeText(ChiTietMonAnActivity.this, "Đã loại khỏi danh sách yêu thích", Toast.LENGTH_SHORT).show();
                    ok_change=1;
                } else {
                    Toast.makeText(ChiTietMonAnActivity.this, "Co loi xay ra", Toast.LENGTH_SHORT).show();
                }
            }

        }
    };

    private View.OnClickListener onclick_play = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(ChiTietMonAnActivity.this, "Tinh năng hiện đang được cập nhật", Toast.LENGTH_SHORT).show();
    //        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=cxLG2wtE7TM")));
    //        song.pause();
        }
    };
}
