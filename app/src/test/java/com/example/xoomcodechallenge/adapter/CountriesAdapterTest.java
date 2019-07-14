package com.example.xoomcodechallenge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ApplicationProvider;
import com.example.xoomcodechallenge.MainActivity;
import com.example.xoomcodechallenge.R;
import com.example.xoomcodechallenge.async.UpdateCountryFavoriteAsyncTask;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.service.CountryService;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class CountriesAdapterTest {
    private static String FLAG_URL = "https://www.countryflags.io/%s/flat/64.png";

    private CountriesAdapter subject;

    private CountriesAdapter.CountriesViewHolder holder;
    private Context mainView;

    @Mock
    private MainActivity.CountryListener countryListener;

    @Mock
    private CountryService countryService;

    @Mock
    private Picasso picasso;

    @Mock
    private RequestCreator requestCreator;

    @Mock
    private UpdateCountryFavoriteAsyncTask updateCountryFavoriteAsyncTask;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mainView = new MainActivity();
        subject = new CountriesAdapter(new ArrayList<Country>(), mainView, countryListener, countryService, picasso);
    }

    @Test
    public void onCreateViewHolder_WithNewMainView_ViewHolder(){
        final CountriesAdapter.CountriesViewHolder countriesViewHolder = subject.onCreateViewHolder(new RecyclerView(ApplicationProvider.getApplicationContext()),0);
        assertThat(countriesViewHolder, is(notNullValue()));
    }

    @Test
    public void getItemCount_WithCountries_ItemCount() {
        subject.setItems(asList(getCountry(), getCountry(), getCountry()));
        assertThat(subject.getItemCount() , is(3));
    }

    @Test
    public void onBindViewHolder_setsTextAndClickEventForCandyView() {
        final String url  = String.format(FLAG_URL, "us");

        subject.setItems(asList(getCountry()));

        LayoutInflater inflater = (LayoutInflater)  ApplicationProvider.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View listItemView = inflater.inflate(R.layout.country_item, null, false);
        holder = new CountriesAdapter.CountriesViewHolder(listItemView);

        when(picasso.load(url)).thenReturn(requestCreator);

        subject.onBindViewHolder(holder, 0);

        assertThat(holder.getNameTextView().getText().toString() , is("United States"));

        holder.getFavBtn().performClick();

        //verify(UpdateCountryFavoriteAsyncTask.class).execute("US");
    }

    private Country getCountry(){
        final Country country = new Country("US", "United States", null , false);
        return country;
    }

}
