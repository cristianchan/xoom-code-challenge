package com.example.xoomcodechallenge.dagger2;

import android.app.Application;
import androidx.room.Room;
import com.example.xoomcodechallenge.db.AppDatabase;
import com.example.xoomcodechallenge.db.CountryDao;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public class DbModule {
    public static final String DATABASE_NAME = "xoom_db";

    @Provides
    @Singleton
    AppDatabase provideDatabase(final Application application) {
        return Room.databaseBuilder(application, AppDatabase.class, DATABASE_NAME).build();
    }

    @Provides
    @Singleton
    CountryDao provideCountryDao(final AppDatabase appDatabase) {
        return appDatabase.countryDao();
    }
}
