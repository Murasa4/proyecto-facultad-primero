package Clases;

public class Telefono {

    private int idTelefono;
    private int idUsuario;
    private int numeroTelefono;
    private boolean estado;

    private Telefono(TelefonoBuilder builder) {
        this.idTelefono = builder.idTelefono;
        this.idUsuario = builder.idUsuario;
        this.numeroTelefono = builder.numeroTelefono;
        this.estado = builder.estado;
    }

    public int getIdTelefono() {
        return idTelefono;
    }

    public void setIdTelefono(int idTelefono) {
        this.idTelefono = idTelefono;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public int getNumeroTelefono() {
        return numeroTelefono;
    }

    public boolean getEstado() {
        return estado;
    }

    public static class TelefonoBuilder {
        private int idTelefono;
        private int idUsuario;
        private int numeroTelefono;
        private boolean estado;

        public TelefonoBuilder setIdTelefono(int idTelefono) {
            this.idTelefono = idTelefono;
            return this;
        }

        public TelefonoBuilder setIdUsuario(int idUsuario) {
            this.idUsuario = idUsuario;
            return this;
        }

        public TelefonoBuilder setNumeroTelefono(int numeroTelefono) {
            this.numeroTelefono = numeroTelefono;
            return this;
        }

        public TelefonoBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public Telefono build() {
            return new Telefono(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles del teléfono:\n" +
                "ID Teléfono: " + idTelefono + "\n" +
                "ID Usuario: " + idUsuario + "\n" +
                "Número de Teléfono: " + numeroTelefono + "\n" +
                "Estado: " + (estado ? "Activo" : "Inactivo");
    }
}