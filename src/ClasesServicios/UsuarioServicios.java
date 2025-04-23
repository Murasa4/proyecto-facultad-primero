package ClasesServicios;

import Clases.*;
import ClasesDAO.UsuarioDAO;
import com.fabdelgado.ciuy.Validator;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsuarioServicios {
    private static final Scanner scanner = new Scanner(System.in);
    private final UsuarioDAO usuarioDAO;
    private final Connection connection;

    //Constructor para conectar con el DAO
    public UsuarioServicios() {
        this.usuarioDAO = UsuarioDAO.getInstance();
        this.connection = usuarioDAO.getConnection();
    }

    // Metodo para validar el formato del correo electrónico
    public static boolean validarFormatoCorreo(String correo) {
        String regexCorreo = "^[A-Za-z0-9._%+-]+@(hotmail\\.com|gmail\\.com|estudiantes\\.utec\\.edu\\.uy)$";
        Pattern pattern = Pattern.compile(regexCorreo);
        Matcher matcher = pattern.matcher(correo);
        return matcher.matches();

    }

    // Metodo para verificar si el correo ya existe en la base de datos
    public static boolean correoExiste(String correo) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        return usuarioDAO.existeCorreo(correo);
    }

    // Metodo para validar el correo completo (formato + existencia)
    public static boolean validarCorreo(String correo) throws SQLException {
        if (!validarFormatoCorreo(correo)) {
            System.out.println("Correo no válido. Por favor, intente nuevamente.");
            return false;
        }
        if (correoExiste(correo)) {
            System.out.println("El correo ya está registrado. Intente con otro.");
            return false;
        }
        return true;
    }

    public static String obtenerFechaNacimientoValida() {
        Scanner scanner = new Scanner(System.in);
        DateTimeFormatter sqlFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        while (true) {
            String fechaStr = scanner.nextLine().trim();

            if (fechaStr.isEmpty()) {
                System.out.println("La fecha de nacimiento no puede estar vacía.");
                continue;
            }

            try {
                LocalDate fechaNacimiento = LocalDate.parse(fechaStr, sqlFormatter);
                LocalDate hoy = LocalDate.now();

                if (fechaNacimiento.isAfter(hoy)) {
                    System.out.println("La fecha de nacimiento no puede ser futura.");
                } else {
                    return fechaStr;
                }
            } catch (DateTimeParseException e) {
                System.out.println("Formato de fecha no válido. Use el formato yyyy-MM-dd.");
            }
        }
    }

    public static String obtenerContraseniaValida() {
        Scanner scanner = new Scanner(System.in);
        String contrasenia;

        while (true) {

            contrasenia = scanner.nextLine().trim();

            if (contrasenia.isEmpty()) {
                System.out.println("La contraseña no puede estar vacía.");
            } else if (contrasenia.length() < 5) {
                System.out.println("Contraseña debil. Debe tener al menos 5 caracteres.");
            } else {
                return contrasenia;
            }
        }
    }

    // Método para validar CI (7 u 8 dígitos sin puntos ni guiones) y verificar si ya existe
    public static String obtenerCIValida() {
        String ci;
        Validator validator = new Validator(); // Crear instancia de Validator

        while (true) {
            System.out.print("Ingrese su CI sin puntos ni guión: ");
            ci = scanner.nextLine().trim();

            // Verificar que la longitud de la cédula sea exactamente 8 dígitos (sin contar puntos y guiones)
            /*String ciNumeros = ci.replaceAll("[^0-9]", "");
            if (ciNumeros.length() != 7 && ciNumeros.length() != 8) {
                System.out.println("El largo de la cédula ingresada no es válido.");
                continue;
            }*/

            // Validar la cédula con la librería para formato y dígito verificador
            if (!validator.validateCi(ci)) {
                System.out.println("Formato de CI incorrecto o CI inválida. Debe usar un formato válido para Uruguay.");
                continue;
            }

            // Verificar si la CI ya está registrada en la base de datos
            if (ciYaRegistrada(ci)) {
                System.out.println("La CI ingresada ya está registrada. Por favor, ingrese otra.");
            }

            return ci;
        }
    }

    // Método para verificar si la CI ya está registrada en la base de datos
    private static boolean ciYaRegistrada(String ci) {
        String url = "jdbc:postgresql://localhost:5432/asur";
        String usuario = "proyecto";
        String contrasenia = "admin";

        String query = "SELECT COUNT(*) FROM proyecto.usuarios WHERE num_documento = ?";

        try (Connection conn = DriverManager.getConnection(url, usuario, contrasenia);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, ci);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true; // La CI ya existe
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // La CI no existe
    }

    // Método para validar nombres y apellidos (no nulos, sin números ni caracteres especiales)
    public static String obtenerNombreValido() {
        String nombre;
        int limiteCaracteres = 25;

        while (true) {
            nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                System.out.println("No puede estar vacío.");
                continue;
            }

            if (nombre.length() > limiteCaracteres) {
                System.out.println("No puede superar los " + limiteCaracteres + " caracteres.");
                continue;
            }

            if (!nombre.matches("^[A-Za-záéíóúÁÉÍÓÚñÑ\\s]+$")) {
                System.out.println("No válido. Solo se permiten letras y espacios.");
                continue;
            }

            // Verificar que solo haya una separación (máximo 2 palabras)
            String[] palabras = nombre.trim().split("\\s+");
            if (palabras.length > 2) {
                System.out.println("Formato no Disponible.");
                continue;
            }

            // Verificar que haya al menos 3 letras (sin contar espacios)
            String soloLetras = nombre.replaceAll("\\s+", "");
            if (soloLetras.length() < 3) {
                System.out.println("Debe tener al menos 3 letras.");
                continue;
            }

            // Capitalizar cada palabra
            StringBuilder nombreFormateado = new StringBuilder();
            for (String palabra : palabras) {
                if (!palabra.isEmpty()) {
                    nombreFormateado.append(Character.toUpperCase(palabra.charAt(0)))
                            .append(palabra.substring(1).toLowerCase())
                            .append(" ");
                }
            }

            return nombreFormateado.toString().trim();
        }
    }

    // Método para validar nombres y apellidos (no nulos, sin números ni caracteres especiales)
    public static String obtenerNombreValidoOpcional() {
        String nombre;
        int limiteCaracteres = 25;

        while (true) {
            nombre = scanner.nextLine().trim();

            if (nombre.isEmpty()) {
                return nombre; // Se acepta vacío
            }

            if (nombre.length() > limiteCaracteres) {
                System.out.println("No puede superar los " + limiteCaracteres + " caracteres.");
                continue;
            }

            if (!nombre.matches("^[A-Za-záéíóúÁÉÍÓÚñÑ\\s]+$")) {
                System.out.println("No válido. Solo se permiten letras y espacios.");
                continue;
            }

            // Verificar que solo haya una separación (máximo dos palabras)
            String[] palabras = nombre.trim().split("\\s+");
            if (palabras.length > 2) {
                System.out.println("Formato no disponible.");
                continue;
            }

            // Verificar que haya al menos 3 letras (sin contar espacios)
            String soloLetras = nombre.replaceAll("\\s+", "");
            if (soloLetras.length() < 3) {
                System.out.println("Debe tener al menos 3 letras.");
                continue;
            }

            // Capitalizar cada palabra
            StringBuilder nombreFormateado = new StringBuilder();
            for (String palabra : palabras) {
                if (!palabra.isEmpty()) {
                    nombreFormateado.append(Character.toUpperCase(palabra.charAt(0)))
                            .append(palabra.substring(1).toLowerCase())
                            .append(" ");
                }
            }

            return nombreFormateado.toString().trim();
        }
    }

    public static boolean validarDificultad() {
        Scanner scanner = new Scanner(System.in);
        String entrada;

        while (true) {
            entrada = scanner.nextLine().trim().toLowerCase();  // Capturar la entrada y convertirla a minúsculas

            if (entrada.isEmpty()) {
                System.out.println("La respuesta no puede estar vacía. Debes ingresar 'si' o 'no'.");
            } else if (entrada.equals("si")) {
                return true; // Respuesta válida: si
            } else if (entrada.equals("no")) {
                return false; // Respuesta válida: no
            } else {
                System.out.println("Respuesta incorrecta. Debes ingresar 'si' o 'no'.");
            }
        }
    }

    //Verificar si existe un usuario con el correo ingresado
    public boolean verificarCorreo(String email) throws SQLException {
        if (email == null || email.isEmpty()) {
            return false;
        }

        String query = "SELECT * FROM proyecto.usuarios WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) { // No se crea nueva conexión aquí
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Devuelve true si se encuentra un registro
            }
        }
    }

    //Verificar si la contrasenia concuerda con el usuario
    public boolean verificarContrasenia(String email, String contrasenia) throws SQLException {
        if (email == null || email.isEmpty() || contrasenia == null || contrasenia.isEmpty()) {
            return false; // Validación inicial
        }

        String query = "SELECT * FROM proyecto.usuarios WHERE email = ? AND contrasenia = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) { // No se crea nueva conexión aquí
            stmt.setString(1, email);
            stmt.setString(2, contrasenia);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next(); // Devuelve true si se encuentra un registro
            }
        }
    }

    //Devuelve el usuario segun el correo
    public Usuario obtenerUsuarioPorCorreo(String email) throws SQLException {
        String sql = "SELECT * FROM proyecto.usuarios WHERE email = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) { // Reutiliza la conexión global
            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
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
    }

    public int getIdTipoUsuarioPorUsuario(Usuario usuario) throws SQLException {
        if (usuario == null || usuario.getIdUsuario() <= 0) {
            return 1;
        }

        String sql = "SELECT id_tipo_usuario FROM proyecto.usuarios WHERE id_usuario = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) { // Reutiliza la conexión global
            stmt.setInt(1, usuario.getIdUsuario());

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id_tipo_usuario"); // Devuelve el ID del tipo de usuario
                } else {
                    return -1; // Devuelve -1 si no se encontró un registro
                }
            }
        }
    }

    public List<Usuario> getUsuariosPorNombre(String nombre) throws SQLException {
        if (nombre == null || nombre.isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        }

        String query = "SELECT * FROM proyecto.usuarios WHERE pri_nombre ILIKE ? OR seg_nombre ILIKE ?";

        List<Usuario> usuariosEncontrados = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + nombre + "%"); // Buscar coincidencias parciales en el primer nombre
            stmt.setString(2, "%" + nombre + "%"); // Buscar coincidencias parciales en el segundo nombre

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario.UsuarioBuilder()
                            .setIdUsuario(rs.getInt("id_usuario"))
                            .setPrimerNombre(rs.getString("pri_nombre"))
                            .setSegundoNombre(rs.getString("seg_nombre"))
                            .setPrimerApellido(rs.getString("pri_apellido"))
                            .setSegundoApellido(rs.getString("seg_apellido"))
                            .setTipoDocumento(new TipoDocumento.TipoDocumentoBuilder()
                                    .setIdTipoDocumento(rs.getInt("id_tipo_documento")).build())
                            .setNumeroDocumento(rs.getString("num_documento"))
                            .setFechaNacimiento(rs.getDate("fec_nacimiento"))
                            .setDomicilio(new Domicilio.DomicilioBuilder()
                                    .setIdDomicilio(rs.getInt("id_domicilio")).build())
                            .setEmail(rs.getString("email"))
                            .setContrasenia(rs.getString("contrasenia"))
                            .setTipoUsuario(new TipoUsuario.TipoUsuarioBuilder()
                                    .setIdTipoUsuario(rs.getInt("id_tipo_usuario")).build())
                            .setCategoriaSocio(new CategoriaSocio.CategoriaSocioBuilder()
                                    .setIdCategoriaSocio(rs.getInt("id_categoria_socio")).build())
                            .setDificultadAuditiva(rs.getBoolean("dif_auditiva"))
                            .setLenguajeSenias(rs.getBoolean("len_senias"))
                            .setSubcomision(new Subcomision.SubcomisionBuilder()
                                    .setIdSubcomision(rs.getInt("id_subcomision")).build())
                            .setEstado(rs.getBoolean("estado"))
                            .build();

                    usuariosEncontrados.add(usuario);
                }
            }
        }
        return usuariosEncontrados;
    }

    public void buscarYMostrarUsuariosNombre() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-------------------------");
        System.out.println("Ingrese el nombre del usuario a buscar:");
        System.out.println("-------------------------");
        String nombre = scanner.nextLine();

        try {
            List<Usuario> usuarios = getUsuariosPorNombre(nombre); // Obtener usuarios por nombre

            if (usuarios.isEmpty()) {
                System.out.println("-------------------------");
                System.out.println("No se encontraron usuarios con el nombre proporcionado.");
                System.out.println("-------------------------");
            } else {
                System.out.println("Usuarios encontrados:");
                System.out.println("-------------------------");
                for (Usuario usuario : usuarios) {
                    System.out.println(usuario); // Imprime el metodo toString de Usuario
                    System.out.println("-------------------------");
                }
            }
        } catch (SQLException e) {
            System.out.println("Ocurrió un error al buscar los usuarios: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public List<Usuario> getUsuariosPorApellido(String apellido) throws SQLException {
        if (apellido == null || apellido.isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede estar vacío.");
        }

        String query = "SELECT * FROM proyecto.usuarios WHERE pri_apellido ILIKE ? OR seg_apellido ILIKE ?";

        List<Usuario> usuariosEncontrados = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, "%" + apellido + "%"); // Buscar coincidencias parciales en el primer apellido
            stmt.setString(2, "%" + apellido + "%"); // Buscar coincidencias parciales en el segundo apellido

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario.UsuarioBuilder()
                            .setIdUsuario(rs.getInt("id_usuario"))
                            .setPrimerNombre(rs.getString("pri_nombre"))
                            .setSegundoNombre(rs.getString("seg_nombre"))
                            .setPrimerApellido(rs.getString("pri_apellido"))
                            .setSegundoApellido(rs.getString("seg_apellido"))
                            .setTipoDocumento(new TipoDocumento.TipoDocumentoBuilder()
                                    .setIdTipoDocumento(rs.getInt("id_tipo_documento")).build())
                            .setNumeroDocumento(rs.getString("num_documento"))
                            .setFechaNacimiento(rs.getDate("fec_nacimiento"))
                            .setDomicilio(new Domicilio.DomicilioBuilder()
                                    .setIdDomicilio(rs.getInt("id_domicilio")).build())
                            .setEmail(rs.getString("email"))
                            .setContrasenia(rs.getString("contrasenia"))
                            .setTipoUsuario(new TipoUsuario.TipoUsuarioBuilder()
                                    .setIdTipoUsuario(rs.getInt("id_tipo_usuario")).build())
                            .setCategoriaSocio(new CategoriaSocio.CategoriaSocioBuilder()
                                    .setIdCategoriaSocio(rs.getInt("id_categoria_socio")).build())
                            .setDificultadAuditiva(rs.getBoolean("dif_auditiva"))
                            .setLenguajeSenias(rs.getBoolean("len_senias"))
                            .setSubcomision(new Subcomision.SubcomisionBuilder()
                                    .setIdSubcomision(rs.getInt("id_subcomision")).build())
                            .setEstado(rs.getBoolean("estado"))
                            .build();

                    usuariosEncontrados.add(usuario);
                }
            }
        }
        return usuariosEncontrados;
    }

    public void buscarYMostrarUsuariosApellido() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("-------------------------");
        System.out.println("Ingrese el apellido del usuario a buscar:");
        System.out.println("-------------------------");
        String apellido = scanner.nextLine();

        try {
            List<Usuario> usuarios = getUsuariosPorApellido(apellido); // Obtener usuarios por nombre

            if (usuarios.isEmpty()) {
                System.out.println("-------------------------");
                System.out.println("No se encontraron usuarios con el apellido proporcionado.");
                System.out.println("-------------------------");
            } else {
                System.out.println("Usuarios encontrados:");
                for (Usuario usuario : usuarios) {
                    System.out.println("-------------------------");
                    System.out.println(usuario); // Imprime el metodo toString de Usuario
                }
            }
        } catch (SQLException e) {
            System.out.println("Ocurrió un error al buscar los usuarios: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void buscarYMostrarUsuariosDocumento() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Validator validator = new Validator(); // Crear instancia de Validator

        System.out.println("-------------------------");
        System.out.println("Ingrese el número de documento del usuario a buscar:");
        System.out.println("-------------------------");
        String input = scanner.nextLine();

        // Validamos que la entrada contenga solo dígitos
        /*if (input.matches("\\d+")) {  // Si la entrada solo contiene números
            String documento = input;  // Lo tratamos como un String*/

        if (validator.validateCi(input)) {
            String documento = input;

            // Ahora llamamos al método de la clase UsuarioDAO para hacer la consulta a la base de datos
            UsuarioDAO usuarioDAO = new UsuarioDAO();
            Usuario usuario = usuarioDAO.obtenerUsuarioPorNumeroDocumento(documento);  // Pasamos el documento como String

            if (usuario != null) {
                // Si el usuario es encontrado, mostramos la información
                System.out.println("Usuario encontrado: ");
                System.out.println("-------------------------");
                System.out.println(usuario);
            } else {
                // Si no se encuentra el usuario
                System.out.println("Usuario no encontrado.");
            }
        } else {
            System.out.println("Error: El documento debe ser un número válido de 8 dígito.");
        }
    }

    public List<Usuario> getUsuariosPorTipoUsuario(int idtipousuario) throws SQLException {
        if (Integer.valueOf(idtipousuario) == null) {
            throw new IllegalArgumentException("El documento no puede estar vacío.");
        }

        String query = "SELECT * FROM proyecto.usuarios WHERE id_tipo_usuario = ? ";

        List<Usuario> usuariosEncontrados = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idtipousuario); // Buscar coincidencias parciales con el id de tipo de usuario

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = new Usuario.UsuarioBuilder()
                            .setIdUsuario(rs.getInt("id_usuario"))
                            .setPrimerNombre(rs.getString("pri_nombre"))
                            .setSegundoNombre(rs.getString("seg_nombre"))
                            .setPrimerApellido(rs.getString("pri_apellido"))
                            .setSegundoApellido(rs.getString("seg_apellido"))
                            .setTipoDocumento(new TipoDocumento.TipoDocumentoBuilder()
                                    .setIdTipoDocumento(rs.getInt("id_tipo_documento")).build())
                            .setNumeroDocumento(rs.getString("num_documento"))
                            .setFechaNacimiento(rs.getDate("fec_nacimiento"))
                            .setDomicilio(new Domicilio.DomicilioBuilder()
                                    .setIdDomicilio(rs.getInt("id_domicilio")).build())
                            .setEmail(rs.getString("email"))
                            .setContrasenia(rs.getString("contrasenia"))
                            .setTipoUsuario(new TipoUsuario.TipoUsuarioBuilder()
                                    .setIdTipoUsuario(rs.getInt("id_tipo_usuario")).build())
                            .setCategoriaSocio(new CategoriaSocio.CategoriaSocioBuilder()
                                    .setIdCategoriaSocio(rs.getInt("id_categoria_socio")).build())
                            .setDificultadAuditiva(rs.getBoolean("dif_auditiva"))
                            .setLenguajeSenias(rs.getBoolean("len_senias"))
                            .setSubcomision(new Subcomision.SubcomisionBuilder()
                                    .setIdSubcomision(rs.getInt("id_subcomision")).build())
                            .setEstado(rs.getBoolean("estado"))
                            .build();

                    usuariosEncontrados.add(usuario);
                }
            }
        }
        return usuariosEncontrados;
    }

    public void buscarYMostrarUsuariosIdTipoUsuario(int idtipousuario) {

        try {
            List<Usuario> usuarios = getUsuariosPorTipoUsuario(idtipousuario); // Obtener usuarios por nombre

            if (usuarios.isEmpty()) {
                System.out.println("-------------------------");
                System.out.println("No se encontraron usuarios con el tipo de usuario proporcionado.");
                System.out.println("-------------------------");
            } else {
                System.out.println("Usuarios encontrados:");
                for (Usuario usuario : usuarios) {
                    System.out.println("-------------------------");
                    System.out.println(usuario); // Imprime el metodo toString de Usuario
                }
            }
        } catch (SQLException e) {
            System.out.println("Ocurrió un error al buscar los usuarios: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
