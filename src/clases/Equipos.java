/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.util.ArrayList;

/**
 *
 * @author MR CHAMAN
 */
public class Equipos {
    private String id;
    private String nombre, entrenador;
    private ArrayList<Jugador> listaJugadores;
    private int cantidadJugadores;

    private int partidosJugados, ganados, empatados, perdidos;
    private int golesFavor, golesContra, puntos;
    
    public Equipos(String id, String nombre, String entrenador, int cantidadJugadores){
        this.id = id;
        this.nombre = nombre;
        this.entrenador = entrenador;
        this.cantidadJugadores = cantidadJugadores;
        this.listaJugadores = new ArrayList<>();
    }
    
    public String getId(){
        return id;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getEntrenador(){
        return entrenador;
    }
    
    public void agregarJugador(Jugador jugador) {
        if (jugador != null) {
            this.listaJugadores.add(jugador);
        }
    }
    
    public int getCantidadJugadores() {
        if (listaJugadores.isEmpty()) {
            return cantidadJugadores;
        }
        return listaJugadores.size();
    }

    public ArrayList<Jugador> getListaJugadores() {
        return listaJugadores;
    }

    public void registrarResultado(int golesAFavor, int golesEnContra) {
        partidosJugados++;
        golesFavor = golesFavor + golesAFavor;
        golesContra = golesContra + golesEnContra;
        if (golesAFavor > golesEnContra) {
            ganados++;
            puntos = puntos + 3;
        } else if (golesAFavor == golesEnContra) {
            empatados++;
            puntos = puntos + 1;
        } else {
            perdidos++;
        }
    }

    public int getPartidosJugados() {
        return partidosJugados;
    }

    public int getGanados() {
        return ganados;
    }

    public int getEmpatados() {
        return empatados;
    }

    public int getPerdidos() {
        return perdidos;
    }

    public int getGolesFavor() {
        return golesFavor;
    }

    public int getGolesContra() {
        return golesContra;
    }

    public int getDiferenciaGoles() {
        return golesFavor - golesContra;
    }

    public int getPuntos() {
        return puntos;
    }
}
