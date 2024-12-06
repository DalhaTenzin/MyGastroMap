package ca.gbc.mygastromap;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.osmdroid.api.IMapController;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.Marker;
import android.util.Log;
import org.osmdroid.config.Configuration;

public class MapViewActivity extends AppCompatActivity {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;
    private MapView mapView;
    private String address;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        // Configure osmdroid User-Agent
        Configuration.getInstance().setUserAgentValue(getApplicationContext().getPackageName());

        // Step 1: Get the address from the intent
        address = getIntent().getStringExtra("address");

        // Log the address
        Log.d("MapViewActivity", "Address received: " + address);

        // Step 2: Check location permissions
        if (!hasLocationPermissions()) {
            requestLocationPermissions();
        } else {
            initializeMap();
        }
    }


    private boolean hasLocationPermissions() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                LOCATION_PERMISSION_REQUEST_CODE
        );
    }

    private void initializeMap() {
        // Initialize the map
        mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK); // OpenStreetMap tiles
        mapView.setMultiTouchControls(true); // Enable zoom and gestures

        // Dummy Lat/Lng for Toronto (replace with real geocoding if needed)
        GeoPoint geoPoint = new GeoPoint(43.6532, -79.3832);
        IMapController mapController = mapView.getController();
        mapController.setZoom(15.0); // Set zoom level
        mapController.setCenter(geoPoint);

        // Create and add a marker
        Marker marker = new Marker(mapView);
        marker.setPosition(geoPoint);
        marker.setTitle("Restaurant Location: " + address);
        mapView.getOverlays().add(marker);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initializeMap();
            } else {
                Toast.makeText(this, "Location permissions are required to display the map.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
