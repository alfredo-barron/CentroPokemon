package com.alfredobarron.proyectofinal;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.alfredobarron.proyectofinal.models.Trainer;
import com.alfredobarron.proyectofinal.resources.SessionManager;
import com.alfredobarron.proyectofinal.rest.RestClient;

import butterknife.ButterKnife;
import butterknife.InjectView;
import quickutils.core.QuickUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Configuracion extends Activity {

    private static final String TAG = Configuracion.class.getSimpleName();
    SessionManager sessionManager;
    int id;

    @InjectView(R.id.nombre_edit)
    EditText name;

    @InjectView(R.id.apellidos_edit)
    EditText last_name;

    @InjectView(R.id.usuario_edit)
    EditText user;

    @InjectView(R.id.password_edit)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        ButterKnife.inject(this);
        sessionManager = new SessionManager(this);
        if(sessionManager.checkLogin());

        QuickUtils.init(this);

        if (QuickUtils.web.hasInternetConnection()) {
            id = sessionManager.obtenerId();
            cargarPerfil(id);
        } else {
            Toast.makeText(this, "Verifique su conexión", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(this, "Verifique su conexión", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_configuracion, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {

            return true;
        }
        if (id == R.id.action_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



}
