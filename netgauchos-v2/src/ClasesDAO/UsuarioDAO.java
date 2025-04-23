package ClasesDAO;

import Clases.*;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    private Connection connection;
    private static UsuarioDAO instance;

    public UsuarioDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static UsuarioDAO getInstance() {
        if (instance == null) {
            instance = new UsuarioDAO();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    public void agregarUsuario(Usuario usuario) throws SQLException {
        String sql = "INSERT INTO proyecto.usuarios (pri_nombre, seg_nombre, pri_apellido, seg_apellido," + "id_tipo_documento, num_documento, fec_nacimiento, id_domicilio," + "email, contrasenia, id_tipo_usuario, id_categoria_socio, dif_auditiva, len_senias, id_subcomision," + "estado) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getPrimerNombre());
            stmt.setString(2, usuario.getSegundoNombre());
            stmt.setString(3, usuario.getPrimerApellido());
            stmt.setString(4, usuario.getSegundoApellido());
            stmt.setInt(5, usuario.getTipoDocumento().getIdTipoDocumento());
            stmt.setString(6, usuario.getNumeroDocumento());
            stmt.setDate(7, usuario.getFechaNacimiento());
            stmt.setInt(8, usuario.getDomicilio().getIdDomicilio());
            stmt.setString(9, usuario.getEmail());
            stmt.setString(10, usuario.getContrasenia());
            stmt.setInt(11, usuario.getTipoUsuario().getIdTipoUsuario());
            stmt.setInt(12, usuario.getCategoriaSocio().getIdCategoriaSocio());
            stmt.setBoolean(13, usuario.getDificultadAuditiva());
            stmt.setBoolean(14, usuario.getLenguajeSenias());
            stmt.setObject(15, usuario.getSubcomision() == null ? null : usuario.getSubcomision().getIdSubcomision(), Types.INTEGER);
            stmt.setBoolean(16, usuario.getEstado());

            int rowsInserted = stmt.executeUpdate();

            // Validar si se insertó alguna fila
            if (rowsInserted == 0) {
                System.err.println("No se insertó el usuario. Posiblemente un problema con la base de datos.");
            } else {
                // Asignar ID generado
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        usuario.setIdUsuario(generatedKeys.getInt(1));
                    }
                }
            }
        }
    }


    public void actualizarUsuario(Usuario usuario) throws SQLException {
        String sql = "UPDATE proyecto.usuarios SET pri_nombre = ?, seg_nombre = ?, pri_apellido = ?, seg_apellido = ?, " +
                "id_tipo_documento = ?, num_documento = ?, fec_nacimiento = ?, id_domicilio = ?, email = ?," +
                "contrasenia = ?, id_tipo_usuario = ?, id_categoria_socio = ?, dif_auditiva = ?, len_senias = ?, id_subcomision = ?," +
                "estado = ? WHERE id_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getPrimerNombre());
            stmt.setString(2, usuario.getSegundoNombre());
            stmt.setString(3, usuario.getPrimerApellido());
            stmt.setString(4, usuario.getSegundoApellido());
            stmt.setInt(5, usuario.getTipoDocumento().getIdTipoDocumento());
            stmt.setString(6, usuario.getNumeroDocumento());
            stmt.setDate(7, usuario.getFechaNacimiento());
            stmt.setInt(8, usuario.getDomicilio().getIdDomicilio());
            stmt.setString(9, usuario.getEmail());
            stmt.setString(10, usuario.getContrasenia());
            stmt.setInt(11, usuario.getTipoUsuario().getIdTipoUsuario());

            // Validación para el id_categoria_socio
            if (usuario.getCategoriaSocio() != null && usuario.getCategoriaSocio().getIdCategoriaSocio() > 0) {
                stmt.setInt(12, usuario.getCategoriaSocio().getIdCategoriaSocio());  // Asignamos un valor válido
            } else {
                stmt.setNull(12, Types.INTEGER);  // Si no es válido, lo asignamos como NULL
            }

            stmt.setBoolean(13, usuario.getDificultadAuditiva());
            stmt.setBoolean(14, usuario.getLenguajeSenias());

            // Subcomisión: Verificación para manejar null si no está presente
            if (usuario.getSubcomision() == null || usuario.getSubcomision().getIdSubcomision() == 0) {
                stmt.setNull(15, Types.INTEGER);
            } else {
                stmt.setInt(15, usuario.getSubcomision().getIdSubcomision());
            }

            stmt.setBoolean(16, usuario.getEstado());
            stmt.setInt(17, usuario.getIdUsuario());

            stmt.executeUpdate();
        }
    }

    public void eliminarUsuario(int idUsuario) throws SQLException {
        String sql = "UPDATE proyecto.usuarios SET estado = false WHERE id_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
    }

    public void activarUsuario(int idUsuario) throws SQLException {
        String sql = "UPDATE proyecto.usuarios SET estado = true WHERE id_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.executeUpdate();
        }
    }

    public Usuario obtenerUsuarioPorId(int idUsuario) throws SQLException {
        String sql = "SELECT * FROM proyecto.usuarios WHERE id_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario.UsuarioBuilder()
                        .setIdUsuario(rs.getInt("id_usuario"))
                        .setPrimerNombre(rs.getString("pri_nombre"))
                        .setSegundoNombre(rs.getString("seg_nombre"))
                        .setPrimerApellido(rs.getString("pri_apellido"))
                        .setSegundoApellido(rs.getString("seg_apellido"))
                        .setTipoDocumento(new TipoDocumento.TipoDocumentoBuilder().setIdTipoDocumento(rs.getInt("id_tipo_documento")).build())
                        .setNumeroDocumento(rs.getString("num_documento"))
                        .setFechaNacimiento(rs.getDate("fec_nacimiento"))
                        .setDomicilio(new Domicilio.DomicilioBuilder().setIdDomicilio(rs.getInt("id_domicilio")).build())
                        .setEmail(rs.getString("email"))
                        .setContrasenia(rs.getString("contrasenia"))
                        .setTipoUsuario(new TipoUsuario.TipoUsuarioBuilder().setIdTipoUsuario(rs.getInt("id_tipo_usuario")).build())
                        .setCategoriaSocio(new CategoriaSocio.CategoriaSocioBuilder().setIdCategoriaSocio(rs.getInt("id_categoria_socio")).build())
                        .setDificultadAuditiva(rs.getBoolean("dif_auditiva"))
                        .setLenguajeSenias(rs.getBoolean("len_senias"))
                        .setSubcomision(new Subcomision.SubcomisionBuilder().setIdSubcomision(rs.getInt("id_subcomision")).build())
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public Usuario obtenerUsuarioPorTipoDocumento(int idTipoDocumento, String numDocumento) throws SQLException {
        String sql = "SELECT * FROM proyecto.usuarios WHERE id_tipo_documento = ? AND num_documento = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idTipoDocumento);
            stmt.setString(2, numDocumento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario.UsuarioBuilder()
                        .setIdUsuario(rs.getInt("id_usuario"))
                        .setPrimerNombre(rs.getString("pri_nombre"))
                        .setSegundoNombre(rs.getString("seg_nombre"))
                        .setPrimerApellido(rs.getString("pri_apellido"))
                        .setSegundoApellido(rs.getString("seg_apellido"))
                        .setTipoDocumento(new TipoDocumento.TipoDocumentoBuilder()
                                .setIdTipoDocumento(rs.getInt("id_tipo_documento"))
                                .build())
                        .setNumeroDocumento(rs.getString("num_documento"))
                        .setFechaNacimiento(rs.getDate("fec_nacimiento"))
                        .setDomicilio(new Domicilio.DomicilioBuilder()
                                .setIdDomicilio(rs.getInt("id_domicilio"))
                                .build())
                        .setEmail(rs.getString("email"))
                        .setContrasenia(rs.getString("contrasenia"))
                        .setTipoUsuario(new TipoUsuario.TipoUsuarioBuilder()
                                .setIdTipoUsuario(rs.getInt("id_tipo_usuario"))
                                .build())
                        .setCategoriaSocio(new CategoriaSocio.CategoriaSocioBuilder()
                                .setIdCategoriaSocio(rs.getInt("id_categoria_socio"))
                                .build())
                        .setDificultadAuditiva(rs.getBoolean("dif_auditiva"))
                        .setLenguajeSenias(rs.getBoolean("len_senias"))
                        .setSubcomision(new Subcomision.SubcomisionBuilder()
                                .setIdSubcomision(rs.getInt("id_subcomision"))
                                .build())
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public Usuario obtenerUsuarioPorNumeroDocumento(String numeroDocumento) throws SQLException {
        String sql = "SELECT * FROM proyecto.usuarios WHERE num_documento = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, numeroDocumento);  // Usamos el documento como String en la consulta
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Usuario.UsuarioBuilder()
                        .setIdUsuario(rs.getInt("id_usuario"))
                        .setPrimerNombre(rs.getString("pri_nombre"))
                        .setSegundoNombre(rs.getString("seg_nombre"))
                        .setPrimerApellido(rs.getString("pri_apellido"))
                        .setSegundoApellido(rs.getString("seg_apellido"))
                        .setTipoDocumento(new TipoDocumento.TipoDocumentoBuilder().setIdTipoDocumento(rs.getInt("id_tipo_documento")).build())
                        .setNumeroDocumento(rs.getString("num_documento"))
                        .setFechaNacimiento(rs.getDate("fec_nacimiento"))
                        .setDomicilio(new Domicilio.DomicilioBuilder().setIdDomicilio(rs.getInt("id_domicilio")).build())
                        .setEmail(rs.getString("email"))
                        .setContrasenia(rs.getString("contrasenia"))
                        .setTipoUsuario(new TipoUsuario.TipoUsuarioBuilder().setIdTipoUsuario(rs.getInt("id_tipo_usuario")).build())
                        .setCategoriaSocio(new CategoriaSocio.CategoriaSocioBuilder().setIdCategoriaSocio(rs.getInt("id_categoria_socio")).build())
                        .setDificultadAuditiva(rs.getBoolean("dif_auditiva"))
                        .setLenguajeSenias(rs.getBoolean("len_senias"))
                        .setSubcomision(new Subcomision.SubcomisionBuilder().setIdSubcomision(rs.getInt("id_subcomision")).build())
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public List<Usuario> listarUsuarios() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.usuarios ORDER BY id_usuario ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario.UsuarioBuilder()
                        .setIdUsuario(rs.getInt("id_usuario"))
                        .setPrimerNombre(rs.getString("pri_nombre"))
                        .setSegundoNombre(rs.getString("seg_nombre"))
                        .setPrimerApellido(rs.getString("pri_apellido"))
                        .setSegundoApellido(rs.getString("seg_apellido"))
                        .setTipoDocumento(new TipoDocumento.TipoDocumentoBuilder().setIdTipoDocumento(rs.getInt("id_tipo_documento")).build())
                        .setNumeroDocumento(rs.getString("num_documento"))
                        .setFechaNacimiento(rs.getDate("fec_nacimiento"))
                        .setDomicilio(new Domicilio.DomicilioBuilder().setIdDomicilio(rs.getInt("id_domicilio")).build())
                        .setEmail(rs.getString("email"))
                        .setContrasenia(rs.getString("contrasenia"))
                        .setTipoUsuario(new TipoUsuario.TipoUsuarioBuilder().setIdTipoUsuario(rs.getInt("id_tipo_usuario")).build())
                        .setCategoriaSocio(new CategoriaSocio.CategoriaSocioBuilder().setIdCategoriaSocio(rs.getInt("id_categoria_socio")).build())
                        .setDificultadAuditiva(rs.getBoolean("dif_auditiva"))
                        .setLenguajeSenias(rs.getBoolean("len_senias"))
                        .setSubcomision(new Subcomision.SubcomisionBuilder().setIdSubcomision(rs.getInt("id_subcomision")).build())
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }


    // Metodo para verificar si un correo ya existe en la base de datos
    public boolean existeCorreo(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM proyecto.usuarios WHERE email = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0; // Si el COUNT es mayor a 0, el correo existe
                }
            }
        }
        return false;
    }

    //Metodo para verificar si existe un usuario con ese id
    public boolean existeUsuarioPorId(int idUsuario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM proyecto.usuarios WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Si COUNT(*) > 0, entonces el id existe
                }
            }
        }
        return false; // Si no se encuentra el id
    }

    public List<Usuario> listarUsuariosActualizar() throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.usuarios ORDER BY id_usuario ASC";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Usuario usuario = new Usuario.UsuarioBuilder()
                        .setIdUsuario(rs.getInt("id_usuario"))
                        .setPrimerNombre(rs.getString("pri_nombre"))
                        .setSegundoNombre(rs.getString("seg_nombre"))
                        .setPrimerApellido(rs.getString("pri_apellido"))
                        .setSegundoApellido(rs.getString("seg_apellido"))
                        .setTipoDocumento(new TipoDocumento.TipoDocumentoBuilder().setIdTipoDocumento(rs.getInt("id_tipo_documento")).build())
                        .setNumeroDocumento(rs.getString("num_documento"))
                        .setFechaNacimiento(rs.getDate("fec_nacimiento"))
                        .setDomicilio(new Domicilio.DomicilioBuilder().setIdDomicilio(rs.getInt("id_domicilio")).build())
                        .setEmail(rs.getString("email"))
                        .setContrasenia(rs.getString("contrasenia"))
                        .setTipoUsuario(new TipoUsuario.TipoUsuarioBuilder().setIdTipoUsuario(rs.getInt("id_tipo_usuario")).build())
                        .setCategoriaSocio(new CategoriaSocio.CategoriaSocioBuilder().setIdCategoriaSocio(rs.getInt("id_categoria_socio")).build())
                        .setDificultadAuditiva(rs.getBoolean("dif_auditiva"))
                        .setLenguajeSenias(rs.getBoolean("len_senias"))
                        .setSubcomision(new Subcomision.SubcomisionBuilder().setIdSubcomision(rs.getInt("id_subcomision")).build())
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                usuarios.add(usuario);
            }
        }
        return usuarios;
    }

    public void eliminarUsuarioFisicamente(int id) throws SQLException {
        String sql = "DELETE FROM proyecto.usuarios WHERE id_usuario = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

