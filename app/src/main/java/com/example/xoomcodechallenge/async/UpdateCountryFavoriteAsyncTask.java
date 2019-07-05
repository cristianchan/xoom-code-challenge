package com.example.xoomcodechallenge.async;

import android.content.Context;
import android.os.AsyncTask;
import com.example.xoomcodechallenge.MainActivity;
import com.example.xoomcodechallenge.db.AppDatabase;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.db.CountryDao;
import com.example.xoomcodechallenge.db.DatabaseConfig;

import java.util.List;

public class UpdateCountryFavoriteAsyncTask extends AsyncTask<Void, Void, List<Country>> {
    private final Context context;
    private final MainActivity.CountryListener countryListener;
    private final String slug;

    public UpdateCountryFavoriteAsyncTask(final Context context, final MainActivity.CountryListener countryListener, final String slug) {
        this.context = context;
        this.countryListener = countryListener;
        this.slug = slug;
    }

    @Override
    protected List<Country> doInBackground(Void... voids) {
        final AppDatabase database = DatabaseConfig.getDatabase(context);
        final CountryDao countryDao = database.countryDao();
        final Country country = countryDao.getCountryBySlug(slug);

        country.setFavorite(!country.isFavorite());
        countryDao.update(country);

        return countryDao.getAll();
    }

    @Override
    protected void onPostExecute(final List<Country> countries) {
        this.countryListener.onSuccess(countries);
    }
}
