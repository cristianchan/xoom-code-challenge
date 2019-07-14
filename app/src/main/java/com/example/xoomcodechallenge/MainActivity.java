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
import com.example.xoomcodechallenge.async.CountryListener;
import com.example.xoomcodechallenge.async.TaskFactory;
import com.example.xoomcodechallenge.service.CountryService;
import com.example.xoomcodechallenge.volley.VolleyQueue;
import dagger.android.AndroidInjection;
import org.json.JSONObject;

import javax.inject.Inject;

import static com.android.volley.Request.Method.GET;

public class MainActivity extends Activity {
    private static String COUNTRIES_URL = "https://mobile.xoom.com/catalog/v2/countries";

    @Inject
    public CountriesAdapter countriesAdapter;
    @Inject
    public CountryListener countryListener;
    @Inject
    public CountryService countryService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView countryRecyclerView = findViewById(R.id.countriesRecyclerView);
        countryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        countryRecyclerView.setAdapter(countriesAdapter);

        final RequestQueue requestQueue = VolleyQueue.getRequestQueue(getApplicationContext());
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET, COUNTRIES_URL, null, success, error);

        requestQueue.add(jsonObjectRequest);


    }

    private Response.Listener<JSONObject> success = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            TaskFactory.getLoadRestApiAsyncTask(countryService, countryListener).execute();
        }
    };

    private Response.ErrorListener error = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e("volley", error.getMessage());
            TaskFactory.getLoadRestApiAsyncTask(countryService, countryListener).execute();
        }
    };
}
