package Clases;

public class Subcomision {

    private int idSubcomision;
    private String nombre;
    private String descripcion;
    private boolean estado;

    private Subcomision(SubcomisionBuilder builder) {
        this.idSubcomision = builder.idSubcomision;
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.estado = builder.estado;
    }

    public int getIdSubcomision() {
        return idSubcomision;
    }

    public void setIdSubcomision(int idSubcomision) {
        this.idSubcomision = idSubcomision;
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

    public static class SubcomisionBuilder {
        private int idSubcomision;
        private String nombre;
        private String descripcion;
        private boolean estado;

        public SubcomisionBuilder setIdSubcomision(int idSubcomision) {
            this.idSubcomision = idSubcomision;
            return this;
        }

        public SubcomisionBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public SubcomisionBuilder setDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public SubcomisionBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public Subcomision build() {
            return new Subcomision(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles de la subcomisión:\n" +
                "ID Subcomisión: " + idSubcomision + "\n" +
                "Nombre: " + nombre + "\n" +
                "Descripción: " + descripcion + "\n" +
                "Estado: " + (estado ? "Activo" : "Inactivo");
    }
}