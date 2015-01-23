package com.alfredobarron.proyectofinal;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alfredobarron.proyectofinal.models.Pokemon;
import com.alfredobarron.proyectofinal.resources.SessionManager;
import com.alfredobarron.proyectofinal.rest.RestClient;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import quickutils.core.QuickUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class InformacionFragment extends Fragment {

    ProgressBar progressBar, pbHT, pbAT, pbDF, pbSP, pbEV, pbAC;
    TextView intenta, hits_points, attack, defense, speed, evasion, accuracy, total;
    ImageView image;
    SessionManager sessionManager;
    Pokedex pokedex;
    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_informacion, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        intenta = (TextView) rootView.findViewById(R.id.intenta);

        image = (ImageView) rootView.findViewById(R.id.icon_pokemon);

        hits_points = (TextView) rootView.findViewById(R.id.hits_points);
        pbHT = (ProgressBar) rootView.findViewById(R.id.progressBarHT);

        attack = (TextView) rootView.findViewById(R.id.attack);
        pbAT = (ProgressBar) rootView.findViewById(R.id.progressBarAT);

        defense = (TextView) rootView.findViewById(R.id.defense);
        pbDF = (ProgressBar) rootView.findViewById(R.id.progressBarDF);

        speed = (TextView) rootView.findViewById(R.id.speed);
        pbSP = (ProgressBar) rootView.findViewById(R.id.progressBarSP);

        evasion = (TextView) rootView.findViewById(R.id.evasion);
        pbEV = (ProgressBar) rootView.findViewById(R.id.progressBarEV);

        accuracy = (TextView) rootView.findViewById(R.id.accuracy);
        pbAC = (ProgressBar) rootView.findViewById(R.id.progressBarAC);

        total = (TextView) rootView.findViewById(R.id.total);

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
        pokedex = new Pokedex();

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
                Picasso.with(getActivity()).load(pokemon.getUrl()).into(image);
                String HT = "HP: <b>"+pokemon.getHitPoints()+"</b>";
                hits_points.setText(Html.fromHtml(HT));
                pbHT.setProgress(pokemon.getHitPoints());
                pbHT.setVisibility(View.VISIBLE);
                String AT = "Ataque: <b>" + pokemon.getAttack()+"</b>";
                attack.setText(Html.fromHtml(AT));
                pbAT.setProgress(pokemon.getAttack());
                pbAT.setVisibility(View.VISIBLE);
                String DF = "Defensa: <b>" + pokemon.getDefense()+"</b>";
                defense.setText(Html.fromHtml(DF));
                pbDF.setProgress(pokemon.getDefense());
                pbDF.setVisibility(View.VISIBLE);
                String SP = "Velocidad: <b>" + pokemon.getSpeed()+"</b>";
                speed.setText(Html.fromHtml(SP));
                pbSP.setProgress(pokemon.getSpeed());
                pbSP.setVisibility(View.VISIBLE);
                String EV = "Evasión: <b>" + pokemon.getEvasion()+"</b>";
                evasion.setText(Html.fromHtml(EV));
                pbEV.setProgress(pokemon.getEvasion());
                pbEV.setVisibility(View.VISIBLE);
                String AC = "Precisión: <b>" + pokemon.getAttack()+"</b>";
                accuracy.setText(Html.fromHtml(AC));
                pbAC.setProgress(pokemon.getAccuracy());
                pbAC.setVisibility(View.VISIBLE);
                int tot = pokemon.getHitPoints()+pokemon.getAttack()+pokemon.getDefense()+pokemon.getSpeed()
                        +pokemon.getEvasion()+pokemon.getAccuracy();
                String TO = "Total: <b>"+tot+"</b>";
                total.setText(Html.fromHtml(TO));
            }

            @Override
            public void failure(RetrofitError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

