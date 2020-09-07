package com.hemanth.cabshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import Fragments.AddRideFragment;
import Fragments.ProfileFragment;
import Fragments.YourRidesFragment;

public class AppActivity extends AppCompatActivity {
    private Fragment selectorFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        selectorFragment = new AddRideFragment();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navProfile:
                        selectorFragment = new ProfileFragment();
                        break;

                    case R.id.navAddRide:
                        selectorFragment = new AddRideFragment();
                        break;

                    case R.id.navYourRides:
                        selectorFragment = new YourRidesFragment();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectorFragment).commit();
                return false;
            }
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AddRideFragment()).commit();
    }
}