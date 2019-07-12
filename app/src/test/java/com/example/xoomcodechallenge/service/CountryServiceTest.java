package com.example.xoomcodechallenge.service;

import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.db.CountryDao;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static com.google.gson.FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CountryServiceTest {
    @Mock
    private CountryDao countryDao;

    @Captor
    private ArgumentCaptor<Country> countryArgumentCaptor;

    private CountryService subject;

    @Before
    public void setUp() {
        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(LOWER_CASE_WITH_UNDERSCORES)
                .create();

        subject = new CountryService(countryDao, gson);
    }

    @Test
    public void updateCountries_WithResultNull_LoadDataBase() {
        subject.updateCountries(null);

        verify(countryDao).getAll();
        verifyNoMoreInteractions(countryDao);
    }

    @Test
    public void updateCountries_WithResultEmpty_LoadDataBase() {
        subject.updateCountries(new JSONObject[0]);

        verify(countryDao).getAll();
        verifyNoMoreInteractions(countryDao);
    }

    @Test
    public void updateCountries_WithResult_InsertCountriesAndLoadDataBase() throws JSONException {
        final JSONObject[] jsonObjects = new JSONObject[1];
        jsonObjects[0] = getJSONObject();

        when(countryDao.getCountryBySlug(any(String.class))).thenReturn(null);

        subject.updateCountries(jsonObjects);

        verify(countryDao, times(2)).insert(countryArgumentCaptor.capture());

        final Country countryInserted = countryArgumentCaptor.getAllValues().get(0);

        assertThat(countryInserted.getName(), is("Australia"));
        assertThat(countryInserted.getSlug(), is("AU"));
        assertThat(countryInserted.isFavorite(), is(false));
        assertThat(countryInserted.getFlagPath(), is(nullValue()));

        verify(countryDao).getAll();
        verify(countryDao, never()).delete(any(Country.class));
    }

    @Test
    public void updateCountries_WithResultAndCountryRegisterWithOutDisbursementOptions_DeleteCountriesAndLoadDataBase() throws JSONException {
        final JSONObject[] jsonObjects = new JSONObject[1];
        jsonObjects[0] = getJSONObject();

        final Country country = getCountry();

        when(countryDao.getCountryBySlug(any(String.class))).thenReturn(country);

        subject.updateCountries(jsonObjects);

        verify(countryDao, times(1)).delete(countryArgumentCaptor.capture());

        final Country countryDeleted = countryArgumentCaptor.getAllValues().get(0);

        assertThat(countryDeleted.getName(), is("United State"));
        assertThat(countryDeleted.getSlug(), is("US"));
        assertThat(countryDeleted.isFavorite(), is(false));
        assertThat(countryDeleted.getFlagPath(), is(nullValue()));

        verify(countryDao).getAll();
        verify(countryDao, never()).insert(any(Country.class));
    }

    @Test
    public void updateFavorite_WithCountry_ChangeFavoriteValue(){
        final Country country = getCountry();

        when(countryDao.getCountryBySlug(any(String.class))).thenReturn(country);

        subject.updateFavorite("US");

        verify(countryDao).update(countryArgumentCaptor.capture());

        final Country countryUpdated = countryArgumentCaptor.getAllValues().get(0);

        assertThat(countryUpdated.isFavorite(), is(true));
    }

    private JSONObject getJSONObject() throws JSONException {
        JSONObject jsonObject = new JSONObject("{\"total_items\":232,\"total_pages\":5,\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/countries?page=1&page_size=50\"},{\"rel\":\"first\",\"href\":\"\\/v2\\/countries?page=1&page_size=50\"},{\"rel\":\"next\",\"href\":\"\\/v2\\/countries?page=2&page_size=50\"},{\"rel\":\"last\",\"href\":\"\\/v2\\/countries?page=5&page_size=50\"}],\"items\":[{\"code\":\"AN\",\"name\":\"Netherlands Antilles\",\"residence\":false,\"phone_prefix\":\"+599\",\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/countries\\/AN\"}],\"min\":{\"currency_code\":\"USD\"},\"max\":{\"currency_code\":\"USD\"}},{\"code\":\"AU\",\"name\":\"Australia\",\"residence\":false,\"phone_prefix\":\"+61\",\"features\":[\"REQUEST_MONEY\"],\"disbursement_options\":[{\"id\":\"AU_AUD_DEPOSIT\",\"mode\":\"ACTIVE\",\"country\":{\"code\":\"AU\",\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/countries\\/AU\"}]},\"currency\":{\"code\":\"AUD\",\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/currencies\\/AUD\"}]},\"disbursement_type\":\"DEPOSIT\",\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/disbursement-options\\/AU_AUD_DEPOSIT\"}]},{\"id\":\"AU_AUD_PICKUP\",\"mode\":\"ACTIVE\",\"country\":{\"code\":\"AU\",\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/countries\\/AU\"}]},\"currency\":{\"code\":\"AUD\",\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/currencies\\/AUD\"}]},\"disbursement_type\":\"PICKUP\",\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/disbursement-options\\/AU_AUD_PICKUP\"}]}],\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/countries\\/AU\"}],\"min\":{\"currency_code\":\"USD\"},\"max\":{\"currency_code\":\"USD\"}},{\"code\":\"CZ\",\"name\":\"Czech Republic\",\"residence\":false,\"phone_prefix\":\"+420\",\"features\":[\"REQUEST_MONEY\"],\"disbursement_options\":[{\"id\":\"CZ_CZK_DEPOSIT\",\"mode\":\"ACTIVE\",\"country\":{\"code\":\"CZ\",\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/countries\\/CZ\"}]},\"currency\":{\"code\":\"CZK\",\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/currencies\\/CZK\"}]},\"disbursement_type\":\"DEPOSIT\",\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/disbursement-options\\/CZ_CZK_DEPOSIT\"}]}],\"links\":[{\"rel\":\"self\",\"href\":\"\\/v2\\/countries\\/CZ\"}],\"min\":{\"currency_code\":\"USD\"},\"max\":{\"currency_code\":\"USD\"}}]}");
        return jsonObject;
    }

    private Country getCountry() {
        final Country country = new Country("US", "United State", null, false);
        return country;
    }
}