package invmgmt.zeelandrefinery.nl.zrinventorymanager;

import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;



public class MainActivity extends AppCompatActivity {

    private ActionBarDrawerToggle mDrawerToggle;
    private MenuItem prevMenuItem;
    public static FragmentManager fragmentManager;

    NotificationManager mNotifyMgr;
    NavigationView nvDrawer;
    DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (savedInstanceState == null) {
            fragmentManager = getSupportFragmentManager();
            initViews();
            Log.d("Intent", "Start nieuwe service");

            FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
            tx.replace(R.id.flContent, new MainFragment());
            tx.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //RelativeLayout layout = (RelativeLayout) findViewById(R.layout.activity_home);

        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    private void initViews() {
        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar); // Attaching the layout to the toolbar object

        // Find our drawer view
        nvDrawer = (NavigationView) findViewById(R.id.nvView);

        // Setup drawer view
        setupDrawerContent(nvDrawer);

        // Find our drawer view
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        // Set Toolbar

        setSupportActionBar(toolbar); // Setting toolbar as the ActionBar with setSupportActionBar()


        // Set the drawer toggle as the DrawerListener
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                // invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //  invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        nvDrawer.getMenu().getItem(0).setChecked(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // Make sure this is the method with just `Bundle` as the signature
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggles
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    /**
     * Setup Fragments
     */


    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }


    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the planet to show based on
        // position
        Fragment fragment = null;
        Class fragmentClass;

        //Checking if the item is in checked state or not, if not make it in checked state

        menuItem.setChecked(true);
        switch (menuItem.getItemId()) {
            case R.id.nav_first_fragment:
                fragmentClass = MainFragment.class;
                break;
            case R.id.nav_second_fragment:
                fragmentClass = DatabaseFragment.class;
                break;
            case R.id.nav_third_fragment:
                fragmentClass = TestFragment.class;
                break;

            default:
                fragmentClass = MainFragment.class;
        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

        // Highlight the selected item, update the title, and close the drawer
        if (prevMenuItem != null) {
            prevMenuItem.setChecked(false);
        } else {
            nvDrawer.getMenu().getItem(0).setChecked(false);
        }
        mDrawerLayout.closeDrawers();
        prevMenuItem = menuItem;
        mDrawerLayout.closeDrawers();
    }


    @Override
    public void onDestroy() {
        mNotifyMgr =
                (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        mNotifyMgr.cancelAll();
        super.onDestroy();

    }

    private Boolean exit = false;
    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, R.string.backpressed,
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }


}
