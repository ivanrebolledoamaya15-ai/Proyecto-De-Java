/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

/**
 *
 * @author MR CHAMAN
 */
public class Partido {

    private Equipos local;
    private Equipos visitante;
    private String fecha;
    private int golesLocal;
    private int golesVisitante;

    public Partido(Equipos local, Equipos visitante, String fecha, int golesLocal, int golesVisitante) {
        this.local = local;
        this.visitante = visitante;
        this.fecha = fecha;
        this.golesLocal = golesLocal;
        this.golesVisitante = golesVisitante;
    }

    public Equipos getLocal() {
        return local;
    }

    public Equipos getVisitante() {
        return visitante;
    }

    public String getFecha() {
        return fecha;
    }

    public int getGolesLocal() {
        return golesLocal;
    }

    public int getGolesVisitante() {
        return golesVisitante;
    }

    public String getMarcador() {
        return golesLocal + " - " + golesVisitante;
    }

    public String getGanador() {
        if (golesLocal > golesVisitante) {
            return local.getNombre();
        }
        if (golesVisitante > golesLocal) {
            return visitante.getNombre();
        }
        return "Empate";
    }
}
