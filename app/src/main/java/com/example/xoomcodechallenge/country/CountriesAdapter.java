package com.example.xoomcodechallenge.country;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.xoomcodechallenge.R;
import com.example.xoomcodechallenge.db.Country;

import java.util.List;

public class CountriesAdapter extends RecyclerView.Adapter<CountriesAdapter.CountriesViewHolder> {
    private List<Country> countries;

    public CountriesAdapter(final List<Country> countries) {
        this.countries = countries;
    }

    @NonNull
    @Override
    public CountriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_item, null);
        final CountriesViewHolder countriesViewHolder = new CountriesViewHolder(view);
        return countriesViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountriesViewHolder holder, int position) {
        final Country country = countries.get(position);
        holder.nameTextView.setText(country.getName());
    }

    @Override
    public int getItemCount() {
        return this.countries != null ? countries.size() : 0;
    }

    public void setItems(final List<Country> newCountries){
        countries.clear();
        countries.addAll(newCountries);
    }

    public class CountriesViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView;

        public CountriesViewHolder(@NonNull View itemView) {
            super(itemView);
            this.nameTextView = itemView.findViewById(R.id.nameTextView);
        }
    }
}
