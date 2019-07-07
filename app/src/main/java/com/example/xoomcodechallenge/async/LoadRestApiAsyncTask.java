package com.example.xoomcodechallenge.async;

import android.content.Context;
import android.os.AsyncTask;
import com.example.xoomcodechallenge.db.AppDatabase;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.db.CountryDao;
import com.example.xoomcodechallenge.db.DatabaseConfig;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static com.example.xoomcodechallenge.MainActivity.CountryListener;

public class LoadRestApiAsyncTask extends AsyncTask<Void, Void, List<Country>> {
    private final Context context;
    private final CountryListener countryListener;
    private final JSONObject jsonObject;

    public LoadRestApiAsyncTask(final Context context, CountryListener countryListener, JSONObject jsonObject) {
        this.context = context;
        this.countryListener = countryListener;
        this.jsonObject = jsonObject;
    }

    @Override
    protected List<Country> doInBackground(Void... voids) {
        final AppDatabase database = DatabaseConfig.getDatabase(context);
        final CountryDao countryDao = database.countryDao();
        try {
            JSONArray items = jsonObject.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                final JSONObject jsonObject = items.getJSONObject(i);
                final String slug = jsonObject.getString("code");
                final String name = jsonObject.getString("name");
                final Country country = countryDao.getCountryBySlug(slug);

                if(country == null){
                    final Country newCountry = new Country(slug, name, null, false);
                    countryDao.insert(newCountry);

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
}
