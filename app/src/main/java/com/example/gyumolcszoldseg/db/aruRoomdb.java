package com.example.gyumolcszoldseg.db;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.gyumolcszoldseg.dao.aruDAO;
import com.example.gyumolcszoldseg.models.aruModell;

@Database(entities = {aruModell.class}, version = 2, exportSchema = false)
public abstract class aruRoomdb extends RoomDatabase {

    public abstract aruDAO aruModellDAO();

    private static aruRoomdb instance;

    public static aruRoomdb getInstance(Context context) {
        if (instance == null) {
            synchronized (aruRoomdb.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                                    context.getApplicationContext(), aruRoomdb.class,
                                    "aru_database").fallbackToDestructiveMigration().build();
                            //.addCallback(populationCallback).build();
                }
            }
        }

        return instance;
    }

    /*
    private static RoomDatabase.Callback populationCallback = new RoomDatabase.Callback() {
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            new InitDb(instance).execute();
        }
    };

    private static class InitDb extends AsyncTask<Void, Void, Void> {
        private aruDAO dao;
        aruModell[] aruModells = new aruModell[]{
                new aruModell("Alma", "kg", 200, 0),
                new aruModell("Körte", "kg", 300, 1),
                new aruModell("Banán", "kg", 400, 2),
                new aruModell("Narancs", "kg", 500, 3),
                new aruModell("Mandarin", "kg", 600, 4),
                new aruModell("Citrom", "kg", 700, 5),
                new aruModell("Málna", "kg", 800, 6),
                new aruModell("Eper", "kg", 900, 7),
                new aruModell("Szeder", "kg", 1000, 8),
                new aruModell("Cseresznye", "kg", 1100, 9),
                new aruModell("Meggy", "kg", 1200, 10),
                new aruModell("Szőlő", "kg", 1300, 11),
                new aruModell("Kivi", "kg", 1400, 12),
                new aruModell("Ananász", "kg", 1500, 13),
                new aruModell("Mango", "kg", 1600, 14),
                new aruModell("Avokádó", "kg", 1700, 15),
                new aruModell("Paradicsom", "kg", 1800, 16),
                new aruModell("Uborka", "kg", 1900, 17),
                new aruModell("Paprika", "kg", 2000, 18),
                new aruModell("Sárgarépa", "kg", 2100, 19),
                new aruModell("Burgonya", "kg", 2200, 20),
                new aruModell("Hagyma", "kg", 2300, 21),
                new aruModell("Fokhagyma", "kg", 2400, 22)
                };

        InitDb(aruRoomdb db) {
            this.dao = db.aruModellDAO();
        }

    }*/
}
