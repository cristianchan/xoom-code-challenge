package com.example.xoomcodechallenge.service;

import android.content.Context;
import android.util.Log;
import com.example.xoomcodechallenge.db.AppDatabase;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.db.CountryDao;
import com.example.xoomcodechallenge.db.DatabaseConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CountryService {
    private final Context context;
    private final AppDatabase database;
    private final CountryDao countryDao;

    public CountryService(Context context) {
        this.context = context;
        this.database = DatabaseConfig.getDatabase(context);
        this.countryDao = database.countryDao();
    }

    public List<Country> updateCountries(final JSONObject countriesResponse) {
        try {
            JSONArray items = countriesResponse.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                final JSONObject jsonObject = items.getJSONObject(i);
                final String slug = jsonObject.getString("code");
                final String name = jsonObject.getString("name");
                final Country country = countryDao.getCountryBySlug(slug);

                if (country == null) {
                    final Country newCountry = new Country(slug, name, null, false);
                    countryDao.insert(newCountry);

                }
            }

        } catch (JSONException error) {
            Log.e("CountryService", error.getMessage());
        }
        return countryDao.getAll();
    }

    public List<Country> getCountries() {
        return countryDao.getAll();
    }

    public List<Country> updateFavorite(final String slug) {
        final Country country = countryDao.getCountryBySlug(slug);

        country.setFavorite(!country.isFavorite());
        countryDao.update(country);

        return countryDao.getAll();
    }
}
