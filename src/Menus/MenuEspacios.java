package Menus;

import Clases.Usuario;
import ClasesServicios.EspacioServicios;
import ClasesServicios.ReservaServicios;

import java.util.Scanner;

import static Menus.MenuAdmin.historial;

public class MenuEspacios {

    public MenuEspacios(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n--- " + (historial.isEmpty() ? "Menú Principal" : historial.peek()) + " ---");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Crear un espacio.");
            System.out.println("2. Listar espacios.");
            System.out.println("3. Modificar un espacio.");
            System.out.println("4. Eliminar un espacio.");
            System.out.println("5. Reservar un espacio.");
            System.out.println("6. Cancelar reserva a un espacio.");
            System.out.println("7. Generar reporte de reservas/cancelaciones de espacios por fechas.");
            System.out.println("8. Generar reporte de reservas/cancelaciones por espacio.");
            System.out.println("0. Volver al menú anterior.");
            System.out.println("-------------------------");

            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    historial.push("Gestión de espacios");
                    EspacioServicios.agregarEspacio();
                    break;
                case 2:
                    historial.push("Gestión de espacios");
                    int filtroOpcion = 0;
                    do {
                        System.out.println("\n--- " + (historial.isEmpty() ? "Menú Principal" : historial.peek()) + " ---");
                        System.out.println("Seleccione una opción para listar espacios:");
                        System.out.println("1. Filtrar por nombre.");
                        System.out.println("2. Filtrar por estado.");
                        System.out.println("0. Volver al menú anterior.");
                        filtroOpcion = scanner.nextInt();

                        switch (filtroOpcion) {
                            case 1:
                                // Filtrar por nombre.
                                EspacioServicios.buscarYMostrarEspaciosPorNombre();
                                break;
                            case 2:
                                // Filtrar por estado
                                EspacioServicios.listarEspaciosPorEstado();
                                break;
                            case 0:
                                System.out.println("Volviendo al menú anterior...");
                                MenuAdmin.volverAtras();
                                break;
                            default:
                                System.out.println("Opción no válida. Intente nuevamente.");
                        }
                    } while (filtroOpcion != 0);
                    break;
                case 3:
                    historial.push("Gestión de espacios");
                    EspacioServicios.actualizarEspacio();
                    break;
                case 4:
                    historial.push("Gestión de espacios");
                    EspacioServicios.eliminarEspacio();
                    break;
                case 5:
                    historial.push("Gestión de espacios");
                    ReservaServicios.reservarEspacio();
                    break;
                case 6:
                    historial.push("Gestión de espacios");
                    ReservaServicios.cancelarReserva();
                    break;
                case 7:
                    int filtroOpcion2 = 0;
                    do {
                        System.out.println("Seleccione el tipo de reporte:");
                        System.out.println("1. Generar reporte de reservas por fecha.");
                        System.out.println("0. Volver al menú anterior.");
                        filtroOpcion2 = scanner.nextInt();

                        switch (filtroOpcion2) {
                            case 1:
                                historial.push("Gestión de espacios");
                                ReservaServicios.generarReporteReservasPorFecha();
                                break;
                            case 0:
                                System.out.println("Volviendo al menú anterior...");
                                MenuAdmin.volverAtras();
                                break;
                            default:
                                System.out.println("Opción no válida. Intente nuevamente.");
                        }
                    } while (filtroOpcion2 != 0);
                    break;
                case 8:
                    int filtroOpcion3 = 0;
                    do {
                        System.out.println("Seleccione el tipo de reporte:");
                        System.out.println("1. Generar reporte de reservas por espacio.");
                        System.out.println("0. Volver al menú anterior.");
                        filtroOpcion3 = scanner.nextInt();

                        switch (filtroOpcion3) {
                            case 1:
                                historial.push("Gestión de espacios");
                                ReservaServicios.generarReporteReservasPorEspacio();
                                break;
                            case 0:
                                System.out.println("Volviendo al menú anterior...");
                                MenuAdmin.volverAtras();
                                break;
                            default:
                                System.out.println("Opción no válida. Intente nuevamente.");
                        }
                    } while (filtroOpcion3 != 0);
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
