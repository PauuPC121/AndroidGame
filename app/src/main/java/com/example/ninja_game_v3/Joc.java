package com.example.ninja_game_v3;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Joc extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instantiate the VistaJoc and set its parent activity
        Objects.requireNonNull(getSupportActionBar()).hide();

        VistaJoc vistaJoc = new VistaJoc(this, null);
        vistaJoc.setPare(this);

        // Set the content view to the VistaJoc
        setContentView(vistaJoc);
    }


}