package ClasesDAO;

import Clases.Actividad;
import Clases.Inscripcion;
import Clases.TipoActividad;
import Clases.Usuario;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscripcionDAO {

    private Connection connection;
    private static InscripcionDAO instance;

    public InscripcionDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static InscripcionDAO getInstance() {
        if (instance == null) {
            instance = new InscripcionDAO();
        }
        return instance;
    }

    public void agregarInscripcion(Inscripcion inscripcion) throws SQLException {
        String sqlInsert = "INSERT INTO proyecto.inscripciones (id_usuario, id_actividad, fec_inscripcion, estado) " +
                "VALUES (?, ?, ?, ?)";
        String sqlUpdate = "UPDATE proyecto.actividades SET can_inscriptos = can_inscriptos + 1 " +
                "WHERE id_actividad = ?";

        try (PreparedStatement stmtInsert = connection.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS)) {
            stmtInsert.setInt(1, inscripcion.getUsuario().getIdUsuario());
            stmtInsert.setInt(2, inscripcion.getActividad().getIdActividad());
            stmtInsert.setTimestamp(3, inscripcion.getFechaInscripcion());
            stmtInsert.setBoolean(4, inscripcion.getEstado());
            stmtInsert.executeUpdate();

            ResultSet generatedKeys = stmtInsert.getGeneratedKeys();
            if (generatedKeys.next()) {
                inscripcion.setIdInscripcion(generatedKeys.getInt(1));
            }

            try (PreparedStatement stmtUpdate = connection.prepareStatement(sqlUpdate)) {
                stmtUpdate.setInt(1, inscripcion.getActividad().getIdActividad());
                stmtUpdate.executeUpdate();
            }
        }
    }

    public List<Inscripcion> listarInscripcionesPorActividad(int idActividad) throws SQLException {
        List<Inscripcion> inscripciones = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.inscripciones WHERE id_actividad = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idActividad);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Inscripcion inscripcion = new Inscripcion.InscripcionBuilder()
                            .setIdInscripcion(rs.getInt("id_inscripcion"))
                            .setUsuario(new Usuario.UsuarioBuilder().setIdUsuario(rs.getInt("id_usuario")).build())
                            .setActividad(new Actividad.ActividadBuilder().setIdActividad(rs.getInt("id_actividad")).build())
                            .setFechaInscripcion(rs.getTimestamp("fec_inscripcion"))
                            .setEstado(rs.getBoolean("estado"))
                            .build();
                    inscripciones.add(inscripcion);
                }
            }
        }
        return inscripciones;
    }

    public boolean usuarioYaInscrito(int idUsuario, int idActividad) throws SQLException {
        String sql = "SELECT 1 FROM proyecto.inscripciones " +
                "WHERE id_usuario = ? AND id_actividad = ? AND estado = true " +
                "LIMIT 1";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idActividad);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Si hay algún resultado, ya está inscripto.
            }
        }
    }

    public List<Actividad> obtenerActividadesPorUsuario(int idUsuario) throws SQLException {
        List<Actividad> actividades = new ArrayList<>();
        String sql = "SELECT a.* " +
                "FROM proyecto.actividades a " +
                "JOIN proyecto.inscripciones i ON a.id_actividad = i.id_actividad " +
                "WHERE i.id_usuario = ? AND i.estado = true";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Actividad actividad = new Actividad.ActividadBuilder()
                            .setIdActividad(rs.getInt("id_actividad"))
                            .setNombre(rs.getString("nombre"))
                            .setDescripcion(rs.getString("descripcion"))
                            // Convertir Timestamp a LocalDateTime
                            .setFecha(rs.getTimestamp("fecha"))
                            .setTipoActividad(new TipoActividad.TipoActividadBuilder()
                                    .setIdTipoActividad(rs.getInt("id_tipo_actividad")).build())
                            .setCupo(rs.getInt("cupo"))
                            .setCantidadInscriptos(rs.getInt("can_inscriptos"))
                            .setCantidadCancelados(rs.getInt("can_cancelados"))
                            .setEstado(rs.getBoolean("estado"))
                            .build();

                    // Agregar actividad a la lista
                    actividades.add(actividad);
                }
            }
        }

        return actividades;
    }
}