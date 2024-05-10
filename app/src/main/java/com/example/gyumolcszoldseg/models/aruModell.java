package com.example.gyumolcszoldseg.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "aruk")
public class aruModell {
    @PrimaryKey
    @NonNull
    String nev;
    @ColumnInfo(name = "mertekegyseg")
    String mertekegyseg;
    @ColumnInfo(name = "ar")
    int ar;
    @ColumnInfo(name = "img")
    int img;

    @ColumnInfo(name = "db")
    int darab;

    @Ignore
    public aruModell(String nev, String mertekegyseg, int ar, int img) {
        this.nev = nev;
        this.mertekegyseg = mertekegyseg;
        this.ar = ar;
        this.img = img;
    }
    public aruModell(String nev, String mertekegyseg, int ar, int img, int darab) {
        this.nev = nev;
        this.mertekegyseg = mertekegyseg;
        this.ar = ar;
        this.img = img;
        this.darab = darab;
    }

    public String getNev() {
        return nev;
    }

    public String getMertekegyseg() {
        return mertekegyseg;
    }

    public int getAr() {
        return ar;
    }

    public int getImg() {
        return img;
    }

    public int getDarab() {
        return darab;
    }
}
