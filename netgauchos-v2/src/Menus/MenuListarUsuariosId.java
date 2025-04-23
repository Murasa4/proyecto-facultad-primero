package Menus;

import ClasesServicios.UsuarioServicios;

import java.util.Scanner;

import static Menus.MenuAdmin.historial;

public class MenuListarUsuariosId {

    public MenuListarUsuariosId() {
        UsuarioServicios usuarioServicios = new UsuarioServicios();
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n--- " + (historial.isEmpty() ? "Menú Principal" : historial.peek()) + " ---");
            System.out.println("Seleccione el tipo de usuario a filtrar ingresando el número correspondiente:");
            System.out.println("1. Listar usuarios Administrador.");
            System.out.println("2. Listar usuarios Auxiliares.");
            System.out.println("3. Listar usuarios Socios");
            System.out.println("4. Listar usuarios No socios");
            System.out.println("0. Volver al menú anterior.");
            System.out.println("-------------------------");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    usuarioServicios.buscarYMostrarUsuariosIdTipoUsuario(1);
                    break;
                case 2:
                    usuarioServicios.buscarYMostrarUsuariosIdTipoUsuario(2);
                    break;
                case 3:
                    usuarioServicios.buscarYMostrarUsuariosIdTipoUsuario(3);
                    break;
                case 4:
                    usuarioServicios.buscarYMostrarUsuariosIdTipoUsuario(4);
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
