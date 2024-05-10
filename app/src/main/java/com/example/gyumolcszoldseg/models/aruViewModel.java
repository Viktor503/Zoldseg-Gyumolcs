package com.example.gyumolcszoldseg.models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.gyumolcszoldseg.repositoryk.aruRepository;

import java.util.List;

public class aruViewModel extends AndroidViewModel {
    private aruRepository repository;
    private LiveData<List<aruModell>> aruk;

    public aruViewModel(Application application) {
        super(application);
        repository = new aruRepository(application);
        aruk = repository.getAllData();
    }

    public LiveData<List<aruModell>> getAruk() {
        return aruk;
    }

    public void insert(aruModell aru) {
        repository.insert(aru);
    }

    public void deleteAll() {
        repository.deleteAll();
    }

}
