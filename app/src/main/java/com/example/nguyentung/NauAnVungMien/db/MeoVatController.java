package com.example.nguyentung.NauAnVungMien.db;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.util.ArrayList;

/**
 * Created by Nguyen Tung on 11/23/2016.
 */

public class MeoVatController extends SQLiteDataController {
    public MeoVatController(Context con) {
        super(con);
    }

    public ArrayList<ThongTinMeoVat> getMeoVat(){
        ArrayList<ThongTinMeoVat> lsMeoVat = new ArrayList<>();
        openDataBase();
        Cursor cs = database.rawQuery("select Name,Detail,Image " + "from meo_vat", null);
        while(cs.moveToNext()){
            String name = cs.getString(0);
            String detail = cs.getString(1);
            byte[] byteArray = cs.getBlob(2);
            Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ThongTinMeoVat mon_an = new ThongTinMeoVat(name, detail, bm);
            lsMeoVat.add(mon_an);
        }
        return lsMeoVat;
    }
}
