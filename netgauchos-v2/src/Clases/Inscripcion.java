package Clases;

import java.sql.Timestamp;

public class Inscripcion {

    private int idInscripcion;
    private Usuario usuario;
    private Actividad actividad;
    private Timestamp fechaInscripcion;
    private boolean estado;

    private Inscripcion(InscripcionBuilder builder) {
        this.idInscripcion = builder.idInscripcion;
        this.usuario = builder.usuario;
        this.actividad = builder.actividad;
        this.fechaInscripcion = builder.fechaInscripcion;
        this.estado = builder.estado;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Actividad getActividad() {
        return actividad;
    }

    public Timestamp getFechaInscripcion() {
        return fechaInscripcion;
    }

    public boolean getEstado() {
        return estado;
    }

    public static class InscripcionBuilder {
        private int idInscripcion;
        private Usuario usuario;
        private Actividad actividad;
        private Timestamp fechaInscripcion;
        private boolean estado;

        public InscripcionBuilder setIdInscripcion(int idInscripcion) {
            this.idInscripcion = idInscripcion;
            return this;
        }

        public InscripcionBuilder setUsuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public InscripcionBuilder setActividad(Actividad actividad) {
            this.actividad = actividad;
            return this;
        }

        public InscripcionBuilder setFechaInscripcion(Timestamp fechaInscripcion) {
            this.fechaInscripcion = fechaInscripcion;
            return this;
        }

        public InscripcionBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public Inscripcion build() {
            return new Inscripcion(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles de la inscripción: [ID Inscripción: " + idInscripcion +
                " | Usuario: " + usuario.getIdUsuario() +
                " | Actividad: " + actividad.getIdActividad() +
                " | Fecha de Inscripción: " + fechaInscripcion +
                " | Estado: " + (estado ? "Activo" : "Inactivo") + "]";
    }
}