package com.example.proyectofunciones;

import java.io.Serializable;
import java.util.ArrayList;

public class AgendaContacto implements Serializable {

    private String nombre;
    private String apellido;
    private int cedula;
    private String fechaNacimiento;
    private String correo;
    private String direccion;
    private String ciudad;
    private String departamento;
    private ArrayList<String> gustos;
    private ArrayList<String> preferencias;

    // Constructor
    public AgendaContacto(String nombre, String apellido, int cedula,
                          String fechaNacimiento, String correo,
                          String direccion, String ciudad, String departamento) {
        this.nombre          = nombre;
        this.apellido        = apellido;
        this.cedula          = cedula;
        this.fechaNacimiento = fechaNacimiento;
        this.correo          = correo;
        this.direccion       = direccion;
        this.ciudad          = ciudad;
        this.departamento    = departamento;
        this.gustos          = new ArrayList<>();
        this.preferencias    = new ArrayList<>();
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public int getCedula() { return cedula; }
    public void setCedula(int cedula) { this.cedula = cedula; }

    public String getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }

    public String getCiudad() { return ciudad; }
    public void setCiudad(String ciudad) { this.ciudad = ciudad; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public ArrayList<String> getGustos() { return gustos; }
    public void setGustos(ArrayList<String> gustos) { this.gustos = gustos; }

    public ArrayList<String> getPreferencias() { return preferencias; }
    public void setPreferencias(ArrayList<String> preferencias) { this.preferencias = preferencias; }
}