package com.example.ninja_game_v3;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {

    boolean musicSet;
    private MediaPlayer gameSong;
    private SharedPreferences.Editor editor;
    private SharedPreferences.Editor editorSettings;
    private String nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        // initialize UI elements
        SharedPreferences sharedPreferences2 = getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE);
        SharedPreferences sharedPreferencesSettings = PreferenceManager.getDefaultSharedPreferences(this);

        editor = sharedPreferences2.edit();
        editorSettings = sharedPreferencesSettings.edit();
        musicSet = sharedPreferencesSettings.getBoolean("reproducir_musica", false);
        Button bScore = findViewById(R.id.bScore);
        Button bPlay = findViewById(R.id.bPlay);
        Button bExit = findViewById(R.id.bExit);
        TextView titleGame = findViewById(R.id.titleText);
        // start title animation
        startAnimationTitle(titleGame);
        // set click listeners for buttons
        bScore.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), PuntuacionsTotal.class);
            startActivity(intent);
        });
        bPlay.setOnClickListener(view -> {
            Intent intent = new Intent(this, Joc.class);

            createAlertDialog(intent);

        });
        bExit.setOnClickListener(view -> finish());
    }

    @Override
    protected void onStart() {
        super.onStart();
        // initialize game song
        if (musicSet) {
            gameSong = MediaPlayer.create(this, R.raw.musica);
            gameSong.setLooping(true);
            gameSong.start();
        }

    }

    // animation method
    private void startAnimationTitle(TextView titleGame) {
        ValueAnimator animator = ValueAnimator.ofFloat(-50f, 50f); // moverse más lejos
        animator.setDuration(2500); // duración más larga
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new AccelerateDecelerateInterpolator()); // suavizar la animación

        animator.addUpdateListener(animation -> {
            float value = (float) animation.getAnimatedValue();
            titleGame.setTranslationY(value);
            float rotation = value / 50f * 15f - 7.5f; // agregar rotación ligera
            titleGame.setRotation(rotation);
            float scaleX = 1f - Math.abs(value / 50f * 0.2f); // ajustar el tamaño horizontal
            titleGame.setScaleX(scaleX);
        });

        animator.start();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_configuracio:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                //TODO
                return true;
            case R.id.menu_informacio:
                //TODO
                Toast.makeText(this, "Programador: Pau Perez Carranco, APP :Ninja Game ", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void createAlertDialog(Intent intent) {


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Nombre jugador");
        alert.setMessage("Por favor introduzca su nombre");
        // Set an EditText view to get user input
        final EditText input = new EditText(this);
        alert.setView(input);
        alert.setPositiveButton("Ok", (dialog, whichButton) -> {
            nombre = input.getText().toString();
            editor.putString(nombre, "0");
            editor.apply();
            editor.commit();
            editorSettings.putString("username", nombre);
            editorSettings.putString("score", "0");

            editorSettings.apply();
            editorSettings.commit();

            startActivity(intent);

        });
        alert.show();


    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferencesSettings = PreferenceManager.getDefaultSharedPreferences(this);
        boolean musicSet = sharedPreferencesSettings.getBoolean("reproducir_musica", false);

        if (musicSet) {
            if (gameSong == null) {
                gameSong = MediaPlayer.create(this, R.raw.musica);
                gameSong.setLooping(true);
            }

            if (!gameSong.isPlaying()) {
                gameSong.start();
            }
        } else {
            if (gameSong != null && gameSong.isPlaying()) {
                gameSong.pause();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (gameSong != null && gameSong.isPlaying()) {
            gameSong.stop();
            gameSong.release();
            gameSong = null;
        }
    }
}