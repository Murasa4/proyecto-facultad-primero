package ClasesDAO;

import Clases.Espacio;
import ConexionDB.DatabaseConnection;

import java.sql.Connection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspacioDAO {

    private Connection connection;
    private static EspacioDAO instance;

    private EspacioDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static EspacioDAO getInstance() {
        if (instance == null) {
            instance = new EspacioDAO();
        }
        return instance;
    }

    public void agregarEspacio(Espacio espacio) throws SQLException {
        String sql = "INSERT INTO proyecto.espacios (nombre, ubicacion, capacidad, fec_vigencia_precios, pre_reserva_socios, pre_reserva_no_socios, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, espacio.getNombre());
            stmt.setString(2, espacio.getUbicacion());
            stmt.setInt(3, espacio.getCapacidad());
            stmt.setDate(4, espacio.getFechaVigenciaPrecios());
            stmt.setDouble(5, espacio.getPrecioReservaSocios());
            stmt.setDouble(6, espacio.getPrecioReservaNoSocios());
            stmt.setBoolean(7, espacio.getEstado());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                espacio.setIdEspacio(generatedKeys.getInt(1));
            }
        }
    }

    public void actualizarEspacio(Espacio espacio) throws SQLException {
        String sql = "UPDATE proyecto.espacios SET nombre = ?, ubicacion = ?, capacidad = ?, fec_vigencia_precios = ?, pre_reserva_socios = ?, pre_reserva_no_socios = ?, estado = ? WHERE id_espacio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, espacio.getNombre());
            stmt.setString(2, espacio.getUbicacion());
            stmt.setInt(3, espacio.getCapacidad());
            stmt.setDate(4, espacio.getFechaVigenciaPrecios()); // Convertir LocalDate a Date
            stmt.setDouble(5, espacio.getPrecioReservaSocios());
            stmt.setDouble(6, espacio.getPrecioReservaNoSocios());
            stmt.setBoolean(7, espacio.getEstado());
            stmt.setInt(8, espacio.getIdEspacio());
            stmt.executeUpdate();
        }
    }

    public void eliminarEspacio(int idEspacio) throws SQLException {
        String sql = "UPDATE proyecto.espacios SET estado = false WHERE id_espacio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEspacio);
            stmt.executeUpdate();
        }
    }

    public Espacio obtenerEspacioPorId(int idEspacio) throws SQLException {
        String sql = "SELECT * FROM proyecto.espacios WHERE id_espacio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEspacio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Espacio.EspacioBuilder()
                        .setIdEspacio(rs.getInt("id_espacio"))
                        .setNombre(rs.getString("nombre"))
                        .setUbicacion(rs.getString("ubicacion"))
                        .setCapacidad(rs.getInt("capacidad"))
                        .setFechaVigenciaPrecios(rs.getDate("fec_vigencia_precios"))
                        .setPrecioReservaSocios(rs.getDouble("pre_reserva_socios"))
                        .setPrecioReservaNoSocios(rs.getDouble("pre_reserva_no_socios"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public List<Espacio> listarEspacios() throws SQLException {
        List<Espacio> espacios = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.espacios WHERE estado = true";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Espacio espacio = new Espacio.EspacioBuilder()
                        .setIdEspacio(rs.getInt("id_espacio"))
                        .setNombre(rs.getString("nombre"))
                        .setUbicacion(rs.getString("ubicacion"))
                        .setCapacidad(rs.getInt("capacidad"))
                        .setFechaVigenciaPrecios(rs.getDate("fec_vigencia_precios"))
                        .setPrecioReservaSocios(rs.getDouble("pre_reserva_socios"))
                        .setPrecioReservaNoSocios(rs.getDouble("pre_reserva_no_socios"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                espacios.add(espacio);
            }
        }
        return espacios;
    }

    public List<Espacio> listarEspaciosPorEstado (boolean estado) throws SQLException{
        List<Espacio> espacios = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.espacios WHERE estado = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, estado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Espacio espacio = new Espacio.EspacioBuilder()
                        .setIdEspacio(rs.getInt("id_espacio"))
                        .setNombre(rs.getString("nombre"))
                        .setUbicacion(rs.getString("ubicacion"))
                        .setCapacidad(rs.getInt("capacidad"))
                        .setFechaVigenciaPrecios(rs.getDate("fec_vigencia_precios"))
                        .setPrecioReservaSocios(rs.getDouble("pre_reserva_socios"))
                        .setPrecioReservaNoSocios(rs.getDouble("pre_reserva_no_socios"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                espacios.add(espacio);
            }
        }
        return espacios;
    }

    public List<Espacio> obtenerEspaciosPorNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        String query = "SELECT * FROM proyecto.espacios WHERE nombre ILIKE ?";

        List<Espacio> espaciosEncontrados = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + nombre + "%"); // Buscar coincidencias parciales en el nombre del espacio

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Espacio espacio = new Espacio.EspacioBuilder()
                            .setIdEspacio(rs.getInt("id_espacio"))
                            .setNombre(rs.getString("nombre"))
                            .setUbicacion(rs.getString("ubicacion"))
                            .setCapacidad(rs.getInt("capacidad"))
                            .setFechaVigenciaPrecios(rs.getDate("fec_vigencia_precios"))
                            .setPrecioReservaSocios(rs.getDouble("pre_reserva_socios"))
                            .setPrecioReservaNoSocios(rs.getDouble("pre_reserva_no_socios"))
                            .setEstado(rs.getBoolean("estado"))
                            .build();

                    espaciosEncontrados.add(espacio);
                }
            }
        }
        return espaciosEncontrados;
    }
}