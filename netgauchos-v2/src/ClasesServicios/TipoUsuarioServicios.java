package ClasesServicios;

import Clases.TipoUsuario;
import ClasesDAO.TipoUsuarioDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TipoUsuarioServicios {

    public static int mostrarYSeleccionarTipoUsuario() throws SQLException {
        TipoUsuarioDAO tipoUsuarioDAO = TipoUsuarioDAO.getInstance(); // Instancia del DAO
        Scanner scanner = new Scanner(System.in);

        List<TipoUsuario> tiposUsuario = tipoUsuarioDAO.listarTiposUsuario(); // Lista de los tipos de usuario

        //Listar tipos
        System.out.println("-------------------------");
        System.out.println("Seleccione el tipo de usuario ingresando el número correspondiente:");
        for (int i = 0; i < tiposUsuario.size(); i++) {
            System.out.println((i + 1) + ". " + tiposUsuario.get(i).getNombre());
        }
        System.out.println("-------------------------");

        //Validar seleccion
        int opcion = -1;
        do {
            if (scanner.hasNextInt()) { // Validar que sea un número
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir salto de línea

                if (opcion > 0 && opcion <= tiposUsuario.size()) {
                    // Retornar el ID del tipo seleccionado
                    return tiposUsuario.get(opcion - 1).getIdTipoUsuario();
                }
            }

            System.out.println("-------------------------");
            System.out.println("Opción no válida. Por favor, intente nuevamente.");
            scanner.nextLine(); // Consumir entrada inválida
        } while (true);
    }
}
