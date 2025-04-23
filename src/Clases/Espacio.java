package Clases;

import java.sql.Date;

public class Espacio {

    private int idEspacio;
    private String nombre;
    private String ubicacion;
    private boolean estado;
    private int capacidad;
    private Date fechaVigenciaPrecios;
    private double precioReservaSocios;
    private double precioReservaNoSocios;

    private Espacio(EspacioBuilder builder) {
        this.idEspacio = builder.idEspacio;
        this.nombre = builder.nombre;
        this.ubicacion = builder.ubicacion;
        this.estado = builder.estado;
        this.capacidad = builder.capacidad;
        this.fechaVigenciaPrecios = builder.fechaVigenciaPrecios;
        this.precioReservaSocios = builder.precioReservaSocios;
        this.precioReservaNoSocios = builder.precioReservaNoSocios;
    }

    public int getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(int idEspacio) {
        this.idEspacio = idEspacio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public Date getFechaVigenciaPrecios() {
        return fechaVigenciaPrecios;
    }

    public void setFechaVigenciaPrecios(Date fechaVigenciaPrecios) {
        this.fechaVigenciaPrecios = fechaVigenciaPrecios;
    }

    public double getPrecioReservaSocios() {
        return precioReservaSocios;
    }

    public void setPrecioReservaSocios(double precioReservaSocios) {
        this.precioReservaSocios = precioReservaSocios;
    }

    public double getPrecioReservaNoSocios() {
        return precioReservaNoSocios;
    }

    public void setPrecioReservaNoSocios(double precioReservaNoSocios) {
        this.precioReservaNoSocios = precioReservaNoSocios;
    }

    public static class EspacioBuilder {
        private int idEspacio;
        private String nombre;
        private String ubicacion;
        private boolean estado;
        private int capacidad;
        private Date fechaVigenciaPrecios;
        private double precioReservaSocios;
        private double precioReservaNoSocios;

        public EspacioBuilder setIdEspacio(int idEspacio) {
            this.idEspacio = idEspacio;
            return this;
        }

        public EspacioBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public EspacioBuilder setUbicacion(String ubicacion) {
            this.ubicacion = ubicacion;
            return this;
        }

        public EspacioBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public EspacioBuilder setCapacidad(int capacidad) {
            this.capacidad = capacidad;
            return this;
        }

        public EspacioBuilder setFechaVigenciaPrecios(Date fechaVigenciaPrecios) {
            this.fechaVigenciaPrecios = fechaVigenciaPrecios;
            return this;
        }

        public EspacioBuilder setPrecioReservaSocios(double precioReservaSocios) {
            this.precioReservaSocios = precioReservaSocios;
            return this;
        }

        public EspacioBuilder setPrecioReservaNoSocios(double precioReservaNoSocios) {
            this.precioReservaNoSocios = precioReservaNoSocios;
            return this;
        }

        public Espacio build() {
            return new Espacio(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles del espacio: [ID Espacio: " + idEspacio +
                " | Nombre: " + nombre +
                " | Ubicación: " + ubicacion +
                " | Estado: " + (estado ? "Activo" : "Inactivo") +
                " | Capacidad: " + capacidad +
                " | Fecha de Vigencia de Precios: " + fechaVigenciaPrecios +
                " | Precio Reserva Socios: $" + precioReservaSocios +
                " | Precio Reserva No Socios: $" + precioReservaNoSocios + "]";
    }
}