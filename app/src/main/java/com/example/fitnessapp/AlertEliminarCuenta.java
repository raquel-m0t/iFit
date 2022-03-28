package com.example.fitnessapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 *Clase alertEliminarCuenta.
 * Genera una instancia de Dialog para mostrar un mensaje de advertencia. Se utiliza para pedir confirmación antes de
 * eliminar una cuenta de la BD para los usuarios entrenador y deportista.
 */
public class AlertEliminarCuenta extends DialogFragment {
    public static MiBaseDatosHelper baseDatos;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        //crear objeto de la clase auxiliar para BD
        baseDatos = new MiBaseDatosHelper(getContext(), "ifit", null, 1);

        //Creamos el objeto AlertDialog.Builder
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        // Asignamos las propiedades que se mostrarán.
        builder.setTitle("Eliminar la cuenta")
                .setMessage("¿De verdad quieres borrar tu perfil? No podrás recuperarlo más adelante si lo haces.")
                .setPositiveButton("Si",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //eliminar la cuenta:

                                //determinar si el usuario es deportista o entrenador
                                //usuario entrenador
                                if (AccesoEntrenadores.codigoEntrenador != null) {
                                    //ejecutar sentencia delete
                                    baseDatos.eliminarEntrenador(AccesoEntrenadores.codigoEntrenador);

                                } else {
                                    //usuario deportista
                                    if (AccesoDeportistas.nombreUsuario != null) {
                                        //ejecutar sentencia delete
                                        baseDatos.eliminarDeportista(AccesoDeportistas.nombreUsuario);
                                    }
                                }
                                //redirigir a la página principal después de eliminar
                                Intent intent = new Intent(getContext(), MainActivity.class);
                                startActivity(intent);
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //cerrar el mensaje
                                dialog.cancel();
                            }
                        });
        return builder.create();

    }

}

