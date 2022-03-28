package com.example.fitnessapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * Esta clase representa a una activity que inicializa los componentes de la vista activity_editar_entrenador y les da funcionalidad.
 * Muestra los datos actuales del usuario entrenador y permite editarlos (a excepción del código de usuario). Para aplicar cualquier cambio, es necesario confirmar la contraseña.
 */
public class EditarEntrenador extends AppCompatActivity {

    Button aplicar;
    Button volver;
    Entrenador entrenador;
    TextView codigoActual;
    TextView registro;
    EditText centroActual;
    EditText especialidadActual;
    EditText contrasenaActual;
    EditText contrasenaRepetir;
    String centroAnterior;
    String contrasenaAnterior;
    String especialidadAnterior;
    String idEntrenador;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_entrenador);

        //guardar el id del entrenador en un string para simplificar consultas.
        idEntrenador = AccesoEntrenadores.baseDatos.obtenerIdEntrenador(AccesoEntrenadores.codigoEntrenador);

        //mostrar contenidos en los elementos de la activity con los datos del deportista
        iniciarElementos();

        //funcionalidad del botón volver, redirigir a la página del perfil
        volver = (Button) findViewById(R.id.volverEditarEntrenador);
        volver.setOnClickListener(view -> {
            if (hayCambios() == true) {
                //pedir confirmación al salir sin guardar el usuario
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("¿Estás segur@?");
                alertDialogBuilder
                        .setMessage("Perderás los datos que has introducido y los cambios no se registrarán")
                        .setCancelable(false)
                        .setPositiveButton("Vale",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(getApplicationContext(), PerfilEntrenador.class);
                                        startActivity(intent);
                                    }
                                })

                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                //si no hay cambios, volver sin mensaje
                Intent intent = new Intent(this, PerfilEntrenador.class);
                startActivity(intent);
            }
        });

        //funcionalidad del botón aplicar, guardar los cambios y mostrar mensaje Toast, luego redirigir a la ventana anterior
        aplicar = (Button) findViewById(R.id.guardarEditar);
        aplicar.setOnClickListener(view -> {

            //si no hay cambios, mostrar mensaje
            if (hayCambios()) {
                //ejecutar sentencia SQL
                if (guardarCambios()) {
                    //mostrar mensaje
                    Toast.makeText(this, "Cambios aplicados correctamente.", Toast.LENGTH_SHORT).show();
                    //redirigir a detalles del perfil
                    Intent intent = new Intent(getApplicationContext(), PerfilEntrenador.class);
                    startActivity(intent);
                }
                //si no se ha cambiado nada, mostrar mensaje
            } else {
                Toast.makeText(this, "No hay cambios que guardar.", Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * establecer valor los editText con los obtenidos de la BD y darles funcionalidad.
     */
    public void iniciarElementos() {
        //obtener datos de BD, si se ha sobreescrito el valor de id
        if (!idEntrenador.isEmpty()) {
            entrenador = AccesoEntrenadores.baseDatos.consultarDatosEntrenador(idEntrenador);

            //asignar datos a elementos
            codigoActual = (TextView) findViewById(R.id.textView641);
            codigoActual.setText(entrenador.getCodigo());

            contrasenaActual = (EditText) findViewById(R.id.EditarContrasenaIntroducida);
            contrasenaActual.setText(entrenador.getContrasena());
            contrasenaAnterior = entrenador.getContrasena();

            contrasenaRepetir = (EditText) findViewById(R.id.contrasenaRepetirEntrenadorEditar);

            centroActual = (EditText) findViewById(R.id.centroEditar);
            centroActual.setText(entrenador.getCentro());
            centroAnterior = entrenador.getCentro();

            especialidadActual = (EditText) findViewById(R.id.especialidadEditar);
            especialidadActual.setText(entrenador.getEspecialidad());
            especialidadAnterior = entrenador.getEspecialidad();

            registro = (TextView) findViewById(R.id.textView22);
            registro.setText(entrenador.getRegistro());

        } else {
            Log.d("Error", "al obtener el id del entrenador para mostrar la información");
        }

    }

    /**
     * Este método comprueba si se han cambiado los datos del usuario. Comprueba los datos de los editText con los que estaban almacenados.
     *
     * @return true si se ha cambiado algún campo. False si todo sigue igual
     */
    public boolean hayCambios() {
        boolean cambios;
        //si no se ha cambiado nada, mostrar dialog
        if (contrasenaRepetir.getText().toString().equals("") && contrasenaActual.getText().toString().equals(contrasenaAnterior) && especialidadActual.getText().toString().equals(especialidadAnterior) && centroActual.getText().toString().equals(centroAnterior)) {
            cambios = false;
        } else { //si se han cambiado cosas
            cambios = true;
        }
        return cambios;
    }

    /**
     * Este método busca qué campos se han cambiado y modifica esos datos, en la BD, ejecutando la sentencia.
     *
     * @return true si no se han producido errores. Los cambios se aplican. False si no se aplican.
     */
    public boolean guardarCambios() {
        //comprobar que las constraseñas coinciden (si el campo repetir contraseña tiene contenido, sino, ignorar el cambio de contraseña):
        if (!contrasenaRepetir.getText().toString().equals(contrasenaActual.getText().toString())) {
            Toast.makeText(this, "Las contraseñas introducidas no coinciden.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            //si las contraseñas coinciden, aplicar
            entrenador.setContrasena(contrasenaActual.getText().toString());
        }
        //si los campos opcionales se han cambiado
        if (!especialidadActual.getText().equals(especialidadAnterior)) {
            //si están vacíos, poner n/A, si tienen contenido, aplicar:

            //especialidad
            if (especialidadActual.getText().toString().isEmpty()) {
                //comprobar si está vacía, poner "N/A"
                entrenador.setEspecialidad("N/A");
            }else {
                entrenador.setEspecialidad(especialidadActual.getText().toString());
            }
        }

        if (!centroActual.getText().equals(centroAnterior)) {
            //centro
            if (centroActual.getText().toString().isEmpty()) {
                //comprobar si está vacía, poner "N/A"
                entrenador.setCentro("N/A");
            }else{
                entrenador.setCentro(centroActual.getText().toString());
            }
        }

        AccesoEntrenadores.baseDatos.actualizarEntrenador(entrenador, idEntrenador);
        return true;

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
                .setMessage("¿Quieres salir de la aplicación? Perderás los cambios de esta pantalla")
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