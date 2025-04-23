package ClasesDAO;

import Clases.Espacio;
import Clases.Reserva;
import Clases.Usuario;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {

    private Connection connection;
    private static ReservaDAO instance;

    private ReservaDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static ReservaDAO getInstance() {
        if (instance == null) {
            instance = new ReservaDAO();
        }
        return instance;
    }

    public void agregarReserva(Reserva reserva) throws SQLException {
        String sql = "INSERT INTO proyecto.reservas (id_usuario, fec_creada, fec_reserva, hor_inicio, hor_fin, id_espacio, mon_senia, saldo, mon_total, can_personas, estado) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, reserva.getUsuario().getIdUsuario());
            stmt.setTimestamp(2, reserva.getFechaCreada());
            stmt.setDate(3, reserva.getFechaReserva());
            stmt.setTime(4, reserva.getHoraInicio());
            stmt.setTime(5, reserva.getHoraFin());
            stmt.setInt(6, reserva.getEspacio().getIdEspacio());
            stmt.setDouble(7, reserva.getMontoSenia());
            stmt.setDouble(8, reserva.getSaldo());
            stmt.setDouble(9, reserva.getMontoTotal());
            stmt.setInt(10, reserva.getCantidadPersonas());
            stmt.setBoolean(11, reserva.getEstado());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                reserva.setIdReserva(generatedKeys.getInt(1));
            }
        }
    }

    public void actualizarReserva(Reserva reserva) throws SQLException {
        String sql = "UPDATE proyecto.reservas SET id_usuario = ?, fec_creada = ?, fec_reserva = ?, hor_inicio = ?, hor_fin = ?, id_espacio = ?, " +
                "mon_senia = ?, saldo = ?, mon_total = ?, can_personas = ?, estado = ? WHERE id_reserva = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, reserva.getUsuario().getIdUsuario());
            stmt.setTimestamp(2, reserva.getFechaCreada());
            stmt.setDate(3, reserva.getFechaReserva());
            stmt.setTime(4, reserva.getHoraInicio());
            stmt.setTime(5, reserva.getHoraFin());
            stmt.setInt(6, reserva.getEspacio().getIdEspacio());
            stmt.setDouble(7, reserva.getMontoSenia());
            stmt.setDouble(8, reserva.getSaldo());
            stmt.setDouble(9, reserva.getMontoTotal());
            stmt.setInt(10, reserva.getCantidadPersonas());
            stmt.setBoolean(11, reserva.getEstado());
            stmt.setInt(12, reserva.getIdReserva());
            stmt.executeUpdate();
        }
    }

    public void eliminarReserva(int idReserva) throws SQLException {
        String sql = "UPDATE proyecto.reservas SET estado = false WHERE id_reserva = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            stmt.executeUpdate();
        }
    }

    public Reserva obtenerReservaPorId(int idReserva) throws SQLException {
        String sql = "SELECT * FROM proyecto.reservas WHERE id_reserva = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idReserva);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Reserva.ReservaBuilder()
                        .setIdReserva(rs.getInt("id_reserva"))
                        .setUsuario(new Usuario.UsuarioBuilder().setIdUsuario(rs.getInt("id_usuario")).build())
                        .setFechaCreada(rs.getTimestamp("fec_creada"))
                        .setFechaReserva(rs.getDate("fec_reserva"))
                        .setHoraInicio(rs.getTime("hor_inicio"))
                        .setHoraFin(rs.getTime("hor_fin"))
                        .setEspacio(new Espacio.EspacioBuilder().setIdEspacio(rs.getInt("id_espacio")).build())
                        .setMontoSenia(rs.getDouble("mon_senia"))
                        .setSaldo(rs.getDouble("saldo"))
                        .setMontoTotal(rs.getDouble("mon_total"))
                        .setCantidadPersonas(rs.getInt("can_personas"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public List<Reserva> listarReservas() throws SQLException {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.reservas";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva.ReservaBuilder()
                        .setIdReserva(rs.getInt("id_reserva"))
                        .setUsuario(new Usuario.UsuarioBuilder().setIdUsuario(rs.getInt("id_usuario")).build())
                        .setFechaCreada(rs.getTimestamp("fec_creada"))
                        .setFechaReserva(rs.getDate("fec_reserva"))
                        .setHoraInicio(rs.getTime("hor_inicio"))
                        .setHoraFin(rs.getTime("hor_fin"))
                        .setEspacio(new Espacio.EspacioBuilder().setIdEspacio(rs.getInt("id_espacio")).build())
                        .setMontoSenia(rs.getDouble("mon_senia"))
                        .setSaldo(rs.getDouble("saldo"))
                        .setMontoTotal(rs.getDouble("mon_total"))
                        .setCantidadPersonas(rs.getInt("can_personas"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                reservas.add(reserva);
            }
        }
        return reservas;
    }

    public List<Reserva> obtenerReservasActivasPorUsuario(int idUsuario) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT r.*, u.*, e.* FROM reservas r " +
                "JOIN usuarios u ON r.id_usuario = u.id_usuario " +
                "JOIN espacios e ON r.id_espacio = e.id_espacio " +
                "WHERE r.estado = true AND u.estado = true AND r.id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Reserva reserva = new Reserva.ReservaBuilder()
                        .setIdReserva(rs.getInt("id_reserva"))
                        .setUsuario(new Usuario.UsuarioBuilder()
                                .setIdUsuario(rs.getInt("id_usuario"))
                                .setPrimerNombre(rs.getString("pri_nombre"))
                                .setPrimerApellido(rs.getString("pri_apellido"))
                                .setNumeroDocumento(rs.getString("num_documento"))
                                .build())
                        .setFechaCreada(rs.getTimestamp("fec_creada"))
                        .setFechaReserva(rs.getDate("fec_reserva"))
                        .setHoraInicio(rs.getTime("hor_inicio"))
                        .setHoraFin(rs.getTime("hor_fin"))
                        .setEspacio(new Espacio.EspacioBuilder()
                                .setIdEspacio(rs.getInt("id_espacio"))
                                .setNombre(rs.getString("nombre"))
                                .build())
                        .setMontoSenia(rs.getDouble("mon_senia"))
                        .setSaldo(rs.getDouble("saldo"))
                        .setMontoTotal(rs.getDouble("mon_total"))
                        .setCantidadPersonas(rs.getInt("can_personas"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservas;
    }

    public List<Reserva> obtenerReservasPorEspacioYFecha(int idEspacio, Date fechaInicial, Date fechaFinal) {
        List<Reserva> reservas = new ArrayList<>();
        String sql = "SELECT r.*, u.*, e.* FROM reservas r " +
                "JOIN usuarios u ON r.id_usuario = u.id_usuario " +
                "JOIN espacios e ON r.id_espacio = e.id_espacio " +
                "WHERE e.id_espacio = ? " +
                "AND r.fec_reserva BETWEEN ? AND ? " +
                "AND r.estado = true " +
                "ORDER BY r.fec_reserva ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEspacio);
            stmt.setDate(2, new java.sql.Date(fechaInicial.getTime()));
            stmt.setDate(3, new java.sql.Date(fechaFinal.getTime()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Reserva reserva = new Reserva.ReservaBuilder()
                        .setIdReserva(rs.getInt("id_reserva"))
                        .setUsuario(new Usuario.UsuarioBuilder()
                                .setIdUsuario(rs.getInt("id_usuario"))
                                .setPrimerNombre(rs.getString("pri_nombre"))
                                .setPrimerApellido(rs.getString("pri_apellido"))
                                .setNumeroDocumento(rs.getString("num_documento"))
                                .build())
                        .setFechaCreada(rs.getTimestamp("fec_creada"))
                        .setFechaReserva(rs.getDate("fec_reserva"))
                        .setHoraInicio(rs.getTime("hor_inicio"))
                        .setHoraFin(rs.getTime("hor_fin"))
                        .setEspacio(new Espacio.EspacioBuilder()
                                .setIdEspacio(rs.getInt("id_espacio"))
                                .setNombre(rs.getString("nombre"))
                                .build())
                        .setMontoSenia(rs.getDouble("mon_senia"))
                        .setSaldo(rs.getDouble("saldo"))
                        .setMontoTotal(rs.getDouble("mon_total"))
                        .setCantidadPersonas(rs.getInt("can_personas"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                reservas.add(reserva);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservas;
    }

    public List<Reserva> obtenerReservasPorEspacio(int idEspacio) throws SQLException {
        String sql = "SELECT r.*, u.* FROM proyecto.reservas r " +
                "JOIN usuarios u ON r.id_usuario = u.id_usuario " +
                "WHERE r.id_espacio = ? AND r.estado = true";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idEspacio);
            ResultSet rs = stmt.executeQuery();

            List<Reserva> reservas = new ArrayList<>();

            while (rs.next()) {
                reservas.add(new Reserva.ReservaBuilder()
                        .setIdReserva(rs.getInt("id_reserva"))
                        .setUsuario(new Usuario.UsuarioBuilder()
                                .setIdUsuario(rs.getInt("id_usuario"))
                                .setPrimerNombre(rs.getString("pri_nombre"))
                                .setPrimerApellido(rs.getString("pri_apellido"))
                                .setNumeroDocumento(rs.getString("num_documento"))
                                .build())
                        .setFechaCreada(rs.getTimestamp("fec_creada"))
                        .setFechaReserva(rs.getDate("fec_reserva"))
                        .setHoraInicio(rs.getTime("hor_inicio"))
                        .setHoraFin(rs.getTime("hor_fin"))
                        .setEspacio(new Espacio.EspacioBuilder().setIdEspacio(rs.getInt("id_espacio")).build())
                        .setMontoSenia(rs.getDouble("mon_senia"))
                        .setSaldo(rs.getDouble("saldo"))
                        .setMontoTotal(rs.getDouble("mon_total"))
                        .setCantidadPersonas(rs.getInt("can_personas"))
                        .setEstado(rs.getBoolean("estado"))
                        .build());
            }

            return reservas;
        }
    }
}