package com.example.xoomcodechallenge.async;

import android.os.AsyncTask;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.service.CountryService;
import org.json.JSONObject;

import java.util.List;

public class LoadRestApiAsyncTask extends AsyncTask<JSONObject, Void, List<Country>> {
    private final CountryListener countryListener;
    private final CountryService countryService;

    public LoadRestApiAsyncTask(CountryService countryService, CountryListener countryListener) {
        this.countryService = countryService;
        this.countryListener = countryListener;
    }

    @Override
    protected List<Country> doInBackground(JSONObject... jsonObjects) {
        return countryService.updateCountries(jsonObjects);
    }

    @Override
    protected void onPostExecute(final List<Country> countries) {
        this.countryListener.onSuccess(countries);
    }
}
