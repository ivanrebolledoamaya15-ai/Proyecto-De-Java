/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author MR CHAMAN
 */
public class Jugador {
    
    private String id;
    private String nombre;
    private int edad;
    private String posicion;
    private int goles;
    
    
    public Jugador(String id, String nombre, int edad, String posicion) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.posicion = posicion;
    }
    
    public String getId(){
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public String getPosicion() {
        return posicion;
    }

    public int getGoles() {
        return goles;
    }

    public void agregarGoles(int cantidad) {
        this.goles = this.goles + cantidad;
    }
}