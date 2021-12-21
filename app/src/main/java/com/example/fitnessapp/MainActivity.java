package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity {

    Button comenzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        comenzar = (Button) findViewById(R.id.buttonComenzar);
        comenzar.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListaEjercicios.class);
            startActivity(intent);
        });
    }
}