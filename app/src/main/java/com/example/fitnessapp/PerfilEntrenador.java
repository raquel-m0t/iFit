package com.example.fitnessapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.TreeMap;

/**
 * Clase que representa a una view que muestra los datos del entrenador y permite realizar acciones relacionadas con ese perfil.
 */
public class PerfilEntrenador extends AppCompatActivity {

    //elementos de la view
    Button cerrar;
    Button gestionarDeportistas;
    Button eliminarCuenta;
    Button editarCuenta;
    TextView codigo;
    TextView fecha;
    TextView especialidad;
    TextView centro;
    TextView numeroDeportistas;
    Entrenador datosEntrenador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_entrenador);

        //obtener datos del usuario de la BD para completar los demás datos del perfil
        datosEntrenador = new Entrenador();
        String cEntrenador = AccesoEntrenadores.codigoEntrenador;

        datosEntrenador = AccesoEntrenadores.baseDatos.obtenerDatosEntrenador(cEntrenador);

        //establecer valores a los textview de la activity
        codigo = (TextView) findViewById(R.id.codigo);
        codigo.setText(datosEntrenador.codigo);

        fecha = (TextView) findViewById(R.id.fechaPerfil);
        fecha.setText(datosEntrenador.registro);

        especialidad = (TextView) findViewById(R.id.especialidad);
        especialidad.setText(datosEntrenador.especialidad);

        centro = (TextView) findViewById(R.id.centro);
        centro.setText(datosEntrenador.centro);


        //obtener número de entrenados por id de entrenador ACTUAL
        int cantidad = AccesoEntrenadores.baseDatos.obtenerEntrenados(datosEntrenador.id);
        String cantidadEntrenados = String.valueOf(cantidad);
        numeroDeportistas = (TextView) findViewById(R.id.cantidadDeportistas);
        numeroDeportistas.setText(cantidadEntrenados);


        //funcionalidad del botón cerrar, cargar Main
        cerrar = (Button) findViewById(R.id.cerrarSesion);
        cerrar.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        //funcionalidad del botón gestionarDeportistas, cargar la view Editar_Deportistas
        gestionarDeportistas = (Button) findViewById(R.id.editarDeportistas);
        gestionarDeportistas.setOnClickListener(view -> {
            Intent intent = new Intent(this, PerfilesEntrenados.class);
            startActivity(intent);
        });

        //editar el perfil del entrenador
        editarCuenta = (Button) findViewById(R.id.editarPerfilEntr);
        editarCuenta.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditarEntrenador.class);
            startActivity(intent);
        });

        //eliminar el perfil actual de entrenador
        eliminarCuenta = (Button) findViewById(R.id.editarPerfil);
        eliminarCuenta.setOnClickListener(view -> {
            //pedir confirmación
            FragmentManager fragmentManager = getSupportFragmentManager();
            AlertEliminarCuenta eliminar = new AlertEliminarCuenta();
            eliminar.show(fragmentManager, "tagAlerta");
        });
    }


    // Método para mostrar y ocultar el menú
    public boolean onCreateOptionsMenu(Menu menu3) {
        getMenuInflater().inflate(R.menu.menu3, menu3);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                mostrarAyuda(findViewById(R.id.action_help));
                return true;
            case R.id.action_exit:
                cerrarApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //opciones del menú

    /**
     * Carga la activity ayuda para leer el archivo .html generado con Javadoc con explicaciones sobre los métodos y clases de la app.
     *
     * @param view
     */
    public void mostrarAyuda(View view) {
        Intent intent = new Intent(this, Ayuda.class);
        startActivity(intent);
    }

    /**
     * muestra un dialog al usuario solicitando confirmación antes de cerrar la app.
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