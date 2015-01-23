package com.alfredobarron.proyectofinal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alfredobarron.proyectofinal.R;
import com.alfredobarron.proyectofinal.models.Type;


import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.view.LayoutInflater.from;

public class TipoAdapter extends BaseAdapter {

    private final Context context;
    private List<Type> types;

    public TipoAdapter(Context context, List<Type> types) {
        this.context = context;
        this.types = types;
    }

    @Override
    public int getCount() {
        return types.size();
    }

    @Override
    public Type getItem(int position) {
        return types.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Type type = getItem(position);
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(context).inflate(R.layout.tipo_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        switch (type.getId()) {
            case 1: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#a8a878"));
                break;
            case 2: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#f08030"));
                break;
            case 3: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#c03028"));
                break;
            case 4: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#6890f0"));
                break;
            case 5: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#a890f0"));
                break;
            case 6: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#78c850"));
                break;
            case 7: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#a040a0"));
                break;
            case 8: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#f8d030"));
                break;
            case 9: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#e0c068"));
                break;
            case 10: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#f85888"));
                break;
            case 11: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#b8a038"));
                break;
            case 12: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#98d8d8"));
                break;
            case 13: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#a8b820"));
                break;
            case 14: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#7038f8"));
                break;
            case 15: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#705898"));
                break;
            case 16: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#705848"));
                break;
            case 17: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#b8b8d0"));
                break;
            case 18: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#ee99ac"));
                break;
            case 19: viewHolder.pokemonType.setBackgroundColor(Color.parseColor("#68a090"));
                break;
        }
        viewHolder.pokemonType.setText(type.getName());

        return convertView;
    }

    class ViewHolder {

        @InjectView(R.id.pokemon_type)
        TextView pokemonType;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
