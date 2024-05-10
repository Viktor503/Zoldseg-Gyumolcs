package com.example.gyumolcszoldseg.repositoryk;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.gyumolcszoldseg.dao.aruDAO;
import com.example.gyumolcszoldseg.db.aruRoomdb;
import com.example.gyumolcszoldseg.models.aruModell;

import java.util.List;

public class aruRepository {
    private aruDAO dao;
    private LiveData<List<aruModell>> aruk;

    public aruRepository(Application application) {
        aruRoomdb db = aruRoomdb.getInstance(application);
        dao = db.aruModellDAO();
        aruk = dao.getdata();
    }

    public LiveData<List<aruModell>> getAllData() {
        return aruk;
    }

    public void insert(aruModell aru) {
        new Insert(this.dao).execute(aru);
    }


    private static class Insert extends AsyncTask<aruModell, Void, Void> {
        private aruDAO mdao;

        Insert(aruDAO dao) {
            this.mdao = dao;
        }

        @Override
        protected Void doInBackground(aruModell... aruModells) {
            mdao.insert(aruModells[0]);
            return null;
        }
    }
}
