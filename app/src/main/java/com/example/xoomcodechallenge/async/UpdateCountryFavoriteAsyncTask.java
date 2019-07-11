package com.example.xoomcodechallenge.async;

import android.os.AsyncTask;
import com.example.xoomcodechallenge.MainActivity;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.service.CountryService;

import java.util.List;

public class UpdateCountryFavoriteAsyncTask extends AsyncTask<String, Void, List<Country>> {
    private final CountryService countryService;
    private final MainActivity.CountryListener countryListener;

    public UpdateCountryFavoriteAsyncTask(final CountryService countryService, final MainActivity.CountryListener countryListener) {
        this.countryService = countryService;
        this.countryListener = countryListener;
    }

    @Override
    protected List<Country> doInBackground(String... strings) {
        final String slug = strings[0];
        return countryService.updateFavorite(slug);
    }

    @Override
    protected void onPostExecute(final List<Country> countries) {
        this.countryListener.onSuccess(countries);
    }
}
