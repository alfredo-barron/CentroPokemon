package com.alfredobarron.proyectofinal;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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


public class Login extends Activity {

    private static final String TAG = Login.class.getSimpleName();

    private ProgressDialog progressDialog;


    SessionManager sessionManager;

    String username, password;

    @InjectView(R.id.usuario_login)
    EditText usernameTxt;

    @InjectView(R.id.password_login)
    EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        sessionManager = new SessionManager(getApplicationContext());

        /*
        Button SignInButton = (Button) findViewById(R.id.sign_in_button);
        SignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/

        Button RegisterButton = (Button) findViewById(R.id.register_button);
        RegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), Register.class);
                startActivity(i);
            }
        });
        QuickUtils.init(this);
    }

    public void entrar(View view) {
        if(usernameTxt.getText().length() == 0 || passwordTxt.getText().length() == 0){
            Toast.makeText(getApplicationContext(), getString(R.string.ingrese), Toast.LENGTH_SHORT).show();
        } else {
            username = usernameTxt.getText().toString();
            password = passwordTxt.getText().toString();
            //new asynlogin().execute();
            if (QuickUtils.web.hasInternetConnection()) {
                login(username, password);
                progressDialog = new ProgressDialog(Login.this);
                progressDialog.setMessage("Verificando datos...");
                progressDialog.setIndeterminate(false);
                progressDialog.setCancelable(true);
                progressDialog.show();
            } else {
                Toast.makeText(getApplicationContext(), "Verifique su conexión", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void login(String username, String password) {
        RestClient.restAdapter().getLogin(username, password, new Callback<Trainer>() {
            @Override
            public void success(Trainer trainer, Response response) {
                if(trainer.getId() == 0) {
                    progressDialog.dismiss();
                    Vibrator vibrar=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
                    vibrar.vibrate(500);
                    Toast.makeText(getApplicationContext(), "Usuario o contraseña invalida", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.dismiss();
                    startActivity(new Intent(getApplicationContext(), Main.class));
                    sessionManager.createLoginSession(trainer.getId(),trainer.getUsername(),trainer.getName(),trainer.getLastName());
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                progressDialog.dismiss();
                Log.e(TAG,error.getMessage());
                Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}
