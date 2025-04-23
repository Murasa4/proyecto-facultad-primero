package ClasesServicios;

import Clases.Espacio;
import ClasesDAO.EspacioDAO;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class EspacioServicios {

    public static void agregarEspacio() {
        Scanner scanner = new Scanner(System.in);
        String nombre = "";
        String ubicacion = "";
        int capacidad = 0;
        Date fechaVigencia = null;
        double precioSocios = 0.0;
        double precioNoSocios = 0.0;
        boolean continuar = true;

        while (continuar) {
            try {

                // Expresiones regulares
                Pattern soloTexto = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$");

                // Solicitar nombre del espacio
                while (true) {
                    try {
                        System.out.println("Ingrese el nombre del espacio:");
                        nombre = scanner.nextLine().trim();
                        if (nombre.isEmpty()) {
                            System.out.println("El nombre no puede estar vacío.");
                        } else if (nombre.length() > 50) {
                            System.out.println("El nombre no puede superar los 50 caracteres.");
                        } else if (!soloTexto.matcher(nombre).matches()) {
                            System.out.println("El nombre solo puede contener letras y espacios.");
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Error inesperado al ingresar el nombre.");
                    }
                }

                // Solicitar ubicación.
                while (true) {
                    try {
                        System.out.println("Ingrese la ubicación del espacio:");
                        ubicacion = scanner.nextLine().trim();
                        if (ubicacion.isEmpty()) {
                            System.out.println("La ubicación no puede estar vacía.");
                        } else if (ubicacion.length() > 150) {
                            System.out.println("La ubicación no puede superar los 150 caracteres.");
                        } else if (!soloTexto.matcher(ubicacion).matches()) {
                            System.out.println("La ubicación solo puede contener letras y espacios.");
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Error inesperado al ingresar la ubicación.");
                    }
                }

                // Solicitar capacidad.
                while (true) {
                    try {
                        System.out.println("Ingrese la capacidad máxima:");
                        capacidad = Integer.parseInt(scanner.nextLine().trim());
                        if (capacidad > 0) {
                            break;
                        } else {
                            System.out.println("La capacidad debe ser un número positivo.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número entero válido para la capacidad.");
                    }
                }

                // Solicitar fecha de vigencia de precios.
                while (true) {
                    try {
                        System.out.println("Ingrese la fecha de vigencia de los precios:");
                        String fechaString = scanner.nextLine().trim();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date fechaIngresada = dateFormat.parse(fechaString);
                        java.util.Date fechaActual = new java.util.Date();

                        if (fechaIngresada.after(fechaActual)) {
                            fechaVigencia = new Date(fechaIngresada.getTime());
                            break;
                        } else {
                            System.out.println("Error: La fecha debe ser futura.");
                        }
                    } catch (ParseException e) {
                        System.out.println("Error: Formato de fecha incorrecto. Use YYYY-MM-DD.");
                    } catch (Exception e) {
                        System.out.println("Error inesperado al ingresar la fecha.");
                    }
                }

                // Solicitar precio de reserva para socios.
                while (true) {
                    try {
                        System.out.println("Ingrese el precio de reserva para socios:");
                        precioSocios = Double.parseDouble(scanner.nextLine().trim());
                        if (precioSocios > 0) {
                            break;
                        } else {
                            System.out.println("El precio debe ser un número positivo.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número válido para el precio.");
                    }
                }

                // Solicitar precio de reserva para no socios.
                while (true) {
                    try {
                        System.out.println("Ingrese el precio de reserva para no socios:");
                        precioNoSocios = Double.parseDouble(scanner.nextLine().trim());
                        if (precioNoSocios > 0) {
                            break;
                        } else {
                            System.out.println("El precio debe ser un número positivo.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número válido para el precio.");
                    }
                }

                // Crear el espacio
                Espacio espacio = new Espacio.EspacioBuilder()
                        .setNombre(nombre)
                        .setUbicacion(ubicacion)
                        .setCapacidad(capacidad)
                        .setFechaVigenciaPrecios(fechaVigencia)
                        .setPrecioReservaSocios(precioSocios)
                        .setPrecioReservaNoSocios(precioNoSocios)
                        .setEstado(true)
                        .build();

                // Guardar el espacio en la base de datos
                try {
                    EspacioDAO espacioDAO = EspacioDAO.getInstance();
                    espacioDAO.agregarEspacio(espacio);
                    System.out.println("Espacio agregado con éxito.");
                } catch (SQLException e) {
                    System.out.println("Error al ingresar el espacio en la base de datos.");
                    e.printStackTrace();
                }

                // Preguntar si desea ingresar otro espacio.
                int opcion;
                while (true) {
                    System.out.println("\n¿Desea ingresar otro espacio?");
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

    public static void actualizarEspacio() {
        Scanner scanner = new Scanner(System.in);
        EspacioDAO espacioDAO = EspacioDAO.getInstance();
        String nuevoNombre = "";
        String nuevaUbicacion = "";
        int nuevaCapacidad = 0;
        Date nuevaFechaVigencia = null;
        double nuevoPrecioSocios = 0.0;
        double nuevoPrecioNoSocios = 0.0;
        Espacio espacioExistente = null;
        boolean continuar = true;

        while (continuar) {
            try {

                // Obtener la lista de espacios
                List<Espacio> espacios;
                try {
                    espacios = espacioDAO.listarEspacios();
                    if (espacios.isEmpty()) {
                        System.out.println("No hay espacios disponibles para actualizar.");
                        return;
                    }
                } catch (SQLException e) {
                    System.out.println("Error al obtener la lista de espacios.");
                    e.printStackTrace();
                    return;
                }

                // Mostrar la lista de espacios disponibles
                System.out.println("\nEspacios:");
                for (int i = 0; i < espacios.size(); i++) {
                    System.out.println((i + 1) + ". " + espacios.get(i).getNombre());
                }

                // Solicitar selección del usuario
                int opcion;
                while (true) {
                    try {
                        System.out.println("\nSeleccione el número del espacio que desea actualizar:");
                        opcion = Integer.parseInt(scanner.nextLine().trim());
                        if (opcion >= 1 && opcion <= espacios.size()) {
                            espacioExistente = espacios.get(opcion - 1);
                            break;
                        } else {
                            System.out.println("Error: Seleccione un número dentro de la lista.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número válido.");
                    }
                }

                // Continuar con la actualización
                System.out.println("Espacio seleccionado: " + espacioExistente.getNombre());

                // Solicitar nuevo nombre del espacio.
                while (true) {
                    try {
                        System.out.println("Ingrese el nuevo nombre del espacio:");
                        nuevoNombre = scanner.nextLine().trim();
                        if (nuevoNombre.isEmpty()) {
                            System.out.println("El nombre no puede estar vacío.");
                        } else if (nuevoNombre.length() > 50) {
                            System.out.println("El nombre no puede superar los 50 caracteres.");
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Error al ingresar el nombre.");
                    }
                }

                // Solicitar nueva ubicación del espacio.
                while (true) {
                    try {
                        System.out.println("Ingrese la nueva ubicación del espacio:");
                        nuevaUbicacion = scanner.nextLine().trim();
                        if (nuevaUbicacion.isEmpty()) {
                            System.out.println("La ubicación no puede estar vacía.");
                        } else if (nuevaUbicacion.length() > 150) {
                            System.out.println("La ubicación no puede superar los 150 caracteres.");
                        } else {
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Error al ingresar la ubicación.");
                    }
                }

                // Solicitar nueva capacidad del espacio.
                while (true) {
                    try {
                        System.out.println("Ingrese la nueva capacidad máxima del espacio:");
                        nuevaCapacidad = Integer.parseInt(scanner.nextLine().trim());
                        if (nuevaCapacidad > 0) {
                            break;
                        } else {
                            System.out.println("La capacidad debe ser un número positivo.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número entero válido para la capacidad.");
                    }
                }

                // Solicitar nueva fecha de vigencia de los precios.
                while (true) {
                    try {
                        System.out.println("Ingrese la nueva fecha de vigencia de los precios (YYYY-MM-DD):");
                        String fechaString = scanner.nextLine().trim();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        java.util.Date fechaIngresada = dateFormat.parse(fechaString);
                        java.util.Date fechaActual = new java.util.Date();

                        if (fechaIngresada.after(fechaActual)) {
                            nuevaFechaVigencia = new Date(fechaIngresada.getTime());
                            break;
                        } else {
                            System.out.println("Error: La fecha debe ser futura.");
                        }
                    } catch (ParseException e) {
                        System.out.println("Error: Formato de fecha incorrecto. Use YYYY-MM-DD.");
                    } catch (Exception e) {
                        System.out.println("Error inesperado al ingresar la fecha.");
                    }
                }

                // Solicitar nuevo precio de reserva para socios.
                while (true) {
                    try {
                        System.out.println("Ingrese el nuevo precio de reserva para socios:");
                        nuevoPrecioSocios = Double.parseDouble(scanner.nextLine().trim());
                        if (nuevoPrecioSocios > 0) {
                            break;
                        } else {
                            System.out.println("El precio debe ser un número positivo.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número válido para el precio.");
                    }
                }

                // Solicitar nuevo precio de reserva para no socios.
                while (true) {
                    try {
                        System.out.println("Ingrese el nuevo precio de reserva para no socios:");
                        nuevoPrecioNoSocios = Double.parseDouble(scanner.nextLine().trim());
                        if (nuevoPrecioNoSocios > 0) {
                            break;
                        } else {
                            System.out.println("El precio debe ser un número positivo.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número válido para el precio.");
                    }
                }

                // Actualizar el espacio con los nuevos datos.
                espacioExistente.setNombre(nuevoNombre);
                espacioExistente.setUbicacion(nuevaUbicacion);
                espacioExistente.setCapacidad(nuevaCapacidad);
                espacioExistente.setFechaVigenciaPrecios(nuevaFechaVigencia);
                espacioExistente.setPrecioReservaSocios(nuevoPrecioSocios);
                espacioExistente.setPrecioReservaNoSocios(nuevoPrecioNoSocios);

                // Guardar los cambios en la base de datos.
                try {
                    espacioDAO.actualizarEspacio(espacioExistente);
                    System.out.println("Espacio actualizado con éxito.");
                } catch (SQLException e) {
                    System.out.println("Error al actualizar el espacio en la base de datos.");
                    e.printStackTrace();
                }

                // Preguntar si desea ingresar otra actividad.
                int opcionContinuar;
                while (true) {
                    System.out.println("\n¿Desea actualizar otro espacio?");
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

    public static void eliminarEspacio() {
        Scanner scanner = new Scanner(System.in);
        EspacioDAO espacioDAO = EspacioDAO.getInstance();
        List<Espacio> espaciosActivos = null;
        boolean continuar = true;

        while (continuar) {
            try {
                // Obtener los espacios activos
                espaciosActivos = espacioDAO.listarEspaciosPorEstado(true);

                if (espaciosActivos.isEmpty()) {
                    System.out.println("No hay espacios activos para eliminar.");
                    break;
                }

                // Mostrar los espacios activos con su ID y nombre
                System.out.println("Espacios disponibles para eliminar:");
                for (int i = 0; i < espaciosActivos.size(); i++) {
                    Espacio espacio = espaciosActivos.get(i);
                    System.out.println((i + 1) + ". " + espacio.getNombre());
                }

                // Solicitar que el usuario elija un espacio para eliminar
                int seleccion = 0;
                while (true) {
                    try {
                        System.out.println("Seleccione el número del espacio que desea eliminar:");
                        seleccion = Integer.parseInt(scanner.nextLine().trim());
                        if (seleccion < 1 || seleccion > espaciosActivos.size()) {
                            System.out.println("Selección inválida. Por favor, elija un número válido.");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número válido.");
                    }
                }

                Espacio espacioSeleccionado = espaciosActivos.get(seleccion - 1);

                // Mostrar información de la actividad seleccionada.
                System.out.println("\nInformación de la actividad seleccionada:");
                System.out.println("\n---------------------------------------------------");
                System.out.println("Nombre: " + espacioSeleccionado.getNombre());
                System.out.println("Ubicación: " + espacioSeleccionado.getUbicacion());
                System.out.println("Capacidad: " + espacioSeleccionado.getCapacidad());
                System.out.println("Fecha de vigencia de precios: " + espacioSeleccionado.getFechaVigenciaPrecios());
                System.out.println("Precio de reserva de socios: " + espacioSeleccionado.getPrecioReservaSocios());
                System.out.println("Precio de reserva de no socios: " + espacioSeleccionado.getPrecioReservaNoSocios());
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
                        espacioDAO.eliminarEspacio(espacioSeleccionado.getIdEspacio());
                        System.out.println("Espacio eliminado con éxito.");
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar el espacio en la base de datos.");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Eliminación cancelada.");
                }

                // Preguntar si desea eliminar otro espacio.
                int opcion;
                while (true) {
                    System.out.println("\n¿Desea eliminar otro espacio?");
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

    public static void listarEspaciosPorEstado() {
        Scanner scanner = new Scanner(System.in);
        EspacioDAO espacioDAO = EspacioDAO.getInstance();
        boolean continuar = true;

        // Bucle para repetir la operación si el usuario lo desea.
        while (continuar) {
            try {


                int estado = -1;

                while (estado != 1 && estado != 2) {
                    System.out.println("¿Qué estado de espacios desea ver?");
                    System.out.println("1. Activos");
                    System.out.println("2. Inactivos");
                    System.out.print("Seleccione una opción (1 o 2): ");

                    String input = scanner.nextLine().trim();

                    // Si el usuario presiona Enter sin ingresar nada, se le pide que ingrese nuevamente.
                    if (input.isEmpty()) {
                        System.out.println("No se seleccionó ninguna opción, por favor ingrese una opción válida.");
                        continue;
                    }

                    try {
                        estado = Integer.parseInt(input);

                        if (estado != 1 && estado != 2) {
                            System.out.println("Opción inválida. Debe elegir 1 para activas o 2 para inactivas.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida. Por favor ingrese un número (1 o 2).");
                    }
                }

                // Mostrar actividades según el estado seleccionado.
                try {
                    List<Espacio> espacios = espacioDAO.listarEspaciosPorEstado(estado == 1);

                    if (espacios.isEmpty()) {
                        System.out.println("No se encontraron espacios con el estado seleccionado.");
                    } else {
                        System.out.println("Espacios " + (estado == 1 ? "activos" : "inactivos") + ":");
                        for (int i = 0; i < espacios.size(); i++) {
                            Espacio esp = espacios.get(i);
                            System.out.println((i + 1) + ". " + esp.getNombre() + " | " + esp.getUbicacion() + " | Capacidad: " + esp.getCapacidad()
                                    + " | Vigencia precios: " + esp.getFechaVigenciaPrecios()
                                    + " | Precio socios: $" + esp.getPrecioReservaSocios()
                                    + " | Precio no socios: $" + esp.getPrecioReservaNoSocios());
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error al consultar los espacios en la base de datos.");
                    e.printStackTrace();
                }

                // Preguntar si desea realizar otra búsqueda.
                int opcion;
                while (true) {
                    System.out.println("\n¿Desea realizar otra búsqueda?");
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

    public static void buscarYMostrarEspaciosPorNombre() {
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            try {

                System.out.println("Ingrese el nombre del espacio a buscar:");
                String nombre = scanner.nextLine().trim();

                // Validar que el nombre no esté vacío
                while (nombre.isEmpty()) {
                    System.out.println("El nombre no puede estar vacío. Inténtelo nuevamente:");
                    nombre = scanner.nextLine().trim();
                }

                try {
                    List<Espacio> espacios = EspacioDAO.getInstance().obtenerEspaciosPorNombre(nombre);

                    if (espacios.isEmpty()) {
                        System.out.println("No se encontraron espacios con el nombre proporcionado.");
                    } else {
                        System.out.println("Espacios encontrados:");
                        System.out.println("-------------------------");
                        for (int i = 0; i < espacios.size(); i++) {
                            Espacio espacio = espacios.get(i);
                            System.out.println((i + 1) + ". " + espacio.getNombre()
                                    + " | Descripción: " + espacio.getUbicacion()
                                    + " | Capacidad: " + espacio.getCapacidad()
                                    + " | Vigencia precios: " + espacio.getFechaVigenciaPrecios()
                                    + " | Precio socios: $" + espacio.getPrecioReservaSocios()
                                    + " | Precio no socios: $" + espacio.getPrecioReservaNoSocios()
                                    + " | Estado: " + (espacio.getEstado() ? "Activo" : "Inactivo"));
                        }

                    }
                } catch (SQLException e) {
                    System.out.println("Ocurrió un error al buscar los espacios: " + e.getMessage());
                } catch (Exception e) { // Captura cualquier otra excepción inesperada
                    System.out.println("Se ha producido un error: " + e.getMessage());
                }

                // Preguntar si desea ingresar otra actividad.
                int opcion;
                while (true) {
                    System.out.println("\n¿Desea realizar otra búsqueda?");
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
}