package com.petarvelikov.currencytracker.model.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.petarvelikov.currencytracker.model.CurrencyIcon;
import com.petarvelikov.currencytracker.model.Transaction;

@Database(entities = {CurrencyIcon.class, Transaction.class}, version = 2, exportSchema = false)
public abstract class CurrencyDatabase extends RoomDatabase {

  public abstract CurrencyIconDao currencyDao();

  public abstract TransactionsDao transactionsDao();
}
