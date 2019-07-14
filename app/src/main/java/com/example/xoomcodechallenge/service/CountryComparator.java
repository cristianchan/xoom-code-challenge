package com.example.xoomcodechallenge.service;

import com.example.xoomcodechallenge.db.Country;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Comparator;

@Singleton
public class CountryComparator implements Comparator<Country> {

    @Inject
    public CountryComparator() {

    }

    @Override
    public int compare(Country country1, Country country2) {
        final Boolean favorite1 = country1.isFavorite();
        final Boolean favorite2 = country2.isFavorite();

        final int favoriteCompare = favorite2.compareTo(favorite1);
        if (favoriteCompare != 0) {
            return favoriteCompare;
        }

        final String name1 = country1.getName();
        final String name2 = country2.getName();

        return name1.compareTo(name2);
    }
}
