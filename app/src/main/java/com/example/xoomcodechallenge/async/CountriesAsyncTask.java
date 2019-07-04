package com.example.xoomcodechallenge.async;

import android.content.Context;
import android.os.AsyncTask;
import com.example.xoomcodechallenge.db.AppDatabase;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.db.CountryDao;
import com.example.xoomcodechallenge.db.DatabaseConfig;

import java.util.List;

import static com.example.xoomcodechallenge.MainActivity.CountryListener;

public class CountriesAsyncTask extends AsyncTask<Void, Void, List<Country>> {
    private final Context context;
    private final CountryListener countryListener;

    public CountriesAsyncTask(final Context context, CountryListener countryListener) {
        this.context = context;
        this.countryListener = countryListener;
    }

    @Override
    protected List<Country> doInBackground(Void... voids) {
        final AppDatabase database = DatabaseConfig.getDatabase(context);
        final CountryDao countryDao = database.countryDao();
        return countryDao.getAll();
    }

    @Override
    protected void onPostExecute(final List<Country> countries){
        this.countryListener.onSuccess(countries);
    }
}
