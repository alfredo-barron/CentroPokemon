package com.alfredobarron.proyectofinal;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alfredobarron.proyectofinal.adapter.NoticesAdapter;
import com.alfredobarron.proyectofinal.dialogs.AlertInformation;
import com.alfredobarron.proyectofinal.models.*;
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

import static android.R.id.*;
import static android.R.id.empty;


public class Inicio_Menu extends Fragment {

    private final String TAG = Inicio_Menu.class.getSimpleName();
    private View rootView;
    ListView noticesList;
    ProgressBar progressBar;
    TextView intenta, noticia;
    SessionManager sessionManager;
    int id;
    private List<Notice> streamData = new ArrayList<Notice>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_inicio, container, false);
        noticesList = (ListView) rootView.findViewById(R.id.noticesList);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        intenta = (TextView) rootView.findViewById(R.id.intenta);
        noticia = (TextView) rootView.findViewById(R.id.empty);

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
            id = sessionManager.obtenerId();
            cargarNotices(id);
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

    public void cargarNotices(int id) {
        RestClient.restAdapter().getNotices(id, new Callback<List<Notice>>() {
            @Override
            public void success(List<Notice> notices, Response response) {
                consumeApiData(notices);
            }

            @Override
            public void failure(RetrofitError error) {
                consumeApiData(null);
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Error al cargar los datos", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void consumeApiData(List<Notice> notices) {
        if(notices != null) {
            streamData.addAll(notices);
            noticesList.setAdapter(new NoticesAdapter(getActivity(), streamData));
            progressBar.setVisibility(View.GONE);

            noticesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final AlertInformation.Callback questionCallback;
                    final Notice item = (Notice) parent.getItemAtPosition(position);
                    questionCallback = new AlertInformation.Callback() {
                        @Override
                        public void onPositiveButtonClicked() {
                            //startActivity(new Intent(getActivity(), Pokedex.class));
                            int id_pokeball = item.getId();
                            if (QuickUtils.web.hasInternetConnection()) {
                                RestClient.restAdapter().getBatalla(id_pokeball, new Callback<Pokeball>() {
                                    @Override
                                    public void success(Pokeball pokeball, Response response) {
                                        Toast.makeText(getActivity(), "Batalla terminada" , Toast.LENGTH_LONG).show();
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
                            getString(R.string.batalla),
                            getString(R.string.aceptar),
                            getString(R.string.aun_no));
                }
            });
        }else {
            noticia.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }
}
