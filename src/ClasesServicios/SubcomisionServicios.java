package ClasesServicios;

import Clases.Subcomision;
import ClasesDAO.SubcomisionDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class SubcomisionServicios {

    public static int mostrarYSeleccionarSubcomision() throws SQLException {
        SubcomisionDAO subcomisionDAO = SubcomisionDAO.getInstance(); // Instancia del DAO
        Scanner scanner = new Scanner(System.in);

        List<Subcomision> subcomisiones = subcomisionDAO.listarSubcomisiones(); // Lista de las subcomisiones

        //Listar subcomisiones
        System.out.println("-------------------------");
        System.out.println("Seleccione la subcomisión ingresando el número correspondiente:");
        for (int i = 0; i < subcomisiones.size(); i++) {
            System.out.println((i + 1) + ". " + subcomisiones.get(i).getNombre());
        }
        System.out.println("-------------------------");
        System.out.println("Presione Enter para omitir la selección de subcomisión.");
        System.out.println("-------------------------");

        // Capturar entrada del usuario7
        String opcion = scanner.nextLine().trim(); // Leer la entrada del usuario y eliminar espacios en blanco

        // Validar selección
        if (opcion.isEmpty()) {
            // Si el usuario presiona Enter, retornar 0 (sin selección)
            return 0;
        }

        try {
            int opcionNumerica = Integer.parseInt(opcion); // Convertir la entrada a número

            // Verificar que sea una opción válida
            if (opcionNumerica > 0 && opcionNumerica <= subcomisiones.size()) {
                return subcomisiones.get(opcionNumerica - 1).getIdSubcomision();
            } else {
                System.out.println("Opción inválida. Intente nuevamente.");
                return mostrarYSeleccionarSubcomision(); // Llamar recursivamente para reintentar
            }
        } catch (NumberFormatException e) {
            // Si la entrada no es un número, mostrar mensaje e intentar nuevamente
            System.out.println("Entrada inválida. Por favor, ingrese un número o presione Enter para omitir.");
            return mostrarYSeleccionarSubcomision();
        }
    }
}
