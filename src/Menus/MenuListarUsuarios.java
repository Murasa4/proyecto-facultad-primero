package Menus;

import Clases.Usuario;
import ClasesServicios.UsuarioServicios;

import java.sql.SQLException;
import java.util.Scanner;

import static Menus.MenuAdmin.historial;

public class MenuListarUsuarios {

    public MenuListarUsuarios(Usuario usuario) throws SQLException {
        UsuarioServicios usuarioServicios = new UsuarioServicios();
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n--- " + (historial.isEmpty() ? "Menú Principal" : historial.peek()) + " ---");
            System.out.println("Seleccione una opción ingresando el número correspondiente:");
            System.out.println("1. Listar usuarios por nombre.");
            System.out.println("2. Listar usuarios por apellido.");
            System.out.println("3. Listar usuarios por documento.");
            System.out.println("4. Listar usuarios por tipo de usuario.");
            System.out.println("0. Volver al menú anterior.");
            System.out.println("-------------------------");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    usuarioServicios.buscarYMostrarUsuariosNombre();
                    break;
                case 2:
                    usuarioServicios.buscarYMostrarUsuariosApellido();
                    break;
                case 3:
                    usuarioServicios.buscarYMostrarUsuariosDocumento();
                    break;
                case 4:
                    new MenuListarUsuariosId();
                    break;
                case 0:
                    System.out.println("Volviendo al menú anterior...");
                    MenuAdmin.volverAtras();
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, intente nuevamente.");
            }
        } while (opcion != 0);
    }
}
