package com.alfredobarron.proyectofinal;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.alfredobarron.proyectofinal.models.Trainer;
import com.alfredobarron.proyectofinal.resources.SessionManager;
import com.alfredobarron.proyectofinal.rest.RestClient;

import java.util.Calendar;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import quickutils.core.QuickUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Register extends Activity {

    private static final String TAG = Register.class.getSimpleName();

    private static final int HELLO_ID = 1;

    @InjectView(R.id.usuario)
    EditText usuario;

    @InjectView(R.id.password)
    EditText contrasena;

    @InjectView(R.id.nombre)
    EditText nombre;

    @InjectView(R.id.apellidos)
    EditText apellidos;

    @InjectView(R.id.fecha_de_nacimiento)
    TextView fecha_de_nacimiento;

    @InjectView(R.id.lugar_de_nacimiento)
    Spinner lugar_de_nacimiento;

    @InjectView(R.id.hombre)
    RadioButton hombre;

    @InjectView(R.id.mujer)
    RadioButton mujer;

    @InjectView(R.id.yes)
    RadioButton yes;

    @InjectView(R.id.lugar_de_residencia)
    Spinner lugar_de_residencia;

    private int year, month, day, region_id, region_id_actual;
    private String name, last_name, image, username, password, gender, birthday;
    private boolean leader;

    static final int DATE_DIALOG_ID = 0;

    SessionManager sessionManager;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        setCurrentDateView();
        addListenerDateView();
        QuickUtils.init(this);
        sessionManager = new SessionManager(getApplicationContext());
    }

    public void setCurrentDateView() {
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        fecha_de_nacimiento.setText(new StringBuilder()
                .append(day).append(" ").append(month).append(", ")
                .append(year).append(" "));
    }

    public void addListenerDateView() {
        fecha_de_nacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this, datePickerListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener datePickerListener
            = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth,
                              int selectedDay) {
            year = selectedYear;
            month = selectedMonth;
            day = selectedDay;

            fecha_de_nacimiento.setText(new StringBuilder()
                    .append(day).append(" ").append(month).append(", ")
                    .append(year).append(" "));
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the main; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
            //mostrarNotBarra();
            //finish();
            registro();
            return true;
        }
        if (id == R.id.action_cancel) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void registro() {
        if(usuario.getText().length() == 0 || contrasena.getText().length() == 0 ||
                nombre.getText().length() == 0 || apellidos.getText().length() == 0) {
            Toast.makeText(getApplicationContext(), "Rellene todos los datos",Toast.LENGTH_SHORT).show();
        } else {
            username = usuario.getText().toString();
            password = contrasena.getText().toString();
            name = nombre.getText().toString();
            last_name = apellidos.getText().toString();
            birthday = year+"-"+month+"-"+day;
            region_id = lugar_de_nacimiento.getSelectedItemPosition() + 1;
            if(hombre.isChecked()) {
                gender = "Hombre";
                image = "http://pokemon-hoenn.herokuapp.com/public/img/entrenador.png";
            } else if(mujer.isChecked()) {
                gender = "Mujer";
                image = "http://pokemon-hoenn.herokuapp.com/public/img/entrenadora.png";
            }
            if(yes.isChecked()) {
                leader = true;
            } else {
                leader = false;
            }
            region_id_actual = lugar_de_residencia.getSelectedItemPosition() + 1;
            progressDialog = new ProgressDialog(Register.this);
            progressDialog.setMessage("Procesando sus datos...");
            progressDialog.setIndeterminate(false);
            progressDialog.setCancelable(true);
            progressDialog.show();
            newRegister(name,last_name,image,username,password,birthday,region_id,gender,leader,region_id_actual);
        }
    }

    private void newRegister(String name, String last_name, String image, final String username,
                             final String password, String birthday, int region_id, String gender,
                             boolean leader, int region_id_actual) {
        if(QuickUtils.web.hasInternetConnection()) {
            RestClient.restAdapter().getEntrenador(name, last_name, image, username, password,
                    birthday, region_id, gender, leader, region_id_actual, new Callback<Trainer>() {
                @Override
                public void success(Trainer trainer, Response response) {
                    progressDialog.dismiss();
                    System.out.println(trainer.getId());
                    System.out.println(trainer.getUsername());
                    System.out.println(trainer.getName());
                    System.out.println(trainer.getLastName());
                    //Toast.makeText(getApplicationContext(), trainer.getName(), Toast.LENGTH_SHORT).show();
                    sessionManager.createLoginSession(trainer.getId(),trainer.getUsername(),trainer.getName(),trainer.getLastName());
                    startActivity(new Intent(getApplicationContext(), Main.class));

                    finish();
                }

                @Override
                public void failure(RetrofitError error) {
                    progressDialog.dismiss();
                    Log.e(TAG,error.getMessage());
                    Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Verifique su conexión", Toast.LENGTH_LONG).show();
        }
    }

    private void mostrarNotBarra() {
        NotificationManager notificationManager =
            (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        int icon = R.drawable.pokeball;
        CharSequence tickerText = "Pokémon";
        long when = System.currentTimeMillis();

        Notification notification = new Notification(icon, tickerText, when);

        Context context = getApplicationContext();
        CharSequence contentTitle = "Alerta de registro";
        CharSequence contentText = "Usuario registrado con éxito";

        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        Intent notificationIntent = new Intent(this, Login.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

        notificationManager.notify(HELLO_ID, notification);

    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
    }


}
