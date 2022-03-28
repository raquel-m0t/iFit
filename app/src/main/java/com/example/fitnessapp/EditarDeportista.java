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

/**
 * Esta clase representa a una activity que permite asignar la vista editar_deportista y darle funcionalidad a sus componentes.
 * Muestra los datos actuales del usuario y permite modificarlos (a excepción del nombre del usuario). Para guardar cualquier cambio, es necesario repetir la contraseña.
 * Antes de aplicar los cambios, se comprueba que estos son válidos, con los mismos requisitos que para registrarlos por primera vez:
 * La estatura debe estar comprendida entre 0.70 y 2.30 metros
 * La edad debe estar entre 1 y 100
 * El peso debe estar entre 1 y 200
 *
 */
public class EditarDeportista extends AppCompatActivity {

    Button aplicar;
    Button volver;
    Deportista deportista;
    TextView nombreActual;
    EditText edadActual;
    EditText pesoActual;
    EditText contrasenaActual;
    EditText contrasenaRepetir;
    EditText estaturaActual;
    String edadAnterior;
    String contrasenaAnterior;
    String estaturaAnterior;
    String pesoAnterior;
    String idDeportista;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_deportista);

        idDeportista = AccesoDeportistas.baseDatos.obtenerIdDeportista(AccesoDeportistas.nombreUsuario);

        //mostrar contenidos en los elementos de la activity con los datos del deportista
        iniciarElementos();

        //funcionalidad del botón volver, redirigir a la página del perfil
        volver = (Button) findViewById(R.id.volverEditarDeportista);
        volver.setOnClickListener(view -> {
            //comprobar si hay modificaciones no guardadas antes de cerrar. Si hay, pedir confirmación antes de descartarlos.
            if (hayCambios() == true) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("¿Estás segur@?");
                alertDialogBuilder
                        .setMessage("Perderás los datos que has introducido y los cambios no se registrarán")
                        .setCancelable(false)
                        //descartar cambios
                        .setPositiveButton("Vale",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(getApplicationContext(), PerfilDeportista.class);
                                        startActivity(intent);
                                    }
                                })
                        //no descartar; cerrar dialog
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            } else {
                //si no hay cambios, no mostrar mensaje y redirigir directamente a los datos del perfil
                Intent intent = new Intent(this, PerfilDeportista.class);
                startActivity(intent);
            }
        });


        //funcionalidad del botón aplicar, guardar los cambios y mostrar mensaje Toast, luego redirigir a la ventana anterior
        aplicar = (Button) findViewById(R.id.guardarEditarDeportista);
        aplicar.setOnClickListener(view -> {

            //si no hay cambios, mostrar mensaje
            if (hayCambios()) {
                //ejecutar sentencia SQL
                if (guardarCambios()) {
                    //mostrar mensaje
                    Toast.makeText(this, "Cambios aplicados correctamente.", Toast.LENGTH_SHORT).show();
                    //redirigir a detalles del perfil
                    Intent intent = new Intent(getApplicationContext(), PerfilDeportista.class);
                    startActivity(intent);
                }
                //si no se ha cambiado nada, mostrar mensaje
            } else {
                Toast.makeText(this, "No hay cambios que guardar.", Toast.LENGTH_SHORT).show();
            }
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
     * mostrarAyuda. Carga la activity ayuda para leer el archivo .html generado con Javadoc con explicaciones sobre los métodos y clases de la app.
     *
     * @param view
     */
    public void mostrarAyuda(View view) {
        Intent intent = new Intent(this, Ayuda.class);
        startActivity(intent);
    }

    /**
     * Este método pide confirmación del usuario antes de cerrar la app.
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

    /**
     * Este método permite establecer los valores de los editText con los obtenidos de la BD.
     */
    public void iniciarElementos() {
        //obtener datos de BD, si se ha sobreescrito el valor de id
        if (!idDeportista.isEmpty()) {
            deportista = AccesoDeportistas.baseDatos.consultarDatosDeportista(idDeportista);

            //asignar datos a elementos
            nombreActual = (TextView) findViewById(R.id.textView640);
            nombreActual.setText(deportista.getNombre());

            contrasenaActual = (EditText) findViewById(R.id.EditarContrasenaIntroducida);
            contrasenaActual.setText(deportista.getContrasena());
            contrasenaAnterior = deportista.getContrasena();

            contrasenaRepetir = (EditText) findViewById(R.id.contrasenaRepetirDeportistaEditar);

            edadActual = (EditText) findViewById(R.id.edadEditar);
            edadActual.setText(deportista.getEdad());
            edadAnterior = deportista.getEdad();

            estaturaActual = (EditText) findViewById(R.id.estaturaDeportistaEditar);
            estaturaActual.setText(deportista.getEstatura());
            estaturaAnterior = deportista.getEstatura();

            pesoActual = (EditText) findViewById(R.id.pesoDeportistaEditar);
            pesoActual.setText(deportista.getPeso());
            pesoAnterior = deportista.getPeso();


        } else {
            Log.d("Error", "al obtener el id del deportista para mostrar la información");
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
        if (contrasenaRepetir.getText().toString().equals("") && contrasenaActual.getText().toString().equals(contrasenaAnterior) && edadActual.getText().toString().equals(edadAnterior) && estaturaActual.getText().toString().equals(estaturaAnterior) && pesoActual.getText().toString().equals(pesoAnterior)) {
            cambios = false;
        } else { //si se han cambiado cosas
            cambios = true;
        }
        return cambios;
    }

    /**
     * Este método busca qué campos se han cambiado y modifica esos datos, en la BD, ejecutando la sentencia, siempre que esos campos cumplan con los requisitos.
     * Siempre es necesario repetir la contraseña (aun si no se cambia este campo) para aplicar el resto de modificaciones.
     *
     * @return true si no se han producido errores y los cambios se aplican. False si no se aplican.
     */
    public boolean guardarCambios() {
        boolean error = false; //activar si se produce un error en algúno de los campos

        //comprobar que las constraseñas coinciden (si el campo repetir contraseña tiene contenido, sino, ignorar el cambio de contraseña):
        if (!contrasenaRepetir.getText().toString().equals(contrasenaActual.getText().toString())) {
            Toast.makeText(this, "Las contraseñas introducidas no coinciden.", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            //si las contraseñas coinciden, aplicar
            deportista.setContrasena(contrasenaActual.getText().toString());
        }
        //comprobar campos opcionales:

        //si se ha cambiado la edad,
        if (!edadActual.getText().equals(edadAnterior)) {
            //comprobar que el campo edad es válido
            if (!edadActual.getText().toString().isEmpty()) {
                try {
                    int edadUsuario = Integer.valueOf(edadActual.getText().toString());
                    //si no está entre 1 y 100
                    if (edadUsuario > 100 || edadUsuario <= 0) {
                        Toast.makeText(this, "La edad para usar esta app debe estar entre 1 y 100 años", Toast.LENGTH_SHORT).show();
                        //resetear edittext
                        edadActual.setText("");
                        return false;

                    } else {
                        //si no está vacío ni fuera de rangos, aplicar.
                        deportista.setEdad(edadActual.getText().toString());
                    }
                } catch (Exception e) {
                    //si la edad no es válida, no se añade.
                    Log.d("error", "edad no admitida");
                    return false;
                }
            } else { //si está vacío, poner 0 (reduce errores null en consultas)
                deportista.setEdad("0");
            }

        }

        //si se ha cambiado la estatura,
        if (!estaturaActual.getText().equals(estaturaAnterior)) {
            //comprobar que el campo estatura es válido (si hay datos)
            if (!estaturaActual.getText().toString().isEmpty()) {
                try {
                    float estaturaUsuario = Float.valueOf(estaturaActual.getText().toString());
                    //si es mayor de 2.30 o menor de 0.70, mostrar mensaje
                    if (estaturaUsuario >= 2.30 || estaturaUsuario < 0.70) {
                        Toast.makeText(this, "La estatura debe estar entre 0.70 y 2.30 metros", Toast.LENGTH_SHORT).show();
                        //resetear edittext
                        estaturaActual.setText("");
                        return false;
                    } else {
                        //si no está vacío ni fuera de rangos, aplicar.
                        deportista.setEstatura(estaturaActual.getText().toString());
                    }
                } catch (Exception e) {
                    //si la estatura no es válida, mostrar error.
                    Log.d("error", "eror al obtener estatura");
                    Toast.makeText(this, "Error al obtener la estatura, inténtalo de nuevo", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else { //si está vacío, poner 0 (reduce errores null en consultas)
                deportista.setEstatura("0");
            }
        }

        //si se ha cambiado el peso,
        if (!pesoActual.getText().equals(pesoAnterior)) {
            //comprobar que el campo peso es válido (si contiene datos)
            if (!pesoActual.getText().toString().isEmpty()) {
                try {
                    float pesoUsuario = Float.valueOf(pesoActual.getText().toString());
                    //si no está entre 1 y 200
                    if (pesoUsuario > 200 || pesoUsuario < 1) {
                        Toast.makeText(this, "El peso debe ser mayor a 1KG y no puede superar los 200KG", Toast.LENGTH_SHORT).show();
                        //resetear editText
                        pesoActual.setText("");
                        return false;
                    } else {
                        //si no está vacío ni fuera de rangos, aplicar.
                        deportista.setPeso(pesoActual.getText().toString());
                    }
                } catch (Exception e) {
                    //si el peso no es válido, no se añade.
                    Log.d("error", "peso no válido");
                    Toast.makeText(this, "Error al obtener el peso, inténtalo de nuevo", Toast.LENGTH_SHORT).show();
                    return false;
                }
            } else { //si está vacío, poner 0 (reduce errores null en consultas)
                deportista.setPeso("0");
            }
        }

        //si no se han encontrado errores, registrar cambios en la BD y mostrar mensaje
        if (!error) {
            //crear usuario en la BD
            AccesoDeportistas.baseDatos.actualizarDeportista(deportista, idDeportista);
        }
        return true;
    }
}

