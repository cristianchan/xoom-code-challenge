package com.example.xoomcodechallenge.db;

import android.content.Context;
import androidx.room.Room;

public final class DatabaseConfig {
    public static final String DATABASE_NAME = "xoom_db";

    private static AppDatabase db = null;

    public static AppDatabase getDatabase(final Context context) {
        if (db == null) {
            db = Room.databaseBuilder(context, AppDatabase.class, DATABASE_NAME).build();
        }

        return db;
    }
}