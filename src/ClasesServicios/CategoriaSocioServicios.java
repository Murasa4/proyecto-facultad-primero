package ClasesServicios;

import Clases.CategoriaSocio;
import ClasesDAO.CategoriaSocioDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class CategoriaSocioServicios {

    public static int mostrarYSeleccionarCategoriaSocio() throws SQLException {
        CategoriaSocioDAO categoriaSocioDAOSocioDAO = CategoriaSocioDAO.getInstance(); // Instancia del DAO
        Scanner scanner = new Scanner(System.in);

        List<CategoriaSocio> categoriasSocio = categoriaSocioDAOSocioDAO.listarCategoriaSocios(); // Lista de las categorias de socio

        //Listar tipos
        System.out.println("-------------------------");
        System.out.println("Seleccione la categoría de socio ingresando el número correspondiente:");
        for (int i = 0; i < categoriasSocio.size(); i++) {
            System.out.println((i + 1) + ". " + categoriasSocio.get(i).getNombre());
        }
        System.out.println("-------------------------");

        //Validar seleccion
        int opcion = -1;
        do {
            if (scanner.hasNextInt()) { // Validar que sea un número
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir salto de línea

                if (opcion > 0 && opcion <= categoriasSocio.size()) {
                    // Retornar el ID de la categoria seleccionada
                    return categoriasSocio.get(opcion - 1).getIdCategoriaSocio();
                }
            }

            System.out.println("-------------------------");
            System.out.println("Opción no válida. Por favor, intente nuevamente.");
            scanner.nextLine(); // Consumir entrada inválida
        } while (true);
    }
}
