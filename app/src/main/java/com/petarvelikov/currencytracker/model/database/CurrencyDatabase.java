package com.petarvelikov.currencytracker.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.petarvelikov.currencytracker.model.CurrencyIcon;

@Database(entities = {CurrencyIcon.class}, version = 1)
public abstract class CurrencyDatabase extends RoomDatabase {

    public abstract CurrencyIconDao currencyDao();
}
