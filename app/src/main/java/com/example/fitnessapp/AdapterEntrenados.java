package com.example.fitnessapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * AdapterEntrenados. Esta clase permite mostrar la lista de entrenados de un mismo entrenador dentro de una lista dinámica de tipo recyclerView y realizar acciones con ella,
 * como determinar la posición en la que se ha pulsado, para eliminar ese registro de la BD, editarlo o ver más detalles.
 */
public class AdapterEntrenados extends RecyclerView.Adapter<AdapterEntrenados.entrenadoViewHolder> {

    public static String idEntrenado = "0"; //poder guardar el id del entrenado para futuras acciones
    protected ArrayList<Entrenado> entrenadoArrayList;
    MiBaseDatosHelper baseDatos;
    private onItemClickListener _listener;


    /**
     * Constructor por parámetros
     *
     * @param entrenadoArrayList
     */
    public AdapterEntrenados(ArrayList<Entrenado> entrenadoArrayList) {
        this.entrenadoArrayList = entrenadoArrayList;
    }

    /**
     * Genera listener para poder asociar eventos al pulsar.
     * @param listener
     */
    public void setOnitemclickListener(onItemClickListener listener) {
        _listener = listener;
    }

    /**
     * Crea una vista asociada al layout que va a recoger cada elemento de la lista
     */
    @NonNull
    @Override
    public entrenadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado_entrenado_item, parent, false);
        entrenadoViewHolder pvh = new entrenadoViewHolder(v, _listener, entrenadoArrayList);

        //crear objeto de la clase auxiliar para BD
        baseDatos = AccesoEntrenadores.baseDatos;
        return pvh;
    }

    /**
     * onBindViewHolder. Obtiene la posición del arraylist para mostrar el nombre de cada elemento.
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull entrenadoViewHolder holder, int position) {
        Entrenado _entrenado = this.entrenadoArrayList.get(position);
        holder.textView.setText(_entrenado.getNombre());

    }

    /**
     * getItemCount.
     * @return el número de elementos del arrayList
     */
    @Override
    public int getItemCount() {
        return this.entrenadoArrayList.size();
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    /**
     * Se crea la clase entrenadoViewHolder para gestionar cada elemento del recyclerview
     */
    public static class entrenadoViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;
        public ImageButton editar;
        public ImageButton eliminar;
        public Entrenado entrenadoSeleccionado;
        public Entrenado entrenadoSeleccionadoEliminar;

        public entrenadoViewHolder(@NonNull View itemView, onItemClickListener listener, ArrayList<Entrenado> entrenados) {
            super(itemView);
            Log.d("entra constructor", "prueba constructor");
            textView = (TextView) itemView.findViewById(R.id.nombreEntrenado);
            editar = (ImageButton) itemView.findViewById(R.id.botonEditar);
            eliminar = (ImageButton) itemView.findViewById(R.id.botonBorrar);


            /**
             * Para cada uno de los elementos le establecemos la acción a realizar si se pulsa sobre él
             */
            //funcionalidad del botón editar: Obtener la posición, guardar el id para más adelante y abrir la activity DetallesPerfilEntrenado.
            editar.setOnClickListener(v -> {
                if (listener != null) {
                    Log.d("editar entrenado", "ha pulsado editar un entrenado");//registrar la acción en el log.
                    entrenadoSeleccionado = entrenados.get(getAdapterPosition());
                    idEntrenado = entrenadoSeleccionado.getId();
                    listener.onItemClick(getAdapterPosition());

                    //abrir activity para ver/editar datos del entrenado
                    Intent intentEditar = new Intent(v.getRootView().getContext(), DetallesPerfilEntrenado.class);
                    intentEditar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getRootView().getContext().startActivity(intentEditar);

                }
            });

            //funcionalidad del botón eliminar: obtener la posición selecionada. Mostrar un dialog para pedir confirmación antes de eliminar. Si se elimina, se vuelve a la lista de perfiles. Si no se elimina, el dialog se cierra sin más.
            eliminar.setOnClickListener(v -> {
                if (listener != null) {
                    Log.d("eliminar entrenado", "ha pulsado eliminar un entrenado"); //registrar en log.
                    entrenadoSeleccionadoEliminar = entrenados.get(getAdapterPosition());
                    listener.onItemClick(getAdapterPosition());
                    //String idEntrenadoEliminar = entrenadoSeleccionadoEliminar.getId();
                    //System.out.println(entrenadoSeleccionadoEliminar.getId());
                    //Creamos el objeto AlertDialog.Builder
                    AlertDialog.Builder builder =
                            new AlertDialog.Builder(v.getRootView().getContext());
                    // Asignamos las propiedades que se mostrarán.
                    builder.setTitle("Eliminar la cuenta")
                            .setMessage("¿De verdad quieres borrarlo? No podrás recuperarlo más adelante si lo haces.")
                            .setPositiveButton("Si",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            //eliminar la cuenta:
                                            AccesoEntrenadores.baseDatos.eliminarEntrenado(entrenadoSeleccionadoEliminar.getId());
                                            //redirigir a la página principal
                                            Intent intent = new Intent(v.getRootView().getContext(), PerfilesEntrenados.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                            v.getRootView().getContext().startActivity(intent);
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
                    builder.show();

                }
            });
            //establecer acciones al pulsar en el nombre de un entrenado: obtiene la posición pulsada, guarda el ID del entrenado y carga la lista de ejercicios.
            textView.setOnClickListener(v -> {
                if (listener != null) {
                    Log.d("Info entrenado", "ha pulsado entrenar entrenado");//registar en log
                    //obtener la posición pulsada
                    entrenadoSeleccionado = entrenados.get(getAdapterPosition());
                    entrenados.get(getAdapterPosition());
                    listener.onItemClick(getAdapterPosition());
                    //guardar ID para usarlo más tarde
                    String idEntrenado = entrenadoSeleccionado.getId();
                    //cargar intent de lista de ejercicios para entrenar.
                    Intent intentEntrenar = new Intent(v.getRootView().getContext(), ListaEjercicios.class);
                    intentEntrenar.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    v.getRootView().getContext().startActivity(intentEntrenar);
                }
            });
        }
    }
}


