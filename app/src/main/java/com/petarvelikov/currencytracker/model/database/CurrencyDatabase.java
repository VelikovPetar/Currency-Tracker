package com.petarvelikov.currencytracker.model.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.petarvelikov.currencytracker.model.CurrencyIcon;
import com.petarvelikov.currencytracker.model.Transaction;

@Database(entities = {CurrencyIcon.class, Transaction.class}, version = 5, exportSchema = false)
public abstract class CurrencyDatabase extends RoomDatabase {

  public abstract CurrencyIconDao currencyDao();

  public abstract TransactionsDao transactionsDao();

  public static class Migrations {

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
      @Override
      public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE 'transactions' " +
            "ADD COLUMN 'is_purchase' INTEGER NOT NULL DEFAULT 0");
      }
    };

    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
      @Override
      public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE 'transactions' " +
            "ADD COLUMN 'base_currency' TEXT");
      }
    };

    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {
      @Override
      public void migrate(@NonNull SupportSQLiteDatabase database) {
        database.execSQL("ALTER TABLE 'icons' " +
            "ADD COLUMN 'name' TEXT NOT NULL DEFAULT ''");
      }
    };

    public static Migration[] all() {
      Migration[] migrations = new Migration[3];
      migrations[0] = MIGRATION_2_3;
      migrations[1] = MIGRATION_3_4;
      migrations[2] = MIGRATION_4_5;
      return migrations;
    }
  }
}
