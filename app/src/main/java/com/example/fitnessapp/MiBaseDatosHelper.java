package com.example.fitnessapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MiBaseDatosHelper extends SQLiteOpenHelper {

    public MiBaseDatosHelper(Context contexto, String nombre, SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //crear tabla tiempos para registrar los resultados
        db.execSQL("CREATE TABLE tiempos (id INTEGER PRIMARY KEY AUTOINCREMENT, ejercicio TEXT, tiempo DOUBLE)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //eliminar versión anterior de la tabla (si existe)
        db.execSQL("DROP TABLE IF EXISTS tiempos");
        //crear nueva versión
        db.execSQL("CREATE TABLE tiempos (id INTEGER PRIMARY KEY AUTOINCREMENT, ejercicio TEXT, tiempo DOUBLE)");
    }
}
