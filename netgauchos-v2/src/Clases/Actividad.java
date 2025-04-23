package Clases;

import java.sql.Timestamp;

public class Actividad {

    private int idActividad;
    private String nombre;
    private String descripcion;
    private Timestamp fecha;
    private TipoActividad tipoActividad;
    private int cupo;
    private int cantidadInscriptos;
    private int cantidadCancelados;
    private boolean estado;

    private Actividad(ActividadBuilder builder) {
        this.idActividad = builder.idActividad;
        this.nombre = builder.nombre;
        this.descripcion = builder.descripcion;
        this.fecha = builder.fecha;
        this.tipoActividad = builder.tipoActividad;
        this.cupo = builder.cupo;
        this.cantidadInscriptos = builder.cantidadInscriptos;
        this.cantidadCancelados = builder.cantidadCancelados;
        this.estado = builder.estado;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
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

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public TipoActividad getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(TipoActividad tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }

    public int getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    public void setCantidadInscriptos(int cantidadInscriptos) {
        this.cantidadInscriptos = cantidadInscriptos;
    }

    public int getCantidadCancelados() {
        return cantidadCancelados;
    }

    public void setCantidadCancelados(int cantidadCancelados) {
        this.cantidadCancelados = cantidadCancelados;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public static class ActividadBuilder {
        private int idActividad;
        private String nombre;
        private String descripcion;
        private Timestamp fecha;
        private TipoActividad tipoActividad;
        private int cupo;
        private int cantidadInscriptos;
        private int cantidadCancelados;
        private boolean estado;

        public ActividadBuilder setIdActividad(int idActividad) {
            this.idActividad = idActividad;
            return this;
        }

        public ActividadBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public ActividadBuilder setDescripcion(String descripcion) {
            this.descripcion = descripcion;
            return this;
        }

        public ActividadBuilder setFecha(Timestamp fecha) {
            this.fecha = fecha;
            return this;
        }

        public ActividadBuilder setTipoActividad(TipoActividad tipoActividad) {
            this.tipoActividad = tipoActividad;
            return this;
        }

        public ActividadBuilder setCupo(int cupo) {
            this.cupo = cupo;
            return this;
        }

        public ActividadBuilder setCantidadInscriptos(int cantidadInscriptos) {
            this.cantidadInscriptos = cantidadInscriptos;
            return this;
        }

        public ActividadBuilder setCantidadCancelados(int cantidadCancelados) {
            this.cantidadCancelados = cantidadCancelados;
            return this;
        }

        public ActividadBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public Actividad build() {
            return new Actividad(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles de la actividad: [ID: " + idActividad +
                " | Nombre: " + nombre +
                " | Descripción: " + descripcion +
                " | Fecha: " + fecha +
                " | Tipo de Actividad: " + tipoActividad.getIdTipoActividad() +
                " | Cupo: " + cupo +
                " | Inscriptos: " + cantidadInscriptos +
                " | Cancelados: " + cantidadCancelados +
                " | Estado: " + (estado ? "Activo" : "Inactivo") + "]";
    }
}