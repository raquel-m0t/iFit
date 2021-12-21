package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.TreeMap;

public class Resultado extends AppCompatActivity {

    public static MiBaseDatosHelper baseDatos;
    Button botonOtro;
    TextView tituloResultado;
    TextView textoResultado;
    ImageView imagenFeedback;
    String nombreEjercicio;
    TreeMap<Double, String> resultados = new TreeMap<Double, String>(); //para que los resultados se ordenen automáticamente, utilizo un linkedtreemap, Double (tiempo medio) será la key (para que se ordenen de forma ascendente.
    RecyclerView recyclerView;
    MyAdapter adapter;
    Double tiempoMedio;
    private int valor;
    Menu menu;

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
        tiempoMedio = (double) getIntent().getExtras().getSerializable("tiempo")/1000;

        //calcular puntuación
        setValor(tiempoMedio);

        //establecer elementos título, imagen y texto
        setElementos(valor);

        ///obtener datos de BD
        baseDatos = new MiBaseDatosHelper(this, "tiempos", null, 1);
        //abrir la base de datos en modo lectura/escritura
        SQLiteDatabase bd = baseDatos.getWritableDatabase();

        String[] misCampos = new String[]{"ejercicio", "tiempo"};
        Cursor cur = bd.query("TIEMPOS", misCampos, null, null, null, null, null);
        //obtener valores de cada columna
        if (cur.moveToFirst()) {
            do {
                String var1 = cur.getString(0);//nombre del ejercicio
                Double var2 = cur.getDouble(1);//tiempo
                resultados.put(var2, var1);
            } while (cur.moveToNext());
        }

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
            case R.id.action_volver:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //evalúa el tiempo medio empleado para cada repetición y asocia una puntuación.
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

    //a partir del valor (puntuación), establece un título, texto e imagen a la pantalla.
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
}