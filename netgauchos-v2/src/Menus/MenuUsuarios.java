package Menus;

import Clases.Usuario;

import java.sql.SQLException;
import java.util.Scanner;

import static Menus.MenuAdmin.historial;

public class MenuUsuarios {

    public MenuUsuarios(Usuario usuario) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n--- " + (historial.isEmpty() ? "Menú Principal" : historial.peek()) + " ---");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Listar usuarios.");
            System.out.println("2. Agregar usuario.");
            System.out.println("3. Actualizar un usuario.");
            System.out.println("4. Eliminar usuario.");
            System.out.println("0. Volver al menú anterior.");
            System.out.println("-------------------------");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    historial.push("Gestión de usuarios");
                    new MenuListarUsuarios(usuario);
                    break;
                case 2:
                    new MenuAgregarUsuario();
                    break;
                case 3:
                    new MenuActualizarUsuario();
                    break;
                case 4:
                    new MenuEliminarUsuario();
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
