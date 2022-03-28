package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.TreeMap;

/**
 * Clase que representa a una view que muestra los datos del deportista y los tiempos de sus últimas actividades en un recyclerview.
 * Permite realizar acciones relacionadas con ese perfil.
 */
public class PerfilDeportista extends AppCompatActivity {
    TextView nombreDeportista;
    Button editar;
    Button eliminarCuenta;
    Button ejercicio;
    TextView registro;
    TextView edad;
    TextView estatura;
    TextView peso;
    Deportista datosDeportista;
    TreeMap<Double, String> resultados = new TreeMap<Double, String>(); //para que los resultados se ordenen automáticamente, utilizo un treemap, Double (tiempo medio) será la key (para que se ordenen de forma ascendente.
    RecyclerView recyclerView;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_deportista);

        //obtener datos del usuario de la BD para completar los demás datos del perfil
        datosDeportista = new Deportista();
        datosDeportista = AccesoDeportistas.baseDatos.obtenerDatosDeportista(AccesoDeportistas.nombreUsuario);

        //establecer valores a los textview de la activity
        nombreDeportista = (TextView) findViewById(R.id.codigo);
        String nombre = datosDeportista.nombre;
        nombreDeportista.setText(nombre);

        registro = (TextView) findViewById(R.id.fechaPerfil);
        registro.setText(datosDeportista.registro);

        edad = (TextView) findViewById(R.id.especialidad);
        if (datosDeportista.edad != null && !datosDeportista.edad.isEmpty()){
            if(!datosDeportista.edad.equals("0")){
                edad.setText(datosDeportista.edad);
            }else{
                edad.setText("N/A");
            }
        }

        estatura = (TextView) findViewById(R.id.centro);
        if (datosDeportista.estatura != null){
            if(!datosDeportista.estatura.equals("0")){
            estatura.setText(datosDeportista.estatura + "m");
            }else{
                estatura.setText("N/A");
            }
        }

        peso = (TextView) findViewById(R.id.cantidadDeportistas);
        if (datosDeportista.peso != null && !datosDeportista.peso.isEmpty()){
            if(!datosDeportista.peso.equals("0")){
                peso.setText(datosDeportista.peso + "kg");
            }else{
                peso.setText("N/A");
            }
        }

        //ver resultados de actividades realizadas en recyclerview
        int id = Integer.valueOf(datosDeportista.id);

        String[] misCampos = new String[]{"codigo_actividad", "id_deportista","tiempo"};

        //abrir la base de datos en modo lectura/escritura
        SQLiteDatabase bd = AccesoDeportistas.baseDatos.getWritableDatabase();

        Cursor cur = bd.query("REALIZA", misCampos, "id_deportista" + "=" + id, null, null, null, null);
        //obtener valores de cada columna
        if (cur.moveToFirst()) {
            do {
                String var1 = cur.getString(0);//numero del ejercicio
                Double var2 = cur.getDouble(2);//tiempo
                resultados.put(var2, var1);
            } while (cur.moveToNext());
        }

        //asociar recyclerview
        adapter = new MyAdapter(resultados);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //funcionalidad del botón editar
        editar = (Button) findViewById(R.id.entrenar);
        editar.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditarDeportista.class);
            startActivity(intent);
        });

        //realizar un nuevo ejercicio
        ejercicio = (Button) findViewById(R.id.NuevoEjercicio);
        ejercicio.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListaEjercicios.class);
            startActivity(intent);

        });

        //borrar cuenta
        eliminarCuenta = (Button) findViewById(R.id.editarPerfil);
        eliminarCuenta.setOnClickListener(view -> {
            //avisar al usuario
            FragmentManager fragmentManager = getSupportFragmentManager();
            AlertEliminarCuenta eliminar = new AlertEliminarCuenta();
            eliminar.show(fragmentManager, "tagAlerta");
        });
    }


    /**
     *  Método para mostrar y ocultar el menú
     */

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_help:
                mostrarAyuda (findViewById(R.id.action_help));
                return true;
            case R.id.action_cerrarSesion:
                menuPrincipal (findViewById(R.id.action_cerrarSesion));
                return true;
            case R.id.action_exit:
                salir (findViewById(R.id.action_exit));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //opciones del menú

    /**
     * Carga la activity ayuda para leer el archivo .html generado con Javadoc con explicaciones sobre los métodos y clases de la app.
     * @param view
     */
    public void mostrarAyuda(View view) {
        Intent intent = new Intent (this, Ayuda.class);
        startActivity(intent);
    }

    /**
     * Genera una instancia de la clase AlertSalir, para mostrar un dialog al usuario solicitando confirmación antes de cerrar la app.
     * @param view
     */
    public void salir(View view) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        AlertSalir salir = new AlertSalir();
        salir.show(fragmentManager, "tagAlerta");
    }

    /**
     * Carga la view Acceso deportistas (cierra la sesión actual)
     * @param view
     */
    public void menuPrincipal(View view) {
        Intent intent = new Intent (this, AccesoDeportistas.class);
        startActivity(intent);
    }

}




