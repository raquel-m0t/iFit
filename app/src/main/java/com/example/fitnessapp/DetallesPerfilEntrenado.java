package com.example.fitnessapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.util.TreeMap;

/**
 * Clase que representa a una activity para mostrar los detalles del perfil del entrenado que se selecciona. Permite ver los datos del usuario y tiempso de
 * los ejercicios anteriores (de todos los tipos de ejercicios disponibles)
 * Así como botones para eliminar la cuenta (con confirmación), y editar.
 */
public class DetallesPerfilEntrenado extends AppCompatActivity {

    Button volver;
    Button eliminarCuenta;
    Button editar;
    TextView nombreEntrenado;
    TextView estaturaEntrenado;
    TextView edadEntrenado;
    TextView objetivoEntrenado;
    TextView codigoEntrenador;
    Entrenado datosEntrenado;
    RecyclerView recyclerView;
    MyAdapter adapter;
    TreeMap<Double, String> resultados = new TreeMap<Double, String>(); //para que los resultados se ordenen automáticamente, utilizo un linkedtreemap, Double (tiempo medio) será la key (para que se ordenen de forma ascendente.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_perfil_entrenado);

        //inicializar el contenido de los textview con los valores de la BD
        iniciarElementos();

        //funcionalidad del botón para eliminar cuenta
        eliminarCuenta = (Button) findViewById(R.id.buttonEliminarPerfil);
        eliminarCuenta.setOnClickListener(view -> {
            //avisar al usuario
            eliminarEntrenado();

        });

        //funcionalidad del botón para editar la cuenta
        editar = (Button) findViewById(R.id.buttonModificar2);
        editar.setOnClickListener(view -> {
            Intent intent = new Intent(this, EditarEntrenado.class);
            Log.d("ok.", "se accede a editar entrenado");
            startActivity(intent);

        });

        //funcionalidad del botón volver, dirigir a la lista de entrenados del entrenador actual
        volver = (Button) findViewById(R.id.buttonVolver3);
        volver.setOnClickListener(view -> {
            Intent intent = new Intent(this, PerfilesEntrenados.class);
            startActivity(intent);

        });
    }

    /**
     * Muestra un mensaje para pedir la confirmación del usuario antes de eliminar la cuenta.
     *
     * @return dialog con el mensaje y dos botones.
     */
    public Dialog eliminarEntrenado() {
        AlertDialog dlg = null;
        dlg = new AlertDialog.Builder(this)

                .setTitle("Eliminar este usuario")
                .setMessage("¿De verdad quieres hacer esto?")
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //eliminar al entrenado
                        if (AdapterEntrenados.idEntrenado != null) {
                            AccesoEntrenadores.baseDatos.eliminarEntrenado(AdapterEntrenados.idEntrenado);
                        } else {
                            Log.d("error", "No se puede obtener el usuario seleccionado");
                        }
                        //volver al listado de entrenados
                        Intent intent = new Intent(getApplicationContext(), PerfilesEntrenados.class);
                        startActivity(intent);

                    }
                })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //cerrar el mensaje
                                dialog.cancel();
                            }
                        })
                .create();
        return dlg;
    }

    /**
     * Este método asocia los elementos de la view que contienen datos de la BD con esos datos, para que se visualicen.
     * Permite ver tanto los datos del entrenado en sus respectivos campos como el recyclerview con los datos de los ejercicios realizados.
     */
    public void iniciarElementos() {
        //consulta a BD para obtener información
        String idE = AdapterEntrenados.idEntrenado;
        datosEntrenado = AccesoEntrenadores.baseDatos.obtenerDatosEntrenado(idE);

        //obtener contenidos del recyclerview

        //abrir la base de datos en modo lectura/escritura
        SQLiteDatabase bd = AccesoEntrenadores.baseDatos.getWritableDatabase();
        try {
            int id = Integer.valueOf(idE);
            String[] misCampos = new String[]{"codigo_actividad", "id_entrenado", "tiempo"};

            Cursor cur = bd.query("EJERCITA", misCampos, "id_entrenado" + "=" + id, null, null, null, null);
            //obtener valores de cada columna
            int length = cur.getCount();
            System.out.println(length);
            if (cur.moveToFirst()) {
                do {
                    String var1 = cur.getString(0);//nombre del ejercicio
                    Double var2 = cur.getDouble(2);//tiempo
                    resultados.put(var2, var1);
                } while (cur.moveToNext());
            }

            //asociar contenidos al recyclerview
            adapter = new MyAdapter(resultados);
            recyclerView = (RecyclerView) findViewById(R.id.recyclerEntrenados);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            //establecer valores a los textview de la activity
            nombreEntrenado = (TextView) findViewById(R.id.textView40);
            String nombre = datosEntrenado.nombre;
            nombreEntrenado.setText(nombre);

            codigoEntrenador = (TextView) findViewById(R.id.idEntrenadorActualMostrar);
            codigoEntrenador.setText(datosEntrenado.getIdEntrenador());

            nombreEntrenado.setText(datosEntrenado.getIdEntrenador());

            estaturaEntrenado = (TextView) findViewById(R.id.textView48);
            if (datosEntrenado.estatura != null) {
                estaturaEntrenado.setText(datosEntrenado.estatura);
            } else {
                estaturaEntrenado.setText("N/A");
            }

            objetivoEntrenado = (TextView) findViewById(R.id.textView43);
            if (datosEntrenado.objetivo != null) {
                objetivoEntrenado.setText(datosEntrenado.objetivo);
            } else {
                objetivoEntrenado.setText("ninguno");
            }

            edadEntrenado = (TextView) findViewById(R.id.textView42);
            if (datosEntrenado.edad != null && !datosEntrenado.edad.isEmpty()) {
                if (!datosEntrenado.edad.equals("0")) {
                    edadEntrenado.setText(datosEntrenado.edad);
                } else {
                    edadEntrenado.setText("N/A");
                }
            }

        }catch (Exception e){
            Log.d("Error:", "Fallo al recuperar los datos del entrenado");
        }

    }
}