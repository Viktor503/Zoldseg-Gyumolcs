package com.example.gyumolcszoldseg.models;

public class aruModell {
    String nev;
    String mertekegyseg;
    int ar;
    int img;

    public aruModell(String nev, String mertekegyseg, int ar, int img) {
        this.nev = nev;
        this.mertekegyseg = mertekegyseg;
        this.ar = ar;
        this.img = img;
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
}
