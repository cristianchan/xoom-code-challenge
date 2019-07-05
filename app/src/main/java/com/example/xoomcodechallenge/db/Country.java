package com.example.xoomcodechallenge.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import org.jetbrains.annotations.NotNull;

@Entity
public class Country {
    @PrimaryKey
    @NotNull
    private String slug;

    private String name;

    private String flagPath;

    private Boolean favorite;

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }

    public Country(String slug, String name, String flagPath, Boolean favorite) {
        this.slug = slug;
        this.name = name;
        this.flagPath = flagPath;
        this.favorite = favorite;
    }

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getFlagPath() {
        return flagPath;
    }

    public Boolean isFavorite() {
        return favorite;
    }

}
