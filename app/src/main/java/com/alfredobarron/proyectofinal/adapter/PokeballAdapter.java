package com.alfredobarron.proyectofinal.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfredobarron.proyectofinal.R;
import com.alfredobarron.proyectofinal.models.Pokeball;
import com.squareup.picasso.Picasso;

import junit.framework.Assert;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.view.LayoutInflater.from;

public class PokeballAdapter extends BaseAdapter {

    private final Context context;
    private List<Pokeball> pokeballs;

    public PokeballAdapter(Context context, List<Pokeball> pokeballs) {
        this.context = context;
        this.pokeballs = pokeballs;
    }

    @Override
    public int getCount() {
        return pokeballs.size();
    }

    @Override
    public Pokeball getItem(int position) {
        return pokeballs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Pokeball pokeball = getItem(position);
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(context).inflate(R.layout.pokemon_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.pokemonName.setText(pokeball.getSpecie());

        Picasso.with(context).load(pokeball.getUrl()).into(viewHolder.pokemonImage);

        viewHolder.pokemonName.setText(pokeball.getSpecie());
        switch (pokeball.getStatusId()) {
            case 1: viewHolder.pokemonStatus.setBackgroundColor(R.color.Blue);
                break;
            case 2: viewHolder.pokemonStatus.setBackgroundColor(R.color.Yellow);
                break;
            case 3: viewHolder.pokemonStatus.setBackgroundColor(R.color.Green);
                break;
            case 4: viewHolder.pokemonStatus.setBackgroundColor(R.color.Navy);
                break;
            case 5: viewHolder.pokemonStatus.setBackgroundColor(R.color.Red);
                break;
            case 6: viewHolder.pokemonStatus.setBackgroundColor(R.color.Orange);
                break;
            case 7: viewHolder.pokemonStatus.setBackgroundColor(R.color.Brown);
                break;
            case 8: viewHolder.pokemonStatus.setBackgroundColor(R.color.Pink);
                break;
        }
        viewHolder.pokemonStatus.setText(pokeball.getStatus());


        return convertView;
    }


    class ViewHolder {

        @InjectView(R.id.pokemon_icon)
        ImageView pokemonImage;

        @InjectView(R.id.pokemon_specie)
        TextView pokemonName;

        @InjectView(R.id.pokemon_status)
        TextView pokemonStatus;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
