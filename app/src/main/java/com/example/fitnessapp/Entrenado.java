package com.example.fitnessapp;

import android.util.Log;

import java.io.Serializable;

/**
 * Esta clase representa a un usuario Entrenado. Es el tipo de usuario más básico de la app. Requiere de un entrenador para poder registrar la actividad. No está pensado como
 * un usuario independiente, sino de un usuario que está siempre vinculado a un entrenador que lo monitoriza y puede tanto modificar sus datos como revisar su historial de actividades.
 */

public class Entrenado implements Serializable {
    String id;
    String nombre;
    String estatura;
    String edad;
    String objetivo;
    String idEntrenador;

    { //si está vacío, poner 0 (reduce errores null en consultas)
        this.edad = "0";
    }

    /**
     * constructor sin parámetros de Entrenado
     */
    public Entrenado() {
    }

    /**
     * Obtener el campo ID
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * Modificar el campo id (no debería usarse)
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtener el nombre del entrenado
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Modificar el nombre del entrenado
     *
     * @param nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtener la estatura del entrenado
     *
     * @return
     */
    public String getEstatura() {
        return estatura;
    }

    /**
     * Modificar la estatura del entrenado. Comprueba si es un número válido, si no, para evitar errores, establece 0 como valor por defecto.
     *
     * @param estatura
     */
    public void setEstatura(String estatura) {
        //estatura
        this.estatura = estatura;

    }

    /**
     * Obtener la edad del entrenado
     *
     * @return
     */
    public String getEdad() {
        return edad;
    }

    /**
     * Modifica la edad del entrenado. Si el valor no es válido, establece 0 como valor por defecto (luego se sustituirá por un N/A en la interfaz en ese caso).
     *
     * @param edad
     */
    public void setEdad(String edad) {
        //comprobar si es un número válido
        this.edad = edad;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    /**
     * obtener el id del entrenador asociado al entrenado
     *
     * @return
     */
    public String getIdEntrenador() {
        return idEntrenador;
    }

    /**
     * cambiar el entrenador asociado. No se usa actualmente, pero podría implementarse esta funcionalidad.
     *
     * @param idEntrenador
     */
    public void setIdEntrenador(String idEntrenador) {
        this.idEntrenador = idEntrenador;
    }

    /**
     * obtener el nombre del entrenado.
     *
     * @return
     */
    public String getNombreEntrenado() {
        return this.nombre;
    }

}

