package com.example.nguyentung.NauAnVungMien;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.nguyentung.NauAnVungMien.Fragment.MienBac_Fragment;
import com.example.nguyentung.NauAnVungMien.Fragment.MienNam_Fragment;
import com.example.nguyentung.NauAnVungMien.Fragment.MienTay_Fragment;
import com.example.nguyentung.NauAnVungMien.Fragment.MienTrung_Fragment;
import com.example.nguyentung.NauAnVungMien.db.MonAnController;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperActivityToast;
import com.github.johnpersano.supertoasts.library.utils.PaletteUtils;

import java.util.ArrayList;
import java.util.Random;

public class HomNayAnGiActivity extends AppCompatActivity implements AdListener, InterstitialAdListener {

    private CheckBox checkBoxMB, checkBoxMTrung, checkBoxMN, checkBoxMTay;
    private LinearLayout linearLayoutChon;
    private ProgressBar progressBar;
    private ImageView imgMonAn;
    private TextView txtTenMonAn, txtGoiY;
    ThongTinMonAn monAn;
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom_nay_an_gi);
        AdSettings.addTestDevice("ede675e65be87983189c63f45edad63d");
        loadInterstitialAd();
        //Doi mau status bar
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.MainColor));
        }

        checkBoxMB = (CheckBox) findViewById(R.id.checkBoxMB);
        checkBoxMTrung = (CheckBox) findViewById(R.id.checkBoxMTrung);
        checkBoxMN = (CheckBox) findViewById(R.id.checkBoxMN);
        checkBoxMTay = (CheckBox) findViewById(R.id.checkBoxMTay);
        linearLayoutChon = (LinearLayout) findViewById(R.id.chon);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imgMonAn = (ImageView) findViewById(R.id.imgMonAn);
        txtTenMonAn = (TextView) findViewById(R.id.txtTenMonAn);
        txtGoiY = (TextView) findViewById(R.id.txtGoiY);
        getSupportActionBar().setTitle("Hôm nay ăn gì?");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8F0461")));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_homnayangi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        linearLayoutChon.setOnClickListener(onChon_Click);
        progressBar.setVisibility(View.GONE);
        checkBoxMB.setChecked(true);
        checkBoxMTrung.setChecked(true);
        checkBoxMN.setChecked(true);
        checkBoxMTay.setChecked(true);
    }

    private View.OnClickListener onChon_Click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Animation myAnim = AnimationUtils.loadAnimation(v.getContext(), R.anim.anim_chon);
            linearLayoutChon.startAnimation(myAnim);
            final long duration = 500;
            if(checkBoxMB.isChecked()==false&&checkBoxMTrung.isChecked()==false&&checkBoxMN.isChecked()==false&&checkBoxMTay.isChecked()==false){
                SuperActivityToast.create(HomNayAnGiActivity.this, new Style(), Style.TYPE_BUTTON)
                        .setProgressBarColor(Color.WHITE)
                        .setText("Bạn chưa chọn miền kìa! ^-^")
                        .setDuration(Style.DURATION_LONG)
                        .setFrame(Style.FRAME_LOLLIPOP)
                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                        .setAnimations(Style.ANIMATIONS_POP).show();
            }else {
                imgMonAn.setVisibility(View.GONE);
                progressBar.setVisibility(View.VISIBLE);
                txtGoiY.setVisibility(View.GONE);
                txtTenMonAn.setText("");
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                imgMonAn.setVisibility(View.VISIBLE);
                                monAn = randomVungMien();
                                Bitmap bitmap = monAn.getBm();
                                Bitmap resized = Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * 2.5), (int) (bitmap.getHeight() * 2.5), true);
                                imgMonAn.setImageBitmap(resized);
                                imgMonAn.startAnimation(myAnim);
                                txtTenMonAn.setText(monAn.getName());
                                Animation animation = AnimationUtils.loadAnimation(HomNayAnGiActivity.this, R.anim.zoom_textview);
                                txtTenMonAn.setAnimation(animation);
                                // Set hieu ung zoom
//                                ZoomAnimation zoomAnimation = new ZoomAnimation(HomNayAnGiActivity.this);
//                                zoomAnimation.zoomReverse(imgMonAn, duration);

                                progressBar.setVisibility(View.GONE);
                                SuperActivityToast.create(HomNayAnGiActivity.this, new Style(), Style.TYPE_BUTTON)
                                        .setProgressBarColor(Color.WHITE)
                                        .setText("Món này được chưa? ^-^")
                                        .setDuration(Style.DURATION_LONG)
                                        .setFrame(Style.FRAME_LOLLIPOP)
                                        .setColor(PaletteUtils.getSolidColor(PaletteUtils.MATERIAL_PURPLE))
                                        .setAnimations(Style.ANIMATIONS_POP).show();
                            }
                        });
                    }
                });
                thread.start();
            }
            imgMonAn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent_chi_tiet = new Intent(HomNayAnGiActivity.this, ChiTietMonAnActivity.class);
                    intent_chi_tiet.putExtra("tenmonan", monAn.getName());
                    intent_chi_tiet.putExtra("nguyenlieumonan", monAn.getMaterial());
                    intent_chi_tiet.putExtra("conthucmonan", monAn.getProcess());
                    Bitmap resized = Bitmap.createScaledBitmap(monAn.getBm(), 250, 200, true);
                    intent_chi_tiet.putExtra("anhmonan", resized);
                    intent_chi_tiet.putExtra("yeuthich", monAn.getThich());
                    startActivity(intent_chi_tiet);
                }
            });
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private ThongTinMonAn ranDom(ArrayList<ThongTinMonAn> listMonAn) {
        int sizeList = listMonAn.size();
        Random r = new Random();
        int id = r.nextInt(sizeList);
        ThongTinMonAn monAn = listMonAn.get(id);
        return monAn;
    }

    private ThongTinMonAn randomVungMien(){
        if(checkBoxMB.isChecked()&&checkBoxMTrung.isChecked()==false&&checkBoxMN.isChecked()==false&&checkBoxMTay.isChecked()==false){
            return ranDom(MienBac_Fragment.lsMonAnMienBac);
        }else if(checkBoxMTrung.isChecked()&&checkBoxMB.isChecked()==false&&checkBoxMN.isChecked()==false&&checkBoxMTay.isChecked()==false){
            return ranDom(MienTrung_Fragment.lsMonAnMienTrung);
        }else if(checkBoxMN.isChecked()&&checkBoxMB.isChecked()==false&&checkBoxMTrung.isChecked()==false&&checkBoxMTay.isChecked()==false){
            return ranDom(MienNam_Fragment.lsMonAnMienNam);
        }else if(checkBoxMTay.isChecked()&&checkBoxMB.isChecked()==false&&checkBoxMTrung.isChecked()==false&&checkBoxMN.isChecked()==false){
            return ranDom(MienTay_Fragment.lsMonAnMienTay);
        }else if(checkBoxMB.isChecked()&&checkBoxMTrung.isChecked()&&checkBoxMN.isChecked()==false&&checkBoxMTay.isChecked()==false){
            ArrayList<ThongTinMonAn> lsMonAnRandom = new ArrayList<>();
            lsMonAnRandom.addAll(MienBac_Fragment.lsMonAnMienBac);
            lsMonAnRandom.addAll(MienTrung_Fragment.lsMonAnMienTrung);
            return ranDom(lsMonAnRandom);
        }else if(checkBoxMB.isChecked()&&checkBoxMTrung.isChecked()==false&&checkBoxMN.isChecked()&&checkBoxMTay.isChecked()==false){
            ArrayList<ThongTinMonAn> lsMonAnRandom = new ArrayList<>();
            lsMonAnRandom.addAll(MienBac_Fragment.lsMonAnMienBac);
            lsMonAnRandom.addAll(MienNam_Fragment.lsMonAnMienNam);
            return ranDom(lsMonAnRandom);
        }else if(checkBoxMB.isChecked()&&checkBoxMTrung.isChecked()==false&&checkBoxMN.isChecked()==false&&checkBoxMTay.isChecked()){
            ArrayList<ThongTinMonAn> lsMonAnRandom = new ArrayList<>();
            lsMonAnRandom.addAll(MienBac_Fragment.lsMonAnMienBac);
            lsMonAnRandom.addAll(MienTay_Fragment.lsMonAnMienTay);
            return ranDom(lsMonAnRandom);
        }else if(checkBoxMB.isChecked()==false&&checkBoxMTrung.isChecked()&&checkBoxMN.isChecked()&&checkBoxMTay.isChecked()==false){
            ArrayList<ThongTinMonAn> lsMonAnRandom = new ArrayList<>();
            lsMonAnRandom.addAll(MienNam_Fragment.lsMonAnMienNam);
            lsMonAnRandom.addAll(MienTrung_Fragment.lsMonAnMienTrung);
            return ranDom(lsMonAnRandom);
        }else if(checkBoxMB.isChecked()==false&&checkBoxMTrung.isChecked()&&checkBoxMN.isChecked()==false&&checkBoxMTay.isChecked()){
            ArrayList<ThongTinMonAn> lsMonAnRandom = new ArrayList<>();
            lsMonAnRandom.addAll(MienTay_Fragment.lsMonAnMienTay);
            lsMonAnRandom.addAll(MienTrung_Fragment.lsMonAnMienTrung);
            return ranDom(lsMonAnRandom);
        }else if(checkBoxMB.isChecked()==false&&checkBoxMTrung.isChecked()==false&&checkBoxMN.isChecked()&&checkBoxMTay.isChecked()){
            ArrayList<ThongTinMonAn> lsMonAnRandom = new ArrayList<>();
            lsMonAnRandom.addAll(MienNam_Fragment.lsMonAnMienNam);
            lsMonAnRandom.addAll(MienTay_Fragment.lsMonAnMienTay);
            return ranDom(lsMonAnRandom);
        }else if(checkBoxMB.isChecked()&&checkBoxMTrung.isChecked()&&checkBoxMN.isChecked()&&checkBoxMTay.isChecked()==false){
            ArrayList<ThongTinMonAn> lsMonAnRandom = new ArrayList<>();
            lsMonAnRandom.addAll(MienBac_Fragment.lsMonAnMienBac);
            lsMonAnRandom.addAll(MienTrung_Fragment.lsMonAnMienTrung);
            lsMonAnRandom.addAll(MienNam_Fragment.lsMonAnMienNam);
            return ranDom(lsMonAnRandom);
        }else if(checkBoxMB.isChecked()&&checkBoxMTrung.isChecked()&&checkBoxMN.isChecked()==false&&checkBoxMTay.isChecked()){
            ArrayList<ThongTinMonAn> lsMonAnRandom = new ArrayList<>();
            lsMonAnRandom.addAll(MienBac_Fragment.lsMonAnMienBac);
            lsMonAnRandom.addAll(MienTrung_Fragment.lsMonAnMienTrung);
            lsMonAnRandom.addAll(MienTay_Fragment.lsMonAnMienTay);
            return ranDom(lsMonAnRandom);
        }else {
            ArrayList<ThongTinMonAn> lsMonAnRandom = new ArrayList<>();
            lsMonAnRandom.addAll(MienTay_Fragment.lsMonAnMienTay);
            lsMonAnRandom.addAll(MienTrung_Fragment.lsMonAnMienTrung);
            lsMonAnRandom.addAll(MienNam_Fragment.lsMonAnMienNam);
            return ranDom(lsMonAnRandom);
        }
    }

    public ArrayList<ThongTinMonAn> getData(){
        return new MonAnController(HomNayAnGiActivity.this).getMonAn();
    }

    private void loadInterstitialAd() {
        interstitialAd = new InterstitialAd(this, "1017140735063948_1023186121126076");
        interstitialAd.setAdListener(this);
        interstitialAd.loadAd();
    }

    @Override
    public void onError(Ad ad, AdError error) {
        // Ad failed to load
    }

    @Override
    public void onAdLoaded(Ad ad) {
        // Ad is loaded and ready to be displayed
        // You can now display the full screen add using this code:
        interstitialAd.show();
    }

    @Override
    public void onAdClicked(Ad ad) {

    }

    @Override
    protected void onDestroy() {
        if (interstitialAd != null) {
            interstitialAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onInterstitialDisplayed(Ad ad) {

    }

    @Override
    public void onInterstitialDismissed(Ad ad) {

    }
}
