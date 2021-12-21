package com.example.fitnessapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

        repeticiones = (EditText) findViewById(R.id.campoRepeticiones);
        consejos = (TextView) findViewById(R.id.consejosEjercicio);
        nombreEjercicio = (TextView) findViewById(R.id.nombreEjercicio);


        //obtener tipo de ejercicio de la pantalla anterior
        tipo = (int) getIntent().getExtras().getSerializable("tipo de ejercicio");
        logoEjercicio = (ImageView) findViewById(R.id.ejercicio);
        asociarTipo(tipo);

        menos = (Button) findViewById(R.id.botonMenos);
        menos.setOnClickListener(view -> {
            restarRepeticiones();
        });

        mas = (Button) findViewById(R.id.botonMas);
        mas.setOnClickListener(view -> {
            sumarRepeticiones();
        });

        comenzar = (Button) findViewById(R.id.buttonComenzarEjercicio);
        comenzar.setOnClickListener(view -> {
            //numeroRepeticiones = repeticiones.getText();
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

    public void sumarRepeticiones() {
        numeroRepeticiones++;
        String numero = String.valueOf(numeroRepeticiones);
        repeticiones.setText(numero);
    }

    public void restarRepeticiones() {
        if (numeroRepeticiones > 0) {
            numeroRepeticiones--;
            String numero = String.valueOf(numeroRepeticiones);
            repeticiones.setText(numero);
        } else {
            Toast.makeText(this, "El número no puede ser menor que cero", Toast.LENGTH_SHORT).show();
        }
    }
}