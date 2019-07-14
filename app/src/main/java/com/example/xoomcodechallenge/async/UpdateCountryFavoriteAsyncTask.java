package com.example.xoomcodechallenge.async;

import android.os.AsyncTask;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.service.CountryService;

import java.util.List;

public class UpdateCountryFavoriteAsyncTask extends AsyncTask<String, Void, List<Country>> {
    private final CountryService countryService;

    public UpdateCountryFavoriteAsyncTask(final CountryService countryService) {
        this.countryService = countryService;
    }

    @Override
    protected List<Country> doInBackground(String... strings) {
        final String slug = strings[0];
        return countryService.updateFavorite(slug);
    }
}
