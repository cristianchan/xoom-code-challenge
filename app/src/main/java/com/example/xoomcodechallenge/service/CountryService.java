package com.example.xoomcodechallenge.service;

import android.util.Log;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.db.CountryDao;
import com.example.xoomcodechallenge.response.CountryResponse;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;



public class CountryService {
    private final CountryDao countryDao;
    private final Gson gson;

    public CountryService(CountryDao countryDao, Gson gson) {
        this.countryDao = countryDao;
        this.gson = gson;
    }

    public List<Country> updateCountries(final JSONObject... jsonObjects) {
        try {
            if (jsonObjects != null && jsonObjects.length > 0) {
                final JSONObject jsonObject = jsonObjects[0];
                JSONArray items = jsonObject.getJSONArray("items");
                final List<CountryResponse> countryResponses = getCountryResponses(items);

                for (final CountryResponse countryResponse : countryResponses) {
                    final String slug = countryResponse.getCode();
                    final String name = countryResponse.getName();
                    final Country country = countryDao.getCountryBySlug(slug);

                    if (country == null && haveDisbursementOptions(countryResponse)) {
                        final Country newCountry = new Country(slug, name, null, false);
                        countryDao.insert(newCountry);

                    } else if (country != null && !haveDisbursementOptions(countryResponse)) {
                        countryDao.delete(country);
                    }
                }
            }
        } catch (JSONException e) {
            Log.e("CountryService", e.getMessage());
        }

        return countryDao.getAll();
    }

    public List<Country> updateFavorite(final String slug) {
        final Country country = countryDao.getCountryBySlug(slug);

        country.setFavorite(!country.isFavorite());
        countryDao.update(country);

        return countryDao.getAll();
    }

    private List<CountryResponse> getCountryResponses(final JSONArray jsonArray) {
        final Type listType = new TypeToken<List<CountryResponse>>() {
        }.getType();
        return gson.fromJson(jsonArray.toString(), listType);
    }

    private Boolean haveDisbursementOptions(final CountryResponse countryResponse) {
        if (countryResponse.getDisbursementOptions() != null) {
            for (final CountryResponse.DisbursementOption disbursementOption : countryResponse.getDisbursementOptions()) {
                if (disbursementOption.getMode().equalsIgnoreCase("ACTIVE")) {
                    return true;
                }
            }
        }
        return false;
    }
}
