package com.example.fitnessapp;

import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Map;
import java.util.TreeMap;

/**
 * Esta clase permite mostrar la lista de actividades y los tiempos empleados de un usuario dentro de una lista dinámica de tipo Recyclerview de solo lectura.
 * La vista de cada elemento consiste en un imageView con la actividad, ya que, en lugar de mostrar el nombre, muestra el icono, y el textview con el tiempo empleado.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    //definir arrays para almacenar el conjunto de información de cada tipo
    String[] nombresEj;
    Double[] tiemposEj;

    //treemap para mostrar los resultados ordenados
    private static TreeMap<Double, String> mDataSet;

    //constructor, inicializar dataset del adaptador
    public static class ViewHolder extends RecyclerView.ViewHolder {
        //definir elementos de la view
        private final TextView textView;
        private final ImageView imageView;

        /**
         * Crea una vista asociada al layout que va a recoger cada elemento de la lista
         */
        public ViewHolder(View view) {
            super(view);
            textView = (TextView) view.findViewById(R.id.tiempo);
            imageView = (ImageView) view.findViewById(R.id.imagenResultado);
        }

        public TextView getTextView() {
            return textView;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }

    /**
     * constructor de MyAdapter. Itera el treemap para obtener los elementos que contiene.
     * @param datos
     */
    public MyAdapter(Map<Double, String> datos) {
        nombresEj = new String[datos.size()];
        tiemposEj = new Double[datos.size()];
        int i = 0;
        for (Map.Entry<Double, String> e : datos.entrySet()) {
            tiemposEj[i] = e.getKey();
            nombresEj[i++] = e.getValue();
        }
    }


    /**
     * Este método permite crear nuevas views (desde el layout manager)
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado_ejercicio_item, parent, false);
        return new ViewHolder(view);
    }

    /**
     * Este método permite reemplazar los contenidos de una view (desde el layour manager). Asocia el tipo de ejercicio (un número) un el icono de la actividad que representa.
     * @param viewHolder
     * @param position
     */

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        //obtener elemento en la posición del array y reemplazar el texView con esa cantidad
        Double segundos = tiemposEj[position] / 1000;
        viewHolder.getTextView().setText(segundos.toString());
        //asociar tipo de ejercicio con imagen
        String tipo = nombresEj[position];
        switch (tipo) {
            case "1":
                viewHolder.getImageView().setImageResource(R.drawable.abdominales);
                break;
            case "2":
                viewHolder.getImageView().setImageResource(R.drawable.abductor);
                break;
            case "3":
                viewHolder.getImageView().setImageResource(R.drawable.burpee);
                break;
            case "4":
                viewHolder.getImageView().setImageResource(R.drawable.flexiones);
                break;
            case "5":
                viewHolder.getImageView().setImageResource(R.drawable.gluteo);
                break;
            case "6":
                viewHolder.getImageView().setImageResource(R.drawable.tricepsg);
                break;
            case "7":
                viewHolder.getImageView().setImageResource(R.drawable.peso);
                break;
            case "8":
                viewHolder.getImageView().setImageResource(R.drawable.puente);
                break;
            case "9":
                viewHolder.getImageView().setImageResource(R.drawable.sentadilla);
                break;
            case "10":
                viewHolder.getImageView().setImageResource(R.drawable.step);
                break;
            case "11":
                viewHolder.getImageView().setImageResource(R.drawable.zancada);
                break;
            case "12":
                viewHolder.getImageView().setImageResource(R.drawable.zancadalateralg);
                break;
            default:
                viewHolder.getImageView().setImageResource(R.drawable.notfound);
                break;
        }
    }

    /**
     * Obtiene el tamaño del conjunto de datos (se utiliza desde el layout manager)
     * @return el número de elementos del arrayList
     */

    @Override
    public int getItemCount() {
        return tiemposEj.length;
    }
}