package com.alfredobarron.proyectofinal;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alfredobarron.proyectofinal.adapter.RegeneratorAdapter;
import com.alfredobarron.proyectofinal.models.*;
import com.alfredobarron.proyectofinal.resources.SessionManager;
import com.alfredobarron.proyectofinal.rest.RestClient;

import java.util.List;

import butterknife.ButterKnife;
import quickutils.core.QuickUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Descanso_Menu extends Fragment {

    ProgressBar progressBar;
    TextView intenta, vacio, lleno;
    SessionManager sessionManager;
    int id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_descanso, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        intenta = (TextView) rootView.findViewById(R.id.intenta);
        vacio = (TextView) rootView.findViewById(R.id.empty);
        lleno = (TextView) rootView.findViewById(R.id.lleno);

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


    public void consumeApiData(List<com.alfredobarron.proyectofinal.models.Register> registers) {
        if (!registers.isEmpty()) {
            lleno.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);

        } else {
            vacio.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
    }

}