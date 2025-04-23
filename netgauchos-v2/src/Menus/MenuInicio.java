package Menus;

import Clases.Usuario;
import ClasesServicios.UsuarioServicios;

import java.io.Console;
import java.sql.SQLException;
import java.util.Scanner;

//importacion para la contrasenia


public class MenuInicio {


    public MenuInicio() {
        Scanner scanner = new Scanner(System.in);
        String email;
        String contrasena;
        boolean correoValido = false;


        Console console = System.console();

        //Instanciar UsuarioServicios
        UsuarioServicios usuarioServicios = new UsuarioServicios();

        while (!correoValido) {
            System.out.println("Ingrese su correo electrónico:");
            email = scanner.nextLine();

            try {

                if (usuarioServicios.verificarCorreo(email)) {
                    Usuario usuario = usuarioServicios.obtenerUsuarioPorCorreo(email);

                    // Verificar si el usuario está activo.
                    if (!usuario.getEstado()) {
                        System.out.println("Este usuario ha sido dado de baja y no puede ingresar al sistema.");
                        continue; // Volver a pedir el correo.
                    }


                    correoValido = true;

                    System.out.println("-------------------------");
                    System.out.println("Ingrese su contraseña:");
                    contrasena = scanner.nextLine();

                    if (usuarioServicios.verificarContrasenia(email, contrasena)) {
                        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-");
                        System.out.println("Bienvenido al sistema.");
                        System.out.println("-*-*-*-*-*-*-*-*-*-*-*-*-");

                        usuarioServicios.obtenerUsuarioPorCorreo(email);
                        Integer idusuario = usuarioServicios.getIdTipoUsuarioPorUsuario(usuario);

                        if (idusuario != 1) { // Si no es administrador
                            System.out.println("Debe introducir un correo de administrador.");
                            correoValido = false; // Volver a pedir correo
                            continue; // Volver al inicio del while
                        }

                        // Si es administrador, continúa normalmente
                        switch (idusuario) {
                            case 1:
                                new MenuAdmin(usuario);
                                break;
                            case 3:
                                //MenuSocio(usuario);
                                break;
                            default:
                                System.out.println("-------------------------");
                                System.out.println("Tipo de usuario no válido.");
                                break;
                        }

                    } else {
                        System.out.println("Contraseña incorrecta. Intente nuevamente:");
                        correoValido = false; // Volver a pedir el correo
                    }
                } else {
                    System.out.println("No existe ningún usuario con ese correo. Intente nuevamente.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

