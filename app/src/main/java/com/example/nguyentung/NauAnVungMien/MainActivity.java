package com.example.nguyentung.NauAnVungMien;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nguyentung.NauAnVungMien.Fragment.MienBac_Fragment;
import com.example.nguyentung.NauAnVungMien.Fragment.MienNam_Fragment;
import com.example.nguyentung.NauAnVungMien.Fragment.MienTay_Fragment;
import com.example.nguyentung.NauAnVungMien.Fragment.MienTrung_Fragment;
import com.example.nguyentung.NauAnVungMien.MeoVat.MeoVatActivity;
import com.example.nguyentung.NauAnVungMien.db.SQLiteDataController;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TabLayout tabLayout;
    ViewPager viewPager;
    MonAnCacMien adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        SQLiteDataController sql = new SQLiteDataController(this);
        sql.isCreatedDatabase();

        Window window = this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.MainColor));
        }

        // Đặt title ra giữa
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mTitle = (TextView) toolbar.findViewById(R.id.toolbar_title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        // ViewPaper
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        Fragment[] screens = {new MienBac_Fragment(), new MienTrung_Fragment(), new MienNam_Fragment(), new MienTay_Fragment()};
        adapter = new MonAnCacMien(getSupportFragmentManager(), screens);
        viewPager.setAdapter(adapter);
        //set off screen page limit la de load toan bo du lieu fragments cung mot luc. Neu khong co thi se chi load fragment tiep theo.
        //Vi du nhu dang o mien bac thi se chi load mien trung, khong load cac mien con lai.
        viewPager.setOffscreenPageLimit(4);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setTabsFromPagerAdapter(adapter);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            finish();
            System.exit(0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.mnSearch) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()){
            case R.id.mnTip:
                Intent meovat = new Intent(this, MeoVatActivity.class);
                startActivity(meovat);
                break;
            case R.id.mnFavorite:
                Intent yeuthich = new Intent(MainActivity.this, YeuThichActivity.class);
                startActivity(yeuthich);
                break;
            case R.id.mnToday:
                Intent anGi = new Intent(MainActivity.this,HomNayAnGiActivity.class);
                startActivity(anGi);
                break;
            case R.id.mnInfo:
                Intent info = new Intent(MainActivity.this, ThongTinActivity.class);
                startActivity(info);
                break;
            case R.id.mnRate:
                launchMarket();
                break;
            case R.id.mnFeed:
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, "emailaddress@emailaddress.com");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
                intent.putExtra(Intent.EXTRA_TEXT, "");
                startActivity(Intent.createChooser(intent, "Send Email"));
                break;
            case R.id.mnFacebook:
                Intent intent_fb = getPackageManager().getLaunchIntentForPackage("com.facebook.katana");
         //       String urlToShare = "http://stackoverflow.com/questions/7545254";
                if (intent_fb != null) {
                    // The application exists
                    Intent shareIntent = new Intent();
                    shareIntent.setType("text/plain");
         //           shareIntent.putExtra(Intent.EXTRA_TEXT, urlToShare);
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setPackage("com.facebook.katana");
           //         shareIntent.putExtra(Intent.EXTRA_TITLE, "Sharing");

                    // Start the specific social application
                    startActivity(Intent.createChooser(shareIntent, "Choose Facebook"));
                } else {
                    // The application does not exist
                    // Open GooglePlay or use the default system picker
          //          String sharerUrl = "https://www.facebook.com/sharer/sharer.php?u=" + urlToShare;
          //          intent = new Intent(Intent.ACTION_VIEW, Uri.parse(sharerUrl));
                    Toast.makeText(this, "Facebook has not been installed", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.mnTwitter:
                Intent intent_tw = getPackageManager().getLaunchIntentForPackage("com.twitter.android");
                if (intent_tw != null) {
                    // The application exists
                    Intent shareIntent = new Intent();
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_TEXT, "This app is awesome");
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.setPackage("com.twitter.android");
                    //         shareIntent.putExtra(Intent.EXTRA_TITLE, "Sharing");

                    // Start the specific social application
                    startActivity(Intent.createChooser(shareIntent, "Choose Twitter"));
                } else {
                    // The application does not exist
                    // Open GooglePlay or use the default system picker

                    Toast.makeText(this, "Twitter has not been installed", Toast.LENGTH_LONG).show();
                }
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void launchMarket() {
        Uri uri = Uri.parse("market://details?id=" + getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }
}
