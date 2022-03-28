package com.example.fitnessapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;

/**
 * MainActivity. Esta clase representa la activity principal de la app. Es la primera pantalla que se carga al abrirla.
 */
public class MainActivity extends AppCompatActivity {

    Button deportista;
    Button registro;
    Button entrenador;

    /**
     * definir accion al pulsar el botón atrás; pedir confirmación para salir de la app.
     */
    @Override
    public void onBackPressed() {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //funcionalidad de los botones

        //registrarse
        registro = (Button) findViewById(R.id.buttonRegistro);
        registro.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegistroSeleccion.class);
            startActivity(intent);
        });

        //acceso deportistas
        deportista = (Button) findViewById(R.id.buttonDeportista);
        deportista.setOnClickListener(view -> {
            Intent intent = new Intent(this, AccesoDeportistas.class);
            startActivity(intent);
        });

        //acceso entrenadores
        entrenador = (Button) findViewById(R.id.buttonEntrenador);
        entrenador.setOnClickListener(view -> {
            Intent intent = new Intent(this, AccesoEntrenadores.class);
            startActivity(intent);
        });
    }
}