package com.example.ninja_game_v3;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PuntuacionsTotal extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puntuacions_layout);

        ListView mListView = findViewById(R.id.scoreListView);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences sharedPreferencesList = getSharedPreferences(getString(R.string.sharedPref), Context.MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sharedPreferencesList.edit();
        String name = sharedPreferences.getString("username", "");
        String score = sharedPreferences.getString("score", "-1");
        spEditor.putString(name, score);
        spEditor.apply();
        List<String> keyValues = new ArrayList<>();
        Map<String, ?> allEntries = sharedPreferencesList.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            String key = entry.getKey();
            String value = String.valueOf(entry.getValue());
            keyValues.add(key + " : " + value);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, keyValues);
        mListView.setAdapter(adapter);
    }
}