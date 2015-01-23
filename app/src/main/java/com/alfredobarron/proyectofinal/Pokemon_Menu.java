package com.alfredobarron.proyectofinal;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alfredobarron.proyectofinal.adapter.PokeballAdapter;
import com.alfredobarron.proyectofinal.adapter.PokedexAdapter;
import com.alfredobarron.proyectofinal.dialogs.AlertInformation;
import com.alfredobarron.proyectofinal.models.*;
import com.alfredobarron.proyectofinal.models.Register;
import com.alfredobarron.proyectofinal.resources.SessionManager;
import com.alfredobarron.proyectofinal.rest.RestClient;
import com.alfredobarron.proyectofinal.util.AlertInformationUtil;

import java.util.ArrayList;
import java.util.List;

import quickutils.core.QuickUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Pokemon_Menu extends Fragment {

    private final String TAG = Pokemon_Menu.class.getSimpleName();
    View rootView;
    ListView pokemonList;
    Button buttonAtrapar;
    SessionManager sessionManager;
    ProgressBar progressBar;
    TextView intenta;
    int id, id_trainer, id_pokeball;
    private List<Pokeball> streamData = new ArrayList<Pokeball>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_pokemon, container, false);
        buttonAtrapar = (Button) rootView.findViewById(R.id.btn_atrapar);
        pokemonList = (ListView) rootView.findViewById(R.id.pokemonlist);
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
        buttonAtrapar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new Pokedex_Menu())
                        .commit();
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getActivity());
        QuickUtils.init(getActivity());

    }

    public void verificar() {
        if (QuickUtils.web.hasInternetConnection()) {
            intenta.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            id = sessionManager.obtenerId();
            cargarPokemon(id);
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

    public void cargarPokemon(int id) {
        RestClient.restAdapter().pokeballsList(id, new Callback<List<Pokeball>>() {
            @Override
            public void success(List<Pokeball> pokeballs, Response response) {
                consumeApiData(pokeballs);
            }

            @Override
            public void failure(RetrofitError error) {
                consumeApiData(null);
               /* progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error al cargar los datos", Toast.LENGTH_SHORT).show(); */
            }
        });
    }

    public void consumeApiData(List<Pokeball> pokeballs) {
        if(!pokeballs.isEmpty()) {

            streamData.addAll(pokeballs);
            pokemonList.setAdapter(new PokeballAdapter(getActivity(), streamData));

            progressBar.setVisibility(View.GONE);
            pokemonList.setAdapter(new PokeballAdapter(getActivity(), pokeballs));

            pokemonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final AlertInformation.Callback questionCallback;
                    final Pokeball item = (Pokeball) parent.getItemAtPosition(position);
                    questionCallback = new AlertInformation.Callback() {
                        @Override
                        public void onPositiveButtonClicked() {
                            //startActivity(new Intent(getActivity(), Pokedex.class));
                            id_trainer = sessionManager.obtenerId();
                            id_pokeball = item.getId();
                            if(QuickUtils.web.hasInternetConnection()) {
                                RestClient.restAdapter().getRegenerador(id_pokeball,id_trainer, new Callback<Register>() {
                                    @Override
                                    public void success(Register register, Response response) {
                                        Toast.makeText(getActivity(), "Pokemon enviado al regenerador", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getActivity(), Main.class));
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.e(TAG, error.getMessage());
                                        Toast.makeText(getActivity(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "Verifique su conexión", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancel() {

                        }
                    };

                    AlertInformationUtil.showDialog(
                            getFragmentManager(),
                            TAG,
                            questionCallback,
                            getString(R.string.name_pokemon).replace("{name}", item.getSpecie()),
                            getString(R.string.alias_pokemon).replace("{alias}","Alias: "+item.getAlias()).replace("{genero}","Genero: "+item.getGender())
                            .replace("{nivel}", "Nivel: "+item.getLevel()).replace("{estatus}","Estatus: "+item.getStatus()).replace("{ht}","HT: "+item.getHitPoints()),
                            getString(R.string.enviar),
                            getString(R.string.cerrar));
                }
            });

            pokemonList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final AlertInformation.Callback questionCallback;
                    final Pokeball item = (Pokeball) parent.getItemAtPosition(position);
                    questionCallback = new AlertInformation.Callback() {
                        @Override
                        public void onPositiveButtonClicked() {
                            //startActivity(new Intent(getActivity(), Pokedex.class));
                            id_trainer = sessionManager.obtenerId();
                            id_pokeball = item.getId();
                            if (QuickUtils.web.hasInternetConnection()) {
                                RestClient.restAdapter().getRegenerador(id_pokeball, id_trainer, new Callback<Register>() {
                                    @Override
                                    public void success(Register register, Response response) {
                                        Toast.makeText(getActivity(), "Pokemon enviado al regenerador", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getActivity(), Main.class));
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.e(TAG, error.getMessage());
                                        Toast.makeText(getActivity(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "Verifique su conexión", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancel() {

                        }
                    };

                    AlertInformationUtil.showDialog(
                            getFragmentManager(),
                            TAG,
                            questionCallback,
                            getString(R.string.name_pokemon).replace("{name}", item.getSpecie()),
                            getString(R.string.alias_pokemon).replace("{alias}", "Alias: " + item.getAlias()).replace("{genero}", "Genero: " + item.getGender())
                                    .replace("{nivel}", "Nivel: " + item.getLevel()).replace("{estatus}", "Estatus: " + item.getStatus()).replace("{ht}", "HT: " + item.getHitPoints()),
                            getString(R.string.enviar),
                            getString(R.string.cerrar));
                }
            });


            pokemonList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                    final AlertInformation.Callback questionCallback;
                    final Pokeball item = (Pokeball) parent.getItemAtPosition(position);
                    questionCallback = new AlertInformation.Callback() {
                        @Override
                        public void onPositiveButtonClicked() {
                            id_pokeball = item.getId();
                            if (QuickUtils.web.hasInternetConnection()) {
                                RestClient.restAdapter().getSoltar(id_pokeball, new Callback<Trainer>() {
                                    @Override
                                    public void success(Trainer trainer, Response response) {
                                        Toast.makeText(getActivity(), "Has dejado libre a tu pokemon", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(getActivity(), Main.class));
                                    }

                                    @Override
                                    public void failure(RetrofitError error) {
                                        Log.e(TAG, error.getMessage());
                                        Toast.makeText(getActivity(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                Toast.makeText(getActivity(), "Verifique su conexión", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancel() {

                        }
                    };

                    AlertInformationUtil.showDialog(
                            getFragmentManager(),
                            TAG,
                            questionCallback,
                            getString(R.string.name_pokemon).replace("{name}", item.getSpecie()),
                            getString(R.string.soltar).replace("{name}",item.getAlias()),
                            getString(R.string.atrapar),
                            getString(R.string.aun_no));

                    return true;
                }

            });


        } else {
            buttonAtrapar.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

}