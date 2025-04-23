package Clases;

import java.sql.Timestamp;

public class CancelacionInscripcion {

    private int idCancelacion;
    private Inscripcion inscripcion;
    private Timestamp fechaCancelacion;

    private CancelacionInscripcion(CancelacionInscripcionBuilder builder) {
        this.idCancelacion = builder.idCancelacion;
        this.inscripcion = builder.inscripcion;
        this.fechaCancelacion = builder.fechaCancelacion;
    }

    public int getIdCancelacion() {
        return idCancelacion;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public Timestamp getFechaCancelacion() {
        return fechaCancelacion;
    }

    public static class CancelacionInscripcionBuilder {
        private int idCancelacion;
        private Inscripcion inscripcion;
        private Timestamp fechaCancelacion;

        public CancelacionInscripcionBuilder setIdCancelacion(int idCancelacion) {
            this.idCancelacion = idCancelacion;
            return this;
        }

        public CancelacionInscripcionBuilder setInscripcion(Inscripcion inscripcion) {
            this.inscripcion = inscripcion;
            return this;
        }

        public CancelacionInscripcionBuilder setFechaCancelacion(Timestamp fechaCancelacion) {
            this.fechaCancelacion = fechaCancelacion;
            return this;
        }

        public CancelacionInscripcion build() {
            return new CancelacionInscripcion(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles de la cancelación:[ID Cancelación: " + idCancelacion +
                " | Inscripción: " + inscripcion.getIdInscripcion() +
                " | Fecha de Cancelación: " + fechaCancelacion + "]";
    }
}