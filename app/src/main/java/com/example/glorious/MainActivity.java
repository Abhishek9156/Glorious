package com.example.glorious;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.glorious.R;
import com.example.glorious.signin.SignInActivity;
import com.example.glorious.util.KeyConstants;
import com.example.glorious.util.SharedPreferenceUtil;
import com.example.glorious.util.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final float END_SCALE = 0.85f;
    private AppBarConfiguration mAppBarConfiguration;
    private NavController navController;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private BottomNavigationView bottomNavView;
    private CoordinatorLayout contentView;
TextView name,email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();

        initNavigation();

    }

    private void initToolbar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }



    private void initNavigation() {

        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        bottomNavView = findViewById(R.id.bottom_nav_view);
        contentView = findViewById(R.id.content_view);
        View headerView=navigationView.getHeaderView(0);
        name=headerView.findViewById(R.id.name);
        email=headerView.findViewById(R.id.email);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_category,R.id.nav_profile,R.id.nav_order_history,
                R.id.nav_address_book, R.id.nav_logout, R.id.nav_wishlist,
                R.id.nav_earn, R.id.nav_cart
                //  , R.id.bottom_dashboard, R.id.bottom_notifications
        )
                .setDrawerLayout(drawer)
                .build();
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        String fname=SharedPreferenceUtil.getInstance(getApplicationContext()).getStringValue(KeyConstants.FNAME,"");
        String lname=SharedPreferenceUtil.getInstance(getApplicationContext()).getStringValue(KeyConstants.LNAME,"");
        String emails=SharedPreferenceUtil.getInstance(getApplicationContext()).getStringValue(KeyConstants.EMAIL,"");
        email.setText(emails);
        name.setText(fname+" "+lname);
        NavigationUI.setupWithNavController(navigationView, navController);
        NavigationUI.setupWithNavController(bottomNavView, navController);
        navigationView.getMenu().findItem(R.id.nav_logout).setOnMenuItemClickListener(menuItem -> {
            logout();


            return true;
        });

    }

    void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Do you really want to logout?")
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        SharedPreferenceUtil.getInstance(getApplicationContext()).clear();
                        startActivity(new Intent(MainActivity.this, SignInActivity.class));
                        finish();
                    }})
                .setNegativeButton(android.R.string.no, null).show();

    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }

    }

}
