package com.example.xoomcodechallenge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.xoomcodechallenge.R;
import com.example.xoomcodechallenge.async.TaskFactory;
import com.example.xoomcodechallenge.db.Country;
import com.example.xoomcodechallenge.service.CountryComparator;
import com.example.xoomcodechallenge.service.CountryService;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Singleton
public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {
    private static String FLAG_URL = "https://www.countryflags.io/%s/flat/64.png";

    private final List<Country> countries;
    private final CountryComparator countryComparator;
    private final Picasso picasso;
    private final CountryService countryService;

    @Inject
    public CountriesAdapter(final CountryComparator countryComparator,
                            final Picasso picasso,
                            final CountryService countryService) {
        this.countryService = countryService;
        this.countries = new ArrayList<>();
        this.countryComparator = countryComparator;
        this.picasso = picasso;
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, null);
        return new CountriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CountriesViewHolder holder, int position) {
        final Country country = countries.get(position);

        if (country.isFavorite()) {
            holder.favBtn.setImageResource(R.drawable.star);
        } else {
            holder.favBtn.setImageResource(R.drawable.star_outline);
        }

        picasso.load(String.format(FLAG_URL, country.getSlug().toLowerCase())).into(holder.flag);

        holder.nameTextView.setText(country.getName());
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TaskFactory.getUpdateCountryFavoriteAsyncTask(countryService).execute(country.getSlug());
                updateFavorites(country);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.countries != null ? countries.size() : 0;
    }

    public void setItems(final List<Country> newCountries) {
        countries.clear();
        countries.addAll(newCountries);
    }

    public static class CountriesViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private ImageView favBtn;
        private ImageView flag;

        public CountriesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTextView = itemView.findViewById(R.id.nameTextView);
            this.favBtn = itemView.findViewById(R.id.favBtn);
            this.flag = itemView.findViewById(R.id.flag);
        }

        public TextView getNameTextView() {
            return nameTextView;
        }

        public ImageView getFavBtn() {
            return favBtn;
        }
    }

    private void updateFavorites(final Country country) {
        country.setFavorite(!country.isFavorite());
        Collections.sort(countries, countryComparator);
        notifyDataSetChanged();
    }
}
