package ClasesDAO;

import Clases.CategoriaSocio;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoriaSocioDAO {

    private Connection connection;
    private static CategoriaSocioDAO instance;

    private CategoriaSocioDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static CategoriaSocioDAO getInstance() {
        if (instance == null) {
            instance = new CategoriaSocioDAO();
        }
        return instance;
    }

    public void agregarCategoriaSocio(CategoriaSocio categoriaSocio) throws SQLException {
        String sql = "INSERT INTO proyecto.cate_socios (nombre, descripcion, estado) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, categoriaSocio.getNombre());
            stmt.setString(2, categoriaSocio.getDescripcion());
            stmt.setBoolean(3, categoriaSocio.getEstado());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                categoriaSocio.setIdCategoriaSocio(generatedKeys.getInt(1));
            }
        }
    }

    public void actualizarCategoriaSocio(CategoriaSocio categoriaSocio) throws SQLException {
        String sql = "UPDATE proyecto.cate_socios SET nombre = ?, descripcion = ?, estado = ? WHERE id_categoria_socio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, categoriaSocio.getNombre());
            stmt.setString(2, categoriaSocio.getDescripcion());
            stmt.setBoolean(3, categoriaSocio.getEstado());
            stmt.setInt(4, categoriaSocio.getIdCategoriaSocio());
            stmt.executeUpdate();
        }
    }

    public void eliminarCategoriaSocio(int idCategoriaSocio) throws SQLException {
        String sql = "UPDATE proyecto.cate_socios SET estado = false WHERE id_categoria_socio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCategoriaSocio);
            stmt.executeUpdate();
        }
    }

    public CategoriaSocio obtenerCategoriaSocioPorId(int idCategoriaSocio) throws SQLException {
        String sql = "SELECT * FROM proyecto.cate_socios WHERE id_categoria_socio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idCategoriaSocio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new CategoriaSocio.CategoriaSocioBuilder()
                        .setIdCategoriaSocio(rs.getInt("id_categoria_socio"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public List<CategoriaSocio> listarCategoriaSocios() throws SQLException {
        List<CategoriaSocio> categoriaSocios = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.cate_socios";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                CategoriaSocio categoriaSocio = new CategoriaSocio.CategoriaSocioBuilder()
                        .setIdCategoriaSocio(rs.getInt("id_categoria_socio"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                categoriaSocios.add(categoriaSocio);
            }
        }
        return categoriaSocios;
    }
}