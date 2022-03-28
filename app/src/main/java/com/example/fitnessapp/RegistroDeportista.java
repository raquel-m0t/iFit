package com.example.fitnessapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

/**
 * Esta clase da funcionalidad a la view registro_deportista. Permite guardar un deportista a la BD si los datos introducidos son válidos y que el nombre de usuario no se repite.
 * En caso contrario, mostrará mensajes de error correspondientes para que sean subsanados.
 * Los siguientes campos son opcionales, pero si se completan, deben cumplir los siguientes requisitos:
 * La estatura debe estar comprendida entre 0.70 y 2.30 metros
 * La edad debe estar entre 1 y 100
 * El peso debe estar entre 1 y 200
 */
public class RegistroDeportista extends AppCompatActivity {

    public static MiBaseDatosHelper baseDatos;
    Button volver;
    Button registrar;
    EditText nombreIntroducido;
    EditText contrasenaIntroducida;
    EditText repetirContrasenaIntroducida;
    EditText pesoIntroducido;
    EditText estaturaIntroducida;
    EditText edadIntroducida;
    CheckBox condiciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_deportista);

        //inicializar elementos
        nombreIntroducido = (EditText) findViewById(R.id.nombreEntrenadoEditar);
        contrasenaIntroducida = (EditText) findViewById(R.id.contrasenaIntroducida);
        repetirContrasenaIntroducida = (EditText) findViewById(R.id.estaturaEntrenadoEditar);
        volver = (Button) findViewById(R.id.volverPerfilEntrenado);
        registrar = (Button) findViewById(R.id.guardarEditarEntrenado);
        pesoIntroducido = (EditText) findViewById(R.id.edadEntrenadoEditar);
        estaturaIntroducida = (EditText) findViewById(R.id.objetivoEntrenadoEditar);
        edadIntroducida = (EditText) findViewById(R.id.edad);
        condiciones = (CheckBox) findViewById(R.id.checkBox);

        //crear objeto de la clase auxiliar para BD
        baseDatos = new MiBaseDatosHelper(this, "ifit", null, 1);

        //funcionalidad del botón volver, redirigir a la página principal
        volver.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);

            startActivity(intent);
        });

        //funcionalidad del botón registrar. Obtiene todos los campos introducidos y los comprueba. Si todo es correcto, guarda los datos en la BD.
        registrar.setOnClickListener(view -> {

            //obtener el texto introducido para el usuario y la contraseña y guardarlo en Strings
            String nombre = nombreIntroducido.getText().toString();
            String contrasena = contrasenaIntroducida.getText().toString();
            String repetirContrasena = repetirContrasenaIntroducida.getText().toString();
            String peso = pesoIntroducido.getText().toString();
            String estatura = estaturaIntroducida.getText().toString();
            String edad = edadIntroducida.getText().toString();

            //comprobar que se han aceptado las condiciones
            if (!condiciones.isChecked()) {
                Toast.makeText(getApplicationContext(), "Por favor, acepta las condiciones", Toast.LENGTH_SHORT).show();
            }
            if (condiciones.isChecked()) {
                boolean error = false; //para saber si alguno de los campos ha producido error y no mostrar el dialog de usuario creado ni lanzar la sentencia SQL.

                //comprobar que los strings tienen contenido y las contraseñas coinciden
                if (!contrasena.isEmpty() && !nombre.isEmpty() && !repetirContrasena.isEmpty()) {
                    //si las contraseñas no coinciden, mostrar error
                    if (!repetirContrasena.equals(contrasena)) {
                        Toast.makeText(this, "Las contraseñas introducidas no coinciden", Toast.LENGTH_SHORT).show();
                        //resetear editexts contraseñas
                        contrasenaIntroducida.setText("");
                        repetirContrasenaIntroducida.setText("");

                    } else { //si las contraseñas coinciden
                        //consultar la BD para comprobar si el usuario ya existe
                        if (!baseDatos.comprobarDeportista(nombre)) {
                            //si el usuario no está en la BD

                            //comprobar que el campo estatura es válido (si hay datos)
                            if (!estatura.isEmpty()) {
                                try {
                                    float estaturaUsuario = Float.valueOf(estatura);
                                    //si es mayor de 2.30 o menor de 0.70, mostrar mensaje
                                    if (estaturaUsuario >= 2.30 || estaturaUsuario < 0.70) {
                                        Toast.makeText(this, "La estatura debe estar entre 0.70 y 2.30 metros", Toast.LENGTH_SHORT).show();
                                        error = true;
                                        //resetear edittext
                                        estaturaIntroducida.setText("");
                                    }
                                } catch (Exception e) {
                                    //si la estatura no es válida, mostrar error.
                                    Log.d("error", "eror al obtener estatura");
                                    error = true;
                                    Toast.makeText(this, "Error al obtener la estatura, inténtalo de nuevo", Toast.LENGTH_SHORT).show();
                                }
                            } else { //si está vacío, poner 0 (reduce errores null en consultas)
                                estatura = "0";
                            }

                            //comprobar que el campo peso es válido (si contiene datos)
                            if (!peso.isEmpty()) {
                                try {
                                    float pesoUsuario = Float.valueOf(peso);
                                    //si no está entre 1 y 200
                                    if (pesoUsuario > 200 || pesoUsuario <= 0) {
                                        Toast.makeText(this, "El peso debe ser mayor a 1KG y no puede superar los 200KG", Toast.LENGTH_SHORT).show();
                                        //resetear editText
                                        pesoIntroducido.setText("");
                                        error = true;
                                    }
                                } catch (Exception e) {
                                    //si el peso no es válido, no se añade.
                                    Log.d("error", "peso no válido");
                                    error = true;
                                    Toast.makeText(this, "Error al obtener el peso, inténtalo de nuevo", Toast.LENGTH_SHORT).show();
                                }
                            } else { //si está vacío, poner 0 (reduce errores null en consultas)
                                peso = "0";
                            }

                            //comprobar que el campo edad es válido
                            if (!edad.isEmpty()) {
                                try {
                                    int edadUsuario = Integer.valueOf(edad);
                                    //si no está entre 1 y 100
                                    if (edadUsuario > 100 || edadUsuario <= 0) {
                                        Toast.makeText(this, "La edad para usar esta app debe estar entre 1 y 100 años", Toast.LENGTH_SHORT).show();
                                        error = true;
                                        edadIntroducida.setText("");
                                    }
                                } catch (Exception e) {
                                    //si la edad no es válida, no se añade.
                                    Log.d("error", "edad no admitida");
                                    error = true;
                                }
                            } else { //si está vacío, poner 0 (reduce errores null en consultas)
                                edad = "0";
                            }

                            //si no se han encontrado errores, registrar en la BD.
                            if (!error) {
                                //crear usuario en la BD
                                baseDatos.crearDeportista(nombre, contrasena, estatura, peso, edad);

                                //mostrar dialog y pasar a la pagina login
                                alertUsuarioCreado();
                            }

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


    /**
     * Método para mostrar y ocultar el menú
     *
     * @param menu
     * @return
     */
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
                salir(findViewById(R.id.action_salir));
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
    public void salir(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AlertSalir salir = new AlertSalir();
        salir.show(fragmentManager, "tagAlerta");
    }

    /**
     * Si el usuario se ha registrado correctamente, este método mostrará un dialog para informar.
     */
    public void alertUsuarioCreado() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("¡Conseguido!");
        alertDialogBuilder
                .setMessage("El usuario se ha creado. Ahora ya puedes iniciar sesión y empezar a entrenar")
                .setCancelable(false)
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //usuario deportista
                                Intent intent = new Intent(getApplicationContext(), AccesoDeportistas.class);
                                startActivity(intent);
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}





