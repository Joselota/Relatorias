package com.isolis.demo.models;

import java.time.LocalDate;

public class Empleado {
    
    // Atributos
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String puesto;
    private String departamento;
    private Double salario;
    private LocalDate fechaContratacion;
    private boolean activo;
    
    // Constructor por defecto
    public Empleado() {
        this.activo = true;
        this.fechaContratacion = LocalDate.now();
    }
    
    // Constructor con parámetros principales
    public Empleado(String nombre, String apellido, String email, String puesto, Double salario) {
        this();
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.puesto = puesto;
        this.salario = salario;
    }
    
    // Constructor completo
    public Empleado(Long id, String nombre, String apellido, String email, String telefono,
                   String puesto, String departamento, Double salario, LocalDate fechaContratacion, boolean activo) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.puesto = puesto;
        this.departamento = departamento;
        this.salario = salario;
        this.fechaContratacion = fechaContratacion;
        this.activo = activo;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getTelefono() {
        return telefono;
    }
    
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
    
    public String getPuesto() {
        return puesto;
    }
    
    public void setPuesto(String puesto) {
        this.puesto = puesto;
    }
    
    public String getDepartamento() {
        return departamento;
    }
    
    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    public Double getSalario() {
        return salario;
    }
    
    public void setSalario(Double salario) {
        this.salario = salario;
    }
    
    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }
    
    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }
    
    public boolean isActivo() {
        return activo;
    }
    
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    // Métodos de utilidad
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }
    
    public void darDeBaja() {
        this.activo = false;
    }
    
    public void reactivar() {
        this.activo = true;
    }
    
    public int getAñosEnEmpresa() {
        return LocalDate.now().getYear() - this.fechaContratacion.getYear();
    }
    
    // toString
    @Override
    public String toString() {
        return "Empleado{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", puesto='" + puesto + '\'' +
                ", departamento='" + departamento + '\'' +
                ", salario=" + salario +
                ", fechaContratacion=" + fechaContratacion +
                ", activo=" + activo +
                '}';
    }
    
    // equals y hashCode
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Empleado empleado = (Empleado) obj;
        
        if (id != null ? !id.equals(empleado.id) : empleado.id != null) return false;
        if (email != null ? !email.equals(empleado.email) : empleado.email != null) return false;
        
        return true;
    }
    
    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
