package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.os.SystemClock;
import android.widget.TextView;
import android.widget.Toast;

public class Cronometro extends AppCompatActivity {

    public static MiBaseDatosHelper baseDatos;
    Button terminar;
    Button pausar;
    Button renaudar;

    Double tiempo; //almacenar el tiempo resultado para pasar a la siguiente pantalla
    int tipo; //tipo de ejercicio
    int repeticiones;
    TextView reloj;
    Handler customHandler = new Handler();
    int sumaTiempo;

    long startTime = 0L, timeInMilliseconds = 0L, timeSwapBuff = 0L, updateTime = 0L;

    //iniciar cronometro al cargar la pantalla
    //Utilicé threads que actualizan un textview porque la clase chronometer no permite mostrar milisegundos. Probé a cambiar el formato sobreescribiendo el método OnChronometerTick, pero solo se actualiza cada segundo, y no cambiaba los milisegundos.
    //esta es la única forma que encontré de conseguir un cronómetro preciso con milisegundos. Más lioso que la clase Chronometer (que es muy fácil de implementar) pero el resultado es mejor para esta app con threads.
    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            updateTime = timeSwapBuff + timeInMilliseconds;
            int secs = (int) (updateTime / 1000);
            int mins = secs / 60;
            secs %= 60;
            int milliseconds = (int) (updateTime % 1000);
            reloj.setText("" + mins + ":" + String.format("%2d", secs) + ":" + String.format("%2d", milliseconds));
            customHandler.postDelayed(updateTimerThread, 0);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cronometro);

        //iniciar cronometro al cargar la pantalla
        reloj = (TextView) findViewById(R.id.reloj);
        startTime = SystemClock.uptimeMillis();
        customHandler.postDelayed(updateTimerThread, 0);


        //crear objeto de la clase auxiliar para BD
        baseDatos = new MiBaseDatosHelper(this, "tiempos", null, 1);

        //obtener tipo del intent
        tipo = (int) getIntent().getExtras().getSerializable("tipo");
        //obtener repeticiones del intent
        repeticiones = (int) getIntent().getExtras().getSerializable("numero de repeticiones");


        //parar el cronómetro al pulsar el botón
        pausar = (Button) findViewById(R.id.botonPausar);
        pausar.setOnClickListener(view -> {
            timeSwapBuff += timeInMilliseconds;
            customHandler.removeCallbacks(updateTimerThread);
        });

        //continuar el cronómetro
        renaudar = (Button) findViewById(R.id.botonRenaudar);
        renaudar.setOnClickListener(view -> {
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
        });

        //terminar el ejercicio. Guarda datos en la BD y pasa al siguiente intent.
        terminar = (Button) findViewById(R.id.botonTerminar);
        terminar.setOnClickListener(view -> {
            //timeSwapBuff+=timeInMilliseconds;
            customHandler.removeCallbacks(updateTimerThread);
            //obtener tiempo transcurrido
            tiempo = (double) updateTime;
            //registrar en la BD
            insertarRegistro(getNombreEjercicio(tipo), getTiempo(tiempo, repeticiones));
            //añadir tiempo al intent para usarlo en la siguiente pantalla
            Intent intent = new Intent(this, Resultado.class);
            intent.putExtra("tiempo", getTiempo(tiempo, repeticiones));
            startActivity(intent);
        });
    }

    public String getNombreEjercicio(int tipoEjercicio) {
        String nombreEjercicio;
        //determinar texto para cada tipo
        switch (tipoEjercicio) {
            case 1:
                nombreEjercicio = "Abdominales";
                break;
            case 2:
                nombreEjercicio = "Abductores";
                break;
            case 3:
                nombreEjercicio = "Burpee";
                break;
            case 4:
                nombreEjercicio = "Flexion";
                break;
            case 5:
                nombreEjercicio = "Patada Gluteos";
                break;
            case 6:
                nombreEjercicio = "Triceps";
                break;
            case 7:
                nombreEjercicio = "Peso muerto";
                break;
            case 8:
                nombreEjercicio = "Puente";
                break;
            case 9:
                nombreEjercicio = "Sentadilla";
                break;
            case 10:
                nombreEjercicio = "Steps";
                break;
            case 11:
                nombreEjercicio = "Zancada";
                break;
            case 12:
                nombreEjercicio = "Zancada Lateral";
                break;
            default:
                nombreEjercicio = "Sin registrar";
                break;
        }
        return nombreEjercicio;
    }

    //este método divide la cantidad de repeticiones entre el tiempo total para obtener el tiempo empleado en cada repetición (se almacenará para la clasificación)
    public double getTiempo(double tiempo, int repeticiones) {
        double resultado = tiempo / repeticiones;
        return resultado;
    }

    public void insertarRegistro(String nombreEjercicio, double tiempoMedio) {
        //abrir la base de datos en modo lectura/escritura
        SQLiteDatabase bd = baseDatos.getWritableDatabase();
        //insertar datos
        try {
            ContentValues miRegistro = new ContentValues();
            miRegistro.put("ejercicio", nombreEjercicio);
            miRegistro.put("tiempo", tiempoMedio);
            //crear consulta insert
            bd.insert("tiempos", null, miRegistro);

        } catch (Exception e) {
            Toast.makeText(this, "No se han podido registrar los datos", Toast.LENGTH_SHORT).show();
        }
        Toast.makeText(this, "Datos registrados correctamente", Toast.LENGTH_SHORT).show();
    }
}