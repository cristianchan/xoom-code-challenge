package com.example.xoomcodechallenge;

import android.app.Activity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.xoomcodechallenge.adapter.CountriesAdapter;
import com.example.xoomcodechallenge.client.CountryClient;
import dagger.android.AndroidInjection;

import javax.inject.Inject;

public class MainActivity extends Activity {

    @Inject
    public CountriesAdapter countriesAdapter;

    @Inject
    public CountryClient countryClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RecyclerView countryRecyclerView = findViewById(R.id.countriesRecyclerView);
        countryRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        countryRecyclerView.setAdapter(countriesAdapter);

        countryClient.getCountries();
    }
}
