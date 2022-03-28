package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Clase AccesoDeportistas. Página de login para usuarios deportistas. Permite que un usuario entre en la aplicación utilizando su nombre de usuario y contraseña.
 * Al pulsar en Entrar, se realiza una consulta a la BD para comprobar si los datos existen y son correctos.
 * Si el usuario no existe, o si la contraseña es incorrecta, se mostrará un mensaje de error.
 */
public class AccesoDeportistas extends AppCompatActivity {

    public static MiBaseDatosHelper baseDatos;
    public static String nombreUsuario; //guardar el nombre de usuario para poder usarlo más adelante
    Button volver;
    Button entrar;
    EditText nombreIntroducido;
    EditText contrasenaIntroducida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_deportistas);

        //inicializar los editText para permitir la entrada de datos
        nombreIntroducido = (EditText) findViewById(R.id.nombreUsuario);
        contrasenaIntroducida = (EditText) findViewById(R.id.contrasena);

        //crear objeto de la clase auxiliar para BD
        baseDatos = new MiBaseDatosHelper(this, "ifit", null, 1);

        //funcionalidad del botón volver, rediriguir a la página principal
        volver = (Button) findViewById(R.id.buttonVolver);
        volver.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        //funcionalidad del botón entrar, para comprobar el login
        entrar = (Button) findViewById(R.id.buttonEntrar);
        entrar.setOnClickListener(view -> {

            //obtener el texto introducido para el usuario y la contraseña y guardarlo en Strings
            String usuario = nombreIntroducido.getText().toString();
            String contrasena = contrasenaIntroducida.getText().toString();

            //comprobar que los strings tienen contenido. Si no tienen contenido, mostrar mensaje corto.
            if (contrasena.isEmpty() && usuario.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce tus datos de usuario", Toast.LENGTH_SHORT).show();
            }
            //si se han introducido datos:
            else {
                //consultar la BD para comprobar si el usuario existe
                if (baseDatos.loginCorrectoUsuario(usuario, contrasena)) {
                    Intent intent = new Intent(this, PerfilDeportista.class);
                    Toast.makeText(getApplicationContext(), "Login correcto", Toast.LENGTH_LONG).show(); //mostrar mensaje de login OK.
                    intent.putExtra("usuario", usuario); //guardar el nombre de usuario para la siguiente pantalla.
                    nombreUsuario = usuario; //si el usuario es correcto, añadir el valor a la variable.
                    //como la pantalla ejercicios es compartida con la interfaz de entrenador, esto permitirá diferenciar los roles de usuario.
                    //intent.putExtra("tipo de usuario", "deportista");
                    startActivity(intent);
                }
                //si el usuario no está en la BD o la contraseña es incorrecta
                else {
                    Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(this, Ayuda.class);
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