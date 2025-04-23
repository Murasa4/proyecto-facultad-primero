package ClasesDAO;

import Clases.TipoActividad;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoActividadDAO {

    private Connection connection;
    private static TipoActividadDAO instance;

    private TipoActividadDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static TipoActividadDAO getInstance() {
        if (instance == null) {
            instance = new TipoActividadDAO();
        }
        return instance;
    }

    public void agregarTipoActividad(TipoActividad tipoActividad) throws SQLException {
        String sql = "INSERT INTO proyecto.tipo_actividades (nombre, descripcion, estado) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tipoActividad.getNombre());
            stmt.setString(2, tipoActividad.getDescripcion());
            stmt.setBoolean(3, tipoActividad.getEstado());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                tipoActividad.setIdTipoActividad(generatedKeys.getInt(1));
            }
        }
    }

    public void actualizarTipoActividad(TipoActividad tipoActividad) throws SQLException {
        String sql = "UPDATE proyecto.tipo_actividades SET nombre = ?, descripcion = ?, estado = ? WHERE id_tipo_actividad = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipoActividad.getNombre());
            stmt.setString(2, tipoActividad.getDescripcion());
            stmt.setBoolean(3, tipoActividad.getEstado());
            stmt.setInt(4, tipoActividad.getIdTipoActividad());
            stmt.executeUpdate();
        }
    }

    public void eliminarTipoActividad(int idTipoActividad) throws SQLException {
        String sql = "UPDATE proyecto.tipo_actividades SET estado = false WHERE id_tipo_actividad = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTipoActividad);
            stmt.executeUpdate();
        }
    }

    public TipoActividad obtenerTipoActividadPorId(int idTipoActividad) throws SQLException {
        String sql = "SELECT * FROM proyecto.tipo_actividades WHERE id_tipo_actividad = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTipoActividad);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new TipoActividad.TipoActividadBuilder()
                        .setIdTipoActividad(rs.getInt("id_tipo_actividad"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public List<TipoActividad> obtenerTipoActividadPorEstado(boolean estado) throws SQLException {
        String sql = "SELECT * FROM proyecto.tipo_actividades WHERE estado = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, estado);
            ResultSet rs = stmt.executeQuery();

            List<TipoActividad> tiposActividades = new ArrayList<>();
            while (rs.next()) {
                tiposActividades.add(new TipoActividad.TipoActividadBuilder()
                        .setIdTipoActividad(rs.getInt("id_tipo_actividad"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setEstado(rs.getBoolean("estado"))
                        .build());
            }
            return tiposActividades;
        }
    }

    public List<TipoActividad> listarTiposActividades() throws SQLException {
        List<TipoActividad> tiposActividades = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.tipo_actividades WHERE estado = true";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TipoActividad tipoActividad = new TipoActividad.TipoActividadBuilder()
                        .setIdTipoActividad(rs.getInt("id_tipo_actividad"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                tiposActividades.add(tipoActividad);
            }
        }
        return tiposActividades;
    }

    public boolean existeNombre(String nombre) {
        String sql = "SELECT COUNT(*) FROM proyecto.tipo_actividades WHERE LOWER(nombre) = LOWER(?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existeDescripcion(String descripcion) {
        String sql = "SELECT COUNT(*) FROM proyecto.tipo_actividades WHERE LOWER(descripcion) = LOWER(?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, descripcion);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<TipoActividad> listarTiposActividadesPorEstado(boolean estado) throws SQLException {
        List<TipoActividad> tiposActividades = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.tipo_actividades WHERE estado = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, estado);  // Establecer el valor del estado
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TipoActividad tipoActividad = new TipoActividad.TipoActividadBuilder()
                        .setIdTipoActividad(rs.getInt("id_tipo_actividad"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                tiposActividades.add(tipoActividad);
            }
        }
        return tiposActividades;
    }
}
