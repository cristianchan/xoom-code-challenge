package com.example.xoomcodechallenge.async;

import com.example.xoomcodechallenge.adapter.CountriesAdapter;
import com.example.xoomcodechallenge.db.Country;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class CountryListener {
    private final CountriesAdapter countriesAdapter;

    @Inject
    public CountryListener(final CountriesAdapter countriesAdapter) {
        this.countriesAdapter = countriesAdapter;
    }

    public void onSuccess(List<Country> countries) {
        countriesAdapter.setItems(countries);
        countriesAdapter.notifyDataSetChanged();
    }
}
