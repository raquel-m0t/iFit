package com.example.fitnessapp;

import android.widget.TextView;

/**
 * La clase Entrenador representa al usuario con más privilegios de esta app. Un usuario entrenador está pensado para monitorizar a otros usuarios entrenados y llevar un
 * registro de su actividad. Puede editar sus perfiles y ver cómo siguen sus objetivos.
 */
public class Entrenador {
    String id;
    String codigo;
    String contrasena;
    String registro;
    String especialidad;
    String centro;

    /**
     * constructor sin parámetros
     */
    public Entrenador() {

    }

    /**
     * Obtener el id del entrenador
     * @return
     */
    public String getId() {
        return this.id;
    }

    /**
     * modificar el id. No debería usarse.
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * obtener el código de entrenador
     * @return
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * modificar el código de entrenador. Cambiar esto podría provocar inestabilidad en el código.
     * @param codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * obtener la contraseña del entrenador
     * @return
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * establecer la contraseña del entrenador.
     * @param contrasena
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * obtener la fecha de registro del entrenador
     * @return
     */
    public String getRegistro() {
        return registro;
    }

    /**
     * establecer la fecha de registro. No debería usarse.
     * @param registro
     */
    public void setRegistro(String registro) {
        this.registro = registro;
    }

    /**
     * obtener la especialidad del entrenador
     * @return
     */
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * establecer la especialidad del entrenador
     * @param especialidad
     */
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    /**
     * obtener el centro del entrenador
     * @return
     */
    public String getCentro() {
        return centro;
    }

    /**
     * modificar el centro del entrenador
     * @param centro
     */
    public void setCentro(String centro) {
        this.centro = centro;
    }
}
