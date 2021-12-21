package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

public class ListaEjercicios extends AppCompatActivity {

    //botones de ejercicio
    ImageButton abdominales;
    ImageButton abductores;
    ImageButton burpees;
    ImageButton flexiones;
    ImageButton gluteos;
    ImageButton triceps;
    ImageButton pesos;
    ImageButton puentes;
    ImageButton sentadillas;
    ImageButton steps;
    ImageButton zancadas;
    ImageButton zancadasLaterales;

    int tipo; //tipo de ejercicio  1. abdominales, 2. abductores, 3. burpees, 4. flexiones, 5. gluteos, 6. triceps, 7.pesos, 8. puentes, 9. sentadillas, 10. steps, 11.zancadas, 12.zancadaslaterales


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_ejercicios);

        //asociar imageButtons a los componentes ImageButton del xml
        abdominales = (ImageButton) findViewById(R.id.botonAbdominales);
        abdominales.setOnClickListener(view -> {
            Intent intent = new Intent(this, Ejercicio.class);
            tipo = 1;
            intent.putExtra("tipo de ejercicio", tipo);//guardar tipo de ejercicio para la siguiente ventana
            startActivity(intent);
        });
        abductores = (ImageButton) findViewById(R.id.botonAbductor);
        abductores.setOnClickListener(view -> {
            Intent intent = new Intent(this, Ejercicio.class);
            tipo = 2;
            intent.putExtra("tipo de ejercicio", tipo);
            startActivity(intent);
        });
        burpees = (ImageButton) findViewById(R.id.botonBurpees);
        burpees.setOnClickListener(view -> {
            Intent intent = new Intent(ListaEjercicios.this, Ejercicio.class);
            tipo = 3;
            intent.putExtra("tipo de ejercicio", tipo);
            startActivity(intent);
        });
        flexiones = (ImageButton) findViewById(R.id.botonFlexiones);
        flexiones.setOnClickListener(view -> {
            Intent intent = new Intent(ListaEjercicios.this, Ejercicio.class);
            tipo = 4;
            intent.putExtra("tipo de ejercicio", tipo);
            startActivity(intent);
        });
        gluteos = (ImageButton) findViewById(R.id.botonGluteos);
        gluteos.setOnClickListener(view -> {
            Intent intent = new Intent(ListaEjercicios.this, Ejercicio.class);
            tipo = 5;
            intent.putExtra("tipo de ejercicio", tipo);
            startActivity(intent);
        });
        triceps = (ImageButton) findViewById(R.id.botonTriceps);
        triceps.setOnClickListener(view -> {
            Intent intent = new Intent(ListaEjercicios.this, Ejercicio.class);
            tipo = 6;
            intent.putExtra("tipo de ejercicio", tipo);
            startActivity(intent);
        });
        pesos = (ImageButton) findViewById(R.id.botonPeso);
        pesos.setOnClickListener(view -> {
            Intent intent = new Intent(ListaEjercicios.this, Ejercicio.class);
            tipo = 7;
            intent.putExtra("tipo de ejercicio", tipo);
            startActivity(intent);
        });

        puentes = (ImageButton) findViewById(R.id.botonPuente);
        puentes.setOnClickListener(view -> {
            Intent intent = new Intent(ListaEjercicios.this, Ejercicio.class);
            tipo = 8;
            intent.putExtra("tipo de ejercicio", tipo);
            startActivity(intent);
        });

        sentadillas = (ImageButton) findViewById(R.id.botonSentadilla);
        sentadillas.setOnClickListener(view -> {
            Intent intent = new Intent(ListaEjercicios.this, Ejercicio.class);
            tipo = 9;
            intent.putExtra("tipo de ejercicio", tipo);
            startActivity(intent);
        });

        steps = (ImageButton) findViewById(R.id.botonSteps);
        steps.setOnClickListener(view -> {
            Intent intent = new Intent(ListaEjercicios.this, Ejercicio.class);
            tipo = 10;
            intent.putExtra("tipo de ejercicio", tipo);
            startActivity(intent);
        });

        zancadas = (ImageButton) findViewById(R.id.botonZancada);
        zancadas.setOnClickListener(view -> {
            Intent intent = new Intent(ListaEjercicios.this, Ejercicio.class);
            tipo = 11;
            intent.putExtra("tipo de ejercicio", tipo);
            startActivity(intent);
        });

        zancadasLaterales = (ImageButton) findViewById(R.id.botonZancadaLateral);
        zancadasLaterales.setOnClickListener(view -> {
            Intent intent = new Intent(ListaEjercicios.this, Ejercicio.class);
            tipo = 12;
            intent.putExtra("tipo de ejercicio", tipo);
            startActivity(intent);
        });

    }

    public void verDetalle() {
        Intent intent = new Intent(this, Ejercicio.class);
        startActivity(intent);
    }
}