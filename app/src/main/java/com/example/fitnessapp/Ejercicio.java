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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Clase que representa a una activity que da funcionalidad a los elementos de activity_ejercicio. Permite establecer un número de repeticiones, mostrar un mensaje
 * con consejos para la realizacioón del ejercicio y al comenzar redirige a la activity cronómentro, si el campo repeticiones es superior a 0.
 */
public class Ejercicio extends AppCompatActivity {

    Button comenzar;
    Button mas;
    Button menos;
    ImageView logoEjercicio;
    EditText repeticiones;
    TextView consejos;
    TextView nombreEjercicio;
    private int tipo;
    private int numeroRepeticiones; //por defecto inicializado a 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ejercicio);

        //inicializar campos para el número de repeticiones y textos fijos
        repeticiones = (EditText) findViewById(R.id.campoRepeticiones);
        consejos = (TextView) findViewById(R.id.consejosEjercicio);
        nombreEjercicio = (TextView) findViewById(R.id.nombreEjercicio);

        //obtener tipo de ejercicio de la pantalla anterior
        tipo = (int) getIntent().getExtras().getSerializable("tipo de ejercicio");
        logoEjercicio = (ImageView) findViewById(R.id.ejercicio);
        asociarTipo(tipo);

        //funcionalidad del botón menos; resta una repetición.
        menos = (Button) findViewById(R.id.botonMenos);
        menos.setOnClickListener(view -> {
            restarRepeticiones();
        });

        //funcionalidad del botón más, incrementar repeticiones.
        mas = (Button) findViewById(R.id.botonMas);
        mas.setOnClickListener(view -> {
            sumarRepeticiones();
        });

        //funcionalidad del botón comenzar, comprueba si hay al menos una repetición (no puede ser 0). En caso afirmativo, inicia la activity cronómetro.
        comenzar = (Button) findViewById(R.id.buttonComenzarEjercicio);
        comenzar.setOnClickListener(view -> {
            //si se ha indicado al menos una repetición, pasar a la siguiente página
            if (numeroRepeticiones >= 1) {
                Intent intent = new Intent(this, Cronometro.class);
                //intent.putExtra("tipo de ejercicio", tipo);//guardar tipo de ejercicio para la siguiente ventana
                intent.putExtra("numero de repeticiones", numeroRepeticiones);//guardar repeticiones para la siguiente ventana
                intent.putExtra("tipo", tipo);
                startActivity(intent);
            }
            //si no hay repeticiones, mostrar mensaje
            else {
                Toast.makeText(this, "Por favor, indica al menos una repetición para empezar", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Este método asocia el código del tipo de ejercicio con su título, logo y consejos correspondientes.
     *
     * @param tipo de ejercicio. el código.
     */
    public void asociarTipo(int tipo) {

        switch (tipo) {
            case 1:
                nombreEjercicio.setText("Abdominales");
                logoEjercicio.setImageResource(R.drawable.abdominalg);
                consejos.setText(getString(R.string.consejo_abdominales));
                break;
            case 2:
                nombreEjercicio.setText("Abductores");
                logoEjercicio.setImageResource(R.drawable.abductorg);
                consejos.setText(getString(R.string.consejo_abductores));
                break;
            case 3:
                nombreEjercicio.setText("Burpees");
                logoEjercicio.setImageResource(R.drawable.burpeeg);
                consejos.setText(getString(R.string.consejo_burpees));
                break;
            case 4:
                nombreEjercicio.setText("Flexiones");
                logoEjercicio.setImageResource(R.drawable.flexiong);
                consejos.setText(getString(R.string.consejo_flexiones));
                break;
            case 5:
                nombreEjercicio.setText("Patada Gluteos");

                logoEjercicio.setImageResource(R.drawable.gluteog);
                consejos.setText(getString(R.string.consejo_gluteos));
                break;
            case 6:
                nombreEjercicio.setText("Tríceps");
                logoEjercicio.setImageResource(R.drawable.tricepsg);
                consejos.setText(getString(R.string.consejo_triceps));
                break;
            case 7:
                nombreEjercicio.setText("Peso Muerto");
                logoEjercicio.setImageResource(R.drawable.pesog);
                consejos.setText(getString(R.string.consejo_peso));
                break;
            case 8:
                nombreEjercicio.setText("Puentes");
                logoEjercicio.setImageResource(R.drawable.puenteg);
                consejos.setText(getString(R.string.consejo_puente));
                break;
            case 9:
                nombreEjercicio.setText("Sentadillas");
                logoEjercicio.setImageResource(R.drawable.sentadillag);
                consejos.setText(getString(R.string.consejo_sentadillas));
                break;
            case 10:
                nombreEjercicio.setText("Steps");
                logoEjercicio.setImageResource(R.drawable.stepg);
                consejos.setText(getString(R.string.consejo_steps));
                break;
            case 11:
                nombreEjercicio.setText("Zancadas");
                logoEjercicio.setImageResource(R.drawable.zancadag);
                consejos.setText(getString(R.string.consejo_zancadas));
                break;
            case 12:
                nombreEjercicio.setText("ZancadasLaterales");
                logoEjercicio.setImageResource(R.drawable.zancadalateralg);
                consejos.setText(getString(R.string.consejo_zancadas_laterales));
                break;
        }
    }

    /**
     * Este método incrementa el contenido del campo numeroRepeticiones en 1 cada vez que se pulsa.
     */
    public void sumarRepeticiones() {
        numeroRepeticiones++;
        String numero = String.valueOf(numeroRepeticiones);
        repeticiones.setText(numero);
    }

    /**
     * resta 1 al contenido del campo numeroRepeticiones cada vez que se pulsa, siempre que este sea mayor que 0.
     */
    public void restarRepeticiones() {
        if (numeroRepeticiones > 0) {
            numeroRepeticiones--;
            String numero = String.valueOf(numeroRepeticiones);
            repeticiones.setText(numero);
        } else {
            Toast.makeText(this, "El número no puede ser menor que cero", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para mostrar y ocultar el menú
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu3, menu);
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
    public void mostrarAyuda(View view) {
        Intent intent = new Intent(this, Ayuda.class);
        startActivity(intent);
    }

    /**
     * Salir de la app. Cierra la aplicación.
     *
     * @param view
     */
    public void salir(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Salir");
        alertDialogBuilder
                .setMessage("¿Quieres salir de la aplicación? Perderás los cambios de esta pantalla")
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