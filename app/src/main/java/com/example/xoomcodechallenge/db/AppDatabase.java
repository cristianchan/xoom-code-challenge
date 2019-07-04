package com.example.xoomcodechallenge.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {Country.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    public abstract CountryDao countryDao();
}