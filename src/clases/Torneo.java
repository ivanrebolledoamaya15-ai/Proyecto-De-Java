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
public class Torneo {
    
    private String nombreTorneo;
    private ArrayList<Equipos> listaEquipos;
    private ArrayList<Partido> listaPartidos;

    public Torneo(String nombreTorneo) {
        this.nombreTorneo = nombreTorneo;
        this.listaEquipos = new ArrayList<>();
        this.listaPartidos = new ArrayList<>();
    }

    public String getNombreTorneo() {
        return nombreTorneo;
    }

    public void inscribirEquipo(Equipos equipo) {
        if (equipo != null) {
            this.listaEquipos.add(equipo);
        }
    }

    public boolean existeEquipo(String nombre) {
        for (Equipos e : listaEquipos) {
            if (e.getNombre().equalsIgnoreCase(nombre)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Equipos> getListaEquipos() {
        return listaEquipos;
    }

    public ArrayList<Partido> getListaPartidos() {
        return listaPartidos;
    }

    public void registrarPartido(Partido partido) {
        if (partido != null) {
            listaPartidos.add(partido);
            partido.getLocal().registrarResultado(partido.getGolesLocal(), partido.getGolesVisitante());
            partido.getVisitante().registrarResultado(partido.getGolesVisitante(), partido.getGolesLocal());
        }
    }

    public ArrayList<Equipos> getTablaPosiciones() {
        ArrayList<Equipos> tabla = new ArrayList<>(listaEquipos);
        for (int i = 0; i < tabla.size() - 1; i++) {
            for (int j = 0; j < tabla.size() - 1 - i; j++) {
                Equipos a = tabla.get(j);
                Equipos b = tabla.get(j + 1);
                if (quedaAntes(b, a)) {
                    tabla.set(j, b);
                    tabla.set(j + 1, a);
                }
            }
        }
        return tabla;
    }

    private boolean quedaAntes(Equipos x, Equipos y) {
        if (x.getPuntos() != y.getPuntos()) {
            return x.getPuntos() > y.getPuntos();
        }
        if (x.getDiferenciaGoles() != y.getDiferenciaGoles()) {
            return x.getDiferenciaGoles() > y.getDiferenciaGoles();
        }
        return x.getGolesFavor() > y.getGolesFavor();
    }

    public ArrayList<Jugador> getGoleadores() {
        ArrayList<Jugador> todos = new ArrayList<>();
        for (Equipos e : listaEquipos) {
            for (Jugador j : e.getListaJugadores()) {
                todos.add(j);
            }
        }
        for (int i = 0; i < todos.size() - 1; i++) {
            for (int k = 0; k < todos.size() - 1 - i; k++) {
                Jugador a = todos.get(k);
                Jugador b = todos.get(k + 1);
                if (b.getGoles() > a.getGoles()) {
                    todos.set(k, b);
                    todos.set(k + 1, a);
                }
            }
        }
        return todos;
    }

    public Equipos getEquipoDeJugador(Jugador jugador) {
        for (Equipos e : listaEquipos) {
            if (e.getListaJugadores().contains(jugador)) {
                return e;
            }
        }
        return null;
    }
}
