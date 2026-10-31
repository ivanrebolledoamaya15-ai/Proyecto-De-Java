/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author MR CHAMAN
 */
public class BaseDatos {

    private ConexionBD conexionBD = new ConexionBD();

    public int login(String usuario, String clave) throws SQLException {
        try (Connection con = conexionBD.getConexion();
                CallableStatement cs = con.prepareCall("{CALL sp_login(?, ?)}")) {
            cs.setString(1, usuario);
            cs.setString(2, clave);
            try (ResultSet rs = cs.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }
        return 0;
    }

    public void registrarEquipo(String nombre, String entrenador, int cantidad) throws SQLException {
        try (Connection con = conexionBD.getConexion();
                CallableStatement cs = con.prepareCall("{CALL sp_registrar_equipo(?, ?, ?)}")) {
            cs.setString(1, nombre);
            cs.setString(2, entrenador);
            cs.setInt(3, cantidad);
            cs.execute();
        }
    }

    public void registrarJugador(String nombre, int edad, String posicion, int idEquipo) throws SQLException {
        try (Connection con = conexionBD.getConexion();
                CallableStatement cs = con.prepareCall("{CALL sp_registrar_jugador(?, ?, ?, ?)}")) {
            cs.setString(1, nombre);
            cs.setInt(2, edad);
            cs.setString(3, posicion);
            cs.setInt(4, idEquipo);
            cs.execute();
        }
    }

    public void registrarPartido(int idLocal, int idVisitante, String fecha, int golesLocal, int golesVisitante) throws SQLException {
        try (Connection con = conexionBD.getConexion();
                CallableStatement cs = con.prepareCall("{CALL sp_registrar_partido(?, ?, ?, ?, ?)}")) {
            cs.setInt(1, idLocal);
            cs.setInt(2, idVisitante);
            cs.setString(3, fecha);
            cs.setInt(4, golesLocal);
            cs.setInt(5, golesVisitante);
            cs.execute();
        }
    }

    public void registrarGoles(int idJugador, int goles) throws SQLException {
        try (Connection con = conexionBD.getConexion();
                CallableStatement cs = con.prepareCall("{CALL sp_registrar_goles(?, ?)}")) {
            cs.setInt(1, idJugador);
            cs.setInt(2, goles);
            cs.execute();
        }
    }

    public boolean existeEquipo(String nombre) throws SQLException {
        try (Connection con = conexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM equipos WHERE nombre = ?")) {
            ps.setString(1, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public boolean existeJugador(int idEquipo, String nombre) throws SQLException {
        try (Connection con = conexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM jugadores WHERE id_equipo = ? AND nombre = ?")) {
            ps.setInt(1, idEquipo);
            ps.setString(2, nombre);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public ArrayList<Object[]> listarEquipos() throws SQLException {
        return consultar("SELECT id, nombre FROM equipos ORDER BY id");
    }

    public ArrayList<Object[]> listarJugadores(int idEquipo) throws SQLException {
        try (Connection con = conexionBD.getConexion();
                PreparedStatement ps = con.prepareStatement("SELECT id, nombre, edad, posicion, goles FROM jugadores WHERE id_equipo = ? ORDER BY id")) {
            ps.setInt(1, idEquipo);
            try (ResultSet rs = ps.executeQuery()) {
                return leerFilas(rs);
            }
        }
    }

    public ArrayList<Object[]> consultar(String sql) throws SQLException {
        try (Connection con = conexionBD.getConexion();
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(sql)) {
            return leerFilas(rs);
        }
    }

    private ArrayList<Object[]> leerFilas(ResultSet rs) throws SQLException {
        ArrayList<Object[]> filas = new ArrayList<>();
        int columnas = rs.getMetaData().getColumnCount();
        while (rs.next()) {
            Object[] fila = new Object[columnas];
            for (int i = 0; i < columnas; i++) {
                fila[i] = rs.getObject(i + 1);
            }
            filas.add(fila);
        }
        return filas;
    }
}
