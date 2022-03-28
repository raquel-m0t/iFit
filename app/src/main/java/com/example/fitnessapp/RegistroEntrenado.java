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
import android.widget.Toast;

/**
 * Esta clase da funcionalidad a la view registro_entrenado y permite añadir a la BD a un usuario de tipo Entrenado. Antes de ejecutar la sentencia insert, en esta actívity se comprueba que
 * los datos introducidos sean válidos.
 */
public class RegistroEntrenado extends AppCompatActivity {

    Button volver;
    Button guardar;
    EditText nombreIntroducido;
    EditText estaturaIntroducida;
    EditText objetivoIntroducido;
    EditText edadIntroducida;
    Entrenado entrenado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entrenado);

        //iniciar edittexts de la view
        nombreIntroducido = (EditText) findViewById(R.id.nombreEntrenadoEditar);
        estaturaIntroducida = (EditText) findViewById(R.id.estaturaEntrenadoEditar);
        edadIntroducida = (EditText) findViewById(R.id.edadEntrenadoEditar);
        objetivoIntroducido = (EditText) findViewById(R.id.objetivoEntrenadoEditar);


        //crear objeto de la clase auxiliar para BD
        AccesoDeportistas.baseDatos = new MiBaseDatosHelper(this, "ifit", null, 1);

        //funcionalidad del botón volver, redirigir a la página de perfiles de entrenados
        volver = (Button) findViewById(R.id.volverEditarEntrenado);
        volver.setOnClickListener(view -> {

            //pedir confirmación al salir sin guardar el usuario
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setTitle("¿Estás segur@?");
            alertDialogBuilder
                    .setMessage("Perderás los datos que has introducido y el usuario no se registrará")
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

        });

        //funcionalidad del botón guardar. comprobar los datos y ejecutar la sentencia insert
        guardar = (Button) findViewById(R.id.guardarEditarEntrenado);
        guardar.setOnClickListener(view -> {
            boolean error = false;

            //obtener el texto introducido para cada campo y guardarlo en Strings
            String nombre = nombreIntroducido.getText().toString();
            String estatura = estaturaIntroducida.getText().toString();
            String edad = edadIntroducida.getText().toString();
            String objetivo = objetivoIntroducido.getText().toString();

            //comprobar si los campos opcionales tienen contenido y si el contenido es válido.

            //comprobar que el campo estatura es válido (si hay datos)
            if (!estatura.isEmpty()) {
                try {
                    float estaturaUsuario = Float.valueOf(estatura);
                    //si es mayor de 2.30 o menor de 0.70, mostrar mensaje
                    if (estaturaUsuario >= 2.30 || estaturaUsuario < 0.70) {
                        Toast.makeText(this, "La estatura debe estar entre 0.70 y 2.30 metros", Toast.LENGTH_SHORT).show();
                        //resetear edittext
                        estaturaIntroducida.setText("");
                        error = true;
                    }
                } catch (Exception e) {
                    //si la estatura no es válida, mostrar error.
                    Log.d("error", "error al obtener estatura");
                    error = true;
                    Toast.makeText(this, "Error al obtener la estatura, inténtalo de nuevo", Toast.LENGTH_SHORT).show();
                }
            } else { //si está vacío, poner 0 (reduce errores null en consultas)
                estatura = "0";
            }

            //comprobar que el campo edad es válido
            if (!edad.isEmpty()) {
                try {
                    int edadUsuario = Integer.valueOf(edad);
                    //si no está entre 1 y 100
                    if (edadUsuario > 100 || edadUsuario <= 0) {
                        Toast.makeText(this, "La edad para usar esta app debe estar entre 1 y 100 años", Toast.LENGTH_SHORT).show();
                        edadIntroducida.setText("");
                        error = true;
                    }
                } catch (Exception e) {
                    //si la edad no es válida, no se añade.
                    Log.d("error", "edad no admitida");
                }
            } else { //si está vacío, poner 0 (reduce errores null en consultas)
                edad = "0";
            }

            //comprobar que el campo objetivo no está vacío, si lo está, poner N/A.
            if (objetivo != null && objetivo.isEmpty()) {
                objetivo = "N/A";
            }

            //comprobar que el nombre del usuario no está vacío
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce al menos el nombre del entrenado", Toast.LENGTH_SHORT).show();
                error = true;
            } else {
                //consultar la BD para comprobar si el usuario entrenado ya existe
                if (!AccesoEntrenadores.baseDatos.comprobarEntrenado(nombre)) {
                    //si el usuario no está en la BD
                    //convertir id del entrenador a Int, dentro de try-catch para controlar posibles errores
                    String idEnt = AccesoEntrenadores.codigoEntrenador;
                    int id = 0;
                    try {
                        id = Integer.valueOf(idEnt);
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    //definir instancia de Entrenado y establecer valores
                    entrenado = new Entrenado();
                    entrenado.setIdEntrenador(idEnt);
                    entrenado.setNombre(nombre);
                    entrenado.setEdad(edad);
                    entrenado.setEstatura(estatura);
                    entrenado.setObjetivo(objetivo);

                    if (!error) {
                        //crear usuario en la BD
                        AccesoEntrenadores.baseDatos.crearEntrenado(entrenado);

                        //si no se produce error, mostrar dialog y pasar a la pagina login
                        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                        alertDialogBuilder.setTitle("¡Listo!");
                        alertDialogBuilder
                                .setMessage("El usuario se ha creado, ya puedes empezar a monitorizar su actividad")
                                .setCancelable(false)
                                .setPositiveButton("¡Genial!",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                Intent intent = new Intent(getApplicationContext(), PerfilesEntrenados.class);
                                                startActivity(intent);
                                            }
                                        });

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
                // si el usuario ya existe, mostrar error
                else {
                    Toast.makeText(getApplicationContext(), "Ya hay un usuario con ese nombre", Toast.LENGTH_LONG).show();
                    error = true;
                }
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
    public void mostrarAyuda(View view) {
        Intent intent = new Intent(this, Ayuda.class);
        startActivity(intent);
    }

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