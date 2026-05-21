package com.example.inmobiliariaapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.example.inmobiliariaapp.databinding.ActivityMainBinding;
import com.example.inmobiliariaapp.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private MainViewModel vm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instanciar y configurar observadores primero
        vm = new ViewModelProvider(this).get(MainViewModel.class);
        configurarObservadores();

        // El VM decide si la sesión es válida o no
        vm.verificarSesion();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment_content_main);
        NavController navController = navHostFragment.getNavController();

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.inicioFragment, R.id.perfilFragment, R.id.nav_inmuebles,
                R.id.nav_inquilinos, R.id.nav_contratos
        ).setOpenableLayout(binding.drawerLayout).build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationView navView = binding.navView;
        NavigationUI.setupWithNavController(navView, navController);

        // Listener para interceptar logout
        navView.setNavigationItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_logout) {
                mostrarDialogoLogout();
                binding.drawerLayout.closeDrawers();
                return true;
            }
            boolean handled = NavigationUI.onNavDestinationSelected(item, navController);
            if (handled) binding.drawerLayout.closeDrawers();
            return handled;
        });
    }

    private void configurarObservadores() {
        // Si la sesión no es válida (por falta de token o por logout), navegamos
        vm.getSesionValida().observe(this, valida -> {
            if (!valida) {
                startLoginAndFinish();
            }
        });
    }

    private void mostrarDialogoLogout() {
        new AlertDialog.Builder(this)
                .setTitle("Cerrar Sesión")
                .setMessage("¿Estás seguro que querés salir?")
                .setPositiveButton("Sí", (dialog, which) -> {
                    // La Activity no borra el token, le pide al VM que lo haga
                    vm.cerrarSesion();
                })
                .setNegativeButton("No", null)
                .show();
    }

    private void startLoginAndFinish() {
        Intent i = new Intent(this, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}