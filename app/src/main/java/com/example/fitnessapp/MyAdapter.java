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

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    String[] nombresEj;
    Double[] tiemposEj;

    private static TreeMap<Double, String> mDataSet;

    //constructor, inicializar dataset del adaptador
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView textView;
        private final ImageView imageView;

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

    public MyAdapter(Map<Double, String> datos) {
        nombresEj = new String[datos.size()];
        tiemposEj = new Double[datos.size()];
        int i = 0;
        for (Map.Entry<Double, String> e : datos.entrySet()) {
            tiemposEj[i] = e.getKey();
            nombresEj[i++] = e.getValue();
        }
    }

    //crear nuevas views (desde el layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado_ejercicio_item, parent, false);
        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        //obtener elemento en la posición del array y reemplazar el texView con esa cantidad
        Double segundos = tiemposEj[position] / 1000;
        viewHolder.getTextView().setText(segundos.toString());
        //asociar tipo de ejercicio con imagen
        String tipo = nombresEj[position];
        switch (tipo) {
            case "Abdominales":
                viewHolder.getImageView().setImageResource(R.drawable.abdominales);
                break;
            case "Abductores":
                viewHolder.getImageView().setImageResource(R.drawable.abductor);
                break;
            case "Burpee":
                viewHolder.getImageView().setImageResource(R.drawable.burpee);
                break;
            case "Flexion":
                viewHolder.getImageView().setImageResource(R.drawable.flexiones);
                break;
            case "Patada Gluteos":
                viewHolder.getImageView().setImageResource(R.drawable.gluteo);
                break;
            case "Triceps":
                viewHolder.getImageView().setImageResource(R.drawable.tricepsg);
                break;
            case "Peso muerto":
                viewHolder.getImageView().setImageResource(R.drawable.peso);
                break;
            case "Puente":
                viewHolder.getImageView().setImageResource(R.drawable.puente);
                break;
            case "Sentadilla":
                viewHolder.getImageView().setImageResource(R.drawable.sentadilla);
                break;
            case "Steps":
                viewHolder.getImageView().setImageResource(R.drawable.step);
                break;
            case "Zancada":
                viewHolder.getImageView().setImageResource(R.drawable.zancada);
                break;
            case "Zancada Lateral":
                viewHolder.getImageView().setImageResource(R.drawable.zancadalateralg);
                break;
            default:
                viewHolder.getImageView().setImageResource(R.drawable.notfound);
                break;
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return tiemposEj.length;
    }
}