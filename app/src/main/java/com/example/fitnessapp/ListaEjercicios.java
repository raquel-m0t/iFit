package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

/**
 * Clase que representa a una activity que contiene un listado de ejercicios. Permite al usuario seleccionar uno. Cada elemento es un imageButton que al pulsar
 * dirige a la activity ejercicio.
 */
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
    //String tipoUsuario;
    //String nombreUsuario;

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

    // Método para mostrar y ocultar el menú
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                mostrarAyuda(findViewById(R.id.action_help));
                return true;
            case R.id.action_exit:
                salir(findViewById(R.id.action_salir));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //opciones del menú

    /**
     * mostrarAyuda. Carga la activity ayuda para leer el archivo .html generado con Javadoc con explicaciones sobre los métodos y clases de la app.
     * @param view
     */
    public void mostrarAyuda(View view) {
        Intent intent = new Intent (this, Ayuda.class);
        startActivity(intent);
    }

    /**
     * Salir. Genera una instancia de la clase AlertSalir, para mostrar un dialog al usuario solicitando confirmación antes de cerrar la app.
     *
     * @param view
     */
    public void salir(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AlertSalir salir = new AlertSalir();
        salir.show(fragmentManager, "tagAlerta");
    }


}