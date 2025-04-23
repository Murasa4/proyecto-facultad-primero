package Menus;

import Clases.*;
import ClasesDAO.*;
import ClasesServicios.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.Scanner;

public class MenuAgregarUsuario {

    public MenuAgregarUsuario() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        DepartamentoDAO departamentoDAO = new DepartamentoDAO();
        TelefonoDAO telefonoDAO = new TelefonoDAO();
        SubcomisionDAO subcomisionDAO = new SubcomisionDAO();

        System.out.println("-------------------------");
        System.out.println("Ingrese los datos del nuevo usuario:");
        System.out.println("-------------------------");
        System.out.print("Primer nombre: ");
        String priNombre = UsuarioServicios.obtenerNombreValido();

        System.out.println("-------------------------");
        System.out.print("Segundo nombre (si no aplica, deje en blanco): ");
        String segNombre = UsuarioServicios.obtenerNombreValidoOpcional();

        System.out.println("-------------------------");
        System.out.print("Primer apellido: ");
        String priApellido = UsuarioServicios.obtenerNombreValido();

        System.out.println("-------------------------");
        System.out.print("Segundo apellido (si no aplica, deje en blanco): ");
        String segApellido = UsuarioServicios.obtenerNombreValidoOpcional();

        int idTipoDoc = TipoDocumentoServicios.mostrarYSeleccionarTipoDocumento();
        TipoDocumento tipoDocumento = TipoDocumentoDAO.getInstance().obtenerTipoDocumentoPorId(idTipoDoc);

        System.out.println("-------------------------");
        System.out.print("Documento (Sin puntos ni guiones): ");
        // String documento = scanner.nextLine();
        String documento = UsuarioServicios.obtenerCIValida();

        System.out.println("-------------------------");
        System.out.print("Fecha de nacimiento (YYYY-MM-DD): ");
        Date fecNacimiento = Date.valueOf(UsuarioServicios.obtenerFechaNacimientoValida());

        int idDomicilio = MenuCrearDomicilio.crearDomicilio();
        Domicilio domicilio = DomicilioDAO.getInstance().obtenerDomicilioPorId(idDomicilio);

        System.out.println("-------------------------");
        // Validar correo
        String email;
        do {
            System.out.print("Email: ");
            email = scanner.nextLine();
        } while (!UsuarioServicios.validarCorreo(email));


        System.out.println("-------------------------");
        System.out.print("Contraseña: ");
        String contrasenia = UsuarioServicios.obtenerContraseniaValida();

        int idTipoUsuario = TipoUsuarioServicios.mostrarYSeleccionarTipoUsuario();
        TipoUsuario tipoUsuario = TipoUsuarioDAO.getInstance().obtenerTipoUsuarioPorId(idTipoUsuario);

        int idCatSocio = CategoriaSocioServicios.mostrarYSeleccionarCategoriaSocio();
        CategoriaSocio categoriaSocio = CategoriaSocioDAO.getInstance().obtenerCategoriaSocioPorId(idCatSocio);

        System.out.println("-------------------------");
        System.out.print("Dificultad auditiva (si/no): ");
        boolean difAuditiva = UsuarioServicios.validarDificultad();

        System.out.println("-------------------------");
        System.out.print("Lengua de señas (si/no): ");
        boolean lenSenias = UsuarioServicios.validarDificultad();

        int idSubcomisionPrevio = SubcomisionServicios.mostrarYSeleccionarSubcomision();
        int idSubcomision;
        if (idSubcomisionPrevio == 0) {
            idSubcomision = -1; // Usamos -1 como indicador de "no aplica"
        } else {
            idSubcomision = idSubcomisionPrevio;
        }

        // Guarda el valor como null si corresponde
        Subcomision subcomision = (idSubcomision == -1) ? null : SubcomisionDAO.getInstance().obtenerSubcomisionPorId(idSubcomision);

        Usuario usuario = new Usuario.UsuarioBuilder()
                .setPrimerNombre(priNombre)
                .setSegundoNombre(segNombre)
                .setPrimerApellido(priApellido)
                .setSegundoApellido(segApellido)
                .setTipoDocumento(tipoDocumento)
                .setNumeroDocumento(documento)
                .setFechaNacimiento(fecNacimiento)
                .setDomicilio(domicilio)
                .setEmail(email)
                .setContrasenia(contrasenia)
                .setTipoUsuario(tipoUsuario)
                .setCategoriaSocio(categoriaSocio)
                .setDificultadAuditiva(difAuditiva)
                .setLenguajeSenias(lenSenias)
                .setSubcomision(subcomision)
                .setEstado(true)
                .build();

        try {
            usuarioDAO.agregarUsuario(usuario);
            System.out.println("Usuario agregado exitosamente.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error al agregar el usuario.");
        }
    }
}
