package com.example.nguyentung.NauAnVungMien;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.nguyentung.NauAnVungMien.db.MonAnController;

import java.util.ArrayList;

import it.gmariotti.recyclerview.adapter.AlphaAnimatorAdapter;

import static com.example.nguyentung.NauAnVungMien.ChiTietMonAnActivity.ok_change;

public class YeuThichActivity extends AppCompatActivity {

    RecyclerView rclyeuthich;
    public static YeuThichAdapter yeuThichAdapter;
    private MonAnController monAnController ;
    private ArrayList<ThongTinMonAn> lsYeuThich;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeu_thich);

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
        getSupportActionBar().setCustomView(R.layout.action_bar_yeuthich);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        rclyeuthich = (RecyclerView) findViewById(R.id.rclyeuthich);
        lsYeuThich = getMonAnYeuThich();
        yeuThichAdapter = new YeuThichAdapter(lsYeuThich, this);
        AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(yeuThichAdapter, rclyeuthich);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rclyeuthich.setLayoutManager(manager);

        rclyeuthich.setAdapter(animatorAdapter);
    }

    public ArrayList<ThongTinMonAn> getMonAnYeuThich(){
        return new MonAnController(YeuThichActivity.this).getMonAnYeuThich();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_monanyeuthich, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.deleteAll:
                final MaterialDialog.Builder builder1 = new MaterialDialog.Builder(this);
                builder1.title("Xóa tất cả");
                builder1.content("Bạn có thật sự muốn xóa tất cả món ăn ra khỏi danh sách yêu thích không?");
                builder1.positiveText("OK");
                builder1.negativeText("Cancel");
                //builder1.show();
                final MaterialDialog materialDialog = builder1.build();
                builder1.onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        for(ThongTinMonAn monan: new MonAnController(YeuThichActivity.this).getMonAnYeuThich()){
                            if(monan.getThich()==1){
                                monan.setThich(0);
                                new MonAnController(YeuThichActivity.this).updateMonAn(monan);
                            }
                        }
                        lsYeuThich.clear();
                        yeuThichAdapter.notifyDataSetChanged();
                    }
                });
                builder1.onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        materialDialog.dismiss();
                    }
                });
                materialDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ok_change==1){
            yeuThichAdapter = new YeuThichAdapter(getMonAnYeuThich(), this);
            AlphaAnimatorAdapter animatorAdapter = new AlphaAnimatorAdapter(yeuThichAdapter, rclyeuthich);
            rclyeuthich.setAdapter(animatorAdapter);
        }
    }
}
