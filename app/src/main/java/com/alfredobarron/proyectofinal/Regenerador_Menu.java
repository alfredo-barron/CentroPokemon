package com.alfredobarron.proyectofinal;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alfredobarron.proyectofinal.adapter.RegeneratorAdapter;
import com.alfredobarron.proyectofinal.dialogs.AlertInformation;
import com.alfredobarron.proyectofinal.models.Pokeball;
import com.alfredobarron.proyectofinal.models.Register;
import com.alfredobarron.proyectofinal.resources.SessionManager;
import com.alfredobarron.proyectofinal.rest.RestClient;
import com.alfredobarron.proyectofinal.util.AlertInformationUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import quickutils.core.QuickUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Regenerador_Menu extends Fragment {

    private static final String TAG = Regenerador_Menu.class.getSimpleName();
    ProgressBar progressBar;
    TextView intenta, vacio;
    SessionManager sessionManager;
    ListView regeneratorList;
    int id;
    private List<Register> streamData = new ArrayList<Register>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_regenerador, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        intenta = (TextView) rootView.findViewById(R.id.intenta);
        vacio = (TextView) rootView.findViewById(R.id.empty);
        regeneratorList = (ListView) rootView.findViewById(R.id.regeneratorlist);

        verificar();

        return rootView;
    }

    @Override
    public void onResume() {
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

        if (sessionManager.checkLogin()) ;

        QuickUtils.init(getActivity());

    }

    public void verificar() {
        if (QuickUtils.web.hasInternetConnection()) {
            intenta.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            id = sessionManager.obtenerId();
            cargarRegenerador(id);
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

    public void cargarRegenerador(int id) {
        RestClient.restAdapter().getRegistros(id, new Callback<List<com.alfredobarron.proyectofinal.models.Register>>() {
            @Override
            public void success(List<com.alfredobarron.proyectofinal.models.Register> registers, Response response) {
                consumeApiData(registers);
            }

            @Override
            public void failure(RetrofitError error) {
                consumeApiData(null);
            }
        });

    }


    public void consumeApiData(List<Register> registers) {
        if (!registers.isEmpty()) {

            streamData.addAll(registers);
            regeneratorList.setAdapter(new RegeneratorAdapter(getActivity(), streamData));

            progressBar.setVisibility(View.GONE);

            regeneratorList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final AlertInformation.Callback questionCallback;
                    final Register item = (Register) parent.getItemAtPosition(position);
                    questionCallback = new AlertInformation.Callback() {
                        @Override
                        public void onPositiveButtonClicked() {
                            //startActivity(new Intent(getActivity(), Pokedex.class));
                            int id_pokeball = item.getPokeballId();
                            if(QuickUtils.web.hasInternetConnection()) {
                                RestClient.restAdapter().getDeleteRe(id_pokeball, new Callback<Pokeball>() {
                                    @Override
                                    public void success(Pokeball pokeball, Response response) {
                                        Toast.makeText(getActivity(), "Pokemon regenerado", Toast.LENGTH_SHORT).show();
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
                            getString(R.string.normal),
                            getString(R.string.aceptar),
                            getString(R.string.aun_no));
                }
            });

        } else {
            vacio.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}