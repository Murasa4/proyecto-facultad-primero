package ClasesServicios;

import Clases.Actividad;
import Clases.TipoActividad;
import ClasesDAO.ActividadDAO;
import ClasesDAO.TipoActividadDAO;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class ActividadServicios {

    public static void agregarActividad() {
        Scanner scanner = new Scanner(System.in);
        boolean continuarAgregando = true;

        while (continuarAgregando) {
            try {
                //Ingreso de nombre.
                String nombre;
                while (true) {
                    System.out.println("Ingrese el nombre de la actividad:");
                    nombre = scanner.nextLine().trim();
                    if (nombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacío.");
                    } else if (nombre.length() > 50) {
                        System.out.println("El nombre no puede superar los 50 caracteres.");
                    } else {
                        break;
                    }
                }

                //Ingreso de descripción.
                String descripcion;
                while (true) {
                    System.out.println("Ingrese la descripción de la actividad:");
                    descripcion = scanner.nextLine().trim();
                    if (descripcion.isEmpty()) {
                        System.out.println("La descripción no puede estar vacía.");
                    } else if (descripcion.length() > 150) {
                        System.out.println("La descripción no puede superar los 150 caracteres.");
                    } else {
                        break;
                    }
                }

                //Ingreso de Fecha.
                Timestamp fecha;
                while (true) {
                    try {
                        System.out.println("Ingrese la fecha de la actividad (YYYY-MM-DD HH:MM:SS):");
                        String fechaInput = scanner.nextLine().trim();
                        fecha = Timestamp.valueOf(fechaInput);
                        if (fecha.before(new Timestamp(System.currentTimeMillis()))) {
                            System.out.println("La fecha debe ser futura.");
                        } else {
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Formato de fecha inválido. Intente nuevamente.");
                    }
                }

                //Selección del tipo de actividad.
                int idTipoActividad;
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
                            TipoActividad tipo = tiposActividad.get(i);
                            System.out.println((i + 1) + ". " + tipo.getNombre() + " - " + tipo.getDescripcion());
                        }

                        int seleccion = Integer.parseInt(scanner.nextLine().trim());
                        if (seleccion < 1 || seleccion > tiposActividad.size()) {
                            System.out.println("Selección inválida. Intente nuevamente.");
                        } else {
                            idTipoActividad = tiposActividad.get(seleccion - 1).getIdTipoActividad();
                            break;
                        }
                    } catch (SQLException e) {
                        System.out.println("Error al obtener los tipos de actividad desde la base de datos.");
                        e.printStackTrace();
                        return;
                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                //Ingreso de cupo.
                int cupo;
                while (true) {
                    try {
                        System.out.println("Ingrese la cantidad de cupos para la actividad:");
                        cupo = Integer.parseInt(scanner.nextLine().trim());
                        if (cupo <= 0) {
                            System.out.println("La cantidad de cupos debe ser mayor a 0.");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("La cantidad de cupos debe ser un número entero.");
                    }
                }

                //Crear actividad con los datos.
                Actividad actividad = new Actividad.ActividadBuilder()
                        .setNombre(nombre)
                        .setDescripcion(descripcion)
                        .setFecha(fecha)
                        .setTipoActividad(new TipoActividad.TipoActividadBuilder().setIdTipoActividad(idTipoActividad).build())
                        .setCupo(cupo)
                        .setCantidadInscriptos(0)
                        .setCantidadCancelados(0)
                        .setEstado(true)
                        .build();

                try {
                    ActividadDAO actividadDAO = ActividadDAO.getInstance();
                    actividadDAO.agregarActividad(actividad);
                    System.out.println("Actividad agregada con éxito.");
                } catch (SQLException e) {
                    System.out.println("Error al ingresar la actividad en la base de datos.");
                    e.printStackTrace();
                }

                // Preguntar si desea ingresar otra actividad.
                int opcion;
                while (true) {
                    System.out.println("\n¿Desea ingresar otra actividad?");
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

                continuarAgregando = (opcion == 1);

            } catch (Exception e) {
                System.out.println("Ha ocurrido un error inesperado.");
                e.printStackTrace();
            }
        }
    }

    public static void actualizarActividad() {
        Scanner scanner = new Scanner(System.in);
        ActividadDAO actividadDAO = ActividadDAO.getInstance();
        boolean continuarActualizacion = true;

        while (continuarActualizacion) {
            try {
                List<Actividad> actividadesDisponibles = actividadDAO.listarActividadesPorEstado(true);

                if (actividadesDisponibles.isEmpty()) {
                    System.out.println("No hay actividades activas disponibles para actualizar.");
                    break;
                }

                // Mostrar actividades disponibles.
                System.out.println("Seleccione una actividad para actualizar:");
                for (int i = 0; i < actividadesDisponibles.size(); i++) {
                    Actividad act = actividadesDisponibles.get(i);
                    System.out.println((i + 1) + ". " + act.getNombre() + " | " + act.getFecha());
                }

                int seleccion = 0;
                while (true) {
                    try {
                        System.out.println("Ingrese el número de la actividad que desea actualizar:");
                        seleccion = Integer.parseInt(scanner.nextLine().trim());
                        if (seleccion < 1 || seleccion > actividadesDisponibles.size()) {
                            System.out.println("Selección inválida. Intente nuevamente.");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor, ingrese un número válido.");
                    }
                }

                Actividad actividadExistente = actividadesDisponibles.get(seleccion - 1);

                // Ingreso de nuevo nombre.
                String nuevoNombre;
                while (true) {
                    System.out.println("Ingrese el nuevo nombre de la actividad:");
                    nuevoNombre = scanner.nextLine().trim();
                    if (nuevoNombre.isEmpty()) {
                        System.out.println("El nombre no puede estar vacío.");
                    } else if (nuevoNombre.length() > 50) {
                        System.out.println("El nombre no puede superar los 50 caracteres.");
                    } else {
                        break;
                    }
                }

                // Ingreso de nueva descripción.
                String nuevaDescripcion;
                while (true) {
                    System.out.println("Ingrese la nueva descripción de la actividad:");
                    nuevaDescripcion = scanner.nextLine().trim();
                    if (nuevaDescripcion.isEmpty()) {
                        System.out.println("La descripción no puede estar vacía.");
                    } else if (nuevaDescripcion.length() > 150) {
                        System.out.println("La descripción no puede superar los 150 caracteres.");
                    } else {
                        break;
                    }
                }

                // Ingreso de nueva fecha.
                Timestamp nuevaFecha;
                while (true) {
                    try {
                        System.out.println("Ingrese la nueva fecha de la actividad (YYYY-MM-DD HH:MM:SS):");
                        String fechaInput = scanner.nextLine().trim();
                        nuevaFecha = Timestamp.valueOf(fechaInput);
                        if (nuevaFecha.before(new Timestamp(System.currentTimeMillis()))) {
                            System.out.println("La fecha debe ser futura.");
                        } else {
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println("Formato de fecha inválido. Intente nuevamente.");
                    }
                }

                // Selección de nuevo tipo de actividad.
                int idTipoActividad = 0;
                while (true) {
                    try {
                        TipoActividadDAO tipoActividadDAO = TipoActividadDAO.getInstance();
                        List<TipoActividad> tiposDeActividad = tipoActividadDAO.listarTiposActividades();

                        if (tiposDeActividad.isEmpty()) {
                            System.out.println("No hay tipos de actividad activos disponibles.");
                            return;
                        }

                        System.out.println("Seleccione el tipo de actividad:");
                        for (int i = 0; i < tiposDeActividad.size(); i++) {
                            TipoActividad tipo = tiposDeActividad.get(i);
                            System.out.printf("%d. %s - %s%n", i + 1, tipo.getNombre(), tipo.getDescripcion());
                        }

                        int tipoSeleccionado = Integer.parseInt(scanner.nextLine().trim());
                        if (tipoSeleccionado < 1 || tipoSeleccionado > tiposDeActividad.size()) {
                            System.out.println("Selección inválida. Intente nuevamente.");
                        } else {
                            idTipoActividad = tiposDeActividad.get(tipoSeleccionado - 1).getIdTipoActividad();
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Por favor, ingrese un número válido.");
                    } catch (SQLException e) {
                        System.out.println("Error al obtener tipos de actividad.");
                        e.printStackTrace();
                        return;
                    }
                }

                // Ingreso del nuevo cupo.
                int nuevoCupo;
                while (true) {
                    try {
                        System.out.println("Ingrese el nuevo número de cupos para la actividad:");
                        nuevoCupo = Integer.parseInt(scanner.nextLine().trim());
                        if (nuevoCupo <= 0) {
                            System.out.println("La cantidad de cupos debe ser mayor a 0.");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("La cantidad de cupos debe ser un número entero.");
                    }
                }

                // Actualizar datos de la actividad.
                actividadExistente.setNombre(nuevoNombre);
                actividadExistente.setDescripcion(nuevaDescripcion);
                actividadExistente.setFecha(nuevaFecha);
                actividadExistente.setTipoActividad(new TipoActividad.TipoActividadBuilder().setIdTipoActividad(idTipoActividad).build());
                actividadExistente.setCupo(nuevoCupo);

                try {
                    actividadDAO.actualizarActividad(actividadExistente);
                    System.out.println("Actividad actualizada con éxito.");
                } catch (SQLException e) {
                    System.out.println("Error al actualizar la actividad en la base de datos.");
                    e.printStackTrace();
                }

                // Preguntar si desea actualizar otra actividad.
                int opcion;
                while (true) {
                    System.out.println("\n¿Desea actualizar otra actividad?");
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

                continuarActualizacion = (opcion == 1);

            } catch (SQLException e) {
                System.out.println("Error al obtener las actividades desde la base de datos.");
                e.printStackTrace();
                break;
            }
        }
    }

    public static void eliminarActividad() {
        Scanner scanner = new Scanner(System.in);
        ActividadDAO actividadDAO = ActividadDAO.getInstance();
        boolean continuarEliminacion = true;

        while (continuarEliminacion) {
            try {
                List<Actividad> actividadesActivas = actividadDAO.listarActividadesPorEstado(true);

                if (actividadesActivas.isEmpty()) {
                    System.out.println("No hay actividades activas para eliminar.");
                    break;
                }

                // Mostrar las actividades activas.
                System.out.println("Actividades disponibles para eliminar:");
                System.out.println("-------------------------");
                for (int i = 0; i < actividadesActivas.size(); i++) {
                    Actividad actividad = actividadesActivas.get(i);
                    System.out.println((i + 1) + ". " + actividad.getNombre());
                }

                // Seleccionar la actividad.
                int seleccion = 0;
                while (true) {
                    try {
                        System.out.println("Seleccione el número de la actividad que desea eliminar:");
                        seleccion = Integer.parseInt(scanner.nextLine().trim());
                        if (seleccion < 1 || seleccion > actividadesActivas.size()) {
                            System.out.println("Selección inválida. Por favor, elija un número válido.");
                        } else {
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Error: Ingrese un número válido.");
                    }
                }

                Actividad actividadSeleccionada = actividadesActivas.get(seleccion - 1);

                // Mostrar información de la actividad seleccionada.
                System.out.println("\nInformación de la actividad seleccionada:");
                System.out.println(actividadSeleccionada);

                // Confirmar eliminación.
                int opcionConfirmacion;
                while (true) {
                    System.out.println("\n¿Está seguro que desea eliminar esta actividad?");
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
                        actividadDAO.eliminarActividad(actividadSeleccionada.getIdActividad());
                        System.out.println("Actividad eliminada con éxito.");
                    } catch (SQLException e) {
                        System.out.println("Error al eliminar la actividad en la base de datos.");
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("Eliminación cancelada.");
                }

                // Preguntar si desea eliminar otra actividad.
                int opcion;
                while (true) {
                    System.out.println("\n¿Desea eliminar otra actividad?");
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

                continuarEliminacion = (opcion == 1);

            } catch (SQLException e) {
                System.out.println("Error al obtener las actividades de la base de datos: " + e.getMessage());
                e.printStackTrace();
                break;
            }
        }
    }

    public static void mostrarActividadesPorNombre() {
        Scanner scanner = new Scanner(System.in);
        boolean continuarBusqueda = true;

        while (continuarBusqueda) {
            System.out.println("-------------------------");
            System.out.println("Ingrese el nombre de la actividad a buscar:");
            System.out.println("-------------------------");

            String nombre = scanner.nextLine().trim();

            // Validar que el nombre no esté vacío.
            while (nombre.isEmpty()) {
                System.out.println("El nombre no puede estar vacío. Inténtelo nuevamente:");
                nombre = scanner.nextLine().trim();
            }

            try {
                List<Actividad> actividades = ActividadDAO.getInstance().obtenerActividadesPorNombre(nombre);

                if (actividades.isEmpty()) {
                    System.out.println("-------------------------");
                    System.out.println("No se encontraron actividades con el nombre proporcionado.");
                    System.out.println("-------------------------");
                } else {
                    System.out.println("Actividades encontradas:");
                    System.out.println("-------------------------");
                    for (Actividad actividad : actividades) {
                        System.out.println(actividad);
                    }
                }
            } catch (SQLException e) {
                System.out.println("Ocurrió un error al buscar las actividades: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Se ha producido un error: " + e.getMessage());
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

            continuarBusqueda = (opcion == 1);
        }
    }

    public static void mostrarActividadesPorTipo() {
        Scanner scanner = new Scanner(System.in);
        boolean continuarBusqueda = true;

        while (continuarBusqueda) {
            try {
                List<TipoActividad> tipos = TipoActividadDAO.getInstance().listarTiposActividades();

                if (tipos.isEmpty()) {
                    System.out.println("No hay tipos de actividades disponibles.");
                    return;
                }

                System.out.println("Tipos de Actividades Disponibles:");
                System.out.println("-------------------------");
                // Listar los tipos de actividades.
                for (int i = 0; i < tipos.size(); i++) {
                    System.out.println((i + 1) + ". " + tipos.get(i).getNombre());
                }

                int idSeleccionado = -1;
                boolean idValido = false;

                while (!idValido) {
                    System.out.println("\nIngrese el número del tipo de actividad que desea consultar:");
                    String entrada = scanner.nextLine().trim();

                    if (entrada.isEmpty()) {
                        System.out.println("Debe ingresar un valor.");
                        continue;
                    }

                    try {
                        idSeleccionado = Integer.parseInt(entrada);

                        if (idSeleccionado < 1 || idSeleccionado > tipos.size()) {
                            System.out.println("El número ingresado no corresponde a ningún tipo de actividad.");
                        } else {
                            idValido = true;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                // Obtener las actividades según el tipo seleccionado.
                List<Actividad> actividades = ActividadDAO.getInstance().listarActividadesPorTipo(idSeleccionado);

                if (actividades.isEmpty()) {
                    System.out.println("\nNo se encontraron actividades para el tipo seleccionado.");
                } else {
                    System.out.println("\nActividades encontradas:");
                    System.out.println("-------------------------");
                    for (Actividad actividad : actividades) {
                        System.out.println(actividad);
                    }
                }

            } catch (SQLException e) {
                System.out.println("Ocurrió un error al buscar los tipos de actividad: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
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

            continuarBusqueda = (opcion == 1);
        }
    }

    public static void mostrarActividadesPorFecha() {
        Scanner scanner = new Scanner(System.in);
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        formatoFecha.setLenient(false); // No permite fechas inválidas.

        boolean continuarBusqueda = true;

        while (continuarBusqueda) {
            try {
                Date fechaIngresada = null;
                boolean fechaValida = false;

                while (!fechaValida) {
                    System.out.println("Ingrese la fecha a consultar (formato: yyyy-MM-dd):");
                    String entrada = scanner.nextLine().trim();

                    if (entrada.isEmpty()) {
                        System.out.println("Debe ingresar una fecha.");
                        continue;
                    }

                    try {
                        fechaIngresada = formatoFecha.parse(entrada);
                        fechaValida = true;
                    } catch (ParseException e) {
                        System.out.println("Formato inválido. Intente nuevamente con el formato yyyy-MM-dd.");
                    }
                }

                List<Actividad> actividades = ActividadDAO.getInstance().listarActividadesPorFecha(fechaIngresada);

                if (actividades.isEmpty()) {
                    System.out.println("\nNo se encontraron actividades para la fecha ingresada.");
                } else {
                    System.out.println("\nActividades encontradas:");
                    System.out.println("-------------------------");
                    for (Actividad actividad : actividades) {
                        System.out.println(actividad);
                    }
                }

            } catch (SQLException e) {
                System.out.println("Ocurrió un error al buscar las actividades: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
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

            continuarBusqueda = (opcion == 1);
        }
    }

    public static void mostrarActividadesPorEstado() {
        Scanner scanner = new Scanner(System.in);
        boolean continuarBusqueda = true;

        while (continuarBusqueda) {
            boolean estado = true;
            boolean opcionValida = false;

            while (!opcionValida) {
                System.out.println("Seleccione el estado de las actividades a consultar:");
                System.out.println("1. Activo");
                System.out.println("2. Inactivo");
                String entrada = scanner.nextLine().trim();

                if (entrada.isEmpty()) {
                    System.out.println("Debe ingresar una opción.");
                    continue;
                }

                if (entrada.equals("1")) {
                    estado = true;
                    opcionValida = true;
                } else if (entrada.equals("2")) {
                    estado = false;
                    opcionValida = true;
                } else {
                    System.out.println("Opción no válida. Intente nuevamente.");
                }
            }

            try {
                List<Actividad> actividades = ActividadDAO.getInstance().listarActividadesPorEstado(estado);

                if (actividades.isEmpty()) {
                    System.out.println("\nNo se encontraron actividades con el estado seleccionado.");
                } else {
                    System.out.println("\nActividades encontradas:");
                    System.out.println("-------------------------");
                    for (Actividad actividad : actividades) {
                        System.out.println(actividad);
                    }
                }

            } catch (SQLException e) {
                System.out.println("Ocurrió un error al buscar las actividades: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error inesperado: " + e.getMessage());
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

            continuarBusqueda = (opcion == 1);
        }
    }

    public static void listarActividadesParaInscribirse() {
        Scanner scanner = new Scanner(System.in);
        List<Actividad> actividades;
        {
            try {
                actividades = ActividadDAO.getInstance().listarActividadesDisponiblesParaInscripcion();
                if (actividades.isEmpty()) {
                    System.out.println("\nNo se encontraron actividades para inscribirse.");
                }
                System.out.println("\nActividades disponibles para inscripción:");
                System.out.println("-------------------------");
                for (Actividad actividad : actividades) {
                    System.out.println(actividad);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}