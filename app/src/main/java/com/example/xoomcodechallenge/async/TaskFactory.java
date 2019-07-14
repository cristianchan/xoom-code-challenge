package com.example.xoomcodechallenge.async;

import com.example.xoomcodechallenge.service.CountryService;

public final class TaskFactory {
    private  TaskFactory(){

    }

    public static UpdateCountryFavoriteAsyncTask getUpdateCountryFavoriteAsyncTask(final CountryService countryService) {
        return  new UpdateCountryFavoriteAsyncTask(countryService);
    }

    public static LoadRestApiAsyncTask getLoadRestApiAsyncTask(final CountryService countryService , final CountryListener countryListener) {
        return new LoadRestApiAsyncTask(countryService, countryListener);
    }
}
