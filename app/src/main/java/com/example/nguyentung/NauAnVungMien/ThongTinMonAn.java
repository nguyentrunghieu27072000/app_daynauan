package com.example.nguyentung.NauAnVungMien;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by hungn on 16-Oct-16.
 */

public class ThongTinMonAn implements Serializable{


    protected String name;
    protected Integer thich;
    protected String material;
    protected String process;
    protected Bitmap bm;

    public ThongTinMonAn(String name, Bitmap bm, String material, String process, int thich) {
        this.name = name;
        this.bm = bm;
        this.process = process;
        this.material = material;
        this.thich = thich;
    }



    public ThongTinMonAn(String name, int thich, String material, String process) {
        this.name = name;
        this.thich = thich;
        this.material = material;
        this.process = process;
    }


    public ThongTinMonAn(String name, String material, String process) {
        this.name = name;
        this.material = material;
        this.process = process;
    }

    public ThongTinMonAn(String tenmonan, Bitmap bm, String nguyenlieu, String cachchebien) {
        this.name = tenmonan;
        this.bm = bm;
        this.material = nguyenlieu;
        this.process = cachchebien;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public Integer getThich() {
        return thich;
    }

    public void setThich(int thich) {
        this.thich = thich;
    }

    public ThongTinMonAn(){}

    public ThongTinMonAn(String name, Bitmap bm) {
        this.name = name;
        this.bm = bm;
    }

    public Bitmap getBm() {
        return bm;
    }

    public void setBm(Bitmap bm) {
        this.bm = bm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
