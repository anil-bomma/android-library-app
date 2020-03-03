package com.example.android_library_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class AdminScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        // nav-bar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(AdminScreen.this);
        navigationView.bringToFront();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ListAllBooksFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_list_all_books);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        System.out.println("-----menuItem.getItemId()------" + menuItem.getItemId());
        switch (menuItem.getItemId()) {
            case R.id.nav_list_all_books:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ListAllBooksFragment()).commit();
                break;

            case R.id.nav_search_book:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SearchBookFragment()).commit();
                break;
            case R.id.nav_login:
                handleLoginIntent();
                break;
            case R.id.nav_register:
                handleRegisterIntent();
                break;
            case R.id.nav_help:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HelpMenuFragment()).commit();
                break;
            case R.id.nav_contact_us:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ContactMenuFragment()).commit();
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    // login intent will be executed
    public void handleLoginIntent() {
        Intent loginIntent = new Intent(AdminScreen.this, LoginActivity.class);
        startActivity(loginIntent);
    }

    // register intent will be executed
    public void handleRegisterIntent() {
        Intent registerIntent = new Intent(AdminScreen.this, RegistrationScreen.class);
        startActivity(registerIntent);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
