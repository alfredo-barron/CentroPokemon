package com.alfredobarron.proyectofinal.resources;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.alfredobarron.proyectofinal.Login;

import java.util.HashMap;

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "PokemonPreferences";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "Log-in";

    // User name (make variable public to access from outside)
    public static final String KEY_NOMBRE = "nombre";

    public static final String KEY_APELLIDOS = "apellidos";

    // Email address (make variable public to access from outside)
    public static final String KEY_USUARIO = "usuario";

    public static final String KEY_ID = "id";



    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(int id, String usuario, String nombre, String apellidos){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        editor.putInt(KEY_ID, id);

        editor.putString(KEY_USUARIO, usuario);

        editor.putString(KEY_NOMBRE, nombre);

        editor.putString(KEY_APELLIDOS, apellidos);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public boolean checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);

            return true;
        }
        return false;
    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_ID, pref.getString(KEY_ID, null));
        // user email id
        user.put(KEY_USUARIO, pref.getString(KEY_USUARIO, null));

        user.put(KEY_NOMBRE, pref.getString(KEY_NOMBRE, null));

        user.put(KEY_APELLIDOS, pref.getString(KEY_APELLIDOS, null));

        // return user
        return user;
    }


    public String obtenerUser(){
       String user = pref.getString(KEY_USUARIO,null);
       return user;
    }

    public int obtenerId(){
       int id = pref.getInt(KEY_ID,0);
        return id;
    }

    public String obtenerNombre(){
        String nombre = pref.getString(KEY_NOMBRE,null);
        return nombre;
    }

    public String obtenerApellido(){
        String apellidos = pref.getString(KEY_APELLIDOS,null);
        return apellidos;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        //editor.remove("KEY_ID");
        //editor.remove("KEY_USUARIO");
        //editor.remove("KEY_NOMBRE");
        //editor.remove("KEY_APELLIDOS");
        //editor.putBoolean(KEY_ID, false);
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}







