package com.example.xoomcodechallenge.db;

import javax.inject.Singleton;

@Singleton
public class CountryRepository {

    private final CountryDao countryDao;

    public CountryRepository(final CountryDao countryDao) {
        this.countryDao = countryDao;
    }
}
