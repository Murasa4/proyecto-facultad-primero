package ClasesServicios;

import Clases.TipoActividad;
import ClasesDAO.TipoActividadDAO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TipoActividadServicios {

    public static void agregarTipoActividad() {
        Scanner scanner = new Scanner(System.in);
        TipoActividadDAO tipoActividadDAO = TipoActividadDAO.getInstance();
        boolean continuar = true;

        while (continuar) {
            String nombre = "";
            String descripcion = "";

            // Solicitar nombre del tipo de actividad.
            while (true) {
                try {
                    System.out.println("Ingrese el nombre del tipo de actividad:");
                    nombre = scanner.nextLine().trim();
                    if (nombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacío.");
                    } else if (nombre.length() > 50) {
                        System.out.println("El nombre no puede superar los 50 caracteres.");
                    } else if (tipoActividadDAO.existeNombre(nombre)) {
                        System.out.println("Ya existe un tipo de actividad con ese nombre.");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Error al ingresar el nombre.");
                }
            }

            // Solicitar descripción del tipo de actividad.
            while (true) {
                try {
                    System.out.println("Ingrese la descripción del tipo de actividad:");
                    descripcion = scanner.nextLine().trim();
                    if (descripcion.isEmpty()) {
                        System.out.println("La descripción no puede estar vacía.");
                    } else if (descripcion.length() > 150) {
                        System.out.println("La descripción no puede superar los 150 caracteres.");
                    } else if (tipoActividadDAO.existeDescripcion(descripcion)) {
                        System.out.println("Ya existe un tipo de actividad con esa descripción.");
                    } else {
                        break;
                    }
                } catch (Exception e) {
                    System.out.println("Error al ingresar la descripción.");
                }
            }

            // Mostrar resumen y confirmar
            System.out.println("\n¿Desea crear el tipo de actividad con los siguientes datos?\n");
            System.out.println("\n----------------------------------");
            System.out.println("Nombre: " + nombre);
            System.out.println("Descripción: " + descripcion);
            System.out.println("----------------------------------");
            System.out.println("\n1. Sí");
            System.out.println("2. No");
            System.out.print("Ingrese el número de la opción: ");

            int confirmacion = 0;
            while (true) {
                try {
                    confirmacion = Integer.parseInt(scanner.nextLine().trim());
                    if (confirmacion == 1 || confirmacion == 2) {
                        break;
                    } else {
                        System.out.println("Opción inválida. Debe ingresar 1 o 2.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Debe ingresar un número válido.");
                }
            }

            if (confirmacion == 1) {
                // Crear y guardar el tipo de actividad
                TipoActividad tipoActividad = new TipoActividad.TipoActividadBuilder()
                        .setNombre(nombre)
                        .setDescripcion(descripcion)
                        .setEstado(true)
                        .build();

                try {
                    tipoActividadDAO.agregarTipoActividad(tipoActividad);
                    System.out.println("Tipo de actividad agregado con éxito.");
                } catch (SQLException e) {
                    System.out.println("Error al ingresar el tipo de actividad en la base de datos.");
                    e.printStackTrace();
                }
            } else {
                System.out.println("Creación cancelada.");
            }

            // Preguntar si desea ingresar otro tipo de actividad.
            int opcion;
            while (true) {
                System.out.println("\n¿Desea ingresar otro tipo de actividad?");
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

    public static void actualizarTipoActividad() {
        Scanner scanner = new Scanner(System.in);
        TipoActividadDAO tipoActividadDAO = TipoActividadDAO.getInstance();
        boolean continuar = true;

        while (continuar) {
            List<TipoActividad> tiposActividades = new ArrayList<>();
            try {
                tiposActividades = tipoActividadDAO.listarTiposActividades();
                if (tiposActividades.isEmpty()) {
                    System.out.println("No hay tipos de actividades disponibles para actualizar.");
                    return;
                }

                System.out.println("Tipos de actividad disponibles:");
                for (int i = 0; i < tiposActividades.size(); i++) {
                    TipoActividad act = tiposActividades.get(i);
                    System.out.println((i + 1) + ". " + act.getNombre() + " | " + act.getDescripcion());
                }

            } catch (SQLException e) {
                System.out.println("Error al obtener los tipos de actividad desde la base de datos.");
                e.printStackTrace();
                return;
            }

            int seleccion = 0;
            TipoActividad tipoActividadSeleccionado = null;

            while (true) {
                try {
                    System.out.print("Seleccione el número del tipo de actividad que desea actualizar: ");
                    seleccion = Integer.parseInt(scanner.nextLine());

                    if (seleccion >= 1 && seleccion <= tiposActividades.size()) {
                        tipoActividadSeleccionado = tiposActividades.get(seleccion - 1);
                        break;
                    } else {
                        System.out.println("Por favor, seleccione un número válido de la lista.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Error: Ingrese un número válido.");
                }
            }

            // Solicitar nuevo nombre
            String nuevoNombre;
            while (true) {
                System.out.println("Ingrese el nuevo nombre del tipo de actividad:");
                nuevoNombre = scanner.nextLine().trim();
                if (nuevoNombre.isEmpty()) {
                    System.out.println("El nombre no puede estar vacío.");
                } else if (nuevoNombre.length() > 50) {
                    System.out.println("El nombre no puede exceder los 50 caracteres.");
                } else if (!nuevoNombre.equalsIgnoreCase(tipoActividadSeleccionado.getNombre())
                        && tipoActividadDAO.existeNombre(nuevoNombre)) {
                    System.out.println("Ya existe un tipo de actividad con ese nombre.");
                } else {
                    break;
                }
            }

            // Solicitar nueva descripción
            String nuevaDescripcion;
            while (true) {
                System.out.println("Ingrese la nueva descripción del tipo de actividad:");
                nuevaDescripcion = scanner.nextLine().trim();
                if (nuevaDescripcion.isEmpty()) {
                    System.out.println("La descripción no puede estar vacía.");
                } else if (nuevaDescripcion.length() > 150) {
                    System.out.println("La descripción no puede exceder los 150 caracteres.");
                } else if (!nuevaDescripcion.equalsIgnoreCase(tipoActividadSeleccionado.getDescripcion())
                        && tipoActividadDAO.existeDescripcion(nuevaDescripcion)) {
                    System.out.println("Ya existe un tipo de actividad con esa descripción.");
                } else {
                    break;
                }
            }

            // Actualizar datos
            tipoActividadSeleccionado.setNombre(nuevoNombre);
            tipoActividadSeleccionado.setDescripcion(nuevaDescripcion);

            try {
                tipoActividadDAO.actualizarTipoActividad(tipoActividadSeleccionado);
                System.out.println("Tipo de actividad actualizado con éxito.");
            } catch (SQLException e) {
                System.out.println("Error al actualizar el tipo de actividad en la base de datos.");
                e.printStackTrace();
            }

            // Preguntar si desea actualizar otro tipo de actividad
            int opcion;
            while (true) {
                System.out.println("\n¿Desea actualizar otro tipo de actividad?");
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

    public static void eliminarTipoActividad() {
        Scanner scanner = new Scanner(System.in);
        TipoActividadDAO tipoActividadDAO = TipoActividadDAO.getInstance();
        boolean continuar = true;

        while (continuar) {
            List<TipoActividad> tiposDeActividadActivos = null;

            // Obtener los tipos de actividad activos.
            try {
                tiposDeActividadActivos = tipoActividadDAO.obtenerTipoActividadPorEstado(true);

                if (tiposDeActividadActivos.isEmpty()) {
                    System.out.println("No hay tipos de actividad activos para eliminar.");
                    return;
                }

                // Mostrar los tipos de actividad activos con su nombre.
                System.out.println("Tipos de actividad disponibles para eliminar:");
                for (int i = 0; i < tiposDeActividadActivos.size(); i++) {
                    TipoActividad tipo = tiposDeActividadActivos.get(i);
                    System.out.println((i + 1) + ". " + tipo.getNombre());
                }

                // Solicitar que el usuario seleccione un tipo de actividad.
                int seleccion = 0;
                while (true) {
                    try {
                        System.out.println("Seleccione el número del tipo de actividad que desea eliminar:");
                        seleccion = Integer.parseInt(scanner.nextLine().trim());
                        if (seleccion < 1 || seleccion > tiposDeActividadActivos.size()) {
                            System.out.println("Selección inválida. Por favor, elija un número válido.");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Ingrese un número válido.");
                    }
                }

                TipoActividad tipoActividadSeleccionado = tiposDeActividadActivos.get(seleccion - 1);

                // Mostrar la información del tipo de actividad seleccionado.
                System.out.println("\nInformación del tipo de actividad seleccionado:");
                System.out.println(tipoActividadSeleccionado.toString());

                // Confirmación para eliminar.
                System.out.println("\n¿Está seguro de que desea eliminar este tipo de actividad?");
                System.out.println("1. Sí");
                System.out.println("2. No");

                int confirmacion = 0;
                while (true) {
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
                    // Realizar la baja lógica del tipo de actividad.
                    try {
                        tipoActividadDAO.eliminarTipoActividad(tipoActividadSeleccionado.getIdTipoActividad());
                        System.out.println("Tipo de actividad eliminado con éxito.");
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar el tipo de actividad en la base de datos.");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Eliminación cancelada.");
                }

                // Preguntar si desea eliminar otro tipo de actividad.
                int opcion;
                while (true) {
                    System.out.println("\n¿Desea eliminar otro tipo de actividad?");
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
                System.out.println("Error al obtener los tipos de actividad de la base de datos.");
                e.printStackTrace();
                continuar = false;
            }
        }
    }

    public static void mostrarTiposActividades() {
        TipoActividadDAO tipoActividadDAO = TipoActividadDAO.getInstance();
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            // Preguntar al usuario si desea ver actividades activas o inactivas.
            System.out.println("¿Qué tipos de actividades desea ver?");
            System.out.println("1. Activas");
            System.out.println("2. Inactivas");
            System.out.print("Ingrese el número de la opción: ");
            int opcion = 0;

            while (true) {
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

            // Obtener el estado según la opción seleccionada.
            boolean estado = (opcion == 1);

            try {
                // Obtener la lista de tipos de actividades según el estado.
                List<TipoActividad> tiposActividades = tipoActividadDAO.listarTiposActividadesPorEstado(estado);

                if (tiposActividades.isEmpty()) {
                    System.out.println("No hay tipos de actividades registrados con el estado seleccionado.");
                } else {
                    System.out.println("Lista de tipos de actividades:");

                    // Mostrar cada tipo de actividad.
                    for (int i = 0; i < tiposActividades.size(); i++) {
                        TipoActividad tipo = tiposActividades.get(i);
                        System.out.println((i + 1) + ". " + tipo.getNombre() + " [" + tipo.getDescripcion() + "]");  // Mostrar la información de cada tipo de actividad.
                    }
                }
            } catch (SQLException e) {
                System.out.println("Error al obtener la lista de tipos de actividades.");
                e.printStackTrace();
            }

            // Preguntar si desea listar con otro filtro.
            System.out.println("\n¿Desea realizar otra búsqueda?");
            System.out.println("1. Sí");
            System.out.println("2. No");
            System.out.print("Ingrese el número de la opción: ");
            int opcionFiltro = 0;

            while (true) {
                try {
                    opcionFiltro = Integer.parseInt(scanner.nextLine().trim());
                    if (opcionFiltro == 1 || opcionFiltro == 2) {
                        break;
                    } else {
                        System.out.println("Opción inválida. Debe ingresar 1 o 2.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Debe ingresar un número válido.");
                }
            }

            continuar = (opcionFiltro == 1);
        }
    }
}