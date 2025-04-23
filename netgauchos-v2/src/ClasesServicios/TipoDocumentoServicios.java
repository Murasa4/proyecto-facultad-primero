package ClasesServicios;

import Clases.TipoDocumento;
import ClasesDAO.TipoDocumentoDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class TipoDocumentoServicios {

    public static int mostrarYSeleccionarTipoDocumento() throws SQLException {
        TipoDocumentoDAO tipoDocumentoDAO = TipoDocumentoDAO.getInstance(); // Instancia del DAO
        Scanner scanner = new Scanner(System.in);

        List<TipoDocumento> tiposDocumento = tipoDocumentoDAO.listarTiposDocumentos(); // Lista de los tipos de documento

        //Listar tipos
        System.out.println("-------------------------");
        System.out.println("Seleccione el tipo de documento ingresando el número correspondiente:");
        for (int i = 0; i < tiposDocumento.size(); i++) {
            System.out.println((i + 1) + ". " + tiposDocumento.get(i).getNombre());
        }
        System.out.println("-------------------------");

        //Validar seleccion
        int opcion = -1;
        do {
            if (scanner.hasNextInt()) { // Validar que sea un número
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir salto de línea

                if (opcion > 0 && opcion <= tiposDocumento.size()) {
                    // Retornar el ID del tipo seleccionado
                    return tiposDocumento.get(opcion - 1).getIdTipoDocumento();
                }
            }

            System.out.println("-------------------------");
            System.out.println("Opción no válida. Por favor, intente nuevamente.");
            scanner.nextLine(); // Consumir entrada inválida
        } while (true);
    }
}
