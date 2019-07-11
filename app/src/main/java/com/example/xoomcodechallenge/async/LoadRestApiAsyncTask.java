package com.example.xoomcodechallenge.async;

import android.os.AsyncTask;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.service.CountryService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONObject;

import java.util.List;

import static com.example.xoomcodechallenge.MainActivity.CountryListener;
import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

public class LoadRestApiAsyncTask extends AsyncTask<JSONObject, Void, List<Country>> {
    private final CountryListener countryListener;
    private final Gson gson;
    private final CountryService countryService;

    public LoadRestApiAsyncTask(CountryService countryService, CountryListener countryListener) {
        this.countryService = countryService;
        this.countryListener = countryListener;
        this.gson = new GsonBuilder()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .create();
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
