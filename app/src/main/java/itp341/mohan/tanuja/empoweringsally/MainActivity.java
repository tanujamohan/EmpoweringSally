package itp341.mohan.tanuja.empoweringsally;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "xyz";
    public String mUsername;
    public TextView mUsernameText;
    public boolean viewIsAtHome = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseApp.initializeApp(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        Bundle inBundle = getIntent().getExtras();
        mUsername = inBundle.getString("username");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        displayView(R.id.nav_home);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        if(!viewIsAtHome) {
            displayView(R.id.nav_home);
        }
        else {
            //super.onBackPressed();
            moveTaskToBack(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            Log.d(TAG, "Clicked on settings");
//            return true;
//        }
        displayView(item.getItemId());
        return true;

        //return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        displayView(item.getItemId());
        return true;

    }

    public void displayView(int viewId) {
        int id = viewId;

        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if(id == R.id.nav_home) {
            fragment = new HomeFragment();
            title = "Home";
            viewIsAtHome = true;
        }
        else if (id == R.id.nav_internships) {
            Log.d(TAG, "Internships clicked");
            fragment = new InternshipFragment();
            title = "Internships";
            viewIsAtHome = false;

        } else if (id == R.id.nav_programs) {
            fragment = new ProgramsFragment();
            title = "Summer Programs";
            viewIsAtHome = false;

        } else if (id == R.id.nav_conferences) {
            fragment = new ConferenceFragment();
            title = "Conferences";
            viewIsAtHome = false;

        } else if (id == R.id.nav_volunteering) {
            fragment = new VolunteeringFragment();
            title = "Volunteering";
            viewIsAtHome = false;

        } else if (id == R.id.nav_events) {
            Intent i = new Intent(MainActivity.this, DrawingActivity.class);
            startActivity(i);
//            fragment = new EventsFragment();
//            title = "Events";
//            viewIsAtHome = false;
        } else if (id == R.id.action_settings) {
            fragment = new SettingsFragment();
            Bundle bundle = new Bundle();
            bundle.putString("username", mUsername);
            fragment.setArguments(bundle);
            title = "Settings";
            viewIsAtHome = false;
        }

        if(fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public String getUsername() {
        return mUsername;
    }
}

