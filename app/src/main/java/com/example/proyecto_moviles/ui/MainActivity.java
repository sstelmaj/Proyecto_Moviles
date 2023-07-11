package com.example.proyecto_moviles.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.proyecto_moviles.R;
import com.example.proyecto_moviles.databinding.ActivityMainBinding;
import com.example.proyecto_moviles.domain.Usuario;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;


public class MainActivity extends AppCompatActivity {
    private SharedPreferences prefs;
    private ActivityMainBinding binding;
    AppBarConfiguration appBarConfiguration;
    NavController navController;

    Usuario loggedUser;
    NavOptions slideToRight;
    NavOptions slideToLeft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        clearPrefs();

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.mainNavHost);
        navController = navHostFragment.getNavController();

        BottomNavigationView bottomNavigationView = binding.mainBottomNav;
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        Toolbar toolbar = findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if(destination.getId() == R.id.login || destination.getId() == R.id.registerFragment) {
                bottomNavigationView.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
            } else if (destination.getId() == R.id.menu_add_suggestion){
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
            }
        });

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.menu_home, R.id.menu_reservations, R.id.menu_search, R.id.menu_favorites, R.id.menu_profile)
                .setFallbackOnNavigateUpListener(this::onSupportNavigateUp)
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
            if(currentDestination == R.layout.fragment_suggestion) {
                navController.navigate(R.id.menu_home, null, slideToLeft);
            }
            NavOptions animationToUse = determineAnimationToUse(currentDestination, item.getItemId());

            switch (item.getItemId()) {
                case R.id.menu_home:
                    navController.navigate(R.id.menu_home, null, animationToUse);
                    break;
                case R.id.menu_reservations:
                    navController.navigate(R.id.menu_reservations, null, animationToUse);
                    break;
                case R.id.menu_search:
                    navController.navigate(R.id.menu_search, null, animationToUse);
                    break;
                case R.id.menu_favorites:
                    navController.navigate(R.id.menu_favorites, null, animationToUse);
                    break;
                case R.id.menu_profile:
                    navController.navigate(R.id.menu_profile, null, animationToUse);
                    break;
            }

            return true;
        });
        bottomNavigationView.setOnItemReselectedListener(item -> {});
    }

    private NavOptions determineAnimationToUse(int current, int destination) {
        // create 2 dictionaries for each direction and asign them with a value
        // if the destination is greater than the current, then use slideToRight
        // else use slideToLeft

        int[] directions = {R.id.menu_home, R.id.menu_reservations, R.id.menu_search, R.id.menu_favorites, R.id.menu_profile};
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

    public boolean onSupportNavigateUp() {
        // Show bottom navigation bar when navigating up
//        findViewById(R.id.mainBottomNav).setVisibility(View.VISIBLE);

        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void clearPrefs() {
        prefs = getSharedPreferences("MisPreferencias.UsuarioLogueado", Context.MODE_PRIVATE);
        prefs.edit().clear().apply();
    }
}