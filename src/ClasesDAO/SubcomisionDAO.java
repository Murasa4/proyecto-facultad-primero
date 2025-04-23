package ClasesDAO;

import Clases.Subcomision;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SubcomisionDAO {

    private Connection connection;
    private static SubcomisionDAO instance;

    public SubcomisionDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static SubcomisionDAO getInstance() {
        if (instance == null) {
            instance = new SubcomisionDAO();
        }
        return instance;
    }

    public void agregarSubcomision(Subcomision subcomision) throws SQLException {
        String sql = "INSERT INTO proyecto.subcomisiones (nombre, descripcion, estado) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, subcomision.getNombre());
            stmt.setString(2, subcomision.getDescripcion());
            stmt.setBoolean(3, subcomision.getEstado());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                subcomision.setIdSubcomision(generatedKeys.getInt(1));
            }
        }
    }

    public void actualizarSubcomision(Subcomision subcomision) throws SQLException {
        String sql = "UPDATE proyecto.subcomisiones SET nombre = ?, descripcion = ?, estado = ? WHERE id_subcomision = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, subcomision.getNombre());
            stmt.setString(2, subcomision.getDescripcion());
            stmt.setBoolean(3, subcomision.getEstado());
            stmt.setInt(4, subcomision.getIdSubcomision());
            stmt.executeUpdate();
        }
    }

    public void eliminarSubcomision(int idSubcomision) throws SQLException {
        String sql = "UPDATE proyecto.subcomisiones SET estado = false WHERE id_subcomision = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSubcomision);
            stmt.executeUpdate();
        }
    }

    public Subcomision obtenerSubcomisionPorId(int idSubcomision) throws SQLException {
        String sql = "SELECT * FROM proyecto.subcomisiones WHERE id_subcomision = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idSubcomision);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Subcomision.SubcomisionBuilder()
                        .setIdSubcomision(rs.getInt("id_subcomision"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public List<Subcomision> listarSubcomisiones() throws SQLException {
        List<Subcomision> subcomisiones = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.subcomisiones";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Subcomision subcomision = new Subcomision.SubcomisionBuilder()
                        .setIdSubcomision(rs.getInt("id_subcomision"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                subcomisiones.add(subcomision);
            }
        }
        return subcomisiones;
    }
}