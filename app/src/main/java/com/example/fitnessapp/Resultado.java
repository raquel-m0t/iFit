package com.example.fitnessapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.TreeMap;

/**
 * Esta clase le da funcionalidad a los componentes del layout activity_resultado. Muestra un breve feedback al concluir la actividad realizada.
 */
public class Resultado extends AppCompatActivity {

    Button botonOtro;
    TextView tituloResultado;
    TextView textoResultado;
    ImageView imagenFeedback;
    String nombreEjercicio;
    TreeMap<Double, String> resultados = new TreeMap<Double, String>(); //para que los resultados se ordenen automáticamente, utilizo un linkedtreemap, Double (tiempo medio) será la key (para que se ordenen de forma ascendente.
    RecyclerView recyclerView;
    MyAdapter adapter;
    Double tiempoMedio;
    Deportista deportista;
    Entrenado entrenado;
    private int valor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        tituloResultado = (TextView) findViewById(R.id.tituloResultado);
        textoResultado = (TextView) findViewById(R.id.textoResultado);
        imagenFeedback = (ImageView) findViewById(R.id.imagenFeedback);


        //obtener nombre del ejercicio del intent
        nombreEjercicio = (String) getIntent().getExtras().getSerializable("ejercicio");

        //obtener tiempo del intent
        tiempoMedio = (double) getIntent().getExtras().getSerializable("tiempo") / 1000;

        //calcular puntuación
        setValor(tiempoMedio);

        //establecer elementos título, imagen y texto
        setElementos(valor);

        ///obtener datos de BD para poblar el recyclerView
        obtenerRegistros();


        //asociar recyclerview
        adapter = new MyAdapter(resultados);
        recyclerView = (RecyclerView) findViewById(R.id.listaTiempos);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //botón para elegir otro ejercicio que redirige a la segunda pantalla
        botonOtro = (Button) findViewById(R.id.botonOtro);
        botonOtro.setOnClickListener(view -> {
            Intent intent = new Intent(this, ListaEjercicios.class);
            startActivity(intent);
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
            case R.id.action_salir:
                cerrarApp();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

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
     * Este método sirve para pedir la confirmación del usuario antes de salir de la app. Muestra un dialog con dos botones.
     */
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

    /**
     * Este método evalúa el tiempo medio empleado para cada repetición y asocia una puntuación.
     *
     * @param tiempo
     */

    public void setValor(double tiempo) {
        //Máxima puntuación, si es menor a 2 segundos por repetición
        if (tiempo < 2) {
            valor = 10;
        } else {
            //si entre 2 y 3 Excelente
            if (tiempo >= 2 && tiempo < 3) {
                valor = 8;
            } else {
                //entre 3 y 4 Bien
                if (tiempo >= 3 && tiempo < 4) {
                    valor = 6;
                } else {
                    //entre 3 y 3.5. Mejorable
                    if (tiempo >= 4 && tiempo <= 5) {
                        valor = 4;
                    } else {
                        //más de 4.5. ¿qué ha pasado?
                        if (tiempo > 5) {
                            valor = 0;
                        }
                    }
                }
            }
        }
    }

    /**
     * Este método establece un título, texto e imagen a la pantalla a partir del valor (puntuación).
     *
     * @param valor
     */

    public void setElementos(int valor) {
        switch (valor) {
            case 10:
                tituloResultado.setText(R.string.impresionante);
                textoResultado.setText(getString(R.string.texto_impresionante, tiempoMedio.toString()));
                imagenFeedback.setImageResource(R.drawable.fuerte);
                break;
            case 8:
                tituloResultado.setText(R.string.fenomenal);
                textoResultado.setText(getString(R.string.texto_fenomenal, tiempoMedio.toString()));
                imagenFeedback.setImageResource(R.drawable.animadora);
                break;
            case 6:
                tituloResultado.setText(R.string.bien);
                textoResultado.setText(getString(R.string.texto_bien, tiempoMedio.toString()));
                imagenFeedback.setImageResource(R.drawable.aplaudir);
                break;
            case 4:
                tituloResultado.setText(R.string.conseguiste);
                textoResultado.setText(getString(R.string.texto_conseguiste, tiempoMedio.toString()));
                imagenFeedback.setImageResource(R.drawable.debilidad);
                break;
            case 0:
                tituloResultado.setText(R.string.mal);
                textoResultado.setText(getString(R.string.texto_mal, tiempoMedio.toString()));
                imagenFeedback.setImageResource(R.drawable.mal);
                break;
            default:
                tituloResultado.setText(R.string.sin_cargar);
                textoResultado.setText(R.string.texto_sin_cargar);
                imagenFeedback.setImageResource(R.drawable.logo);
                break;
        }
    }

    /**
     * Este método determina si el usuario es deportista o entrenador y así ejecuta la consulta en la tabla que corresponda.
     */
    public void obtenerRegistros() {

        //usuario entrenador
        if (AccesoEntrenadores.codigoEntrenador != null) {
            //abrir la base de datos en modo lectura/escritura
            SQLiteDatabase bd = AccesoEntrenadores.baseDatos.getWritableDatabase();
            //obtener id entrenado de consulta a BD
            //consulta para obtener id usuario entrenado
            entrenado = AccesoEntrenadores.baseDatos.obtenerDatosEntrenado(PerfilesEntrenados.idEntrenado);

            int id = Integer.valueOf(AdapterEntrenados.idEntrenado);

            String[] misCampos = new String[]{"codigo_actividad", "id_entrenado", "tiempo"};

            Cursor cur = bd.query("EJERCITA", misCampos, "codigo_actividad" + "=" + Cronometro.tipo + " AND " + "id_entrenado" + "=" + id, null, null, null, null);
            //obtener valores de cada columna
            if (cur.moveToFirst()) {
                do {
                    String var1 = cur.getString(0);//nombre del ejercicio
                    Double var2 = cur.getDouble(2);//tiempo
                    resultados.put(var2, var1);
                } while (cur.moveToNext());
            }


        } else {
            //usuario deportista
            if (AccesoDeportistas.nombreUsuario != null) {
                //abrir la base de datos en modo lectura/escritura
                SQLiteDatabase bd = AccesoDeportistas.baseDatos.getWritableDatabase();
                //obtener id deportista de consulta a BD
                //consulta para obtener id usuario deportista
                deportista = new Deportista();
                deportista = AccesoDeportistas.baseDatos.obtenerDatosDeportista(AccesoDeportistas.nombreUsuario);

                int id = Integer.valueOf(deportista.id);

                String[] misCampos = new String[]{"codigo_actividad", "id_deportista", "tiempo"};

                Cursor cur = bd.query("REALIZA", misCampos, "codigo_actividad" + "=" + Cronometro.tipo + " AND " + "id_deportista" + "=" + id, null, null, null, null);
                //obtener valores de cada columna
                if (cur.moveToFirst()) {
                    do {
                        String var1 = cur.getString(0);//nombre del ejercicio
                        Double var2 = cur.getDouble(2);//tiempo
                        resultados.put(var2, var1);
                    } while (cur.moveToNext());
                }

            } else {
                Toast.makeText(this, "Error al determinar el tipo de usuario", Toast.LENGTH_SHORT).show();
            }
        }

    }
}