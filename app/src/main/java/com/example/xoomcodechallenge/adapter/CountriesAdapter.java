package com.example.xoomcodechallenge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.xoomcodechallenge.R;
import com.example.xoomcodechallenge.async.UpdateCountryFavoriteAsyncTask;
import com.example.xoomcodechallenge.db.Country;
import com.squareup.picasso.Picasso;

import java.util.List;

import static com.example.xoomcodechallenge.MainActivity.CountryListener;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {
    private static String FLAG_URL = "https://www.countryflags.io/%s/flat/64.png";

    private final List<Country> countries;
    private final Context context;
    private final CountryListener countryListener;

    public CountriesAdapter(final List<Country> countries,
                            final Context applicationContext,
                            final CountryListener countryListener) {
        this.context = applicationContext;
        this.countries = countries;
        this.countryListener = countryListener;
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, null);
        final CountriesViewHolder countriesViewHolder = new CountriesViewHolder(view);
        return countriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CountriesViewHolder holder, int position) {
        final Country country = countries.get(position);

        if (country.isFavorite()) {
            holder.favBtn.setImageResource(R.drawable.star);
        } else {
            holder.favBtn.setImageResource(R.drawable.star_outline);
        }

        Picasso.with(context).load(String.format(FLAG_URL, country.getSlug().toLowerCase())).into(holder.flag);

        holder.nameTextView.setText(country.getName());
        holder.favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final UpdateCountryFavoriteAsyncTask updateCountryFavoriteAsyncTask = new UpdateCountryFavoriteAsyncTask(context, countryListener, country.getSlug());
                updateCountryFavoriteAsyncTask.execute();
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

    public class CountriesViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;
        private ImageView favBtn;
        private ImageView flag;

        public CountriesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTextView = itemView.findViewById(R.id.nameTextView);
            this.favBtn = itemView.findViewById(R.id.favBtn);
            this.flag = itemView.findViewById(R.id.flag);
        }
    }
}
