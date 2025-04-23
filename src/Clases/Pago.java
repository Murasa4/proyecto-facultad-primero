package Clases;

import java.sql.Timestamp;

public class Pago {
    private int idPago;
    private Timestamp fecha;
    private double monto;
    private Usuario usuario;
    private boolean estado;

    private Pago(PagoBuilder builder) {
        this.idPago = builder.idPago;
        this.fecha = builder.fecha;
        this.monto = builder.monto;
        this.usuario = builder.usuario;
        this.estado = builder.estado;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Timestamp getFecha() {
        return fecha;
    }

    public void setFecha(Timestamp fecha) {
        this.fecha = fecha;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public boolean getEstado() {
        return estado;
    }

    public static class PagoBuilder {
        private int idPago;
        private Timestamp fecha;
        private double monto;
        private Usuario usuario;
        private boolean estado;

        public PagoBuilder setIdPago(int idPago) {
            this.idPago = idPago;
            return this;
        }

        public PagoBuilder setFecha(Timestamp fecha) {
            this.fecha = fecha;
            return this;
        }

        public PagoBuilder setMonto(double monto) {
            this.monto = monto;
            return this;
        }

        public PagoBuilder setUsuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public PagoBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public Pago build() {
            return new Pago(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles del pago: [ID Pago: " + idPago +
                " | Fecha: " + fecha + " | Monto: $" + monto +
                " | ID Usuario: " + usuario.getIdUsuario() +
                " | Estado: " + (estado ? "Activo" : "Inactivo") + "]";
    }
}