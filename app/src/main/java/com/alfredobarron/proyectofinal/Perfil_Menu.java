package com.alfredobarron.proyectofinal;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alfredobarron.proyectofinal.models.Region;
import com.alfredobarron.proyectofinal.models.Trainer;

import com.alfredobarron.proyectofinal.resources.SessionManager;
import com.alfredobarron.proyectofinal.rest.RestClient;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import quickutils.core.QuickUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Perfil_Menu extends Fragment {

    private static final String TAG = Perfil_Menu.class.getSimpleName();
    TextView name_trainer, birthday_trainer, edad_trainer, sexo_trainer, lider_trainer, region_trainer, region_actual_trainer;
    SessionManager sessionManager;
    ImageView imageProfile;
    ProgressBar progressBar;
    TextView intenta;
    int id;

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_perfil, container, false);
        name_trainer = (TextView) rootView.findViewById(R.id.name_perfil);
        imageProfile = (ImageView) rootView.findViewById(R.id.profile);
        birthday_trainer = (TextView) rootView.findViewById(R.id.birthday_perfil);
        edad_trainer = (TextView) rootView.findViewById(R.id.edad_perfil);
        sexo_trainer = (TextView) rootView.findViewById(R.id.sexo_perfil);
        lider_trainer = (TextView) rootView.findViewById(R.id.lider_perfil);
        region_trainer = (TextView) rootView.findViewById(R.id.region_perfil);
        region_actual_trainer = (TextView) rootView.findViewById(R.id.region_actual_perfil);

        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        intenta = (TextView) rootView.findViewById(R.id.intenta);

        verificar();

        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        QuickUtils.init(getActivity());
        sessionManager = new SessionManager(getActivity());

    }

    public void verificar() {
        if (QuickUtils.web.hasInternetConnection()) {
            intenta.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
            id = sessionManager.obtenerId();
            cargarPerfil(id);
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


    public void cargarPerfil(int id) {
        if (QuickUtils.web.hasInternetConnection()) {
            RestClient.restAdapter().getEntrenadorVista(id, new Callback<Trainer>() {
                @Override
                public void success(Trainer trainer, Response response) {
                    progressBar.setVisibility(View.GONE);
                    Picasso.with(getActivity()).load(trainer.getImage()).into(imageProfile);
                    name_trainer.setText("Nombre: " + trainer.getName() + " " + trainer.getLastName());
                    birthday_trainer.setText("Fecha de nacimiento: " + trainer.getBirthday());

                    edad_trainer.setText("Edad: " + calcularEdad(trainer.getBirthday()));
                    sexo_trainer.setText("Sexo: " + trainer.getGender());
                    String leader;
                    if (trainer.getLeader() == true) {
                        leader = "Si";
                    } else {
                        leader = "No";
                    }
                    lider_trainer.setText("Lider: " + leader);

                    region_trainer.setText("Lugar de origen: " + trainer.getRegionId().getName());
                    region_actual_trainer.setText("Lugar actual: " + trainer.getRegionIdActual().getName());
                }

                @Override
                public void failure(RetrofitError error) {
                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG, error.getMessage());
                    Toast.makeText(getActivity(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Verifique su conexión", Toast.LENGTH_SHORT).show();
        }
    }

    public static Integer calcularEdad(String fecha){
        Date fechaNac=null;
        try {
            /**Se puede cambiar la mascara por el formato de la fecha
             que se quiera recibir, por ejemplo año mes día "yyyy-MM-dd"
             en este caso es día mes año*/
            fechaNac = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);
        } catch (Exception ex) {
            System.out.println("Error:"+ex);
        }
        Calendar fechaNacimiento = Calendar.getInstance();
        //Se crea un objeto con la fecha actual
        Calendar fechaActual = Calendar.getInstance();
        //Se asigna la fecha recibida a la fecha de nacimiento.
        fechaNacimiento.setTime(fechaNac);
        //Se restan la fecha actual y la fecha de nacimiento
        int año = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
        int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
        //Se ajusta el año dependiendo el mes y el día
        if(mes<0 || (mes==0 && dia<0)){
            año--;
        }
        //Regresa la edad en base a la fecha de nacimiento
        return año;
    }

}

