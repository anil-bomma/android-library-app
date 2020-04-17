package com.example.android_library_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class AdminScreen extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {
//        SearchPersonFragment.SearchListener {

    private DrawerLayout drawer;
    public static Menu nav_menu;
    UserRoleBasedNavigation navSetting = new UserRoleBasedNavigation();

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        NavigationView nav_view = findViewById(R.id.nav_view);
        nav_menu = nav_view.getMenu();

        if (MainActivity.loginStatus) {
            Intent loginIntent = getIntent();
            String userId = loginIntent.getStringExtra("userId");
            String userRole = loginIntent.getStringExtra("userRole");
            fAuth = FirebaseAuth.getInstance();
            navSetting.setNavigation(AdminScreen.this, userRole, userId);
        } else {
            navSetting.setNavigation(AdminScreen.this, "", "");
        }

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


//            FragmentManager fm = getSupportFragmentManager();
//            searchPersonFragment = (SearchPersonFragment) fm.findFragmentByTag("orderFR");
            return;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

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
            case R.id.nav_logout:
                handleLogoutIntent();
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
            case R.id.nav_add_book:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddBookFragment()).commit();
                break;
            case R.id.nav_add_dept_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new AddDepartmentAdminFragment()).commit();
                break;
            case R.id.nav_user_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UserProfileFragment()).commit();
                break;
            case R.id.nav_change_password:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ChangePasswordFragment()).commit();
                break;
            case R.id.nav_list_all_dept_admin:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ListAllDrtAdminsFragment()).commit();
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

    // logout intent will be executed
    public void handleLogoutIntent() {
        MainActivity.loginStatus = false;
        fAuth.signOut();
        navSetting.setNavigation(AdminScreen.this, "", "");
        Toast.makeText(AdminScreen.this, "Logged Out..!!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AdminScreen.this, AdminScreen.class);
        startActivity(intent);
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


//    // list all dept-admin fragment code comes here
//    private SearchPersonFragment searchPersonFragment;
//
//    @Override
//    public void addPressed() {
//
//        Toast.makeText(AdminScreen.this, "Added the user as department admin", Toast.LENGTH_SHORT).show();
//    }
}
