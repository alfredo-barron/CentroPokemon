package com.alfredobarron.proyectofinal;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;

import com.alfredobarron.proyectofinal.resources.SessionManager;

import butterknife.ButterKnife;
import butterknife.InjectView;
import quickutils.core.QuickUtils;

public class Main extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = Main.class.getSimpleName();

    private NavigationDrawerFragment mNavigationDrawerFragment;

    private CharSequence mTitle;

    static SessionManager sessionManager;

    @Override
    public void onResume(){
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(getApplication());

        sessionManager.checkLogin();

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));

        if (savedInstanceState == null) {
            onNavigationDrawerItemSelected(0);
        }

        // QuickUtils.init(this);
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new Fragment();

        switch (position) {
            case 0:
                fragment = new Inicio_Menu();
                mTitle = getString(R.string.title_section1);
                break;
            case 1:
                fragment = new Perfil_Menu();
                mTitle = getString(R.string.title_section2);
                break;
            case 2:
                fragment = new Pokemon_Menu();
                mTitle = getString(R.string.title_section3);
                break;
            case 3:
                fragment = new Pokedex_Menu();
                mTitle = getString(R.string.title_section4);
                break;
            case 4:
                fragment = new Regenerador_Menu();
                mTitle = getString(R.string.title_section5);
                break;
            case 5:
                fragment = new Descanso_Menu();
                mTitle = getString(R.string.title_section6);
                break;
            case 6:
                fragment = new Configuracion_Menu();
                mTitle = getString(R.string.title_section7);
                break;
            case 7:
                sessionManager.logoutUser();
                finish();
                break;

        }

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}