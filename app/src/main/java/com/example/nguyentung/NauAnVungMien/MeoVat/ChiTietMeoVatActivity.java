package com.example.nguyentung.NauAnVungMien.MeoVat;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.nguyentung.NauAnVungMien.R;
import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSettings;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;

public class ChiTietMeoVatActivity extends AppCompatActivity {

    private TextView txtName, txtDetail;
    private ImageView imgMeoVat;
    private String tenMeoVat, chitiet;
    private Bitmap anhmeovat;
    private NativeAd nativeAd;
    private LinearLayout nativeAdContainer;
    private LinearLayout adView;
    private AdChoicesView adChoicesView;
    private ImageView ad_close;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_meo_vat);
        AdSettings.addTestDevice("ede675e65be87983189c63f45edad63d");
        showNativeAd();
        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.MainColor));
        }

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#8F0461")));
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.action_bar_chi_tiet_meovat);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        View action_bar_view = getSupportActionBar().getCustomView();
        TextView txtTitle = (TextView) action_bar_view.findViewById(R.id.TitleActionBarMeovat);

        txtName = (TextView) findViewById(R.id.TenMeoVat);
        txtDetail = (TextView) findViewById(R.id.txtChiTiet);
        imgMeoVat = (ImageView) findViewById(R.id.imgmeovat);
        txtTitle.setSelected(true);
        txtName.setSelected(true);

        tenMeoVat = getIntent().getStringExtra("tenmeovat");
        chitiet = getIntent().getStringExtra("chitiet");
        anhmeovat = getIntent().getParcelableExtra("anhmeovat");

        txtName.setText(tenMeoVat);
        txtDetail.setText(chitiet);
        imgMeoVat.setImageBitmap(anhmeovat);
        txtTitle.setText(tenMeoVat);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void showNativeAd(){
        nativeAd = new NativeAd(this, "1017140735063948_1023186121126076");
        nativeAd.setAdListener(new AdListener() {

            @Override
            public void onError(Ad ad, AdError error) {

            }

            @Override
            public void onAdLoaded(Ad ad) {
                if (ad != nativeAd) {
                    return;
                }

                nativeAdContainer = (LinearLayout) findViewById(R.id.activity_chi_tiet_meo_vat);
                LayoutInflater inflater = LayoutInflater.from(ChiTietMeoVatActivity.this);
                adView = (LinearLayout)inflater.inflate(R.layout.ad_unit, nativeAdContainer, false);
                nativeAdContainer.addView(adView);
                ImageView nativeAdIcon = (ImageView)adView.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle = (TextView)adView.findViewById(R.id.native_ad_title);
                TextView nativeAdBody = (TextView)adView.findViewById(R.id.native_ad_body);
                MediaView nativeAdMedia = (MediaView)adView.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext = (TextView)adView.findViewById(R.id.native_ad_social_context);
                Button nativeAdCallToAction = (Button)adView.findViewById(R.id.native_ad_call_to_action);

                // Setting the Text.
                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
                nativeAdTitle.setText(nativeAd.getAdTitle());
                nativeAdBody.setText(nativeAd.getAdBody());

                // Downloading and setting the ad icon.
                NativeAd.Image adIcon = nativeAd.getAdIcon();
                NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

                // Download and setting the cover image.
                nativeAdMedia.setNativeAd(nativeAd);

                // Add adChoices icon
//                if (adChoicesView == null) {
//                    adChoicesView = new AdChoicesView(ChiTietMeoVatActivity.this, nativeAd, true);
//                    adView.addView(adChoicesView, 0);
//                }

                nativeAd.registerViewForInteraction(adView);


            }


            @Override
            public void onAdClicked(Ad ad) {

            }
        });

        nativeAd.loadAd();
    }
}
