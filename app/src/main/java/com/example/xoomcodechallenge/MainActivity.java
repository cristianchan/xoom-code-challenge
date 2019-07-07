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
import com.example.xoomcodechallenge.async.LoadCountriesDBAsyncTask;
import com.example.xoomcodechallenge.async.LoadRestApiAsyncTask;
import com.example.xoomcodechallenge.country.CountriesAdapter;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.service.VolleyQueue;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends Activity {
    private static String COUNTRIES_URL = "https://mobile.xoom.com/catalog/v2/countries";

    private CountriesAdapter countriesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView countryRecyclerView = findViewById(R.id.countriesRecyclerView);
        countryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        countriesAdapter = new CountriesAdapter(new ArrayList<Country>(), getApplicationContext(), countryListener);
        countryRecyclerView.setAdapter(countriesAdapter);

        final LoadCountriesDBAsyncTask loadCountriesDBAsyncTask = new LoadCountriesDBAsyncTask(getApplicationContext(), countryListener);

        loadCountriesDBAsyncTask.execute();

        final RequestQueue requestQueue = VolleyQueue.getRequestQueue(getApplicationContext());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET, COUNTRIES_URL, null, success, error);

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
            final LoadRestApiAsyncTask loadRestApiAsyncTask = new LoadRestApiAsyncTask(getApplicationContext(), countryListener, response);
            loadRestApiAsyncTask.execute();
        }
    };

    private Response.ErrorListener error = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("volley", error.getMessage());
        }
    };

    public interface CountryListener {
        void onSuccess(final List<Country> countries);
    }
}
