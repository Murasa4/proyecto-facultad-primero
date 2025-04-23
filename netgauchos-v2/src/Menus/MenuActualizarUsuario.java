package Menus;

import Clases.CategoriaSocio;
import Clases.Usuario;
import ClasesDAO.CategoriaSocioDAO;
import ClasesDAO.SubcomisionDAO;
import ClasesDAO.UsuarioDAO;
import ClasesServicios.UsuarioServicios;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuActualizarUsuario {

    public MenuActualizarUsuario() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

        System.out.println("Lista de usuarios disponibles:");

        List<Usuario> listaUsuarios = usuarioDAO.listarUsuariosActualizar();

        System.out.printf("%-5s | %-15s | %-15s | %-10s | %-30s%n", "ID", "Nombre", "Apellido", "Estado", "Email");
        System.out.println("-------------------------------------------------------------------------------");

        for (Usuario u : listaUsuarios) {
            String estado = u.getEstado() ? "Activo" : "Inactivo";
            System.out.printf("%-5d | %-15s | %-15s | %-10s | %-30s%n",
                    u.getIdUsuario(),
                    u.getPrimerNombre(),
                    u.getPrimerApellido(),
                    estado,
                    u.getEmail());
        }


        System.out.println("-------------------------");
        System.out.print("Ingrese el ID del usuario que desea actualizar: ");
        int idUsuario = Integer.parseInt(scanner.nextLine());
        System.out.println("-------------------------");

        // Verificar si el usuario existe
        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(idUsuario);
        if (usuario == null) {
            System.out.println("Usuario con ID " + idUsuario + " no encontrado.");
            return;
        }

        Usuario.UsuarioBuilder usuarioBuilder = new Usuario.UsuarioBuilder(usuario);

        boolean continuarEditar = true;

        while (continuarEditar) {
            System.out.println("-------------------------");
            System.out.println("Menu de edición del usuario:");
            System.out.println("-------------------------");
            System.out.println("1. Editar primer nombre (actual: " + usuario.getPrimerNombre() + ")");
            System.out.println("2. Editar segundo nombre (actual: " + usuario.getSegundoNombre() + ")");
            System.out.println("3. Editar primer apellido (actual: " + usuario.getPrimerApellido() + ")");
            System.out.println("4. Editar segundo apellido (actual: " + usuario.getSegundoApellido() + ")");
            System.out.println("5. Editar estado (actual: " + (usuario.getEstado() ? "Activo" : "Inactivo") + ")");
            System.out.println("6. Editar email (actual: " + usuario.getEmail() + ")");
            System.out.println("7. Editar fecha de nacimiento (actual: " + usuario.getFechaNacimiento() + ")");
            System.out.println("8. Editar dificultad auditiva (actual: " + (usuario.getDificultadAuditiva() ? "Sí" : "No") + ")");
            System.out.println("9. Editar lenguaje de señas (actual: " + (usuario.getLenguajeSenias() ? "Sí" : "No") + ")");
            System.out.println("10. Editar subcomisión (actual: " +
                    (usuario.getSubcomision() == null ? "Ninguna" : usuario.getSubcomision().getIdSubcomision()) + ")");
            System.out.println("11. Editar ID de categoría de socio (actual: " +
                    (usuario.getCategoriaSocio() == null ? "Ninguna" : usuario.getCategoriaSocio().getIdCategoriaSocio()) + ")");
            System.out.println("12. Finalizar edición.");
            System.out.print("Seleccione una opción: ");

            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.print("Nuevo primer nombre: ");
                    String priNombre = UsuarioServicios.obtenerNombreValido();
                    usuarioBuilder.setPrimerNombre(priNombre);
                    break;
                case 2:
                    System.out.print("Nuevo segundo nombre: ");
                    String segNombre = UsuarioServicios.obtenerNombreValidoOpcional();
                    usuarioBuilder.setSegundoNombre(segNombre);
                    break;
                case 3:
                    System.out.print("Nuevo primer apellido: ");
                    String priApellido = UsuarioServicios.obtenerNombreValido();
                    usuarioBuilder.setPrimerApellido(priApellido);
                    break;
                case 4:
                    System.out.print("Nuevo segundo apellido: ");
                    String segApellido = UsuarioServicios.obtenerNombreValidoOpcional();
                    usuarioBuilder.setSegundoApellido(segApellido);
                    break;
                case 5:
                    System.out.println("Nuevo estado: ");
                    System.out.println("Eliga una Opcion: 1.Activo 2.Inactivo");
                    String estadoInput = scanner.nextLine().trim();

                    if (estadoInput.equals("1")) {
                        usuarioBuilder.setEstado(true);
                    } else if (estadoInput.equals("2")) {
                        usuarioBuilder.setEstado(false);
                    } else {
                        System.out.println("Opción inválida. Debe ingresar 1 o 2.");
                    }
                    break;
                case 6:
                    System.out.print("Nuevo email: ");
                    String email;
                    do {
                        System.out.print("Email: ");
                        email = scanner.nextLine();
                        usuarioBuilder.setEmail(email);
                    } while (!UsuarioServicios.validarCorreo(email));
                    break;
                case 7:
                    System.out.print("Nueva fecha de nacimiento (YYYY-MM-DD): ");
                    Date fecNacimiento = Date.valueOf(UsuarioServicios.obtenerFechaNacimientoValida());
                    usuarioBuilder.setFechaNacimiento(fecNacimiento);
                    break;
                case 8:
                    System.out.print("Nueva dificultad auditiva (Si/No): ");
                    boolean difAuditiva = UsuarioServicios.validarDificultad();
                    usuarioBuilder.setDificultadAuditiva(difAuditiva);
                    break;
                case 9:
                    System.out.print("Nuevo lenguaje de señas (Si/No): ");
                    boolean lenSenias = UsuarioServicios.validarDificultad();
                    usuarioBuilder.setLenguajeSenias(lenSenias);
                    break;
                case 10:
                    System.out.print("Nuevo ID de subcomisión: ");
                    String subcomisionInput = scanner.nextLine();
                    if (!subcomisionInput.isEmpty()) {
                        int idSubcomision = Integer.parseInt(subcomisionInput);
                        usuarioBuilder.setSubcomision(SubcomisionDAO.getInstance().obtenerSubcomisionPorId(idSubcomision));
                    }
                    break;
                case 11:  // Aquí está la validación para el ID de categoría de socio
                    System.out.print("Nuevo ID de categoría de socio: ");
                    String categoriaSocioInput = scanner.nextLine();
                    if (!categoriaSocioInput.isEmpty()) {
                        int idCategoriaSocio = Integer.parseInt(categoriaSocioInput);

                        // Validamos que el id_categoria_socio no sea 0
                        if (idCategoriaSocio > 0) {
                            // Validamos que el id_categoria_socio exista en la base de datos
                            CategoriaSocio categoriaSocio = CategoriaSocioDAO.getInstance().obtenerCategoriaSocioPorId(idCategoriaSocio);
                            if (categoriaSocio != null) {
                                usuarioBuilder.setCategoriaSocio(categoriaSocio);
                            } else {
                                System.out.println("Error: La categoría de socio con el ID proporcionado no existe.");
                            }
                        } else {
                            System.out.println("Error: El ID de categoría de socio no puede ser 0.");
                        }
                    } else {
                        System.out.println("El ID de categoría de socio no puede estar vacío.");
                    }
                    break;
                case 12:
                    continuarEditar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }

        System.out.println("Nombre a actualizar en la base de datos: " + usuario.getPrimerNombre());
        // Reconstruir el usuario con los cambios
        usuario = usuarioBuilder.build();
        System.out.println("Detalles del usuario actualizado: " + usuario.toString());

        // Guardar los cambios en la base de datos
        try {
            usuarioDAO.actualizarUsuario(usuario);
            System.out.println("Usuario actualizado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al actualizar el usuario.");
        }
    }
}
