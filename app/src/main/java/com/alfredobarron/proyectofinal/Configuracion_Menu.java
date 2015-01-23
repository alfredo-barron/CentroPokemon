package com.alfredobarron.proyectofinal;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alfredobarron.proyectofinal.models.Trainer;
import com.alfredobarron.proyectofinal.resources.SessionManager;
import com.alfredobarron.proyectofinal.rest.RestClient;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import quickutils.core.QuickUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Configuracion_Menu extends Fragment {

    private static final String TAG = Configuracion_Menu.class.getSimpleName();
    ProgressBar progressBar;
    TextView intenta;
    SessionManager sessionManager;
    EditText user, name, password, last_name;
    Button edit;
    int id;
    String nombre, usuario, contra, apellido;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_configuracion, container, false);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        intenta = (TextView) rootView.findViewById(R.id.intenta);

        user = (EditText) rootView.findViewById(R.id.usuario_edit);
        password = (EditText) rootView.findViewById(R.id.password_edit);
        name = (EditText) rootView.findViewById(R.id.nombre_edit);
        last_name = (EditText) rootView.findViewById(R.id.apellidos_edit);
        edit = (Button) rootView.findViewById(R.id.edit_button);

        return rootView;
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombre = name.getText().toString();
                apellido = last_name.getText().toString();
                usuario = user.getText().toString();
                contra = password.getText().toString();
                hecho(nombre, apellido, usuario, contra);
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(getActivity());
        QuickUtils.init(getActivity());

        if (QuickUtils.web.hasInternetConnection()) {
            id = sessionManager.obtenerId();
            cargarPerfil(id);
        } else {
            Toast.makeText(getActivity(), "Verifique su conexión", Toast.LENGTH_SHORT).show();
        }

    }


    public void cargarPerfil(int id) {
        if (QuickUtils.web.hasInternetConnection()) {
            RestClient.restAdapter().getEntrenadorVista(id, new Callback<Trainer>() {
                @Override
                public void success(Trainer trainer, Response response) {
                    user.setText(trainer.getUsername());
                    password.setText(trainer.getPassword());
                    name.setText(trainer.getName());
                    last_name.setText(trainer.getLastName());
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, error.getMessage());
                    Toast.makeText(getActivity(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(getActivity(), "Verifique su conexión", Toast.LENGTH_SHORT).show();
        }
    }

    public void hecho(String name, String last_name, String user, String password) {
        if (QuickUtils.web.hasInternetConnection()) {
            RestClient.restAdapter().updateEntrenador(id, name, last_name, user, password, new Callback<Trainer>() {
                @Override
                public void success(Trainer trainer, Response response) {
                    Toast.makeText(getActivity(), "Datos actualizados", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.e(TAG, error.getMessage());
                    Toast.makeText(getActivity(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(getActivity(), "Verifique su conexión", Toast.LENGTH_SHORT).show();
        }
    }
}