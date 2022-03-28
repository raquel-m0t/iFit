package com.example.fitnessapp;

/**
 * Esta clase representa a la view registro_selección y le da funcionalidad a sus componentes. Tiene botones para volver a la página anterior o para seleccionar el tipo de
 * usuario a crear. Según la opción seleccionada se redirige a una activity u otra.
 */

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class RegistroSeleccion extends AppCompatActivity {

    Button registroDeportista;
    Button registroEntrenador;
    Button registroVolver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_seleccion);

        //funcionalidad del botón registro deportista; dirigir a la activity para registrar este tipo de usuario.
        registroDeportista = (Button) findViewById(R.id.buttonRegistroDeportista);
        registroDeportista.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegistroDeportista.class);
            startActivity(intent);
        });

        //funcionalidad del botón registro entrenador; dirigir a la activity para registrar este tipo de usuario.
        registroEntrenador = (Button) findViewById(R.id.buttonRegistroEntrenador);
        registroEntrenador.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegistroEntrenador.class);
            startActivity(intent);
        });

        //funcionalidad del botón para volver a la activity anterior
        registroVolver = (Button) findViewById(R.id.buttonRegistroVolver);
        registroVolver.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });
    }

    // Método para mostrar y ocultar el menú
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                mostrarAyuda(findViewById(R.id.action_help));
                return true;
            case R.id.action_salir:
                cerrarApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * mostrarAyuda. Carga la activity ayuda para leer el archivo .html generado con Javadoc con explicaciones sobre los métodos y clases de la app.
     *
     * @param view
     */
    public void mostrarAyuda(View view) {
        Intent intent = new Intent(this, Ayuda.class);
        startActivity(intent);
    }

    /**
     * Este método sirve para pedir la confirmación del usuario antes de salir de la app. Muestra un dialog con dos botones.
     */
    public void cerrarApp() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Salir");
        alertDialogBuilder
                .setMessage("¿Quieres salir de la aplicación?")
                .setCancelable(false)
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}