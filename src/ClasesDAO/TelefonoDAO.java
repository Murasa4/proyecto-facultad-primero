package ClasesDAO;

import Clases.Telefono;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TelefonoDAO {

    private Connection connection;
    private static TelefonoDAO instance;

    public TelefonoDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static TelefonoDAO getInstance() {
        if (instance == null) {
            instance = new TelefonoDAO();
        }
        return instance;
    }

    public void agregarTelefono(Telefono telefono) throws SQLException {
        String sql = "INSERT INTO proyecto.telefonos (id_usuario, num_telefono, estado) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, telefono.getIdUsuario());
            stmt.setInt(2, telefono.getNumeroTelefono());
            stmt.setBoolean(3, telefono.getEstado());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                telefono.setIdTelefono(generatedKeys.getInt(1));
            }
        }
    }

    public void actualizarTelefono(Telefono telefono) throws SQLException {
        String sql = "UPDATE proyecto.telefonos SET num_telefono = ?, estado = ? WHERE id_telefono = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, telefono.getNumeroTelefono());
            stmt.setBoolean(2, telefono.getEstado());
            stmt.setInt(3, telefono.getIdTelefono());
            stmt.executeUpdate();
        }
    }

    public void eliminarTelefono(int idTelefono) throws SQLException {
        String sql = "UPDATE proyecto.telefonos SET estado = false WHERE id_telefono = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTelefono);
            stmt.executeUpdate();
        }
    }

    public Telefono obtenerTelefonoPorId(int idTelefono) throws SQLException {
        String sql = "SELECT * FROM proyecto.telefonos WHERE id_telefono = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTelefono);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Telefono.TelefonoBuilder()
                        .setIdTelefono(rs.getInt("id_telefono"))
                        .setIdUsuario(rs.getInt("id_usuario"))
                        .setNumeroTelefono(rs.getInt("num_telefono"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public List<Telefono> listarTelefonos() throws SQLException {
        List<Telefono> telefonos = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.telefonos";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Telefono telefono = new Telefono.TelefonoBuilder()
                        .setIdTelefono(rs.getInt("id_telefono"))
                        .setIdUsuario(rs.getInt("id_usuario"))
                        .setNumeroTelefono(rs.getInt("num_telefono"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                telefonos.add(telefono);
            }
        }
        return telefonos;
    }

    public List<Telefono> obtenerTelefonosPorUsuario(int idUsuario) throws SQLException {
        List<Telefono> telefonos = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.telefonos WHERE id_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Telefono telefono = new Telefono.TelefonoBuilder()
                        .setIdTelefono(rs.getInt("id_telefono"))
                        .setIdUsuario(rs.getInt("id_usuario"))
                        .setNumeroTelefono(rs.getInt("num_telefono"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                telefonos.add(telefono);
            }
        }
        return telefonos;
    }
}