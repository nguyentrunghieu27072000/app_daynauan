package com.example.nguyentung.NauAnVungMien.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.nguyentung.NauAnVungMien.ThongTinMonAn;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by Nguyen Tung on 11/12/2016.
 */

public class MonAnController extends SQLiteDataController{
    public MonAnController(Context con) {
        super(con);
    }

    public ArrayList<ThongTinMonAn> getMonAn(){
        ArrayList<ThongTinMonAn> lsMonAn = new ArrayList<>();
        openDataBase();
        Cursor cs = database.rawQuery("select Name,Material,Process,YeuThich, Image " + "from mon_an", null);
        while(cs.moveToNext()){
            String name = cs.getString(0);
            String material = cs.getString(1);
            String process = cs.getString(2);
            Integer yeuthich = cs.getInt(3);
            byte[] byteArray = cs.getBlob(4);
            Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ThongTinMonAn mon_an = new ThongTinMonAn(name, bm, material, process, yeuthich);
            lsMonAn.add(mon_an);
        }
        return lsMonAn;
    }

    public ArrayList<ThongTinMonAn> getMonAnMienBac(){
        ArrayList<ThongTinMonAn> lsMonAn = new ArrayList<>();
        openDataBase();
        Cursor cs = database.rawQuery("select Name, Material, Process, YeuThich, Image " + "from mon_an where VungMien = 1", null);
        while(cs.moveToNext()){
            String name = cs.getString(0);
            String material = cs.getString(1);
            String process = cs.getString(2);
            Integer yeuthich = cs.getInt(3);
            byte[] byteArray = cs.getBlob(4);
            Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ThongTinMonAn mon_an = new ThongTinMonAn(name, bm, material, process, yeuthich);
            lsMonAn.add(mon_an);
        }
        return lsMonAn;
    }

    public ArrayList<ThongTinMonAn> getMonAnMienTrung(){
        ArrayList<ThongTinMonAn> lsMonAn = new ArrayList<>();
        openDataBase();
        Cursor cs = database.rawQuery("select Name,Material,Process,YeuThich, Image " + "from mon_an where VungMien = 2", null);
        while(cs.moveToNext()){
            String name = cs.getString(0);
            String material = cs.getString(1);
            String process = cs.getString(2);
            Integer yeuthich = cs.getInt(3);
            byte[] byteArray = cs.getBlob(4);
            Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ThongTinMonAn mon_an = new ThongTinMonAn(name, bm, material, process, yeuthich);
            lsMonAn.add(mon_an);
        }
        return lsMonAn;
    }

    public ArrayList<ThongTinMonAn> getMonAnMienNam(){
        ArrayList<ThongTinMonAn> lsMonAn = new ArrayList<>();
        openDataBase();
        Cursor cs = database.rawQuery("select Name,Material,Process,YeuThich, Image " + "from mon_an where VungMien = 3", null);
        while(cs.moveToNext()){
            String name = cs.getString(0);
            String material = cs.getString(1);
            String process = cs.getString(2);
            Integer yeuthich = cs.getInt(3);
            byte[] byteArray = cs.getBlob(4);
            Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ThongTinMonAn mon_an = new ThongTinMonAn(name, bm, material, process, yeuthich);
            lsMonAn.add(mon_an);
        }
        return lsMonAn;
    }

    public ArrayList<ThongTinMonAn> getMonAnMienTay(){
        ArrayList<ThongTinMonAn> lsMonAn = new ArrayList<>();
        openDataBase();
        Cursor cs = database.rawQuery("select Name,Material,Process,YeuThich, Image " + "from mon_an where VungMien = 4", null);
        while(cs.moveToNext()){
            String name = cs.getString(0);
            String material = cs.getString(1);
            String process = cs.getString(2);
            Integer yeuthich = cs.getInt(3);
            byte[] byteArray = cs.getBlob(4);
            Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ThongTinMonAn mon_an = new ThongTinMonAn(name, bm, material, process, yeuthich);
            lsMonAn.add(mon_an);
        }
        return lsMonAn;
    }

    public ArrayList<ThongTinMonAn> getMonAnYeuThich(){
        ArrayList<ThongTinMonAn> lsMonAn = new ArrayList<>();
        openDataBase();
        Cursor cs = database.rawQuery("select Name,Material,Process,YeuThich, Image " + "from mon_an where YeuThich = 1", null);
        while(cs.moveToNext()){
            String name = cs.getString(0);
            String material = cs.getString(1);
            String process = cs.getString(2);
            Integer yeuthich = cs.getInt(3);
            byte[] byteArray = cs.getBlob(4);
            Bitmap bm = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            ThongTinMonAn mon_an = new ThongTinMonAn(name, bm, material, process, yeuthich);
            lsMonAn.add(mon_an);
        }
        return lsMonAn;
    }


    public boolean updateMonAn(ThongTinMonAn monAn){
        boolean rs = false;
        try {
            openDataBase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Name", monAn.getName());
            contentValues.put("Material", monAn.getMaterial());
            contentValues.put("Process", monAn.getProcess());
            contentValues.put("YeuThich", monAn.getThich());
            contentValues.put("Image", getByteArrayFromBitmat(monAn.getBm()));
            long id = database.update("mon_an", contentValues, "Name like '"+ monAn.getName() + "'", null);
            if(id>=0){
                rs = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return rs;
    }

    private byte[] getByteArrayFromBitmat(Bitmap bmp){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
