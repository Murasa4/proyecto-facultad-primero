package ClasesServicios;

import Clases.*;
import ClasesDAO.*;
import com.fabdelgado.ciuy.Validator;

import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;

public class InscripcionServicios {

    public static void generarReporteActividadPorFecha() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        boolean continuarReporte = true;

        while (continuarReporte) {
            try {
                // Solicitar la fecha de la actividad.
                System.out.print("Ingrese la fecha de la actividad (formato: yyyy-MM-dd): ");
                String fechaStr = scanner.nextLine();
                java.util.Date fechaUtil = formatoFecha.parse(fechaStr);
                Date fecha = new Date(fechaUtil.getTime());

                ActividadDAO actividadDAO = ActividadDAO.getInstance();
                InscripcionDAO inscripcionDAO = InscripcionDAO.getInstance();

                List<Actividad> actividades = actividadDAO.listarActividadesPorFecha(fecha);

                // Si no se encontraron actividades.
                if (actividades.isEmpty()) {
                    System.out.println("No se encontraron actividades para la fecha ingresada.");
                    String respuesta;
                    while (true) {
                        System.out.println("\n¿Desea generar otro reporte? (sí/no)");
                        respuesta = scanner.nextLine().trim().toLowerCase();

                        if (respuesta.isEmpty()) {
                            System.out.println("Debe ingresar una respuesta.");
                        } else if (!respuesta.equals("si") && !respuesta.equals("no")) {
                            System.out.println("Respuesta no válida. Por favor, escriba 'sí' o 'no'.");
                        } else {
                            break;
                        }
                    }

                    // Se termina el ciclo.
                    if (respuesta.equals("no")) {
                        continuarReporte = false;
                    }
                    continue;  // Si desea generar otro reporte.
                }

                System.out.println("\nActividades en la fecha seleccionada:");
                for (int i = 0; i < actividades.size(); i++) {
                    System.out.println((i + 1) + ". " + actividades.get(i).getNombre());
                }

                System.out.print("\nSeleccione una actividad (número): ");
                int seleccion = scanner.nextInt();
                scanner.nextLine();

                if (seleccion < 1 || seleccion > actividades.size()) {
                    System.out.println("Selección inválida.");
                    return;
                }

                Actividad actividadSeleccionada = actividades.get(seleccion - 1);

                System.out.println("\nSeleccione el tipo de reporte:");
                System.out.println("1. Inscripciones");
                System.out.println("2. Cancelaciones");
                System.out.print("Opción: ");
                int opcion = scanner.nextInt();
                scanner.nextLine();

                List<Inscripcion> inscripciones = inscripcionDAO.listarInscripcionesPorActividad(actividadSeleccionada.getIdActividad());
                List<CancelacionInscripcion> cancelaciones = CancelacionInscripcionDAO.getInstance().listarCancelacionesPorActividad(actividadSeleccionada.getIdActividad());

                if (opcion == 1) {
                    boolean hayInscripciones = false;
                    System.out.println("\nResultados de Inscripciones:");
                    System.out.println("---------------------------");
                    for (Inscripcion inscripcion : inscripciones) {
                        System.out.println(inscripcion);
                        hayInscripciones = true;
                    }
                    if (!hayInscripciones) {
                        System.out.println("No hay inscripciones registradas para esta actividad.");
                    }

                } else if (opcion == 2) {
                    boolean hayCanceladas = false;
                    System.out.println("\nResultados de Cancelaciones:");
                    System.out.println("---------------------------");
                    for (CancelacionInscripcion cancelacion : cancelaciones) {
                        System.out.println(cancelacion);
                        hayCanceladas = true;
                    }
                    if (!hayCanceladas) {
                        System.out.println("No hay cancelaciones registradas para esta actividad.");
                    }

                } else {
                    System.out.println("Opción inválida.");
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

                continuarReporte = (opcionContinuar == 1);

            } catch (ParseException e) {
                System.out.println("Formato de fecha inválido. Por favor, ingrese la fecha en el formato correcto.");
            } catch (SQLException e) {
                System.out.println("Error al acceder a la base de datos: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        }
    }

    public static void generarReporteActividadPorTipoActividad() {
        Scanner scanner = new Scanner(System.in);
        boolean continuarReporte = true;

        while (continuarReporte) {
            try {
                // Mostrar los tipos de actividad disponibles y solicitar al usuario que seleccione uno.
                int idTipoActividad = -1;
                while (true) {
                    try {
                        TipoActividadDAO tipoActividadDAO = TipoActividadDAO.getInstance();
                        List<TipoActividad> tiposActividad = tipoActividadDAO.listarTiposActividades();

                        if (tiposActividad.isEmpty()) {
                            System.out.println("No hay tipos de actividad disponibles en la base de datos.");
                            return;
                        }

                        System.out.println("Seleccione el tipo de actividad:");
                        for (int i = 0; i < tiposActividad.size(); i++) {
                            System.out.println((i + 1) + ". " + tiposActividad.get(i).getNombre());
                        }

                        System.out.print("Opción: ");
                        int seleccion = Integer.parseInt(scanner.nextLine().trim());

                        if (seleccion > 0 && seleccion <= tiposActividad.size()) {
                            idTipoActividad = tiposActividad.get(seleccion - 1).getIdTipoActividad();
                            break;
                        } else {
                            System.out.println("Selección inválida. Intente nuevamente.");
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al obtener los tipos de actividad desde la base de datos.");
                        e.printStackTrace();
                        return;
                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                // Buscar actividades según el tipo seleccionado.
                ActividadDAO actividadDAO = ActividadDAO.getInstance();
                List<Actividad> actividades = actividadDAO.listarActividadesPorTipo(idTipoActividad);

                if (actividades.isEmpty()) {
                    System.out.println("No se encontraron actividades para el tipo seleccionado.");

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

                    continuarReporte = (opcionContinuar == 1);
                }

                // Mostrar actividades disponibles.
                System.out.println("\nActividades del tipo seleccionado:");
                for (int i = 0; i < actividades.size(); i++) {
                    System.out.println((i + 1) + ". " + actividades.get(i).getNombre() + " | Descripción: " + actividades.get(i).getDescripcion() + " | Fecha: " + actividades.get(i).getFecha());
                }

                System.out.print("\nSeleccione una actividad (número): ");
                int seleccionActividad = Integer.parseInt(scanner.nextLine());

                if (seleccionActividad < 1 || seleccionActividad > actividades.size()) {
                    System.out.println("Selección inválida.");
                    return;
                }

                Actividad actividadSeleccionada = actividades.get(seleccionActividad - 1);

                // Elegir tipo de reporte.
                System.out.println("\nSeleccione el tipo de reporte:");
                System.out.println("1. Inscripciones");
                System.out.println("2. Cancelaciones");
                System.out.print("Opción: ");
                int opcion = Integer.parseInt(scanner.nextLine());

                InscripcionDAO inscripcionDAO = InscripcionDAO.getInstance();
                CancelacionInscripcionDAO cancelacionInscripcionDAO = CancelacionInscripcionDAO.getInstance();

                List<Inscripcion> inscripciones = inscripcionDAO.listarInscripcionesPorActividad(actividadSeleccionada.getIdActividad());
                List<CancelacionInscripcion> cancelaciones = cancelacionInscripcionDAO.listarCancelacionesPorActividad(actividadSeleccionada.getIdActividad());

                if (opcion == 1) {
                    System.out.println("\nResultados de Inscripciones:");
                    System.out.println("---------------------------");
                    if (inscripciones.isEmpty()) {
                        System.out.println("No hay inscripciones registradas para esta actividad.");
                    } else {
                        for (Inscripcion inscripcion : inscripciones) {
                            System.out.println(inscripcion);
                        }
                    }

                } else if (opcion == 2) {
                    System.out.println("\nResultados de Cancelaciones:");
                    System.out.println("---------------------------");
                    if (cancelaciones.isEmpty()) {
                        System.out.println("No hay cancelaciones registradas para esta actividad.");
                    } else {
                        for (CancelacionInscripcion cancelacion : cancelaciones) {
                            System.out.println(cancelacion);
                        }
                    }

                } else {
                    System.out.println("Opción inválida.");
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

                continuarReporte = (opcionContinuar == 1);

            } catch (SQLException e) {
                System.out.println("Error al acceder a la base de datos: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Asegúrese de ingresar números donde corresponda.");
            } catch (Exception e) {
                System.out.println("Ocurrió un error inesperado: " + e.getMessage());
            }
        }
    }

    public static void realizarInscripcionActividad() {
        Scanner scanner = new Scanner(System.in);
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        ActividadDAO actividadDAO = ActividadDAO.getInstance();
        InscripcionDAO inscripcionDAO = InscripcionDAO.getInstance();
        boolean continuar = true;

        while (continuar) {
            try {
                int idTipoDocumento = 0;
                String numDocumento;
                Usuario usuarioExistente;

                // Listar actividades disponibles.
                List<Actividad> actividadesDisponibles = null;
                Actividad actividadSeleccionada = null;

                try {
                    actividadesDisponibles = actividadDAO.listarActividadesDisponiblesParaInscripcion();
                    if (actividadesDisponibles.isEmpty()) {
                        System.out.println("No hay actividades disponibles para inscribirse.");
                        return;
                    }

                    System.out.println("\nActividades disponibles:");
                    System.out.println("------------------------------------");
                    for (int i = 0; i < actividadesDisponibles.size(); i++) {
                        Actividad act = actividadesDisponibles.get(i);
                        System.out.println((i + 1) + ". " + act.getNombre() + " | Descripción: " + act.getDescripcion() + " | Fecha: " + act.getFecha());
                    }

                    int seleccion = 0;
                    while (true) {
                        try {
                            System.out.println("\nSeleccione el número de la actividad a la que desea inscribirse:");
                            seleccion = Integer.parseInt(scanner.nextLine().trim());
                            if (seleccion >= 1 && seleccion <= actividadesDisponibles.size()) {
                                actividadSeleccionada = actividadesDisponibles.get(seleccion - 1);
                                break;
                            } else {
                                System.out.println("Selección fuera de rango.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Ingrese un número válido.");
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error al obtener las actividades.");
                    e.printStackTrace();
                    return;
                }

                while (true) {
                    System.out.println("Seleccione el tipo de documento del usuario a inscribir:");
                    System.out.println("1. Cédula de Identidad");
                    System.out.println("2. Pasaporte");
                    String opcion = scanner.nextLine();

                    if (opcion.equals("1")) {
                        idTipoDocumento = 1;

                        // Validar formato de la CI.
                        while (true) {
                            System.out.print("Ingrese su CI: ");
                            numDocumento = scanner.nextLine().trim();

                            // Verificar que tenga exactamente 8 dígitos.
                            if (numDocumento.replaceAll("[^0-9]", "").length() != 8) {
                                System.out.println("La cédula debe incluir 8 dígitos, incluyendo el verificador.");
                                continue;
                            }

                            // Validar el formato con la librería.
                            Validator validator = new Validator();
                            if (!validator.validateCi(numDocumento)) {
                                System.out.println("Formato de CI incorrecto o CI inválida.");
                                continue;
                            }

                            break;
                        }

                        break;

                    } else if (opcion.equals("2")) {
                        idTipoDocumento = 2;

                        while (true) {
                            System.out.println("Ingrese el número de pasaporte:");
                            numDocumento = scanner.nextLine().trim();

                            if (numDocumento.matches("^[A-Z]\\d{6}$")) {
                                break;
                            } else {
                                System.out.println("El pasaporte debe tener una letra seguida de 6 dígitos.");
                            }
                        }

                        break;

                    } else {
                        System.out.println("Opción inválida. Seleccione 1 o 2.");
                    }
                }

                // Buscar usuario.
                while (true) {
                    try {
                        usuarioExistente = usuarioDAO.obtenerUsuarioPorTipoDocumento(idTipoDocumento, numDocumento);
                        if (usuarioExistente == null) {
                            System.out.println("Usuario no encontrado. Ingrese otro número de documento:");
                            numDocumento = scanner.nextLine().trim();
                        } else {
                            break;
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al verificar el usuario.");
                        e.printStackTrace();
                        return;
                    }
                }

                // Verificar si ya está inscrito.
                try {
                    boolean yaInscripto = inscripcionDAO.usuarioYaInscrito(usuarioExistente.getIdUsuario(), actividadSeleccionada.getIdActividad());
                    if (yaInscripto) {
                        System.out.println("El usuario ya está inscrito en esta actividad.");
                        continue; // vuelve al principio del while.
                    }
                } catch (SQLException e) {
                    System.out.println("Error al verificar inscripción previa.");
                    e.printStackTrace();
                    return;
                }

                // Confirmar inscripción.
                System.out.println("¿Desea inscribir al usuario: " + usuarioExistente.getPrimerNombre() + " " + usuarioExistente.getPrimerApellido() + " a la actividad: " + actividadSeleccionada.getNombre() + "?");
                System.out.println("1. Sí");
                System.out.println("2. No");
                int confirmacion = 0;
                while (true) {
                    try {
                        confirmacion = Integer.parseInt(scanner.nextLine().trim());
                        if (confirmacion == 1 || confirmacion == 2) {
                            break;
                        } else {
                            System.out.println("Ingrese 1 o 2.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ingrese un número válido.");
                    }
                }

                if (confirmacion == 1) {
                    // Crear inscripción.
                    Timestamp fechaInscripcion = new Timestamp(System.currentTimeMillis());
                    Inscripcion inscripcion = new Inscripcion.InscripcionBuilder()
                            .setUsuario(usuarioExistente)
                            .setActividad(actividadSeleccionada)
                            .setFechaInscripcion(fechaInscripcion)
                            .setEstado(true)
                            .build();

                    try {
                        inscripcionDAO.agregarInscripcion(inscripcion);
                        System.out.println("Inscripción realizada con éxito.");
                    } catch (SQLException e) {
                        System.out.println("Error al guardar la inscripción.");
                        e.printStackTrace();
                    }
                }

                // Preguntar si desea hacer otra inscripción.
                int seguir = 0;
                while (true) {
                    System.out.println("\n¿Desea inscribir a otro usuario?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");
                    try {
                        seguir = Integer.parseInt(scanner.nextLine().trim());
                        if (seguir == 1 || seguir == 2) break;
                        else System.out.println("Ingrese 1 o 2.");
                    } catch (NumberFormatException e) {
                        System.out.println("Ingrese un número válido.");
                    }
                }

                continuar = (seguir == 1);

            } catch (Exception e) {
                System.out.println("Ha ocurrido un error inesperado.");
                e.printStackTrace();
            }
        }
    }

    public static void cancelarInscripción() {
        Scanner scanner = new Scanner(System.in);
        UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
        ActividadDAO actividadDAO = ActividadDAO.getInstance();
        InscripcionDAO inscripcionDAO = InscripcionDAO.getInstance();
        CancelacionInscripcionDAO cancelacionInscripcionDAO = CancelacionInscripcionDAO.getInstance();
        boolean continuar = true;

        while (continuar) {
            try {
                int idTipoDocumento = 0;
                String numDocumento;
                Usuario usuarioExistente;

                while (true) {
                    System.out.println("Seleccione el tipo de documento del usuario a inscribir:");
                    System.out.println("1. Cédula de Identidad");
                    System.out.println("2. Pasaporte");
                    String opcion = scanner.nextLine();

                    if (opcion.equals("1")) {
                        idTipoDocumento = 1;

                        // Validar formato de la CI.
                        while (true) {
                            System.out.print("Ingrese su CI: ");
                            numDocumento = scanner.nextLine().trim();

                            // Verificar que tenga exactamente 8 dígitos.
                            if (numDocumento.replaceAll("[^0-9]", "").length() != 8) {
                                System.out.println("La cédula debe incluir 8 dígitos, incluyendo el verificador.");
                                continue;
                            }

                            // Validar el formato con la librería.
                            Validator validator = new Validator();
                            if (!validator.validateCi(numDocumento)) {
                                System.out.println("Formato de CI incorrecto o CI inválida.");
                                continue;
                            }

                            break;
                        }

                        break;

                    } else if (opcion.equals("2")) {
                        idTipoDocumento = 2;

                        while (true) {
                            System.out.println("Ingrese el número de pasaporte:");
                            numDocumento = scanner.nextLine().trim();

                            if (numDocumento.matches("^[A-Z]\\d{6}$")) {
                                break;
                            } else {
                                System.out.println("El pasaporte debe tener una letra seguida de 6 dígitos.");
                            }
                        }

                        break;

                    } else {
                        System.out.println("Opción inválida. Seleccione 1 o 2.");
                    }
                }

                // Buscar usuario.
                while (true) {
                    try {
                        usuarioExistente = usuarioDAO.obtenerUsuarioPorTipoDocumento(idTipoDocumento, numDocumento);
                        if (usuarioExistente == null) {
                            System.out.println("Usuario no encontrado. Ingrese otro número de documento:");
                            numDocumento = scanner.nextLine().trim();
                        } else {
                            break;
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al verificar el usuario.");
                        e.printStackTrace();
                        return;
                    }
                }

                // Obtener actividades a las que está inscrito el usuario
                List<Actividad> actividadesInscripto;
                try {
                    actividadesInscripto = inscripcionDAO.obtenerActividadesPorUsuario(usuarioExistente.getIdUsuario());

                    if (actividadesInscripto.isEmpty()) {
                        System.out.println("\nEl usuario no está inscrito a ninguna actividad.");
                        return;
                    }

                    System.out.println("\nActividades a las que está inscrito el usuario:");
                    for (int i = 0; i < actividadesInscripto.size(); i++) {
                        Actividad act = actividadesInscripto.get(i);
                        System.out.println((i + 1) + ". " + act.getNombre() + " | Descripción: " + act.getDescripcion() + " | Fecha: " + act.getFecha());
                    }

                    System.out.println("\nSeleccione una actividad para cancelar la inscripción:");
                    int opcionActividad;
                    while (true) {
                        try {
                            opcionActividad = Integer.parseInt(scanner.nextLine());
                            if (opcionActividad >= 1 && opcionActividad <= actividadesInscripto.size()) {
                                break;
                            } else {
                                System.out.println("Número fuera de rango. Intente de nuevo:");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida. Ingrese un número:");
                        }
                    }

                    Actividad actividadSeleccionada = actividadesInscripto.get(opcionActividad - 1);

                    // Mostrar detalles de la actividad y datos del usuario.
                    System.out.println("\n---------------------------------------");
                    System.out.println("Detalles de la actividad seleccionada:");
                    System.out.println("Nombre: " + actividadSeleccionada.getNombre());
                    System.out.println("Descripción: " + actividadSeleccionada.getDescripcion());
                    System.out.println("Fecha: " + actividadSeleccionada.getFecha());
                    System.out.println("Tipo de actividad: " + actividadSeleccionada.getNombre());
                    System.out.println("Cupo: " + actividadSeleccionada.getCupo());
                    System.out.println("Cantidad de inscritos: " + actividadSeleccionada.getCantidadInscriptos());
                    System.out.println("---------------------------------------");
                    System.out.println("Datos del usuario:");
                    System.out.println("Nombre: " + usuarioExistente.getPrimerNombre() + " " + usuarioExistente.getPrimerApellido());
                    System.out.println("Cédula de Identidad: " + usuarioExistente.getNumeroDocumento());
                    System.out.println("---------------------------------------");


                    // Confirmación.
                    System.out.println("\n¿Desea eliminar la inscripción del usuario en esta actividad?");
                    System.out.println("1. Sí");
                    System.out.println("2. No");

                    String confirmacion = scanner.nextLine().trim();
                    if (confirmacion.equals("1")) {
                        try {
                            boolean exito = cancelacionInscripcionDAO.agregarCancelacion(usuarioExistente.getIdUsuario(), actividadSeleccionada.getIdActividad());
                            if (exito) {
                                System.out.println("Inscripción cancelada exitosamente.");
                            } else {
                                System.out.println("No se pudo cancelar la inscripción (puede que ya esté cancelada o no exista).");
                            }
                        } catch (SQLException e) {
                            System.out.println("Error al cancelar la inscripción.");
                            e.printStackTrace();
                        }
                    } else {
                        System.out.println("Operación cancelada. La inscripción no fue eliminada.");
                    }

                    // Preguntar si desea cancelar otra inscripción.
                    int seguir = 0;
                    while (true) {
                        System.out.println("\n¿Desea cancelar otra inscripción?");
                        System.out.println("1. Sí");
                        System.out.println("2. No");
                        try {
                            seguir = Integer.parseInt(scanner.nextLine().trim());
                            if (seguir == 1 || seguir == 2) break;
                            else System.out.println("Ingrese 1 o 2.");
                        } catch (NumberFormatException e) {
                            System.out.println("Ingrese un número válido.");
                        }
                    }

                    continuar = (seguir == 1);

                } catch (SQLException e) {
                    System.out.println("Error al obtener actividades del usuario.");
                    e.printStackTrace();
                    return;
                }

            } catch (Exception e) {
                System.out.println("Ha ocurrido un error inesperado.");
                e.printStackTrace();
            }
        }
    }
}

