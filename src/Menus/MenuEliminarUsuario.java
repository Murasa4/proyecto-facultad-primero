package Menus;

import Clases.Usuario;
import ClasesDAO.UsuarioDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MenuEliminarUsuario {

    public MenuEliminarUsuario() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int seleccion;

        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();

        do {
            System.out.println("\nLista de usuarios disponibles:");

            List<Usuario> listaUsuarios = usuarioDAO.listarUsuarios();

            if (listaUsuarios.isEmpty()) {
                System.out.println("No hay usuarios registrados.");
                return;
            }

            System.out.printf("%-5s | %-15s | %-15s | %-15s | %-30s%n", "Nro", "Nombre", "Apellido", "Estado", "Email");
            System.out.println("----------------------------------------------------------------------------------------");

            List<Usuario> usuariosMostrados = new ArrayList<>();
            int contador = 1;
            for (Usuario u : listaUsuarios) {
                System.out.printf("%-5d | %-15s | %-15s | %-15s | %-30s%n",
                        contador++,
                        u.getPrimerNombre(),
                        u.getPrimerApellido(),
                        u.getEstado() ? "Activo" : "Inactivo",
                        u.getEmail());
                usuariosMostrados.add(u);
            }

            System.out.println("-------------------------");
            System.out.println("Ingrese el número del usuario a dar de baja:");
            System.out.println("Ingrese '0' para volver atrás.");
            System.out.println("-------------------------");

            seleccion = scanner.nextInt();
            scanner.nextLine(); // Limpia el salto de línea pendiente.

            if (seleccion == 0) {
                break;
            }

            if (seleccion > 0 && seleccion <= usuariosMostrados.size()) {
                Usuario usuario = usuariosMostrados.get(seleccion - 1);
                int id = usuario.getIdUsuario(); // Obtiene el ID del usuario.

                System.out.println(usuario);
                System.out.println("-------------------------");
                System.out.println("¿Qué desea hacer?");
                System.out.println("1. Dar de baja (Inactivo)");
                System.out.println("2. Eliminar permanentemente");
                System.out.print("Seleccione una opción: ");
                String opcion = scanner.nextLine();

                switch (opcion) {
                    case "1":
                        if (!usuario.getEstado()) {
                            System.out.println("\n-----------------------------------");
                            System.out.println("El usuario ya se encuentra inactivo.");
                            System.out.println("Por favor, seleccione otra opción.");
                            System.out.println("-----------------------------------");
                            break;
                        }

                        // Confirmación de baja lógica
                        System.out.println("-----------------------------------");
                        System.out.println("¿Desea confirmar la baja del usuario?");
                        System.out.println("1. Sí");
                        System.out.println("2. No");
                        System.out.print("Seleccione una opción: ");
                        String confirmacion = scanner.nextLine();

                        if (confirmacion.equals("1")) {
                            usuarioDAO.eliminarUsuario(id);
                            System.out.println("El usuario fue dado de baja correctamente.");
                            Usuario usuarioEliminado = usuarioDAO.obtenerUsuarioPorId(id);
                            System.out.println("Estado actual del usuario: " +
                                    (usuarioEliminado.getEstado() ? "Activo" : "Inactivo"));
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                        break;

                    case "2":
                        System.out.println("ATENCIÓN: Esta acción eliminará el usuario de forma permanente.");
                        System.out.println("Nombre completo: " +
                                usuario.getPrimerNombre() + " " + usuario.getSegundoNombre() + " " +
                                usuario.getPrimerApellido() + " " + usuario.getSegundoApellido());
                        System.out.print("¿Está seguro que desea continuar? (Si/No): ");
                        String confirmacionFinal = scanner.nextLine();

                        if (confirmacionFinal.equalsIgnoreCase("Si")) {
                            usuarioDAO.eliminarUsuarioFisicamente(id);
                            System.out.println("El usuario fue eliminado permanentemente.");
                        } else {
                            System.out.println("Operación cancelada.");
                        }
                        break;

                    default:
                        System.out.println("Opción inválida.");
                        break;
                }

            } else {
                System.out.println("Número inválido. Intente de nuevo.");
            }

        } while (true);
    }
}
