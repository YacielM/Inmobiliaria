package com.example.inmobiliariaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.inmobiliariaapp.databinding.ActivityMainBinding;
import com.example.inmobiliariaapp.request.ApiClient;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.Navigation;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Si no hay token, ir a LoginActivity
        String token = ApiClient.obtenerToken(this);
        if (token == null || token.trim().isEmpty()) {
            startLoginAndFinish();
            return;
        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main);
        assert navHostFragment != null;
        NavController navController = navHostFragment.getNavController();

        // Agregar aquí los destinos top-level para que el botón de hamburguesa funcione correctamente
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.inicioFragment,
                R.id.perfilFragment,
                R.id.nav_inmuebles,
                R.id.nav_inquilinos,
                R.id.nav_contratos
        ).setOpenableLayout(binding.drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        // Conectar NavigationView con NavController para que los items naveguen automáticamente
        NavigationView navView = binding.navView;
        NavigationUI.setupWithNavController(navView, navController);

        // Listener para interceptar logout (no es un destino del nav graph)
        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_logout) {
                new AlertDialog.Builder(this)
                        .setTitle("Logout")
                        .setMessage("¿Estás seguro que querés salir de la sesión?")
                        .setPositiveButton("Sí", (dialog, which) -> {
                            // Borrar token y volver a LoginActivity
                            ApiClient.borrarToken(this);
                            startLoginAndFinish();
                        })
                        .setNegativeButton("No", (dialog, which) -> dialog.dismiss())
                        .setCancelable(false)
                        .show();

                binding.drawerLayout.closeDrawers();
                return true;
            }

            // Dejar que NavigationUI maneje el resto de destinos
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            if (handled) binding.drawerLayout.closeDrawers();
            return handled;
        });
    }

    private void startLoginAndFinish() {
        Intent i = new Intent(this, com.example.inmobiliariaapp.ui.login.LoginActivity.class);
        // Limpiar pila para que no se pueda volver con back
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        boolean result = super.onCreateOptionsMenu(menu);
        // Inflar overflow solo si no hay NavigationView visible en la configuración actual
        NavigationView navView = findViewById(R.id.nav_view);
        if (navView == null) {
            getMenuInflater().inflate(R.menu.overflow, menu);
        }
        return result;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Si tenés settings en overflow, lo manejás aquí
        if (item.getItemId() == R.id.nav_settings) {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_settings);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}
