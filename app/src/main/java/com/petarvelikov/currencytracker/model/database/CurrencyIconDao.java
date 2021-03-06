package com.petarvelikov.currencytracker.model.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.petarvelikov.currencytracker.model.CurrencyIcon;

import java.util.List;

@Dao
public interface CurrencyIconDao {

  @Query("SELECT * FROM icons")
  List<CurrencyIcon> getAll();

  @Query("SELECT * FROM icons WHERE symbol = :symbol")
  CurrencyIcon getCurrencyIconBySymbol(String symbol);

  @Query("SELECT * FROM icons WHERE name COLLATE NOCASE = :name")
  CurrencyIcon getCurrencyIconByName(String name);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertMultiple(CurrencyIcon... currencyIcons);

  @Query("SELECT COUNT(uid) from icons")
  int getNumberOfIcons();

  @Query("DELETE FROM icons")
  void deleteAll();

}
