package ClasesDAO;

import Clases.Actividad;
import Clases.CancelacionInscripcion;
import Clases.Inscripcion;
import ConexionDB.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CancelacionInscripcionDAO {

    private Connection connection;
    private static CancelacionInscripcionDAO instance;

    private CancelacionInscripcionDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static CancelacionInscripcionDAO getInstance() {
        if (instance == null) {
            instance = new CancelacionInscripcionDAO();
        }
        return instance;
    }

    public List<CancelacionInscripcion> listarCancelacionesPorActividad(int idActividad) throws SQLException {
        List<CancelacionInscripcion> cancelaciones = new ArrayList<>();
        String sql = "SELECT c.id_cancelacion, c.id_inscripcion, c.fec_cancelacion " +
                "FROM proyecto.cancelaciones c " +
                "JOIN proyecto.inscripciones i ON c.id_inscripcion = i.id_inscripcion " +
                "WHERE i.id_actividad = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idActividad);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CancelacionInscripcion cancelacion = new CancelacionInscripcion.CancelacionInscripcionBuilder()
                            .setIdCancelacion(rs.getInt("id_cancelacion"))
                            .setInscripcion(new Inscripcion.InscripcionBuilder()
                                    .setIdInscripcion(rs.getInt("id_inscripcion"))
                                    .setActividad(new Actividad.ActividadBuilder().setIdActividad(idActividad).build())
                                    .build())
                            .setFechaCancelacion(rs.getTimestamp("fec_cancelacion"))
                            .build();
                    cancelaciones.add(cancelacion);
                }
            }
        }

        return cancelaciones;
    }

    public boolean agregarCancelacion(int idUsuario, int idActividad) throws SQLException {
        String obtenerInscripcionSQL = "SELECT id_inscripcion FROM proyecto.inscripciones " +
                "WHERE id_usuario = ? AND id_actividad = ? AND estado = true";

        String insertarCancelacionSQL = "INSERT INTO proyecto.cancelaciones (id_inscripcion, fec_cancelacion) " +
                "VALUES (?, current_timestamp)";

        String actualizarEstadoInscripcionSQL = "UPDATE proyecto.inscripciones SET estado = false WHERE id_inscripcion = ?";

        String actualizarActividadSQL = "UPDATE proyecto.actividades " +
                "SET can_cancelados = can_cancelados + 1, " +
                "    can_inscriptos = can_inscriptos - 1 " +
                "WHERE id_actividad = ?";

        try (
                PreparedStatement obtenerStmt = connection.prepareStatement(obtenerInscripcionSQL);
                PreparedStatement insertarStmt = connection.prepareStatement(insertarCancelacionSQL);
                PreparedStatement actualizarInscripcionStmt = connection.prepareStatement(actualizarEstadoInscripcionSQL);
                PreparedStatement actualizarActividadStmt = connection.prepareStatement(actualizarActividadSQL)
        ) {
            // Buscar inscripción activa.
            obtenerStmt.setInt(1, idUsuario);
            obtenerStmt.setInt(2, idActividad);
            ResultSet rs = obtenerStmt.executeQuery();

            if (!rs.next()) {
                return false; // No hay inscripción activa.
            }

            int idInscripcion = rs.getInt("id_inscripcion");

            // Insertar cancelación.
            insertarStmt.setInt(1, idInscripcion);
            insertarStmt.executeUpdate();

            // Actualizar inscripción a inactiva.
            actualizarInscripcionStmt.setInt(1, idInscripcion);
            actualizarInscripcionStmt.executeUpdate();

            // Actualizar actividad.
            actualizarActividadStmt.setInt(1, idActividad);
            actualizarActividadStmt.executeUpdate();

            return true;
        }
    }
}
