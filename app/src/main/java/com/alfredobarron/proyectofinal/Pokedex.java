package com.alfredobarron.proyectofinal;

import java.util.Locale;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v13.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.alfredobarron.proyectofinal.dialogs.AlertInformation;
import com.alfredobarron.proyectofinal.models.Pokeball;
import com.alfredobarron.proyectofinal.models.Pokemon;
import com.alfredobarron.proyectofinal.resources.SessionManager;
import com.alfredobarron.proyectofinal.rest.RestClient;
import com.alfredobarron.proyectofinal.util.AlertInformationUtil;

import butterknife.ButterKnife;
import quickutils.core.QuickUtils;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class Pokedex extends Activity implements ActionBar.TabListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;

    private final String TAG = Pokedex.class.getSimpleName();

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    int id_trainer, id_pokemon;
    SessionManager sessionManager;
    String specie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);

        QuickUtils.init(this);

        sessionManager = new SessionManager(this);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            id_pokemon = extras.getInt("id");
            specie = extras.getString("specie");
            getActionBar().setTitle(specie);
        }

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pokedex, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Intent i = new Intent(this, Atrapar.class);
            //startActivity(i);

            final AlertInformation.Callback questionCallback;
            questionCallback = new AlertInformation.Callback() {
                @Override
                public void onPositiveButtonClicked() {
                    id_trainer = sessionManager.obtenerId();
                    if(QuickUtils.web.hasInternetConnection()) {
                        RestClient.restAdapter().getPokebola(id_trainer,id_pokemon, new Callback<Pokeball>() {
                            @Override
                            public void success(Pokeball pokeball, Response response) {
                                Toast.makeText(getApplicationContext(), "Pokemon atrapado", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), Main.class));
                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Log.e(TAG, error.getMessage());
                                Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        Toast.makeText(getApplicationContext(), "Verifique su conexión", Toast.LENGTH_LONG).show();
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
                    getString(R.string.title),
                    getString(R.string.content),
                    getString(R.string.atrapar),
                    getString(R.string.cancelar));

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new InformacionFragment();
                case 1:
                    return new TipoFragment();
                case 2:
                    return new HabilidadesFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section9).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section10).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section11).toUpperCase(l);
            }
            return null;
        }
    }


}
