package Clases;

public class CategoriaSocio {

    private int idCategoriaSocio;
    private String nombre;
    private String descripcion;
    private boolean estado;

    private CategoriaSocio(CategoriaSocioBuilder builder) {
        this.idCategoriaSocio = builder.idCategoriaSocio;
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.estado = builder.estado;
    }

    public int getIdCategoriaSocio() {
        return idCategoriaSocio;
    }

    public void setIdCategoriaSocio(int idCategoriaSocio) {
        this.idCategoriaSocio = idCategoriaSocio;
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

    public static class CategoriaSocioBuilder {
        private int idCategoriaSocio;
        private String nombre;
        private String descripcion;
        private boolean estado;

        public CategoriaSocioBuilder setIdCategoriaSocio(int idCategoriaSocio) {
            this.idCategoriaSocio = idCategoriaSocio;
            return this;
        }

        public CategoriaSocioBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public CategoriaSocioBuilder setDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public CategoriaSocioBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public CategoriaSocio build() {
            return new CategoriaSocio(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles de la categoría de socio:\n" +
                "ID Categoría Socio: " + idCategoriaSocio + "\n" +
                "Nombre: " + nombre + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Estado: " + (estado ? "Activo" : "Inactivo");
    }
}