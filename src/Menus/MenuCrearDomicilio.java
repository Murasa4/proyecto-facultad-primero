package Menus;

import Clases.Departamento;
import Clases.Domicilio;
import ClasesDAO.DepartamentoDAO;
import ClasesDAO.DomicilioDAO;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class MenuCrearDomicilio {

    public static int crearDomicilio() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        DomicilioDAO domicilioDAO = DomicilioDAO.getInstance();
        DepartamentoDAO departamentoDAO = DepartamentoDAO.getInstance();

        System.out.println("-------------------------");
        System.out.println("Ingrese los datos del domicilio:");
        System.out.println("-------------------------");

        //Listar departamentos
        System.out.println("Seleccione el departamento donde reside:");
        List<Departamento> departamentos = departamentoDAO.listarDepartamentos();

        for (int i = 0; i < departamentos.size(); i++) {
            System.out.println((i + 1) + ". " + departamentos.get(i).getNombre());
        }

        int opcionDepartamento = -1;
        Departamento departamentoSeleccionado = null;
        while (true) {
            if (scanner.hasNextInt()) {
                opcionDepartamento = scanner.nextInt();
                scanner.nextLine(); // Consumir salto de línea
                if (opcionDepartamento > 0 && opcionDepartamento <= departamentos.size()) {
                    departamentoSeleccionado = departamentos.get(opcionDepartamento - 1);
                    break;
                }
            }
            System.out.println("Opción inválida. Intente nuevamente.");
        }

        // Pedir el resto de los datos del domicilio
        System.out.println("-------------------------");
        System.out.print("Ciudad: ");
        String ciudad = scanner.nextLine();

        System.out.println("-------------------------");
        System.out.print("Calle: ");
        String calle = scanner.nextLine();

        System.out.println("-------------------------");
        System.out.print("Número de puerta: ");
        int numPuerta = scanner.nextInt();
        scanner.nextLine(); // Consumir salto de línea

        System.out.println("-------------------------");
        System.out.print("Piso (si no aplica, deje en blanco): ");
        String piso = scanner.nextLine();

        System.out.println("-------------------------");
        System.out.print("Apartamento (si no aplica, deje en blanco): ");
        String apartamento = scanner.nextLine();

        // Crear objeto Domicilio
        Domicilio domicilio = new Domicilio.DomicilioBuilder()
                .setDepartamento(departamentoSeleccionado)
                .setCiudad(ciudad)
                .setCalle(calle)
                .setNumPuerta(numPuerta)
                .setPiso(piso.isEmpty() ? null : piso)
                .setApartamento(apartamento.isEmpty() ? null : apartamento)
                .setEstado(true)
                .build();

        // Registrar el domicilio en la base de datos
        domicilioDAO.agregarDomicilio(domicilio);

        // Retornar el ID del domicilio creado
        return domicilio.getIdDomicilio();
    }
}