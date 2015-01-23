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
import com.alfredobarron.proyectofinal.models.Register;

import junit.framework.Assert;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static android.view.LayoutInflater.from;

public class RegeneratorAdapter extends BaseAdapter{

    private final Context context;
    private List<Register> registers;

    public RegeneratorAdapter(Context context, List<Register> registers) {
        this.context = context;
        this.registers = registers;
    }

    @Override
    public int getCount() {
        return registers.size();
    }

    @Override
    public Register getItem(int position) {
        return registers.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Register register = getItem(position);
        ViewHolder viewHolder;
        if (convertView != null) {
            viewHolder = (ViewHolder) convertView.getTag();
        } else {
            convertView = from(context).inflate(R.layout.regenerator_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }

        viewHolder.pokemonName.setText(register.getSpecie());

        return convertView;
    }

    class ViewHolder {

        @InjectView(R.id.pokemon_name)
        TextView pokemonName;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
