package com.example.xoomcodechallenge.client;

import android.util.Log;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.xoomcodechallenge.async.CountryListener;
import com.example.xoomcodechallenge.async.TaskFactory;
import com.example.xoomcodechallenge.service.CountryService;
import org.json.JSONObject;

import javax.inject.Inject;
import javax.inject.Singleton;

import static com.android.volley.Request.Method.GET;

@Singleton
public class CountryClient {
    private static String COUNTRIES_URL = "https://mobile.xoom.com/catalog/v2/countries";

    private final RequestQueue requestQueue;
    private final CountryService countryService;
    private final CountryListener countryListener;

    @Inject
    public CountryClient(final RequestQueue requestQueue,
                         final CountryService countryService,
                         final CountryListener countryListener) {
        this.requestQueue = requestQueue;
        this.countryService = countryService;
        this.countryListener = countryListener;
    }

    public void getCountries() {
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(GET, COUNTRIES_URL, null, success, error);
        requestQueue.add(jsonObjectRequest);
    }

    private Response.Listener<JSONObject> success = new Response.Listener<JSONObject>() {
        @Override
        public void onResponse(JSONObject response) {
            TaskFactory.getLoadRestApiAsyncTask(countryService, countryListener).execute(response);
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
