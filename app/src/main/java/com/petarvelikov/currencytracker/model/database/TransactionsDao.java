package com.petarvelikov.currencytracker.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.petarvelikov.currencytracker.model.Transaction;

import java.util.List;

@Dao
public interface TransactionsDao {

  @Query("SELECT * FROM transactions")
  List<Transaction> getAll();

  @Insert
  void insert(Transaction... transactions);

  @Delete
  void delete(Transaction transaction);
}
