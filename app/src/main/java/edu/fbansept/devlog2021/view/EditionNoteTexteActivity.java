package edu.fbansept.devlog2021.view;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.io.IOException;
import java.io.Serializable;
import java.net.URI;

import edu.fbansept.devlog2021.R;
import edu.fbansept.devlog2021.controller.NoteController;
import edu.fbansept.devlog2021.model.NoteTexte;

public class EditionNoteTexteActivity extends AppCompatActivity implements LocationListener {

    EditText editTextTitre;
    EditText editTextContenu;
    NoteTexte note;
    BottomAppBar bottomAppBar;
    FloatingActionButton floatingActionButton;
    SupportMapFragment mapFragment;
    GoogleMap googleMap;
    LocationManager locationManager;
    boolean permissionRejete = false;
    ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edition_note_texte);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if(!permissionRejete) {
            verificationPermission();
        }
    }

    private void verificationPermission() {
        if(ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION},
                            1234
                            );
        } else {
            activationLocation();
        }
    }

    @SuppressLint("MissingPermission")
    private void activationLocation() {
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        }
        if(locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 10000, 0, this);
        }
        if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       if(requestCode == 1234) {
            if(grantResults[0] != PackageManager.PERMISSION_GRANTED
            && grantResults[1] != PackageManager.PERMISSION_GRANTED){
                permissionRejete= true;
                new AlertDialog.Builder(this)
                        .setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle(":'(")
                        .setMessage("Si vous n'activez pas votre position, cette fonctionnalité est indisponible")
                        .setPositiveButton("Ok...Je change", (d,w) -> verificationPermission())
                        .setNegativeButton("Non vraiment pas merci...", (d,w) -> {})
                        .show();
            } else {
                permissionRejete= false;
                activationLocation();
            }
        }
    }

    private void init() {

        note = (NoteTexte)getIntent().getSerializableExtra("note");

        editTextTitre = findViewById(R.id.editText_titreNoteTexte);
        editTextContenu = findViewById(R.id.editText_contenuNoteTexte);

        FragmentManager fm = getSupportFragmentManager();
        mapFragment = (SupportMapFragment) fm.findFragmentById(R.id.mapView);
        if (mapFragment == null) {
            mapFragment = new SupportMapFragment();
            fm.beginTransaction().replace(R.id.mapView, mapFragment).commit();
        }

        editTextTitre.setText(note.getTitre());
        editTextContenu.setText(note.getTexte());

        bottomAppBar = findViewById(R.id.bottomAppBar_editionNoteTexte);

        bottomAppBar.setOnMenuItemClickListener(menuItem -> {

            if(menuItem.getItemId() == R.id.menuItem_ajouterFichier) {
                Intent gallerie = new Intent(
                        Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                activityResultLauncher.launch(gallerie);
                return true;
            } else if(menuItem.getItemId() == R.id.menuItem_ajouterPhoto) {
                Log.d("event","bouton ajout photo cliqué");
                return true;
            } else if(menuItem.getItemId() == R.id.menuItem_ajouterPosition) {
                Log.d("event","bouton ajout position cliqué");
                return true;
            } else if(menuItem.getItemId() == R.id.menuItem_supprimer) {
                Log.d("event","bouton supprimé cliqué");
                return true;
            }

            return false;
        });

        floatingActionButton = findViewById(R.id.fab_editionNoteTexte);

        floatingActionButton.setOnClickListener(v -> {
            //on change les propriétés de l'objet note avec les valeur des champs de saisie
            note.setTitre(editTextTitre.getText().toString());
            note.setTexte(editTextContenu.getText().toString());

            //on envoie l'objet note au controleur pour le sauvegarder
            try {
                NoteController.getInstance().save(
                        this,
                        note,
                        urlNote -> {
                            Intent intent = new Intent(
                                    this,
                                    ListeNoteActivity.class);

                            startActivity(intent);
                        });
            } catch (JSONException e) {
                Toast.makeText(this, "Erreur interne", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
        });

        telechargeGoogleMaps();

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                (ActivityResult result) -> {
                    if(result != null
                            && result.getData() != null
                            && result.getResultCode() == Activity.RESULT_OK
                    ) {
                        Intent intent = result.getData();
                        Uri uri = intent.getData();
                        try {
                            NoteController.getInstance()
                                    .ajoutImageNote(this,uri);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
        );
    }

    private void telechargeGoogleMaps() {

        mapFragment.getMapAsync(googleMap -> {
            this.googleMap = googleMap;
            this.googleMap.moveCamera(
                    CameraUpdateFactory.zoomTo(10)
            );
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println(location.getLatitude());
        if(googleMap != null) {
            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}