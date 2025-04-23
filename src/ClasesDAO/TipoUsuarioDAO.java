package ClasesDAO;

import Clases.TipoUsuario;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoUsuarioDAO {

    private Connection connection;
    private static TipoUsuarioDAO instance;

    private TipoUsuarioDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static TipoUsuarioDAO getInstance() {
        if (instance == null) {
            instance = new TipoUsuarioDAO();
        }
        return instance;
    }

    public void agregarTipoUsuario(TipoUsuario tipoUsuario) throws SQLException {
        String sql = "INSERT INTO proyecto.tipo_usuarios (nombre, descripcion, estado) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tipoUsuario.getNombre());
            stmt.setString(2, tipoUsuario.getDescripcion());
            stmt.setBoolean(3, tipoUsuario.getEstado());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                tipoUsuario.setIdTipoUsuario(generatedKeys.getInt(1));
            }
        }
    }

    public void actualizarTipoUsuario(TipoUsuario tipoUsuario) throws SQLException {
        String sql = "UPDATE proyecto.tipo_usuarios SET nombre = ?, descripcion = ?, estado = ? WHERE id_tipo_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipoUsuario.getNombre());
            stmt.setString(2, tipoUsuario.getDescripcion());
            stmt.setBoolean(3, tipoUsuario.getEstado());
            stmt.setInt(4, tipoUsuario.getIdTipoUsuario());
            stmt.executeUpdate();
        }
    }

    public void eliminarTipoUsuario(int idTipoUsuario) throws SQLException {
        String sql = "UPDATE proyecto.tipo_usuarios SET estado = false WHERE id_tipo_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTipoUsuario);
            stmt.executeUpdate();
        }
    }

    public TipoUsuario obtenerTipoUsuarioPorId(int idTipoUsuario) throws SQLException {
        String sql = "SELECT * FROM proyecto.tipo_usuarios WHERE id_tipo_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTipoUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new TipoUsuario.TipoUsuarioBuilder()
                        .setIdTipoUsuario(rs.getInt("id_tipo_usuario"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public List<TipoUsuario> listarTiposUsuario() throws SQLException {
        List<TipoUsuario> tipoUsuarios = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.tipo_usuarios";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TipoUsuario tipoUsuario = new TipoUsuario.TipoUsuarioBuilder()
                        .setIdTipoUsuario(rs.getInt("id_tipo_usuario"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                tipoUsuarios.add(tipoUsuario);
            }
        }
        return tipoUsuarios;
    }
}