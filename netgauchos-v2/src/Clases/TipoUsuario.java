package Clases;

public class TipoUsuario {

    private int idTipoUsuario;
    private String nombre;
    private String descripcion;
    private boolean estado;

    private TipoUsuario(TipoUsuarioBuilder builder) {
        this.idTipoUsuario = builder.idTipoUsuario;
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.estado = builder.estado;
    }

    public int getIdTipoUsuario() {
        return idTipoUsuario;
    }

    public void setIdTipoUsuario(int idTipoUsuario) {
        this.idTipoUsuario = idTipoUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public boolean getEstado() {
        return estado;
    }

    public static class TipoUsuarioBuilder {
        private int idTipoUsuario;
        private String nombre;
        private String descripcion;
        private boolean estado;

        public TipoUsuarioBuilder setIdTipoUsuario(int idTipoUsuario) {
            this.idTipoUsuario = idTipoUsuario;
            return this;
        }

        public TipoUsuarioBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public TipoUsuarioBuilder setDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public TipoUsuarioBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public TipoUsuario build() {
            return new TipoUsuario(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles del tipo de usuario:\n" +
                "ID Tipo Usuario: " + idTipoUsuario + "\n" +
                "Nombre: " + nombre + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Estado: " + (estado ? "Activo" : "Inactivo");
    }
}