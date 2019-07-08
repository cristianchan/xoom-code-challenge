package com.example.xoomcodechallenge.async;

import android.content.Context;
import android.os.AsyncTask;
import com.example.xoomcodechallenge.db.AppDatabase;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.db.CountryDao;
import com.example.xoomcodechallenge.db.DatabaseConfig;
import com.example.xoomcodechallenge.response.CountryResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import static com.example.xoomcodechallenge.MainActivity.CountryListener;
import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

public class LoadRestApiAsyncTask extends AsyncTask<Void, Void, List<Country>> {
    private final Context context;
    private final CountryListener countryListener;
    private final JSONObject jsonObject;
    private final Gson gson;

    public LoadRestApiAsyncTask(final Context context, CountryListener countryListener, JSONObject jsonObject) {
        this.context = context;
        this.countryListener = countryListener;
        this.jsonObject = jsonObject;
        this.gson = new GsonBuilder()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    @Override
    protected List<Country> doInBackground(Void... voids) {
        final AppDatabase database = DatabaseConfig.getDatabase(context);
        final CountryDao countryDao = database.countryDao();
        try {
            if(jsonObject != null){
                JSONArray items = jsonObject.getJSONArray("items");
                final List<CountryResponse> countryResponses = getCountryResponses(items);

                for (final CountryResponse countryResponse : countryResponses) {
                    final String slug = countryResponse.getCode();
                    final String name = countryResponse.getName();
                    final Country country = countryDao.getCountryBySlug(slug);

                    if (country == null && countryResponse.getDisbursementOptions() != null) {
                        final Country newCountry = new Country(slug, name, null, false);
                        countryDao.insert(newCountry);

                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return countryDao.getAll();
    }

    @Override
    protected void onPostExecute(final List<Country> countries) {
        this.countryListener.onSuccess(countries);
    }

    private List<CountryResponse> getCountryResponses(final JSONArray jsonArray) {
        final Type listType = new TypeToken<List<CountryResponse>>() {
        }.getType();
        return gson.fromJson(jsonArray.toString(), listType);
    }
}
