package com.example.proyecto_moviles;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.proyecto_moviles.databinding.ActivityMainBinding;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private ActivityMainBinding binding;
    AppBarConfiguration appBarConfiguration;
    NavOptions slideToRight;
    NavOptions slideToLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavController navController = Navigation.findNavController(this, R.id.mainNavHost);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_reservations, R.id.nav_search, R.id.nav_favorites, R.id.nav_profile)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        slideToRight = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.enter_from_right)
                .setExitAnim(R.anim.exit_to_left)
                .build();

        slideToLeft = new NavOptions.Builder()
                .setLaunchSingleTop(true)
                .setEnterAnim(R.anim.enter_from_left)
                .setExitAnim(R.anim.exit_to_right)
                .build();

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int currentDestination = navController.getCurrentDestination().getId();
            if (currentDestination == R.id.fragment_suggestion) {
                navController.navigateUp();
            }

            NavOptions animationToUse = determineAnimationToUse(currentDestination, item.getItemId());

            switch (item.getItemId()) {
                case R.id.nav_home:
                    navController.navigate(R.id.nav_home, null, animationToUse);
                    break;
                case R.id.nav_reservations:
                    navController.navigate(R.id.nav_reservations, null, animationToUse);
                    break;
                case R.id.nav_search:
                    navController.navigate(R.id.nav_search, null, animationToUse);
                    break;
                case R.id.nav_favorites:
                    navController.navigate(R.id.nav_favorites, null, animationToUse);
                    break;
                case R.id.nav_profile:
                    navController.navigate(R.id.nav_profile, null, animationToUse);
                    break;
            }

            return true;
        });
        bottomNavigationView.setOnItemReselectedListener(item -> {});

        setUser();
    }

    private NavOptions determineAnimationToUse(int current, int destination) {
        // create 2 dictionaries for each direction and asign them with a value
        // if the destination is greater than the current, then use slideToRight
        // else use slideToLeft

        int[] directions = {R.id.nav_home, R.id.nav_reservations, R.id.nav_search, R.id.nav_favorites, R.id.nav_profile};
        int[] values = {0, 1, 2, 3, 4};
        int currentDirection = 0;
        int destinationDirection = 0;

        for (int i = 0; i < directions.length; i++) {
            if (directions[i] == current) {
                currentDirection = values[i];
            }
            if (directions[i] == destination) {
                destinationDirection = values[i];
            }
        }

        if (destinationDirection > currentDirection) {
            return slideToRight;
        } else {
            return slideToLeft;
        }
    }

    private void setUser() {
        prefs = getSharedPreferences("MisPreferencias.UsuarioLogueado", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("usuarioId", "22");
        editor.putString("token", "Bearer 64a4ca6c5cd97");
        editor.apply();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if ( v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.mainNavHost);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}