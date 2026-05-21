package com.example.inmobiliariaapp.ui.inicio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.inmobiliariaapp.R;
import com.example.inmobiliariaapp.databinding.FragmentInicioBinding;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class InicioFragment extends Fragment {

    private InicioViewModel vm;
    private FragmentInicioBinding binding;
    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationClient;

    // Coordenadas por defecto
    private final LatLng inmobiliaria = new LatLng(-33.301710, -66.331052);

    // Lanzador para pedir permiso de ubicación
    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                    new ActivityResultCallback<Boolean>() {
                        @Override
                        public void onActivityResult(Boolean isGranted) {
                            if (isGranted) {
                                enableMyLocation();
                            } else {
                                Toast.makeText(requireContext(), "Permiso de ubicación denegado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentInicioBinding.inflate(inflater, container, false);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        vm = new ViewModelProvider(this).get(InicioViewModel.class);
        vm.getMensaje().observe(getViewLifecycleOwner(), texto -> {
            if (texto != null) binding.tvCentrado.setText(texto);
        });
        vm.cargarDatos();

        // Obtener el SupportMapFragment por id
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;
                    map.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                    // Añadir marcador por defecto y centrar cámara
                    map.addMarker(new MarkerOptions().position(inmobiliaria).title("Inmobiliaria"));
                    CameraPosition camPos = new CameraPosition.Builder()
                            .target(inmobiliaria)
                            .zoom(16)
                            .build();
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(camPos);
                    map.moveCamera(cameraUpdate);

                    // Intentar habilitar ubicación si permiso está dado
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        enableMyLocation();
                    } else {
                        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                }
            });
        }

        binding.btnMiUbicacion.setOnClickListener(v -> centerOnMyLocation());
    }

    private void enableMyLocation() {
        if (map == null) return;
        try {
            map.setMyLocationEnabled(true);
        } catch (SecurityException e) {
            // permiso no concedido
        }
    }

    private void centerOnMyLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null && map != null) {
                LatLng myLatLng = new LatLng(location.getLatitude(), location.getLongitude());
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLatLng, 16f));
                map.addMarker(new MarkerOptions().position(myLatLng).title("Mi ubicación"));
            } else {
                Toast.makeText(requireContext(), "No se pudo obtener la ubicación", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(e -> Toast.makeText(requireContext(), "Error al obtener ubicación", Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
