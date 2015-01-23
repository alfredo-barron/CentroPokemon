package com.alfredobarron.proyectofinal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alfredobarron.proyectofinal.adapter.PowerAdapter;
import com.alfredobarron.proyectofinal.models.Pokemon;
import com.alfredobarron.proyectofinal.models.Power;
import com.alfredobarron.proyectofinal.resources.SessionManager;
import com.alfredobarron.proyectofinal.rest.RestClient;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import quickutils.core.QuickUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class HabilidadesFragment extends Fragment {

    ProgressBar progressBar;
    TextView intenta;
    ListView powerList;
    SessionManager sessionManager;
    int id;
   // private List<Power> streamData = new ArrayList<Power>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_habilidades, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        intenta = (TextView) rootView.findViewById(R.id.intenta);

        powerList = (ListView) rootView.findViewById(R.id.powerlist);

        if (getActivity().getIntent().getExtras() != null) {
            Bundle bundle = getActivity().getIntent().getExtras();
            id = bundle.getInt("id");
        }

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

        ButterKnife.inject(getActivity());
        sessionManager = new SessionManager(getActivity());

        if(sessionManager.checkLogin());

        QuickUtils.init(getActivity());

    }

    public void verificar() {
        if (QuickUtils.web.hasInternetConnection()) {
            intenta.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
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
        RestClient.restAdapter().getPokemon(id, new Callback<Pokemon>() {
            @Override
            public void success(Pokemon pokemon, Response response) {
                progressBar.setVisibility(View.GONE);
                powerList.setAdapter(new PowerAdapter(getActivity(), pokemon.getPowers()));
                //consumeApiData(pokemon.getPowers());
            }

            @Override
            public void failure(RetrofitError error) {
                //consumeApiData(null);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*public void consumeApiData(List<Power> powers) {
        if(!powers.isEmpty()) {
            streamData.addAll(powers);

            progressBar.setVisibility(View.GONE);
        }
    } */
}