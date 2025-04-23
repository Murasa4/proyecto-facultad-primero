package ClasesDAO;

import Clases.Pago;
import Clases.Usuario;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PagoDAO {

    private Connection connection;
    private static PagoDAO instance;

    private PagoDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static PagoDAO getInstance() {
        if (instance == null) {
            instance = new PagoDAO();
        }
        return instance;
    }

    public void agregarPago(Pago pago) throws SQLException {
        String sql = "INSERT INTO proyecto.pagos (fecha, monto, id_usuario, estado) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setTimestamp(1, pago.getFecha());
            stmt.setDouble(2, pago.getMonto());
            stmt.setInt(3, pago.getUsuario().getIdUsuario());
            stmt.setBoolean(4, pago.getEstado());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                pago.setIdPago(generatedKeys.getInt(1));
            }
        }
    }

    public void actualizarPago(Pago pago) throws SQLException {
        String sql = "UPDATE proyecto.pagos SET fecha = ?, monto = ?, id_usuario = ?, estado = ? WHERE id_pago = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, pago.getFecha());
            stmt.setDouble(2, pago.getMonto());
            stmt.setInt(3, pago.getUsuario().getIdUsuario());
            stmt.setBoolean(4, pago.getEstado());
            stmt.setInt(5, pago.getIdPago());
            stmt.executeUpdate();
        }
    }

    public void eliminarPago(int idPago) throws SQLException {
        String sql = "UPDATE proyecto.pagos SET estado = false WHERE id_pago = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPago);
            stmt.executeUpdate();
        }
    }

    public Pago obtenerPagoPorId(int idPago) throws SQLException {
        String sql = "SELECT * FROM proyecto.pagos WHERE id_pago = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idPago);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Pago.PagoBuilder()
                        .setIdPago(rs.getInt("id_pago"))
                        .setFecha(rs.getTimestamp("fecha"))
                        .setMonto(rs.getDouble("monto"))
                        .setUsuario(new Usuario.UsuarioBuilder().setIdUsuario(rs.getInt("id_usuario")).build())
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public List<Pago> obtenerPagoPorEstado(boolean estado) throws SQLException {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT * FROM pagos WHERE estado = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, estado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pago pago = new Pago.PagoBuilder()
                        .setIdPago(rs.getInt("id_pago"))
                        .setFecha(rs.getTimestamp("fecha"))
                        .setMonto(rs.getDouble("monto"))
                        .setUsuario(new Usuario.UsuarioBuilder().setIdUsuario(rs.getInt("id_usuario")).build())
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                pagos.add(pago);
            }
        }
        return pagos;
    }

    public List<Pago> listarPagos() throws SQLException {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.pagos";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pago pago = new Pago.PagoBuilder()
                        .setIdPago(rs.getInt("id_pago"))
                        .setFecha(rs.getTimestamp("fecha"))
                        .setMonto(rs.getDouble("monto"))
                        .setUsuario(new Usuario.UsuarioBuilder().setIdUsuario(rs.getInt("id_usuario")).build())
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                pagos.add(pago);
            }
        }
        return pagos;
    }

    public List<Pago> obtenerPagosPorUsuario(Usuario usuario) throws SQLException {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.pagos WHERE id_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getIdUsuario());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pago pago = new Pago.PagoBuilder()
                        .setIdPago(rs.getInt("id_pago"))
                        .setFecha(rs.getTimestamp("fecha"))
                        .setMonto(rs.getDouble("monto"))
                        .setUsuario(usuario) // ya lo tenés, no hace falta crear otro
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                pagos.add(pago);
            }
        }
        return pagos;
    }

    public List<Pago> obtenerPagosActivosPorUsuario(Usuario usuario) throws SQLException {
        List<Pago> pagos = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.pagos WHERE id_usuario = ? AND estado = true";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuario.getIdUsuario());
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Pago pago = new Pago.PagoBuilder()
                        .setIdPago(rs.getInt("id_pago"))
                        .setFecha(rs.getTimestamp("fecha"))
                        .setMonto(rs.getDouble("monto"))
                        .setUsuario(usuario) // ya lo tenés, no hace falta crear otro
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                pagos.add(pago);
            }
        }
        return pagos;
    }
}