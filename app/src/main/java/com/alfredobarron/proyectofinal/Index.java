package com.alfredobarron.proyectofinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.alfredobarron.proyectofinal.resources.SessionManager;

import java.util.Timer;
import java.util.TimerTask;


public class Index extends Activity {

    ProgressBar progressBar;
    TextView textView;
    SessionManager sessionManager;
   // private static final long SPLASH_SCREEN_DELAY= 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_index);
        getActionBar().hide();


        sessionManager = new SessionManager(getApplicationContext());

        boolean activity = sessionManager.isLoggedIn();

        if (activity != false) {
            startActivity(new Intent(getApplicationContext(), Main.class));
            finish();
        } else {

            progressBar = (ProgressBar)findViewById(R.id.progressBar1);
            progressBar.setMax(100);
            progressBar.setProgress(0);
            textView = (TextView)findViewById(R.id.carga);

            AsyncTaskCarga asyncTaskCarga = new AsyncTaskCarga(this);
            asyncTaskCarga.execute();


        }

    }

    public class AsyncTaskCarga extends AsyncTask<Void, Integer, Void> {
        Context mContext;

        AsyncTaskCarga(Context context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(Void... params) {

            setProgress(0);
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(50);
                    publishProgress(i + 1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... value) {
            textView.setText(value[0] + " %");
            progressBar.setProgress(value[0]);
        }

        @Override
        protected void onPostExecute(Void result) {

            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();

        }

    }

}
