package com.example.xoomcodechallenge.db;

import androidx.room.*;

import java.util.List;

@Dao
public interface CountryDao {
    @Query("SELECT * FROM country ORDER BY favorite desc, name")
    List<Country> getAll();

    @Query("SELECT * FROM country WHERE slug = :slug ")
    Country getCountryBySlug(String slug);

    @Insert
    long insert(Country country);

    @Update
    int update(Country country);

    @Delete
    int delete(Country country);

}
