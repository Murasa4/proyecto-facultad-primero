package Menus;

import Clases.Usuario;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.Stack;

public class MenuAdmin {
    public static Stack<String> historial = new Stack<>(); // Pila para volver atrás
    private Usuario usuario;
    private Scanner scanner = new Scanner(System.in);

    public MenuAdmin(Usuario usuario) throws SQLException {
        this.usuario = usuario;
        mostrarMenu();
    }

    private void mostrarMenu() throws SQLException {
        int opcion;
        do {
            System.out.println("\n--- " + (historial.isEmpty() ? "Menú Principal" : historial.peek()) + " ---");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Gestión de usuarios.");
            System.out.println("2. Gestión de actividades.");
            System.out.println("3. Gestión de tipos de actividades.");
            System.out.println("4. Gestión de pagos.");
            System.out.println("5. Gestión de espacios.");
            System.out.println("6. Cerrar sesión.");
            System.out.println("-------------------------");

            opcion = scanner.nextInt();
            scanner.nextLine(); // Limpiar buffer

            switch (opcion) {
                case 1:
                    historial.push("Gestión de usuarios");
                    new MenuUsuarios(usuario);
                    break;
                case 2:
                    historial.push("Gestión de actividades");
                    new MenuActividades(usuario);
                    break;
                case 3:
                    historial.push("Gestión de tipos de actividades");
                    new MenuTipoActividades(usuario);
                    break;
                case 4:
                    historial.push("Gestión de pagos");
                    new MenuPagos(usuario);
                    break;
                case 5:
                    historial.push("Gestión de espacios");
                    new MenuEspacios(usuario);
                    break;
                case 6:
                    System.out.println("Cerrar sesión");
                    new MenuInicio();
                    break;
                default:
                    System.out.println("Opción no válida, intente nuevamente...");
            }

        } while (opcion != 6);
    }

    public static void volverAtras() {
        if (!historial.isEmpty()) {
            //System.out.println("Volviendo a: " + historial.peek());
            historial.pop();
        }
    }
}
