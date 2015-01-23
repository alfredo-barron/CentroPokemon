package com.alfredobarron.proyectofinal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alfredobarron.proyectofinal.R;
import com.alfredobarron.proyectofinal.models.Power;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.view.LayoutInflater.from;

public class PowerAdapter extends BaseAdapter {

    private final Context context;
    private List<Power> powers;

    public PowerAdapter(Context context, List<Power> powers) {
        this.context = context;
        this.powers = powers;
    }

    @Override
    public int getCount() {
        return powers.size();
    }

    @Override
    public Power getItem(int position) {
        return powers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Power power = getItem(position);
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(context).inflate(R.layout.power_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.pokemonPower.setText(power.getName());

        return convertView;
    }

    class ViewHolder {

        @InjectView(R.id.pokemon_power)
        TextView pokemonPower;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}