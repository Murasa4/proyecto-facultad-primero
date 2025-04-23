package Clases;

public class TipoActividad {

    private int idTipoActividad;
    private String nombre;
    private String descripcion;
    private final boolean estado;

    private TipoActividad(TipoActividadBuilder builder) {
        this.idTipoActividad = builder.idTipoActividad;
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.estado = builder.estado;
    }

    public int getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(int idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "Detalles del tipo de actividad: [ID: " + idTipoActividad +
                " | Nombre: " + nombre +
                " | Descripción: " + descripcion +
                " | Estado: " + (estado ? "Activo" : "Inactivo") + "]";
    }

    public static class TipoActividadBuilder {
        private int idTipoActividad;
        private String nombre;
        private String descripcion;
        private boolean estado;

        public TipoActividadBuilder setIdTipoActividad(int idTipoActividad) {
            this.idTipoActividad = idTipoActividad;
            return this;
        }

        public TipoActividadBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public TipoActividadBuilder setDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public TipoActividadBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public TipoActividad build() {
            return new TipoActividad(this);
        }
    }
}