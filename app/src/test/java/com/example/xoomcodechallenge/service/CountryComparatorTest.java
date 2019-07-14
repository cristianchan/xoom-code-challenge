package com.example.xoomcodechallenge.service;

import com.example.xoomcodechallenge.db.Country;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


public class CountryComparatorTest {
    private CountryComparator countryComparator;

    @Before
    public void setUp() {
        countryComparator = new CountryComparator();
    }

    @Test
    public void compare_WithSameCountries_ReturnZero() {
        final Country country1 = getCountry();
        final Country country2 = getCountry();

        final int result = countryComparator.compare(country1, country2);

        assertEquals(0, result);
    }

    @Test
    public void compare_WithDifferentNameSameFavoriteStatus_ReturnZero() {
        final Country country1 = getCountry();
        final Country country2 = getCountry();

        country2.setName("Barbados");

        final int result = countryComparator.compare(country1, country2);

        assertNotEquals(0 , result);
    }

    @Test
    public void compare_WithSameNameDifferentFavoriteStatus_ReturnZero() {
        final Country country1 = getCountry();
        final Country country2 = getCountry();

        country1.setFavorite(true);

        final int result = countryComparator.compare(country1, country2);

        assertNotEquals(0 , result);
    }

    private Country getCountry() {
        final Country country = new Country("US", "United States", null, false);
        return country;
    }
}