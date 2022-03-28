package com.example.fitnessapp;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

/**
 * AlertSalir. Muestra un mensaje para solicitar la confirmación del usuario antes de cerrar la app.
 */
public class AlertSalir extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Creamos el objeto AlertDialog.Builder
        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());
        // Asignamos las propiedades que se mostrarán.
        builder.setTitle("Salir")
                .setMessage("¿Quieres salir de la aplicación?")
                .setPositiveButton("Vale!",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //salir de la app
                                System.exit(0);
                            }
                        })
                .setNegativeButton("No quiero",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //cerrar el mensaje
                                dialog.cancel();
                            }
                        });
        return builder.create();
    }

}
