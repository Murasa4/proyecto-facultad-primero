package ClasesDAO;

import Clases.TipoDocumento;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TipoDocumentoDAO {

    private Connection connection;
    private static TipoDocumentoDAO instance;

    private TipoDocumentoDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static TipoDocumentoDAO getInstance() {
        if (instance == null) {
            instance = new TipoDocumentoDAO();
        }
        return instance;
    }

    public void agregarTipoDocumento(TipoDocumento tipoDocumento) throws SQLException {
        String sql = "INSERT INTO proyecto.tipo_documentos (nombre, estado) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, tipoDocumento.getNombre());
            stmt.setBoolean(2, tipoDocumento.getEstado());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                tipoDocumento.setIdTipoDocumento(generatedKeys.getInt(1));
            }
        }
    }

    public void actualizarTipoDocumento(TipoDocumento tipoDocumento) throws SQLException {
        String sql = "UPDATE proyecto.tipo_documentos SET nombre = ?, estado = ? WHERE id_tipo_documento = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, tipoDocumento.getNombre());
            stmt.setBoolean(2, tipoDocumento.getEstado());
            stmt.setInt(3, tipoDocumento.getIdTipoDocumento());
            stmt.executeUpdate();
        }
    }

    public void eliminarTipoDocumento(int idTipoDocumento) throws SQLException {
        String sql = "UPDATE proyecto.tipo_documentos SET estado = false WHERE id_tipo_documento = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTipoDocumento);
            stmt.executeUpdate();
        }
    }

    public TipoDocumento obtenerTipoDocumentoPorId(int idTipoDocumento) throws SQLException {
        String sql = "SELECT * FROM proyecto.tipo_documentos WHERE id_tipo_documento = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTipoDocumento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new TipoDocumento.TipoDocumentoBuilder()
                        .setIdTipoDocumento(rs.getInt("id_tipo_documento"))
                        .setNombre(rs.getString("nombre"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public List<TipoDocumento> listarTiposDocumentos() throws SQLException {
        List<TipoDocumento> tipoDocumentos = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.tipo_documentos";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                TipoDocumento tipoDocumento = new TipoDocumento.TipoDocumentoBuilder()
                        .setIdTipoDocumento(rs.getInt("id_tipo_documento"))
                        .setNombre(rs.getString("nombre"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                tipoDocumentos.add(tipoDocumento);
            }
        }
        return tipoDocumentos;
    }
}
