package com.example.nguyentung.NauAnVungMien.db;

import android.graphics.Bitmap;

/**
 * Created by Nguyen Tung on 11/23/2016.
 */

public class ThongTinMeoVat {
    private String Name;
    private String Detail;
    private Bitmap Bitmap;

    public ThongTinMeoVat() {
    }

    public ThongTinMeoVat(String name, String detail, android.graphics.Bitmap bitmap) {
        Name = name;
        Detail = detail;
        Bitmap = bitmap;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public android.graphics.Bitmap getBitmap() {
        return Bitmap;
    }

    public void setBitmap(android.graphics.Bitmap bitmap) {
        Bitmap = bitmap;
    }
}
