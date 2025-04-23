package Menus;

import Clases.Usuario;
import ClasesServicios.PagoServicios;

import java.util.Scanner;

import static Menus.MenuAdmin.historial;

public class MenuPagos {

    public MenuPagos(Usuario usuario) {
        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        do {
            System.out.println("\n--- " + (historial.isEmpty() ? "Menú Principal" : historial.peek()) + " ---");
            System.out.println("Seleccione una opción:");
            System.out.println("1. Crear un pago.");
            System.out.println("2. Listar pagos.");
            System.out.println("3. Modificar un pago.");
            System.out.println("4. Eliminar un pago.");
            System.out.println("0. Volver al menú anterior.");
            System.out.println("-------------------------");

            // Verificar que el usuario ingrese un número válido
            if (scanner.hasNextInt()) {
                opcion = scanner.nextInt();

                // Verificar si la opción está dentro del rango de opciones
                if (opcion >= 0 && opcion <= 4) {
                    switch (opcion) {
                        case 1:
                            PagoServicios.agregarPago();
                            break;
                        case 2:
                            PagoServicios.mostrarPagos();
                            break;
                        case 3:
                            PagoServicios.actualizarPago();
                            break;
                        case 4:
                            PagoServicios.eliminarPago();
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
        } while (opcion != 0); // Continuar mientras la opción no sea 0
    }
}