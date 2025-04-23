package Menus;

import Clases.Usuario;
import ClasesServicios.ActividadServicios;
import ClasesServicios.InscripcionServicios;

import java.util.Scanner;

import static Menus.MenuAdmin.historial;

public class MenuActividades {

    public MenuActividades(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n--- " + (historial.isEmpty() ? "Menú Principal" : historial.peek()) + " ---");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Crear una actividad.");
            System.out.println("2. Listar actividades.");
            System.out.println("3. Modificar una actividad.");
            System.out.println("4. Eliminar una actividad.");
            System.out.println("5. Listar actividades para inscribirse.");
            System.out.println("6. Inscribir en una actividad.");
            System.out.println("7. Cancelar inscripción en una actividad.");
            System.out.println("8. Generar reporte de inscripciones/cancelaciones a actividades por fecha.");
            System.out.println("9. Generar reporte de inscripciones/cancelaciones a actividades por tipo de actividad.");
            System.out.println("0. Volver al menú anterior.");
            System.out.println("-------------------------");

            // Verificar que el usuario ingrese un número válido.
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();

                // Verificar si la opción está dentro del rango de opciones.
                if (opcion >= 0 && opcion <= 9) {
                    switch (opcion) {
                        case 1:
                            ActividadServicios.agregarActividad();
                            break;
                        case 2:
                            int filtroOpcion = 0;
                            do {
                                System.out.println("Seleccione el tipo de filtro para el listado:");
                                System.out.println("1. Nombre.");
                                System.out.println("2. Tipo de actividad.");
                                System.out.println("3. Fecha.");
                                System.out.println("4. Estado.");
                                System.out.println("0. Volver al menú anterior.");
                                filtroOpcion = scanner.nextInt();

                                switch (filtroOpcion) {
                                    case 1:
                                        //Listar actividades por nombre.
                                        ActividadServicios.mostrarActividadesPorNombre();
                                        break;
                                    case 2:
                                        //Listar actividades por tipo de actividad.
                                        ActividadServicios.mostrarActividadesPorTipo();
                                        break;
                                    case 3:
                                        //Listar actividades por fecha:
                                        ActividadServicios.mostrarActividadesPorFecha();
                                        break;
                                    case 4:
                                        //Listar actividades por estado.
                                        ActividadServicios.mostrarActividadesPorEstado();
                                        break;
                                    case 0:
                                        System.out.println("Volviendo al menú anterior...");
                                        break;
                                    default:
                                        System.out.println("Opción no válida. Intente nuevamente.");
                                }
                            } while (filtroOpcion != 0);
                            break;
                        case 3:
                            ActividadServicios.actualizarActividad();
                            break;
                        case 4:
                            ActividadServicios.eliminarActividad();
                            break;
                        case 5:
                            ActividadServicios.listarActividadesParaInscribirse();
                            break;
                        case 6:
                            InscripcionServicios.realizarInscripcionActividad();
                            break;
                        case 7:
                            InscripcionServicios.cancelarInscripción();
                            break;
                        case 8:
                            InscripcionServicios.generarReporteActividadPorFecha();
                            break;
                        case 9:
                            InscripcionServicios.generarReporteActividadPorTipoActividad();
                            break;
                        case 0:
                            System.out.println("Volviendo al menú anterior...");
                            MenuAdmin.volverAtras();
                            break;
                    }
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            } else {
                //Si el usuario no ingresa un número entero:
                System.out.println("Por favor, ingrese un número válido.");
                scanner.next();
            }
        } while (opcion != 0);
    }
}
