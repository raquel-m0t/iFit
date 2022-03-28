package com.example.fitnessapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Esta clase permite mostrar una view con un listado dinámico de los entrenados que corresponden al entrenador actual y realizar acciones relacionadas con sus perfiles.
 * Entrenar pulsando en el nombre del entrenado, editar su perfil o eliminarlo mediante los botones.
 */
public class PerfilesEntrenados extends AppCompatActivity {

    public static MiBaseDatosHelper datos;
    public static String idEntrenado;
    Button volver;
    Button nuevo;
    RecyclerView recyclerView;
    AdapterEntrenados adapter;
    Entrenador entrenador;
    private ArrayList<Entrenado> _listaEntrenados;
    private RecyclerView.LayoutManager _layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfiles_entrenados);

        //obtener datos del usuario de la BD para completar el código
        String codigoE = AccesoEntrenadores.codigoEntrenador;
        entrenador = AccesoEntrenadores.baseDatos.obtenerDatosEntrenador(codigoE);

        //se invoca el método para añadir entrenados al arraylist
        iniciarEntrenados();
        //Si se ha cambiado de orientación, se recupera la información
        restaurarEntrenados(savedInstanceState);
        // Se invoca el método para generar el recyclerView, indicándole el layoutManager y el adaptador
        iniciarRecyclerView();

        //funcinalidad del botón volver, cargar el perfil del entrenador actual
        volver = (Button) findViewById(R.id.button2Volver);
        volver.setOnClickListener(view -> {
            Intent intent = new Intent(this, PerfilEntrenador.class);
            startActivity(intent);
        });

        //funcionalidad del botón nuevo, registrar un entrenado.
        nuevo = (Button) findViewById(R.id.buttonNuevo);
        nuevo.setOnClickListener(view -> {
            Intent intent = new Intent(this, RegistroEntrenado.class);
            startActivity(intent);
        });


    }

    /**
     * Este método permite recuperar la información del recyclerView si se cambia la orientación del dispositivo.
     * @param savedInstanceState
     */
    private void restaurarEntrenados(Bundle savedInstanceState) {

        // Si hay algo en el bundle, es que se ha guardado algo y lo recuperaremos
        if (savedInstanceState != null) {
            this._listaEntrenados = (ArrayList<Entrenado>) savedInstanceState.getSerializable("entrenados");

        }
    }

    /**
     * Se guarda el objeto _listaEntrenados en el Bundle para cambios de orientación
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("entrenados", _listaEntrenados);

    }

    /**
     * Este método carga la lista de entrenados que corresponden al entrenador actual. Realiza una consulta a la BD y guarda el resultado en un arrayList
     */
    public void iniciarEntrenados() {
        ///obtener datos de BD
        //abrir la base de datos en modo lectura/escritura
        SQLiteDatabase bd = AccesoEntrenadores.baseDatos.getWritableDatabase();

        //convertir id del entrenador a Int, dentro de try-catch para controlar posibles errores
        String idEnt = entrenador.getId();
        int id = 0;
        try {
            id = Integer.valueOf(idEnt);
        } catch (Exception e) {
            System.out.println(e);
        }
        //Creamos el objeto
        _listaEntrenados = new ArrayList<Entrenado>();

        //obtener lista de entrenados de la BD
        _listaEntrenados = AccesoEntrenadores.baseDatos.obtenerEntrenados(id);

    }

    /**
     * Este método inicializa el recyclerview, asocia el adapter y los datos para que puedan ser mostrados.
     */
    public void iniciarRecyclerView() {

        // Asociamos la vista al atributo recyclerView
        recyclerView = findViewById(R.id.listadoPerfiles);
        recyclerView.setHasFixedSize(true);

        _layoutManager = new GridLayoutManager(this, 1);

        // Crear el adaptador, pasándole los datos que va a mostrar
        adapter = new AdapterEntrenados(this._listaEntrenados);
        // Se establece el layoutManager del recyclerview
        recyclerView.setLayoutManager(_layoutManager);
        // Se establece el adaptador al recyclerview
        recyclerView.setAdapter(adapter);
        adapter.setOnitemclickListener(position -> {
            adapter.notifyItemChanged(position);
        });
    }


    /**
     * Método para mostrar y ocultar el menú
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
        Intent intent = new Intent(this, Ayuda.class);
        startActivity(intent);
    }

    /**
     * muestra un dialog al usuario solicitando confirmación antes de cerrar la app.
     * @param view
     */
    public void salir(View view) {
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
