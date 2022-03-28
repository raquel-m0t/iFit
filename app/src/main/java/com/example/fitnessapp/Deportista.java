package com.example.fitnessapp;

import java.io.Serializable;

/**
 * La clase deportista representa al usuario estándar de esta app. Un usuario deportista independiente que puede realizar actividades, comprobar sus tiempos y revisar el historial de ejercicios.
 * Así como editar sus datos. Esta clase contiene los campos contenidos en la tabla de la BD para el usuario deportista,
 */
public class Deportista implements Serializable {
    String id;
    String nombre;
    String contrasena;
    String edad;
    String estatura;
    String peso;
    String registro;

    /**
     * constructor sin parámetros. instancia un objeto de la clase Deportista. Como los datos ya se comprueban mediante los métodos setter y se utilizan antes
     * de pasar los datos a la BD, no es necesario que el constructor tenga más parámetros.
     */

    public Deportista() {
    }

    /**
     * obtener el ID del usuario deportista
     *
     * @return
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    /**
     * obtener el nombre del usuario deportista
     *
     * @return
     */
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * obtener la contraseña del usuario deportista
     *
     * @return
     */
    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * obtener la edad del usuario deportista
     *
     * @return
     */

    public String getEdad() {
        return edad;
    }

    /**
     * Este método se utiliza para cambiar la edad del usuario deportista
     * @param edad
     */
    public void setEdad(String edad) {
        this.edad = edad;
    }

    /**
     * Este método se utiliza para obtener la estatura del usuario deportista
     * @return
     */
    public String getEstatura() {
        return estatura;
    }

    /**
     * Este método se utiliza para cambiar la estatura del usuario deportista
     * @param estatura
     */
    public void setEstatura(String estatura) {
        this.estatura = estatura;
    }

    /**
     * obtener el peso del usuario deportista
     * @return
     */
    public String getPeso() {
        return peso;
    }

    /**
     * cambiar el peso del usuario deportista
     * @param peso
     */
    public void setPeso(String peso) {
        this.peso = peso;
    }

    /**
     * obtener la fecha de registro del usuario deportista
     * @return
     */
    public String getRegistro() {
        return registro;
    }

    /**
     * modificar la fecha de registro del usuario deportista (en teoría no debería usarse)
     * @param registro

    public void setRegistro(String registro) {
        this.registro = registro;
    }
    */
}
