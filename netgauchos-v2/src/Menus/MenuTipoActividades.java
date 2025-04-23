package Menus;

import Clases.Usuario;
import ClasesServicios.TipoActividadServicios;

import java.util.Scanner;

import static Menus.MenuAdmin.historial;

public class MenuTipoActividades {

    public MenuTipoActividades(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n--- " + (historial.isEmpty() ? "Menú Principal" : historial.peek()) + " ---");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Crear tipo de actividad.");
            System.out.println("2. Listar tipos de actividades.");
            System.out.println("3. Modificar tipo de actividad.");
            System.out.println("4. Eliminar tipo de actividad.");
            System.out.println("0. Volver al menú anterior.");
            System.out.println("-------------------------");

            // Verificar que el usuario ingrese un número válido
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();

                // Verificar si la opción está dentro del rango de opciones
                if (opcion >= 0 && opcion <= 4) {
                    switch (opcion) {
                        case 1:
                            TipoActividadServicios.agregarTipoActividad();
                            break;
                        case 2:
                            TipoActividadServicios.mostrarTiposActividades();
                            break;
                        case 3:
                            TipoActividadServicios.actualizarTipoActividad();
                            break;
                        case 4:
                            TipoActividadServicios.eliminarTipoActividad();
                            break;
                        case 0:
                            System.out.println("Volviendo al menú anterior...");
                            MenuAdmin.volverAtras();
                            break;
                    }
                } else {
                    System.out.println("Opción no válida. Por favor, ingrese un número entre 0 y 4.");
                }
            } else {
                //Si el usuario no ingresa un número entero
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next();
            }
        } while (opcion != 0); // Continuar mientras la opción no sea 0
    }
}