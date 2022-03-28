/**
 * Clase EditarEntrenado
 */
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
 * Esta clase representa a la activity editar_entrenado. Permite al usuario entrenador modificar detalles del perfil del entrenado. Esta clase le da funcionalidad a los
 * componentes de la view y antes de ejecutar la sentencia Update en la BD, comprueba que los datos son válidos; en caso contrario, mostrará mensajes de error para que dichos errores
 * sean corregidos.
 * La estatura debe estar comprendida entre 0.70 y 2.30 metros
 * La edad debe estar entre 1 y 100
 */
public class EditarEntrenado extends AppCompatActivity {

    Button aplicar;
    Button volver;
    TextView nombreActual;
    EditText edadActual;
    EditText objetivoActual;
    EditText estaturaActual;
    Entrenado entrenado;
    String edadAnterior;
    String objetivoAnterior;
    String estaturaAnterior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_entrenado);

        //mostrar contenidos en los elementos de la activity con los datos del entrenado
        iniciarElementosEntrenado();

        //funcionalidad del botón volver, redirigir a la página de perfiles de entrenados
        volver = (Button) findViewById(R.id.volverPerfilEntrenado);
        volver.setOnClickListener(view -> {
            accionVolver();
        });

        //funcionalidad del botón aplicar, guardar los cambios y mostrar mensaje Toast, luego redirigir a la ventana anterior
        aplicar = (Button) findViewById(R.id.guardarEditarEntrenado);
        aplicar.setOnClickListener(view -> {
            //ejecutar sentencia SQL
            if (guardarCambios() == true) {
                //mostrar mensaje
                Toast.makeText(this, "Cambios aplicados correctamente.", Toast.LENGTH_SHORT).show();
                //redirigir a detalles del perfil
                Intent intent = new Intent(getApplicationContext(), DetallesPerfilEntrenado.class);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Error al aplicar los cambios.", Toast.LENGTH_SHORT).show();
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
     * establecer valor los editText con los obtenidos de la BD. Se inicializan strings con los valores antiguos para modificarlos solo en el caso de que el usuario haya cambiado los datos, luego pasarlos al método de la consulta SQL
     */
    public void iniciarElementosEntrenado() {

        String idEntrenado = AdapterEntrenados.idEntrenado;
        //obtener datos de BD
        entrenado = AccesoEntrenadores.baseDatos.consultarDatosEntrenado(idEntrenado);

        //asignar datos a elementos
        nombreActual = (TextView) findViewById(R.id.nombreEntrenadoMostrar);
        nombreActual.setText(entrenado.getNombre());

        estaturaActual = (EditText) findViewById(R.id.estaturaEntrenadoEditar);
        estaturaActual.setText(entrenado.getEstatura());
        estaturaAnterior = entrenado.getEstatura();

        edadActual = (EditText) findViewById(R.id.edadEntrenadoEditar);
        edadActual.setText(entrenado.getEdad());
        edadAnterior = entrenado.getEdad();

        objetivoActual = (EditText) findViewById(R.id.objetivoEntrenadoEditar);
        objetivoActual.setText(entrenado.getObjetivo());
        objetivoAnterior = entrenado.getObjetivo();

    }

    /**
     * Método para comprobar qué campos han cambiado y ejecutar la sentencia SQL para aplicar dichos cambios.
     *
     * @return true si se ha guardado sin errores
     */
    public boolean guardarCambios() {
        boolean error = false;
        boolean guardar = false;

        //comprobar si los editText han cambiado

        //si no se ha cambiado nada, mostrar mensaje breve
        if (estaturaActual.getText().equals(estaturaAnterior) && edadActual.getText().equals(edadAnterior) && objetivoActual.getText().equals(objetivoAnterior)) {
            Toast.makeText(this, "No hay ningún cambio que aplicar.", Toast.LENGTH_LONG).show();
        } else { //si se han cambiado cosas

            //si la edad se ha modificado:
            if (!edadActual.getText().equals(edadAnterior)) {
                //comprobar que la edad es válida
                if (!edadActual.getText().toString().isEmpty()) {
                    try {
                        int edadUsuario = Integer.valueOf(edadActual.getText().toString());
                        //si no está entre 1 y 100
                        if (edadUsuario > 100 || edadUsuario <= 0) {
                            Toast.makeText(this, "La edad para usar esta app debe estar entre 1 y 100 años", Toast.LENGTH_SHORT).show();
                            //resetear edittext
                            edadActual.setText("");
                            error = true;
                        } else {
                            //si no está vacío ni fuera de rangos, aplicar.
                            entrenado.setEdad(edadActual.getText().toString());
                        }
                    } catch (Exception e) {
                        //si la edad no es válida, no se añade.
                        Log.d("error", "edad no admitida");
                        error = true;
                    }
                } else { //si está vacío, poner 0 (reduce errores null en consultas)
                    entrenado.setEdad("0");
                }
            }

            //si la estatura se ha modificado:
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
                            error = true;
                        } else {
                            //si no está vacío ni fuera de rangos, aplicar.
                            entrenado.setEstatura(estaturaActual.getText().toString());
                        }
                    } catch (Exception e) {
                        //si la estatura no es válida, mostrar error.
                        Log.d("error", "eror al obtener estatura");
                        Toast.makeText(this, "Error al obtener la estatura, inténtalo de nuevo", Toast.LENGTH_SHORT).show();
                        error = true;
                    }
                } else { //si está vacío, poner 0 (reduce errores null en consultas)
                    entrenado.setEstatura("0");
                }
            }

            //si el objetivo se ha modificado:
            if (!objetivoActual.getText().equals(objetivoAnterior)) {
                if (objetivoActual.getText().toString().isEmpty()) {
                    entrenado.setObjetivo("N/A");
                } else {
                    entrenado.setObjetivo(objetivoActual.getText().toString());
                }
            }
            //si hay cambios y ningún error, aplicar todos los cambios
            AccesoEntrenadores.baseDatos.actualizarEntrenado(entrenado, AdapterEntrenados.idEntrenado);
        }
        //si no hay errores, guardar = true para finalizar
        if (!error){
            guardar = true;
        }
        return guardar;
    }

        /**
         * Método para comprobar si alguno de los campos ha cambiado
         * @return true si se ha cambiado algo; false si no se ha cambiado.
         */
        public boolean hayCambios () {
            boolean cambios;
            //si no se ha cambiado nada, mostrar dialog
            if (edadActual.getText().toString().equals(edadAnterior) && estaturaActual.getText().toString().equals(estaturaAnterior) && objetivoActual.getText().toString().equals(objetivoAnterior)) {
                cambios = false;
            } else { //si se han cambiado cosas
                cambios = true;
            }
            return cambios;
        }

        /**
         * Método para definir la funcionalidad del botón volver. Pide confirmación del usuario en caso de que haya cambios sin aplicar.
         */
        public void accionVolver () {
            //revisar si hay cambios:
            if (hayCambios()) { //si se han cambiado cosas, pedir confirmación
                //pedir confirmación al salir sin guardar el usuario
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setTitle("¿Estás segur@?");
                alertDialogBuilder
                        .setMessage("Perderás los datos que has introducido y los cambios no se registrarán")
                        .setCancelable(false)
                        .setPositiveButton("Vale",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(getApplicationContext(), PerfilesEntrenados.class);
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
            } else { //si no se ha cambiado nada, volver sin más.
                Intent intent = new Intent(getApplicationContext(), PerfilesEntrenados.class);
                startActivity(intent);
            }
        }
    }