package Clases;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Reserva {

    private int idReserva;
    private Timestamp fechaCreada;
    private Date fechaReserva;
    private Time horaInicio;
    private Time horaFin;
    private Usuario usuario;
    private double montoSenia;
    private double saldo;
    private double montoTotal;
    private int cantidadPersonas;
    private Espacio espacio;
    private boolean estado;

    private Reserva(ReservaBuilder builder) {
        this.idReserva = builder.idReserva;
        this.fechaCreada = builder.fechaCreada;
        this.fechaReserva = builder.fechaReserva;
        this.horaInicio = builder.horaInicio;
        this.horaFin = builder.horaFin;
        this.usuario = builder.usuario;
        this.montoSenia = builder.montoSenia;
        this.saldo = builder.saldo;
        this.montoTotal = builder.montoTotal;
        this.cantidadPersonas = builder.cantidadPersonas;
        this.espacio = builder.espacio;
        this.estado = builder.estado;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public Timestamp getFechaCreada() {
        return fechaCreada;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public Time getHoraInicio() {
        return horaInicio;
    }

    public Time getHoraFin() {
        return horaFin;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public double getMontoSenia() {
        return montoSenia;
    }

    public double getSaldo() {
        return saldo;
    }

    public double getMontoTotal() {
        return montoTotal;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public boolean getEstado() {
        return estado;
    }

    public static class ReservaBuilder {
        private int idReserva;
        private Timestamp fechaCreada;
        private Date fechaReserva;
        private Time horaInicio;
        private Time horaFin;
        private Usuario usuario;
        private double montoSenia;
        private double saldo;
        private double montoTotal;
        private int cantidadPersonas;
        private Espacio espacio;
        private boolean estado;

        public ReservaBuilder setIdReserva(int idReserva) {
            this.idReserva = idReserva;
            return this;
        }

        public ReservaBuilder setFechaCreada(Timestamp fechaCreada) {
            this.fechaCreada = fechaCreada;
            return this;
        }

        public ReservaBuilder setFechaReserva(Date fechaReserva) {
            this.fechaReserva = fechaReserva;
            return this;
        }

        public ReservaBuilder setHoraInicio(Time horaInicio) {
            this.horaInicio = horaInicio;
            return this;
        }

        public ReservaBuilder setHoraFin(Time horaFin) {
            this.horaFin = horaFin;
            return this;
        }

        public ReservaBuilder setUsuario(Usuario usuario) {
            this.usuario = usuario;
            return this;
        }

        public ReservaBuilder setMontoSenia(double montoSenia) {
            this.montoSenia = montoSenia;
            return this;
        }

        public ReservaBuilder setSaldo(double saldo) {
            this.saldo = saldo;
            return this;
        }

        public ReservaBuilder setMontoTotal(double montoTotal) {
            this.montoTotal = montoTotal;
            return this;
        }

        public ReservaBuilder setCantidadPersonas(int cantidadPersonas) {
            this.cantidadPersonas = cantidadPersonas;
            return this;
        }

        public ReservaBuilder setEspacio(Espacio espacio) {
            this.espacio = espacio;
            return this;
        }

        public ReservaBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public Reserva build() {
            return new Reserva(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles de la reserva:\n" +
                "ID Reserva: " + idReserva + "\n" +
                "Fecha Creada: " + fechaCreada + "\n" +
                "Fecha Reserva: " + fechaReserva + "\n" +
                "Hora Inicio: " + horaInicio + "\n" +
                "Hora Fin: " + horaFin + "\n" +
                "Usuario: " + usuario.getPrimerNombre() + " " + usuario.getPrimerApellido() + "\n" +
                "Monto Seña: $" + String.format("%.2f", montoSenia) + "\n" +
                "Saldo: $" + String.format("%.2f", saldo) + "\n" +
                "Monto Total: $" + String.format("%.2f", montoTotal) + "\n" +
                "Cantidad de Personas: " + cantidadPersonas + "\n" +
                "Espacio: " + espacio.getNombre() + "\n" +
                "Estado: " + (estado ? "Activo" : "Inactivo");
    }
}
