package com.alfredobarron.proyectofinal.adapter;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.alfredobarron.proyectofinal.R;
import com.alfredobarron.proyectofinal.models.Pokemon;

import junit.framework.Assert;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.view.LayoutInflater.from;

public class PokedexAdapter extends BaseAdapter{

    private final Context context;
    private List<Pokemon> pokemons;

    public PokedexAdapter(Context context, List<Pokemon> pokemons) {
        this.context = context;
        this.pokemons = pokemons;
    }

    @Override
    public int getCount() {
        return pokemons.size();
    }

    @Override
    public Pokemon getItem(int position) {
        return pokemons.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Pokemon pokemon = getItem(position);
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(context).inflate(R.layout.pokedex_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.pokemonName.setText(pokemon.getSpecies());

        String name = pokemon.getImage();
        int drawableId = getDrawable(context, name);
        if (drawableId != 0) {
            viewHolder.pokemonImage.setImageResource(drawableId);
        } else {
            viewHolder.pokemonImage.setImageResource(R.drawable.pokeball);
        }

        String number = Integer.toString(pokemon.getId());
        while (number.length() != 3) {
            number = "0" + number;
        }
        String source = "<font color=\"#969696\">" + "#"+number + "</font> " + pokemon.getSpecies();
        viewHolder.pokemonName.setText(Html.fromHtml(source));

        return convertView;
    }

    public int getDrawable(Context context, String name) {
        Assert.assertNotNull(context);
        Assert.assertNotNull(name);

        return context.getResources().getIdentifier(name,
                "drawable", context.getPackageName());
    }

    class ViewHolder {

        @InjectView(R.id.pokemon_icon)
        ImageView pokemonImage;

        @InjectView(R.id.pokemon_name)
        TextView pokemonName;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
