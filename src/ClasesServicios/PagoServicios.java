package ClasesServicios;

import Clases.Pago;
import Clases.Usuario;
import ClasesDAO.PagoDAO;
import ClasesDAO.UsuarioDAO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

public class PagoServicios {

    public static void agregarPago() {
        Scanner scanner = new Scanner(System.in);

        int idTipoDocumento = 0;
        String numDocumento = "";
        double monto = 0.0;
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario usuarioExistente = null;
        boolean continuar = true;

        while (continuar) {
            // Seleccionar tipo de documento.
            while (true) {
                System.out.println("Seleccione el tipo de documento del usuario asociado al pago:");
                System.out.println("1. Cédula de Identidad");
                System.out.println("2. Pasaporte");
                System.out.println("Ingrese 1 o 2:");
                String opcion = scanner.nextLine();

                if (opcion.equals("1")) {
                    idTipoDocumento = 1;
                    break;
                } else if (opcion.equals("2")) {
                    idTipoDocumento = 2;
                    break;
                } else {
                    System.out.println("Opción inválida. Seleccione 1 o 2.");
                }
            }

            // Solicitar número de documento con validaciones.
            while (true) {
                System.out.println("Ingrese el número de documento:");
                numDocumento = scanner.nextLine().trim();

                if (idTipoDocumento == 1) { // Validación para CI uruguaya
                    if (numDocumento.matches("\\d{8}")) {
                        break;
                    } else {
                        System.out.println("Error: La cédula de identidad debe contener 8 dígitos sin puntos ni guiones.");
                    }
                } else if (idTipoDocumento == 2) { // Validación para pasaporte uruguayo.
                    if (numDocumento.matches("^[A-Z]\\d{6}$")) {
                        break;
                    } else {
                        System.out.println("Error: El pasaporte debe tener una letra seguida de 6 dígitos numéricos.");
                    }
                }
            }

            // Buscar usuario en la base de datos y verificar existencia
            while (true) {
                try {
                    usuarioExistente = usuarioDAO.obtenerUsuarioPorTipoDocumento(idTipoDocumento, numDocumento);
                    if (usuarioExistente == null) {
                        System.out.println("Error: No existe un usuario con el documento ingresado.");
                        System.out.println("Por favor, a continuación ingrese un número de documento válido.");
                        numDocumento = scanner.nextLine().trim(); // Volver a pedir el número de documento
                    } else {
                        break; // Si el usuario existe, salimos del ciclo
                    }
                } catch (SQLException e) {
                    System.out.println("Error al verificar el usuario en la base de datos.");
                    e.printStackTrace();
                    return;
                }
            }

            // Obtener la fecha actual
            Timestamp fecha = new Timestamp(System.currentTimeMillis());

            // Solicitar monto del pago
            while (true) {
                try {
                    System.out.println("Ingrese el monto del pago:");
                    monto = Double.parseDouble(scanner.nextLine());
                    if (monto > 0) break;
                    else System.out.println("El monto debe ser un número positivo.");
                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número válido para el monto.");
                }
            }

            // Crear el pago
            Pago pago = new Pago.PagoBuilder()
                    .setFecha(fecha)
                    .setMonto(monto)
                    .setUsuario(usuarioExistente)
                    .setEstado(true)
                    .build();

            // Guardar el pago en la base de datos
            try {
                PagoDAO pagoDAO = PagoDAO.getInstance();
                pagoDAO.agregarPago(pago);
                System.out.println("Pago ingresado con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al ingresar el pago en la base de datos.");
                e.printStackTrace();
            }

            // Preguntar si desea ingresar otro tipo de actividad.
            int opcion;
            while (true) {
                System.out.println("\n¿Desea ingresar otro pago?");
                System.out.println("1. Sí");
                System.out.println("2. No");
                System.out.print("Ingrese el número de la opción: ");
                try {
                    opcion = Integer.parseInt(scanner.nextLine().trim());
                    if (opcion == 1 || opcion == 2) {
                        break;
                    } else {
                        System.out.println("Opción inválida. Debe ingresar 1 o 2.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Debe ingresar un número válido.");
                }
            }

            continuar = (opcion == 1);
        }
    }

    public static void actualizarPago() {
        Scanner scanner = new Scanner(System.in);
        int idPago = 0;
        int idTipoDocumento = 0;
        String numDocumento = "";
        Timestamp nuevaFecha = null;
        double nuevoMonto = 0.0;

        PagoDAO pagoDAO = PagoDAO.getInstance();
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Pago pagoExistente = null;
        Usuario usuarioExistente = null;
        boolean continuar = true;

        while (continuar) {
            // Seleccionar tipo de documento.
            while (true) {
                System.out.println("Seleccione el tipo de documento del usuario asociado al pago:");
                System.out.println("1. Cédula de Identidad");
                System.out.println("2. Pasaporte");
                System.out.println("Ingrese 1 o 2:");
                String opcion = scanner.nextLine();

                if (opcion.equals("1")) {
                    idTipoDocumento = 1;
                    break;
                } else if (opcion.equals("2")) {
                    idTipoDocumento = 2;
                    break;
                } else {
                    System.out.println("Opción inválida. Seleccione 1 o 2.");
                }
            }

            // Solicitar número de documento con validaciones.
            while (true) {
                System.out.println("Ingrese el número de documento:");
                numDocumento = scanner.nextLine().trim();

                if (idTipoDocumento == 1) { // Validación para CI uruguaya
                    if (numDocumento.matches("\\d{8}")) {
                        break;
                    } else {
                        System.out.println("Error: La cédula de identidad debe contener 8 dígitos sin puntos ni guiones.");
                    }
                } else if (idTipoDocumento == 2) { // Validación para pasaporte uruguayo.
                    if (numDocumento.matches("^[A-Z]\\d{6}$")) {
                        break;
                    } else {
                        System.out.println("Error: El pasaporte debe tener una letra seguida de 6 dígitos numéricos.");
                    }
                }
            }

            // Buscar usuario en la base de datos y verificar existencia.
            while (true) {
                try {
                    usuarioExistente = usuarioDAO.obtenerUsuarioPorTipoDocumento(idTipoDocumento, numDocumento);
                    if (usuarioExistente == null) {
                        System.out.println("No existe un usuario con el documento ingresado.");
                        System.out.println("Por favor, a continuación ingrese un número de documento válido.");
                        numDocumento = scanner.nextLine().trim(); // Volver a pedir el número de documento
                    } else {
                        break;
                    }
                } catch (SQLException e) {
                    System.out.println("Error al verificar el usuario en la base de datos.");
                    e.printStackTrace();
                    return;
                }
            }

            try {
                List<Pago> pagos = pagoDAO.obtenerPagosPorUsuario(usuarioExistente);

                if (pagos.isEmpty()) {
                    System.out.println("El usuario no tiene pagos registrados.");
                    return;
                }

                // Mostrar pagos para el usuario.
                System.out.println("Pagos del usuario:");
                for (int i = 0; i < pagos.size(); i++) {
                    Pago pago = pagos.get(i);
                    System.out.println((i + 1) + ". Monto: $" + pago.getMonto() + " | Fecha: " + pago.getFecha());
                }

                // Seleccionar pago a actualizar.
                int pagoSeleccionado = -1;
                while (pagoSeleccionado < 1 || pagoSeleccionado > pagos.size()) {
                    System.out.println("Seleccione el número del pago que desea actualizar:");
                    try {
                        pagoSeleccionado = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor, ingrese un número válido.");
                        continue;
                    }
                }

                Pago pagoAActualizar = pagos.get(pagoSeleccionado - 1);
                System.out.println("Has seleccionado el pago con Monto: $" + pagoAActualizar.getMonto() + " | Fecha: " + pagoAActualizar.getFecha());

                // Ingresar nuevo monto.
                while (true) {
                    System.out.println("Ingrese el nuevo monto para este pago:");
                    try {
                        nuevoMonto = Double.parseDouble(scanner.nextLine());
                        if (nuevoMonto <= 0) {
                            System.out.println("El monto debe ser mayor a 0.");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor, ingrese un monto válido.");
                    }
                }

                // Confirmación para actualizar el pago.
                int confirmacion = 0;
                while (true) {
                    System.out.println("\n¿Está seguro de que desea actualizar el monto de este pago?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");

                    try {
                        confirmacion = Integer.parseInt(scanner.nextLine().trim());
                        if (confirmacion == 1 || confirmacion == 2) {
                            break;
                        } else {
                            System.out.println("Por favor, elija una opción válida (1 o 2).");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                if (confirmacion == 1) {
                    // Actualizar el pago.
                    pagoAActualizar.setMonto(nuevoMonto);
                    try {
                        pagoDAO.actualizarPago(pagoAActualizar);
                        System.out.println("Pago actualizado exitosamente.");
                    } catch (SQLException e) {
                        System.out.println("Error al actualizar el pago en la base de datos.");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Actualización cancelada.");
                }

                // Preguntar si desea actualizar otro pago.
                int opcion;
                while (true) {
                    System.out.println("\n¿Desea actualizar otro pago?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");
                    System.out.print("Ingrese el número de la opción: ");
                    try {
                        opcion = Integer.parseInt(scanner.nextLine().trim());
                        if (opcion == 1 || opcion == 2) {
                            break;
                        } else {
                            System.out.println("Opción inválida. Debe ingresar 1 o 2.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                continuar = (opcion == 1);

            } catch (SQLException e) {
                System.out.println("Error al obtener los pagos del usuario.");
                e.printStackTrace();
                continuar = false;
            }
        }
    }

    public static void eliminarPago() {
        Scanner scanner = new Scanner(System.in);
        PagoDAO pagoDAO = PagoDAO.getInstance();
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        Usuario usuarioExistente = null;
        List<Pago> pagosActivos = null;
        int idTipoDocumento = 0;
        String numDocumento = "";
        boolean continuar = true;

        while (continuar) {
            // Seleccionar tipo de documento.
            while (true) {
                System.out.println("Seleccione el tipo de documento del usuario asociado al pago:");
                System.out.println("1. Cédula de Identidad");
                System.out.println("2. Pasaporte");
                System.out.println("Ingrese 1 o 2:");
                String opcion = scanner.nextLine();

                if (opcion.equals("1")) {
                    idTipoDocumento = 1;
                    break;
                } else if (opcion.equals("2")) {
                    idTipoDocumento = 2;
                    break;
                } else {
                    System.out.println("Opción inválida. Seleccione 1 o 2.");
                }
            }

            // Solicitar número de documento con validaciones.
            while (true) {
                System.out.println("Ingrese el número de documento:");
                numDocumento = scanner.nextLine().trim();

                if (idTipoDocumento == 1) { // Validación para CI uruguaya
                    if (numDocumento.matches("\\d{8}")) {
                        break;
                    } else {
                        System.out.println("Error: La cédula de identidad debe contener 8 dígitos sin puntos ni guiones.");
                    }
                } else if (idTipoDocumento == 2) { // Validación para pasaporte uruguayo.
                    if (numDocumento.matches("^[A-Z]\\d{6}$")) {
                        break;
                    } else {
                        System.out.println("Error: El pasaporte debe tener una letra seguida de 6 dígitos numéricos.");
                    }
                }
            }

            // Buscar usuario en la base de datos y verificar existencia.
            while (true) {
                try {
                    usuarioExistente = usuarioDAO.obtenerUsuarioPorTipoDocumento(idTipoDocumento, numDocumento);
                    if (usuarioExistente == null) {
                        System.out.println("No existe un usuario con el documento ingresado.");
                        System.out.println("Por favor, a continuación ingrese un número de documento válido.");
                        numDocumento = scanner.nextLine().trim(); // Volver a pedir el número de documento
                    } else {
                        break;
                    }
                } catch (SQLException e) {
                    System.out.println("Error al verificar el usuario en la base de datos.");
                    e.printStackTrace();
                    return;
                }
            }

            try {
                List<Pago> pagos = pagoDAO.obtenerPagosActivosPorUsuario(usuarioExistente);

                if (pagos.isEmpty()) {
                    System.out.println("El usuario no tiene pagos registrados.");
                    return;
                }

                // Mostrar pagos para el usuario.
                System.out.println("Pagos del usuario:");
                for (int i = 0; i < pagos.size(); i++) {
                    Pago pago = pagos.get(i);
                    System.out.println((i + 1) + ". Monto: $" + pago.getMonto() + " | Fecha: " + pago.getFecha());
                }

                // Seleccionar pago a eliminar.
                int pagoSeleccionado = -1;
                while (pagoSeleccionado < 1 || pagoSeleccionado > pagos.size()) {
                    System.out.println("Seleccione el número del pago que desea eliminar:");
                    try {
                        pagoSeleccionado = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor, ingrese un número válido.");
                        continue;
                    }
                }

                Pago pagoAEliminar = pagos.get(pagoSeleccionado - 1);
                System.out.println("Has seleccionado el pago con Monto: $" + pagoAEliminar.getMonto() + " | Fecha: " + pagoAEliminar.getFecha());


                // Confirmación para eliminar el pago.
                int confirmacion = 0;
                while (true) {
                    System.out.println("\n¿Está seguro de que desea eliminar este pago?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");

                    try {
                        confirmacion = Integer.parseInt(scanner.nextLine().trim());
                        if (confirmacion == 1 || confirmacion == 2) {
                            break;
                        } else {
                            System.out.println("Por favor, elija una opción válida (1 o 2).");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                if (confirmacion == 1) {
                    //Eliminar el pago.
                    try {
                        pagoDAO.eliminarPago(pagoAEliminar.getIdPago());
                        System.out.println("Pago eliminado exitosamente.");
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar el pago en la base de datos.");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Actualización cancelada.");
                }

                // Preguntar si desea eliminar otro pago.
                int opcion;
                while (true) {
                    System.out.println("\n¿Desea eliminar otro pago?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");
                    System.out.print("Ingrese el número de la opción: ");
                    try {
                        opcion = Integer.parseInt(scanner.nextLine().trim());
                        if (opcion == 1 || opcion == 2) {
                            break;
                        } else {
                            System.out.println("Opción inválida. Debe ingresar 1 o 2.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                continuar = (opcion == 1);

            } catch (SQLException e) {
                System.out.println("Error al obtener los pagos del usuario.");
                e.printStackTrace();
                continuar = false;
            }
        }
    }


    public static void mostrarPagos() {
        // Crear una instancia de PagoDAO para obtener los pagos.
        PagoDAO pagoDAO = PagoDAO.getInstance();

        try {
            // Obtener la lista de pagos.
            List<Pago> pagos = pagoDAO.listarPagos();

            if (pagos.isEmpty()) {
                System.out.println("No hay pagos registrados.");
            } else {
                System.out.println("Lista de pagos:");

                // Mostrar los pagos.
                for (Pago pago : pagos) {
                    System.out.println(pago.toString());  // Mostrar la información de cada pago.
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al obtener la lista de pagos.");
            e.printStackTrace();
        }

        System.out.println("Proceso finalizado.");
    }
}