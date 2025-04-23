package ClasesServicios;

import Clases.Espacio;
import Clases.Reserva;
import Clases.Usuario;
import ClasesDAO.EspacioDAO;
import ClasesDAO.ReservaDAO;
import ClasesDAO.UsuarioDAO;
import com.fabdelgado.ciuy.Validator;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class ReservaServicios {

    public static void reservarEspacio() {

        Scanner scanner = new Scanner(System.in);
        int idTipoDocumento = 0;
        String numDocumento = "";
        Usuario usuarioExistente;
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        EspacioDAO espacioDAO = EspacioDAO.getInstance();
        boolean continuar = true;
        Validator validator = new Validator(); // Crear instancia de Validator

        while (continuar) {
            try {
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

                    if (idTipoDocumento == 1) { // Validación para CI
                        if (validator.validateCi(numDocumento)) {
                            break;
                        } else {
                            System.out.println("Error: La cédula ingresada no tiene un formato válido.");
                        }
                    } else if (idTipoDocumento == 2) { // Validación para pasaporte
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
                            System.out.println("Error: No existe un usuario con el documento ingresado.");
                            System.out.println("Por favor, a continuación ingrese un número de documento válido.");
                            numDocumento = scanner.nextLine().trim(); // Volver a pedir el número de documento.
                        } else {
                            break; // Si el usuario existe, salimos del ciclo.
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al verificar el usuario en la base de datos.");
                        e.printStackTrace();
                        return;
                    }
                }

                // Obtener la fecha y hora actual del sistema.
                Timestamp fechaCreada = new Timestamp(System.currentTimeMillis());

                java.sql.Date fechaReserva = null;
                while (true) {
                    try {
                        System.out.println("Ingrese la fecha de la reserva (YYYY-MM-DD):");
                        String fechaString = scanner.nextLine();

                        // Convertir la fecha en String a java.sql.Date
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date parsedDate = dateFormat.parse(fechaString);

                        // Convertir java.util.Date a java.sql.Date
                        fechaReserva = new java.sql.Date(parsedDate.getTime());

                        Date ahora = new Date(System.currentTimeMillis());

                        // Verificar si la fecha de la reserva es futura.
                        if (fechaReserva.before(ahora)) {
                            System.out.println("Error: La fecha de la reserva debe ser futura.");
                        } else {
                            break;
                        }
                    } catch (ParseException e) {
                        System.out.println("Error: Formato de fecha incorrecto. Use YYYY-MM-DD.");
                    }
                }

                // Convertir la fecha a Date.
                Date fechaReservaDate = fechaReserva;

                Time horaInicio, horaFin;

                // Solicitar la hora de inicio
                while (true) {
                    try {
                        System.out.println("Ingrese la hora de inicio de la reserva (HH:MM:SS):");
                        String horaInicioString = scanner.nextLine();
                        horaInicio = Time.valueOf(horaInicioString);
                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: Formato de hora incorrecto. Use HH:MM:SS.");
                    }
                }

                //Solicitar la hora de fin
                while (true) {
                    try {
                        System.out.println("Ingrese la hora de fin de la reserva (HH:MM:SS):");
                        String horaFinString = scanner.nextLine();
                        horaFin = Time.valueOf(horaFinString);

                        // Verificar si la hora de fin es antes de la hora de inicio
                        if (horaFin.before(horaInicio)) {
                            // Si la hora de fin es medianoche, asumimos que es al día siguiente.
                            if (horaFin.equals(Time.valueOf("00:00:00"))) {
                                horaFin.setTime(horaFin.getTime() + 24 * 60 * 60 * 1000); // Añadir 24 horas.
                            } else {
                                System.out.println("Error: La hora de fin debe ser posterior a la hora de inicio.");
                                continue;
                            }
                        }

                        break;
                    } catch (IllegalArgumentException e) {
                        System.out.println("Error: Formato de hora incorrecto. Use HH:MM:SS.");
                    }
                }

                List<Espacio> espaciosDisponibles = null;
                Espacio espacioSeleccionado = null;

                do {
                    try {
                        // Obtener los espacios disponibles.
                        espaciosDisponibles = espacioDAO.listarEspaciosPorEstado(true);

                        if (espaciosDisponibles.isEmpty()) {
                            System.out.println("No hay espacios disponibles para reservar.");
                            return;
                        }

                        // Mostrar los espacios disponibles.
                        System.out.println("Espacios disponibles para reservar:");
                        for (int i = 0; i < espaciosDisponibles.size(); i++) {
                            Espacio espacio = espaciosDisponibles.get(i);
                            System.out.println((i + 1) + ". " + espacio.getNombre() + " - Capacidad: " + espacio.getCapacidad());
                        }

                        // Solicitar que el usuario seleccione un espacio.
                        int seleccion = 0;
                        while (true) {
                            try {
                                System.out.println("Seleccione el número del espacio que desea reservar:");
                                seleccion = Integer.parseInt(scanner.nextLine().trim());
                                if (seleccion < 1 || seleccion > espaciosDisponibles.size()) {
                                    System.out.println("Selección inválida. Por favor, elija un número válido.");
                                } else {
                                    break;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Error: Ingrese un número válido.");
                            }
                        }

                        espacioSeleccionado = espaciosDisponibles.get(seleccion - 1);
                        System.out.println("Has seleccionado: " + espacioSeleccionado.getNombre());

                        break;
                    } catch (SQLException e) {
                        System.out.println("Error al obtener los espacios de la base de datos.");
                        e.printStackTrace();
                    }
                } while (true);

                double montoSenia, saldo, montoTotal;

                // Solicitar monto de la seña.
                while (true) {
                    try {
                        System.out.println("Ingrese el monto de la seña:");
                        montoSenia = Double.parseDouble(scanner.nextLine());
                        if (montoSenia >= 0) break;
                        else System.out.println("El monto de la seña debe ser un número positivo o cero.");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número válido para el monto de la seña.");
                    }
                }

                // Solicitar saldo.
                while (true) {
                    try {
                        System.out.println("Ingrese el saldo restante:");
                        saldo = Double.parseDouble(scanner.nextLine());
                        if (saldo >= 0) break;
                        else System.out.println("El saldo debe ser un número positivo o cero.");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número válido para el saldo.");
                    }
                }

                // Solicitar monto total.
                while (true) {
                    try {
                        System.out.println("Ingrese el monto total:");
                        montoTotal = Double.parseDouble(scanner.nextLine());
                        if (montoTotal > 0) {
                            if (montoTotal >= montoSenia + saldo) break;
                            else System.out.println("El monto total debe ser al menos la suma de la seña y el saldo.");
                        } else {
                            System.out.println("El monto total debe ser un número positivo.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número válido para el monto total.");
                    }
                }

                int cantidadPersonas;

                // Solicitar cantidad de personas.
                while (true) {
                    try {
                        System.out.println("Ingrese la cantidad de personas:");
                        cantidadPersonas = Integer.parseInt(scanner.nextLine());
                        if (cantidadPersonas > 0) break;
                        else System.out.println("La cantidad de personas debe ser un número entero positivo.");
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número válido para la cantidad de personas.");
                    }
                }

                // Crear la reserva.
                Reserva reserva = new Reserva.ReservaBuilder()
                        .setUsuario(usuarioExistente)
                        .setFechaCreada(fechaCreada)
                        .setFechaReserva(fechaReservaDate)
                        .setHoraInicio(horaInicio)
                        .setHoraFin(horaFin)
                        .setEspacio(espacioSeleccionado)
                        .setMontoSenia(montoSenia)
                        .setSaldo(saldo)
                        .setMontoTotal(montoTotal)
                        .setCantidadPersonas(cantidadPersonas)
                        .setEstado(true)
                        .build();

                // Guardar la reserva en la base de datos.
                try {
                    ReservaDAO reservaDAO = ReservaDAO.getInstance();
                    reservaDAO.agregarReserva(reserva);
                    System.out.println("Reserva ingresada con éxito.");
                } catch (SQLException e) {
                    System.out.println("Error al ingresar la reserva en la base de datos.");
                    e.printStackTrace();
                }

                // Preguntar si desea ingresar otra actividad.
                int opcion;
                while (true) {
                    System.out.println("\n¿Desea crear otra reserva?");
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

            } catch (Exception e) {
                System.out.println("Ha ocurrido un error inesperado.");
                e.printStackTrace();
            }
        }
    }

    public static void cancelarReserva() {
        Scanner scanner = new Scanner(System.in);
        int idTipoDocumento = 0;
        String numDocumento = "";
        Usuario usuarioExistente;
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        ReservaDAO reservaDAO = ReservaDAO.getInstance();
        EspacioDAO espacioDAO = EspacioDAO.getInstance();
        boolean continuar = true;

        while (continuar) {
            try {
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

                    if (idTipoDocumento == 1) { // Validación para CI
                        if (numDocumento.matches("\\d{8}")) {
                            break;
                        } else {
                            System.out.println("Error: La cédula de identidad debe contener 8 dígitos sin puntos ni guiones.");
                        }
                    } else if (idTipoDocumento == 2) { // Validación para pasaporte
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
                            System.out.println("Error: No existe un usuario con el documento ingresado.");
                            System.out.println("Por favor, a continuación ingrese un número de documento válido.");
                            numDocumento = scanner.nextLine().trim(); // Volver a pedir el número de documento.
                        } else {
                            break; // Si el usuario existe, salimos del ciclo.
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al verificar el usuario en la base de datos.");
                        e.printStackTrace();
                        return;
                    }
                }

                // Obtener reservas activas asociadas al usuario.
                List<Reserva> reservasActivas = reservaDAO.obtenerReservasActivasPorUsuario(usuarioExistente.getIdUsuario());

                if (reservasActivas.isEmpty()) {
                    System.out.println("No tiene reservas activas asociadas.");
                    System.out.println("\n¿Desea eliminar reservas de otros usuarios? (Si/No):");
                    if (!scanner.nextLine().trim().equalsIgnoreCase("si")) {
                        break;
                    }
                    continue; // Volver a preguntar por otro usuario.
                }

                // Mostrar las reservas activas.
                System.out.println("Reservas activas para el usuario: " + usuarioExistente.getPrimerNombre() + " " + usuarioExistente.getPrimerApellido() + " | Nro. Documento: " + usuarioExistente.getNumeroDocumento());
                for (int i = 0; i < reservasActivas.size(); i++) {
                    Reserva reserva = reservasActivas.get(i);
                    System.out.println((i + 1) + ". Espacio: " + reserva.getEspacio().getNombre() +
                            " | Fecha: " + reserva.getFechaReserva());
                }

                // Solicitar al usuario que elija una reserva para eliminar.
                int seleccion = 0;
                while (true) {
                    System.out.println("Seleccione el número de la reserva que desea eliminar:");
                    try {
                        seleccion = Integer.parseInt(scanner.nextLine().trim());
                        if (seleccion >= 1 && seleccion <= reservasActivas.size()) {
                            break; // Reserva válida
                        } else {
                            System.out.println("Selección inválida. Elija un número de la lista.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor ingrese un número válido.");
                    }
                }

                // Obtener la reserva seleccionada y mostrar información completa.
                Reserva reservaSeleccionada = reservasActivas.get(seleccion - 1);
                System.out.println("Información de la reserva seleccionada:");
                System.out.println("\n---------------------------------------------------");
                System.out.println("Usuario: " + usuarioExistente.getPrimerNombre() + " " + usuarioExistente.getPrimerApellido());
                System.out.println("Fecha de la reserva: " + reservaSeleccionada.getFechaReserva());
                System.out.println("Hora de inicio: " + reservaSeleccionada.getHoraInicio());
                System.out.println("Hora de fin: " + reservaSeleccionada.getHoraFin());
                System.out.println("Espacio: " + reservaSeleccionada.getEspacio().getNombre());
                System.out.println("Monto de la seña: " + reservaSeleccionada.getMontoSenia());
                System.out.println("Saldo: " + reservaSeleccionada.getSaldo());
                System.out.println("Monto total: " + reservaSeleccionada.getMontoTotal());
                System.out.println("Cantidad de personas: " + reservaSeleccionada.getCantidadPersonas());
                System.out.println("---------------------------------------------------");

                // Confirmar eliminación.
                int opcionConfirmacion;
                while (true) {
                    System.out.println("\n¿Está seguro que desea eliminar este espacio?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");
                    System.out.print("Ingrese el número de la opción: ");
                    try {
                        opcionConfirmacion = Integer.parseInt(scanner.nextLine().trim());
                        if (opcionConfirmacion == 1 || opcionConfirmacion == 2) {
                            break;
                        } else {
                            System.out.println("Opción inválida. Debe ingresar 1 o 2.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                if (opcionConfirmacion == 1) {
                    try {
                        espacioDAO.eliminarEspacio(reservaSeleccionada.getIdReserva());
                        System.out.println("Reserva eliminada con éxito.");
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar la reserva en la base de datos.");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Eliminación cancelada.");
                }

                // Preguntar si desea cancelar otra reserva.
                int opcion;
                while (true) {
                    System.out.println("\n¿Desea cancelar otra reserva?");
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

            } catch (Exception e) {
                System.out.println("Ha ocurrido un error inesperado.");
                e.printStackTrace();
            }
        }
    }

    public static void generarReporteReservasPorFecha() {

        Scanner scanner = new Scanner(System.in);
        EspacioDAO espacioDAO = EspacioDAO.getInstance();
        ReservaDAO reservaDAO = ReservaDAO.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        boolean continuar = true;

        while (continuar) {
            try {
                List<Espacio> espacios = espacioDAO.listarEspaciosPorEstado(true);

                if (espacios.isEmpty()) {
                    System.out.println("No hay espacios registrados.");
                    return;
                }

                System.out.println("Lista de espacios:");
                for (int i = 0; i < espacios.size(); i++) {
                    Espacio espacio = espacios.get(i);
                    System.out.println((i + 1) + ". " + espacio.getNombre() + " - " + espacio.getUbicacion());
                }

                int seleccionEspacio;
                while (true) {
                    System.out.println("Seleccione el número del espacio para el reporte:");
                    try {
                        seleccionEspacio = Integer.parseInt(scanner.nextLine().trim());
                        if (seleccionEspacio >= 1 && seleccionEspacio <= espacios.size()) {
                            break;
                        } else {
                            System.out.println("Selección inválida. Elija un número válido.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor, ingrese un número válido.");
                    }
                }

                Espacio espacioSeleccionado = espacios.get(seleccionEspacio - 1);
                System.out.println("Has seleccionado el espacio: " + espacioSeleccionado.getNombre());

                java.util.Date fechaInicial = null;
                java.util.Date fechaFinal = null;
                while (fechaInicial == null) {
                    System.out.println("Ingrese la fecha inicial (YYYY-MM-DD):");
                    try {
                        fechaInicial = dateFormat.parse(scanner.nextLine().trim());
                    } catch (ParseException e) {
                        System.out.println("Fecha inválida. Intente nuevamente.");
                    }
                }

                while (fechaFinal == null) {
                    System.out.println("Ingrese la fecha final (YYYY-MM-DD):");
                    try {
                        fechaFinal = dateFormat.parse(scanner.nextLine().trim());
                        if (fechaFinal.before(fechaInicial)) {
                            System.out.println("La fecha final no puede ser antes de la fecha inicial.");
                            fechaFinal = null;
                        }
                    } catch (ParseException e) {
                        System.out.println("Fecha inválida. Intente nuevamente.");
                    }
                }

                List<Reserva> reservas = reservaDAO.obtenerReservasPorEspacioYFecha(
                        espacioSeleccionado.getIdEspacio(),
                        new java.sql.Date(fechaInicial.getTime()),
                        new java.sql.Date(fechaFinal.getTime())
                );

                if (reservas.isEmpty()) {
                    System.out.println("No se encontraron reservas para " + espacioSeleccionado.getNombre() + " en el rango de fechas.");
                } else {
                    System.out.println("Reservas para " + espacioSeleccionado.getNombre() + " entre " + dateFormat.format(fechaInicial) + " y " + dateFormat.format(fechaFinal) + ":");

                    for (Reserva reserva : reservas) {
                        Usuario usuario = reserva.getUsuario();
                        String primerNombre = usuario.getPrimerNombre();
                        String primerApellido = usuario.getPrimerApellido();

                        System.out.println("\n----------------------------------------");
                        System.out.println("Usuario: " + primerNombre + " " + primerApellido);
                        System.out.println("Espacio: " + espacioSeleccionado.getNombre());
                        System.out.println("Fecha: " + reserva.getFechaReserva());
                        System.out.println("Hora Inicio: " + reserva.getHoraInicio());
                        System.out.println("Hora Fin: " + reserva.getHoraFin());
                        System.out.println("----------------------------------------");
                    }
                }

                // Preguntar si desea ingresar otra actividad.
                int opcionContinuar;
                while (true) {
                    System.out.println("\n¿Desea generar otro reporte?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");
                    System.out.print("Ingrese el número de la opción: ");
                    try {
                        opcionContinuar = Integer.parseInt(scanner.nextLine().trim());
                        if (opcionContinuar == 1 || opcionContinuar == 2) {
                            break;
                        } else {
                            System.out.println("Opción inválida. Debe ingresar 1 o 2.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                continuar = (opcionContinuar == 1);

            } catch (Exception e) {
                System.out.println("Ha ocurrido un error inesperado.");
                e.printStackTrace();
            }
        }
    }

    public static void generarReporteReservasPorEspacio() {
        Scanner scanner = new Scanner(System.in);
        EspacioDAO espacioDAO = EspacioDAO.getInstance();
        ReservaDAO reservaDAO = ReservaDAO.getInstance();
        boolean continuar = true;

        while (continuar) {
            try {
                List<Espacio> espacios = espacioDAO.listarEspaciosPorEstado(true);

                if (espacios.isEmpty()) {
                    System.out.println("No hay espacios registrados.");
                    return;
                }


                System.out.println("Lista de espacios:");
                for (int i = 0; i < espacios.size(); i++) {
                    System.out.println((i + 1) + ". " + espacios.get(i).getNombre() + " - " + espacios.get(i).getUbicacion());
                }

                int seleccionEspacio;
                while (true) {
                    System.out.println("Seleccione el número del espacio para ver sus reservas:");
                    try {
                        seleccionEspacio = Integer.parseInt(scanner.nextLine().trim());
                        if (seleccionEspacio >= 1 && seleccionEspacio <= espacios.size()) {
                            break;
                        } else {
                            System.out.println("Selección inválida. Elija un número válido.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor, ingrese un número válido.");
                    }
                }

                Espacio espacioSeleccionado = espacios.get(seleccionEspacio - 1);
                System.out.println("Consultando reservas para el espacio: " + espacioSeleccionado.getNombre());

                List<Reserva> reservas = reservaDAO.obtenerReservasPorEspacio(espacioSeleccionado.getIdEspacio());

                if (reservas.isEmpty()) {
                    System.out.println("No hay reservas para " + espacioSeleccionado.getNombre() + ".");
                } else {
                    System.out.println("Reservas para " + espacioSeleccionado.getNombre() + ":");
                    for (Reserva reserva : reservas) {
                        Usuario usuario = reserva.getUsuario();
                        String primerNombre = usuario.getPrimerNombre();
                        String primerApellido = usuario.getPrimerApellido();

                        System.out.println("\n----------------------------------------");
                        System.out.println("Usuario: " + primerNombre + " " + primerApellido);
                        System.out.println("Espacio: " + espacioSeleccionado.getNombre());
                        System.out.println("Fecha: " + reserva.getFechaReserva());
                        System.out.println("Hora Inicio: " + reserva.getHoraInicio());
                        System.out.println("Hora Fin: " + reserva.getHoraFin());
                        System.out.println("----------------------------------------");
                    }
                }

                // Preguntar si desea realizar otra búsqueda.
                int opcionContinuar;
                while (true) {
                    System.out.println("\n¿Desea generar otro reporte?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");
                    System.out.print("Ingrese el número de la opción: ");
                    try {
                        opcionContinuar = Integer.parseInt(scanner.nextLine().trim());
                        if (opcionContinuar == 1 || opcionContinuar == 2) {
                            break;
                        } else {
                            System.out.println("Opción inválida. Debe ingresar 1 o 2.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                continuar = (opcionContinuar == 1);

            } catch (Exception e) {
                System.out.println("Ha ocurrido un error inesperado.");
                e.printStackTrace();
            }
        }
    }
}