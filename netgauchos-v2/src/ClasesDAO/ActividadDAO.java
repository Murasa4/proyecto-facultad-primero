package ClasesDAO;

import Clases.Actividad;
import Clases.TipoActividad;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActividadDAO {

    private Connection connection;
    private static ActividadDAO instance;

    public ActividadDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static ActividadDAO getInstance() {
        if (instance == null) {
            instance = new ActividadDAO();
        }
        return instance;
    }

    public void agregarActividad(Actividad actividad) throws SQLException {
        String sql = "INSERT INTO proyecto.actividades (nombre, descripcion, fecha, id_tipo_actividad, cupo, can_inscriptos, can_cancelados, estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, actividad.getNombre());
            stmt.setString(2, actividad.getDescripcion());
            stmt.setTimestamp(3, actividad.getFecha());
            stmt.setInt(4, actividad.getTipoActividad().getIdTipoActividad());
            stmt.setInt(5, actividad.getCupo());
            stmt.setInt(6, actividad.getCantidadInscriptos());
            stmt.setInt(7, actividad.getCantidadCancelados());
            stmt.setBoolean(8, actividad.getEstado());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                actividad.setIdActividad(generatedKeys.getInt(1));
            }
        }
    }

    public void actualizarActividad(Actividad actividad) throws SQLException {
        String sql = "UPDATE proyecto.actividades SET nombre = ?, descripcion = ?, fecha = ?, id_tipo_actividad = ?, cupo = ?, can_inscriptos = ?, can_cancelados = ?, estado = ? WHERE id_actividad = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, actividad.getNombre());
            stmt.setString(2, actividad.getDescripcion());
            stmt.setTimestamp(3, actividad.getFecha());
            stmt.setInt(4, actividad.getTipoActividad().getIdTipoActividad());
            stmt.setInt(5, actividad.getCupo());
            stmt.setInt(6, actividad.getCantidadInscriptos());
            stmt.setInt(7, actividad.getCantidadCancelados());
            stmt.setBoolean(8, actividad.getEstado());
            stmt.setInt(9, actividad.getIdActividad());
            stmt.executeUpdate();
        }
    }

    public void eliminarActividad(int idActividad) throws SQLException {
        String sql = "UPDATE proyecto.actividades SET estado = false WHERE id_actividad = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idActividad);
            stmt.executeUpdate();
        }
    }

    public List<Actividad> listarActividadesPorFecha(Date fecha) throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.actividades WHERE DATE(fecha) = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, new java.sql.Date(fecha.getTime()));
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Actividad actividad = new Actividad.ActividadBuilder()
                        .setIdActividad(rs.getInt("id_actividad"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setFecha(rs.getTimestamp("fecha"))
                        .setTipoActividad(new TipoActividad.TipoActividadBuilder()
                                .setIdTipoActividad(rs.getInt("id_tipo_actividad")).build())
                        .setCupo(rs.getInt("cupo"))
                        .setCantidadInscriptos(rs.getInt("can_inscriptos"))
                        .setCantidadCancelados(rs.getInt("can_cancelados"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                actividades.add(actividad);
            }
        }
        return actividades;
    }

    public List<Actividad> listarActividadesPorTipo(int idTipoActividad) throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.actividades WHERE id_tipo_actividad = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTipoActividad);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Actividad actividad = new Actividad.ActividadBuilder()
                        .setIdActividad(rs.getInt("id_actividad"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setFecha(rs.getTimestamp("fecha"))
                        .setTipoActividad(new TipoActividad.TipoActividadBuilder()
                                .setIdTipoActividad(rs.getInt("id_tipo_actividad")).build())
                        .setCupo(rs.getInt("cupo"))
                        .setCantidadInscriptos(rs.getInt("can_inscriptos"))
                        .setCantidadCancelados(rs.getInt("can_cancelados"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                actividades.add(actividad);
            }
        }
        return actividades;
    }

    public List<Actividad> listarActividadesPorEstado(boolean estado) throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.actividades WHERE estado = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setBoolean(1, estado);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Actividad actividad = new Actividad.ActividadBuilder()
                        .setIdActividad(rs.getInt("id_actividad"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setFecha(rs.getTimestamp("fecha"))
                        .setTipoActividad(new TipoActividad.TipoActividadBuilder()
                                .setIdTipoActividad(rs.getInt("id_tipo_actividad")).build())
                        .setCupo(rs.getInt("cupo"))
                        .setCantidadInscriptos(rs.getInt("can_inscriptos"))
                        .setCantidadCancelados(rs.getInt("can_cancelados"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                actividades.add(actividad);
            }
        }
        return actividades;
    }

    public List<Actividad> obtenerActividadesPorNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        String query = "SELECT * FROM proyecto.actividades WHERE nombre ILIKE ?";

        List<Actividad> actividadesEncontradas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + nombre + "%"); // Coincidencias parciales en el nombre

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Actividad actividad = new Actividad.ActividadBuilder()
                            .setIdActividad(rs.getInt("id_actividad"))
                            .setNombre(rs.getString("nombre"))
                            .setDescripcion(rs.getString("descripcion"))
                            .setFecha(rs.getTimestamp("fecha"))
                            .setTipoActividad(new TipoActividad.TipoActividadBuilder()
                                    .setIdTipoActividad(rs.getInt("id_tipo_actividad")).build())
                            .setCupo(rs.getInt("cupo"))
                            .setCantidadInscriptos(rs.getInt("can_inscriptos"))
                            .setCantidadCancelados(rs.getInt("can_cancelados"))
                            .setEstado(rs.getBoolean("estado"))
                            .build();

                    actividadesEncontradas.add(actividad);
                }
            }
        }
        return actividadesEncontradas;
    }

    public List<Actividad> listarActividadesDisponiblesParaInscripcion() throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.actividades " +
                "WHERE estado = TRUE " +
                "AND fecha >= CURRENT_TIMESTAMP " +
                "AND can_inscriptos < cupo";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Actividad actividad = new Actividad.ActividadBuilder()
                        .setIdActividad(rs.getInt("id_actividad"))
                        .setNombre(rs.getString("nombre"))
                        .setDescripcion(rs.getString("descripcion"))
                        .setFecha(rs.getTimestamp("fecha"))
                        .setTipoActividad(new TipoActividad.TipoActividadBuilder()
                                .setIdTipoActividad(rs.getInt("id_tipo_actividad")).build())
                        .setCupo(rs.getInt("cupo"))
                        .setCantidadInscriptos(rs.getInt("can_inscriptos"))
                        .setCantidadCancelados(rs.getInt("can_cancelados"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();

                actividades.add(actividad);
            }
        }

        return actividades;
    }
}