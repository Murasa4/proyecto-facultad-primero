package Clases;

import java.sql.Date;

public class Usuario {

    private int idUsuario;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private TipoDocumento tipoDocumento;
    private String numeroDocumento;
    private Date fechaNacimiento;
    private Domicilio domicilio;
    private String email;
    private String contrasenia;
    private TipoUsuario tipoUsuario;
    private CategoriaSocio categoriaSocio;
    private boolean dificultadAuditiva;
    private boolean lenguajeSenias;
    private Subcomision subcomision;
    private boolean estado;

    private Usuario(UsuarioBuilder builder) {
        this.idUsuario = builder.idUsuario;
        this.primerNombre = builder.primerNombre;
        this.segundoNombre = builder.segundoNombre;
        this.primerApellido = builder.primerApellido;
        this.segundoApellido = builder.segundoApellido;
        this.tipoDocumento = builder.tipoDocumento;
        this.numeroDocumento = builder.numeroDocumento;
        this.fechaNacimiento = builder.fechaNacimiento;
        this.domicilio = builder.domicilio;
        this.email = builder.email;
        this.contrasenia = builder.contrasenia;
        this.tipoUsuario = builder.tipoUsuario;
        this.categoriaSocio = builder.categoriaSocio;
        this.dificultadAuditiva = builder.dificultadAuditiva;
        this.lenguajeSenias = builder.lenguajeSenias;
        this.subcomision = builder.subcomision;
        this.estado = builder.estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getSegundoNombre() {
        return segundoNombre;
    }

    public String getPrimerApellido() {
        return primerApellido;
    }

    public String getSegundoApellido() {
        return segundoApellido;
    }

    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public Domicilio getDomicilio() {
        return domicilio;
    }

    public String getEmail() {
        return email;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public CategoriaSocio getCategoriaSocio() {
        return categoriaSocio;
    }

    public boolean getDificultadAuditiva() {
        return dificultadAuditiva;
    }

    public boolean getLenguajeSenias() {
        return lenguajeSenias;
    }

    public Subcomision getSubcomision() {
        return subcomision;
    }

    public boolean getEstado() {
        return estado;
    }

    // Builder
    public static class UsuarioBuilder {
        private int idUsuario;
        private String primerNombre;
        private String segundoNombre;
        private String primerApellido;
        private String segundoApellido;
        private TipoDocumento tipoDocumento;
        private String numeroDocumento;
        private Date fechaNacimiento;
        private Domicilio domicilio;
        private String email;
        private String contrasenia;
        private TipoUsuario tipoUsuario;
        private CategoriaSocio categoriaSocio;
        private boolean dificultadAuditiva;
        private boolean lenguajeSenias;
        private Subcomision subcomision;
        private boolean estado;

        public UsuarioBuilder() {
            //Constructor vacío
        }

        public UsuarioBuilder(Usuario usuario) {
            this.idUsuario = usuario.getIdUsuario();
            this.primerNombre = usuario.getPrimerNombre();
            this.segundoNombre = usuario.getSegundoNombre();
            this.primerApellido = usuario.getPrimerApellido();
            this.segundoApellido = usuario.getSegundoApellido();
            this.tipoDocumento = usuario.getTipoDocumento();
            this.numeroDocumento = usuario.getNumeroDocumento();
            this.fechaNacimiento = usuario.getFechaNacimiento();
            this.domicilio = usuario.getDomicilio();
            this.email = usuario.getEmail();
            this.contrasenia = usuario.getContrasenia();
            this.tipoUsuario = usuario.getTipoUsuario();
            this.categoriaSocio = usuario.getCategoriaSocio();
            this.dificultadAuditiva = usuario.getDificultadAuditiva();
            this.lenguajeSenias = usuario.getLenguajeSenias();
            this.subcomision = usuario.getSubcomision();
            this.estado = usuario.getEstado();
        }

        public UsuarioBuilder setIdUsuario(int idUsuario) {
            this.idUsuario = idUsuario;
            return this;
        }

        public UsuarioBuilder setPrimerNombre(String primerNombre) {
            this.primerNombre = primerNombre;
            return this;
        }

        public UsuarioBuilder setSegundoNombre(String segundoNombre) {
            this.segundoNombre = segundoNombre;
            return this;
        }

        public UsuarioBuilder setPrimerApellido(String primerApellido) {
            this.primerApellido = primerApellido;
            return this;
        }

        public UsuarioBuilder setSegundoApellido(String segundoApellido) {
            this.segundoApellido = segundoApellido;
            return this;
        }

        public UsuarioBuilder setTipoDocumento(TipoDocumento tipoDocumento) {
            this.tipoDocumento = tipoDocumento;
            return this;
        }

        public UsuarioBuilder setNumeroDocumento(String numeroDocumento) {
            this.numeroDocumento = numeroDocumento;
            return this;
        }

        public UsuarioBuilder setFechaNacimiento(Date fechaNacimiento) {
            this.fechaNacimiento = fechaNacimiento;
            return this;
        }

        public UsuarioBuilder setDomicilio(Domicilio domicilio) {
            this.domicilio = domicilio;
            return this;
        }

        public UsuarioBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UsuarioBuilder setContrasenia(String contrasenia) {
            this.contrasenia = contrasenia;
            return this;
        }

        public UsuarioBuilder setTipoUsuario(TipoUsuario tipoUsuario) {
            this.tipoUsuario = tipoUsuario;
            return this;
        }

        public UsuarioBuilder setCategoriaSocio(CategoriaSocio categoriaSocio) {
            this.categoriaSocio = categoriaSocio;
            return this;
        }

        public UsuarioBuilder setDificultadAuditiva(boolean dificultadAuditiva) {
            this.dificultadAuditiva = dificultadAuditiva;
            return this;
        }

        public UsuarioBuilder setLenguajeSenias(boolean lenguajeSenias) {
            this.lenguajeSenias = lenguajeSenias;
            return this;
        }

        public UsuarioBuilder setSubcomision(Subcomision subcomision) {
            this.subcomision = subcomision;
            return this;
        }

        public UsuarioBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public Usuario build() {
            return new Usuario(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles del usuario:\n" +
                "ID Usuario: " + valor(idUsuario) + "\n" +
                "Primer Nombre: " + valor(primerNombre) + "\n" +
                "Segundo Nombre: " + valor(segundoNombre) + "\n" +
                "Primer Apellido: " + valor(primerApellido) + "\n" +
                "Segundo Apellido: " + valor(segundoApellido) + "\n" +
                "Tipo Documento: " + valor(tipoDocumento != null ? tipoDocumento.getNombre() : null) + "\n" +
                "Número Documento: " + valor(numeroDocumento) + "\n" +
                "Fecha Nacimiento: " + valor(fechaNacimiento) + "\n" +
                "Domicilio: " + (domicilio != null
                ? valor(domicilio.getCalle()) + " " + (domicilio.getNumeroPuerta() != 0 ? domicilio.getNumeroPuerta() : "")
                : "") + "\n" +
                "Email: " + valor(email) + "\n" +
                "Tipo Usuario: " + valor(tipoUsuario != null ? tipoUsuario.getNombre() : null) + "\n" +
                "Categoría Socio: " + valor(categoriaSocio != null ? categoriaSocio.getNombre() : null) + "\n" +
                "Dificultad Auditiva: " + (dificultadAuditiva ? "Sí" : "No") + "\n" +
                "Lenguaje de Señas: " + (lenguajeSenias ? "Sí" : "No") + "\n" +
                "Subcomisión: " + (subcomision != null && subcomision.getNombre() != null ? subcomision.getNombre() : "N/A") + "\n" +
                "Estado: " + (estado ? "Activo" : "Inactivo");
    }

    private String valor(Object dato) {
        return dato != null ? dato.toString() : "";
    }
}