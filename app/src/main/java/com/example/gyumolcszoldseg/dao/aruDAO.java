package com.example.gyumolcszoldseg.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.gyumolcszoldseg.models.aruModell;

import java.util.List;

@Dao
public interface aruDAO {


        @Insert(onConflict = OnConflictStrategy.REPLACE)
        void insert(aruModell aru);

        @Query("DELETE FROM aruk")
        void deleteAll();

        @Query("DELETE FROM aruk WHERE nev = :nev")
        void delete(String nev);

        //@Delete
        // void delete(Word word);

        @Query("SELECT * FROM aruk ORDER BY nev ASC")
        LiveData<List<aruModell>> getdata();
}

