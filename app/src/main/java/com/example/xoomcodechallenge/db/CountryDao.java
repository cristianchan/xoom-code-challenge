package com.example.xoomcodechallenge.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Optional;

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

}
