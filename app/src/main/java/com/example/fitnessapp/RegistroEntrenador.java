package com.example.fitnessapp;

import static androidx.core.os.LocaleListCompat.create;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

/**
 * Esta clase le da funcionalidad a la view registro_entrenado. Inicializa los componentes, obtiene el texto introducido en cada edittext y después comprueba si
 * los datos introducidos son válidos, y no están en blanco. En caso contrario se reemplazan con valores por defecto. Si el registro del usuario ha sido correcto, se mostrará un dialog
 * para informar al usuario después de ejecutar la sentencia insert en la BD.
 */
public class RegistroEntrenador extends AppCompatActivity {

    public static MiBaseDatosHelper baseDatos;
    Button volver;
    Button registrar;
    EditText codigoIntroducido;
    EditText contrasenaIntroducida;
    EditText repetirContrasenaIntroducida;
    EditText especialidadIntroducida;
    EditText centroIntroducido;
    CheckBox condiciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_entrenador);

        codigoIntroducido = (EditText) findViewById(R.id.codigoNuevoEntrenador);
        contrasenaIntroducida = (EditText) findViewById(R.id.contrasenaNuevoEntrenador);
        repetirContrasenaIntroducida = (EditText) findViewById(R.id.repetirContrasenaNuevoEntrenador);
        volver = (Button) findViewById(R.id.button);
        registrar = (Button) findViewById(R.id.buttonRegistro3);
        centroIntroducido = (EditText) findViewById(R.id.centroNuevoEntrenador);
        especialidadIntroducida = (EditText) findViewById(R.id.especialidadNuevoEntrenador);
        condiciones = (CheckBox) findViewById(R.id.checkBox2);

        //crear objeto de la clase auxiliar para BD
        baseDatos = new MiBaseDatosHelper(this, "ifit", null, 1);


        //funcionalidad del botón volver, redirigir a la página principal
        volver.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        //funcionalidad del botón registrar; comprobar los datos y llamar al método que ejecuta la sentencia insert.
        registrar.setOnClickListener(view -> {

            //obtener el texto introducido para el usuario y la contraseña y guardarlo en Strings
            String codigo = codigoIntroducido.getText().toString();
            String contrasena = contrasenaIntroducida.getText().toString();
            String repetirContrasena = repetirContrasenaIntroducida.getText().toString();
            String centro = centroIntroducido.getText().toString();
            String especialidad = especialidadIntroducida.getText().toString();

            //comprobar que se han aceptado las condiciones
            if (!condiciones.isChecked()) {
                Toast.makeText(getApplicationContext(), "Por favor, acepta las condiciones", Toast.LENGTH_SHORT).show();
            }
            if (condiciones.isChecked()) {
                //comprobar que los strings tienen contenido y las contraseñas coinciden
                if (!contrasena.isEmpty() && !codigo.isEmpty() && !repetirContrasena.isEmpty()) {
                    //si las contraseñas no coinciden, mostrar error
                    if (!repetirContrasena.equals(contrasena)) {
                        Toast.makeText(this, "Las contraseñas introducidas no coinciden", Toast.LENGTH_SHORT).show();
                    } else { //si las contraseñas coinciden
                        //consultar la BD para comprobar si el usuario ya existe
                        if (!baseDatos.comprobarEntrenador(codigo)) {
                            //si el usuario no está en la BD

                            //comprobar si los campos opcionales tienen contenido y si el contenido es válido. En caso negativo, se reemplazan por "N/A".

                            //especialidad
                            if (especialidad.isEmpty()) {
                                //comprobar si está vacía, poner "N/A"
                                especialidad = "N/A";
                            }

                            //centro
                            if (centro.isEmpty()) {
                                centro = "N/A";
                            }

                            //crear usuario en la BD
                            baseDatos.crearEntrenador(codigo, contrasena, especialidad, centro);
                            //si no se produce error, mostrar dialog y pasar a la pagina login
                            createNewUser().show();
                        }
                        // si el usuario ya existe, mostrar error
                        else {
                            Toast.makeText(getApplicationContext(), "Ya hay un usuario con ese nombre", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "Por favor, completa todos los campos requeridos", Toast.LENGTH_SHORT).show();
                }
            }
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
            case R.id.action_exit:
                salir(findViewById(R.id.action_exit));
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
        Intent intent = new Intent (this, Ayuda.class);
        startActivity(intent);
    }

    /**
     * Este método pide la confirmación del usuario antes de cerrar la app.
     */
    public void salir(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Salir");
        alertDialogBuilder
                .setMessage("¿Quieres salir de la aplicación? El usuario no se guardará.")
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
     * Es dialog informa de que el usuario Entrenador se ha registrado correctamente
     * @return
     */

    public Dialog createNewUser() {
        //Creamos el objeto AlertDialog.Builder
        AlertDialog.Builder builder =
                new AlertDialog.Builder(this)
                        // Asignamos las propiedades que se mostrarán.
                        .setTitle("¡Conseguido!")
                        .setMessage("El usuario se ha creado. Ahora ya puedes iniciar sesión y empezar a entrenar")
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        Intent intent = new Intent(getApplicationContext(), AccesoEntrenadores.class);
                                        startActivity(intent);
                                    }
                                });

        return builder.create();
    }


}