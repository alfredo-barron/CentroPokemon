package com.alfredobarron.proyectofinal;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alfredobarron.proyectofinal.adapter.PokedexAdapter;
import com.alfredobarron.proyectofinal.models.Pokemon;
import com.alfredobarron.proyectofinal.rest.RestClient;

import java.util.List;

import quickutils.core.QuickUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import butterknife.InjectView;

public class Pokedex_Menu extends Fragment {


    ListView pokedexList;
    ProgressBar progressBar;
    TextView intenta;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_pokedex, container, false);
       pokedexList = (ListView) rootView.findViewById(R.id.pokedexlist);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        intenta = (TextView) rootView.findViewById(R.id.intenta);

       verificar();

       return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QuickUtils.init(getActivity());

    }

    public void verificar() {
        if (QuickUtils.web.hasInternetConnection()) {
            intenta.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            cargarPokedex();
        } else {
            progressBar.setVisibility(View.GONE);
            intenta.setVisibility(View.VISIBLE);
            intenta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    verificar();
                }
            });
            Toast.makeText(getActivity(), "Verifique su conexión", Toast.LENGTH_SHORT).show();
        }
    }


    public void cargarPokedex() {
        RestClient.restAdapter().pokeList(new Callback<List<Pokemon>>() {
            @Override
            public void success(List<Pokemon> pokemons, Response response) {
                progressBar.setVisibility(View.GONE);
                pokedexList.setAdapter(new PokedexAdapter(getActivity(), pokemons));

                pokedexList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        final Pokemon item = (Pokemon) parent.getItemAtPosition(position);
                        Intent intent = new Intent(getActivity(), Pokedex.class);
                        int pokemon_id = item.getId();
                        intent.putExtra("id",pokemon_id);
                        intent.putExtra("specie",item.getSpecies());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
