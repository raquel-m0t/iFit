package com.example.fitnessapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Clase AccesoEntrenadores. Página de login para usuarios entrenadores. Permite que un usuario entre en la aplicación utilizando su codigo de usuario y contraseña.
 * Al pulsar en Entrar, se realiza una consulta a la BD para comprobar si los datos existen y son correctos.
 * Si el usuario no existe, o si la contraseña es incorrecta, se mostrará un mensaje de error.
 */
public class AccesoEntrenadores extends AppCompatActivity {

    public static MiBaseDatosHelper baseDatos;
    public static String codigoEntrenador; //guardar el codigo de usuario para poder usarlo más adelante
    Button volver;
    Button entrar;
    EditText codigoIntroducido;
    EditText contrasenaIntroducida;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso_entrenadores);

        //inicializar los editText para el código y la contraseña (entrada de datos)
        codigoIntroducido = (EditText) findViewById(R.id.codigoEntrenador);
        contrasenaIntroducida = (EditText) findViewById(R.id.contrasenaEntrenador);

        //crear objeto de la clase auxiliar para BD
        baseDatos = new MiBaseDatosHelper(this, "ifit", null, 1);

        //funcionalidad del botón volver, redirigir a la página principal
        volver = (Button) findViewById(R.id.button5);
        volver.setOnClickListener(view -> {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        });

        //funcionalidad del botón entrar: Comprobar si se han introducido datos, en caso negativo, mostrar Toast con mensaje. En caso afirmativo, obtener datos introducidos y realizar consulta a la BD
        entrar = (Button) findViewById(R.id.button3);
        entrar.setOnClickListener(view -> {

            //obtener el texto introducido para el usuario y la contraseña y guardarlo en Strings
            String codigo = codigoIntroducido.getText().toString();
            String contrasena = contrasenaIntroducida.getText().toString();

            //comprobar que los strings tienen contenido
            if (contrasena.isEmpty() && codigo.isEmpty()) {
                Toast.makeText(this, "Por favor, introduce tus datos de entrenador", Toast.LENGTH_SHORT).show();
            } else {
                //consultar la BD para comprobar si el usuario existe
                if (baseDatos.loginCorrectoEntrenador(codigo, contrasena)) {
                    Intent intent = new Intent(this, PerfilEntrenador.class);
                    Toast.makeText(getApplicationContext(), "Login correcto", Toast.LENGTH_LONG).show(); //mostrar mensaje de login OK.
                    codigoEntrenador = codigo;
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
        getMenuInflater().inflate(R.menu.menu3, menu);
        return true;
    }

    //determinar la opción seleccionada del menú.
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                mostrarAyuda(findViewById(R.id.action_help));
                return true;
            case R.id.action_exit:
                salir(findViewById(R.id.action_salir));
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