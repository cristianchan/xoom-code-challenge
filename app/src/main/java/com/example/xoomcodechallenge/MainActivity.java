package com.example.xoomcodechallenge;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.xoomcodechallenge.adapter.CountriesAdapter;
import com.example.xoomcodechallenge.async.LoadRestApiAsyncTask;
import com.example.xoomcodechallenge.db.AppDatabase;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.db.CountryDao;
import com.example.xoomcodechallenge.db.DatabaseConfig;
import com.example.xoomcodechallenge.service.CountryService;
import com.example.xoomcodechallenge.volley.VolleyQueue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;
import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;

public class MainActivity extends Activity {
    private static String COUNTRIES_URL = "https://mobile.xoom.com/catalog/v2/countries";

    private CountriesAdapter countriesAdapter;
    private LoadRestApiAsyncTask loadRestApiAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView countryRecyclerView = findViewById(R.id.countriesRecyclerView);
        countryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        final AppDatabase database = DatabaseConfig.getDatabase(getApplicationContext());
        final CountryDao countryDao = database.countryDao();
        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .create();
        final CountryService countryService = new CountryService(countryDao, gson);

        final Picasso picasso = new Picasso.Builder(getApplicationContext()).build();

        countriesAdapter = new CountriesAdapter(new ArrayList<Country>(), getApplicationContext(), countryListener, countryService , picasso);
        countryRecyclerView.setAdapter(countriesAdapter);

        final RequestQueue requestQueue = VolleyQueue.getRequestQueue(getApplicationContext());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET, COUNTRIES_URL, null, success, error);

        loadRestApiAsyncTask = new LoadRestApiAsyncTask(countryService, countryListener);


        requestQueue.add(jsonObjectRequest);

    }

    private CountryListener countryListener = new CountryListener() {
        @Override
        public void onSuccess(List<Country> countries) {
            countriesAdapter.setItems(countries);
            countriesAdapter.notifyDataSetChanged();
        }
    };

    private Response.Listener<JSONObject> success = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            loadRestApiAsyncTask.execute(response);
        }
    };

    private Response.ErrorListener error = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("volley", error.getMessage());
            loadRestApiAsyncTask.execute();
        }
    };

    public interface CountryListener {
        void onSuccess(final List<Country> countries);
    }
}
