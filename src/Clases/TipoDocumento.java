package Clases;

public class TipoDocumento {

    private int idTipoDocumento;
    private String nombre;
    private boolean estado;

    private TipoDocumento(TipoDocumentoBuilder builder) {
        this.idTipoDocumento = builder.idTipoDocumento;
        this.nombre = builder.nombre;
        this.estado = builder.estado;
    }

    public int getIdTipoDocumento() {
        return idTipoDocumento;
    }

    public void setIdTipoDocumento(int idTipoDocumento) {
        this.idTipoDocumento = idTipoDocumento;
    }

    public String getNombre() {
        return nombre;
    }

    public boolean getEstado() {
        return estado;
    }

    public static class TipoDocumentoBuilder {
        private int idTipoDocumento;
        private String nombre;
        private boolean estado;

        public TipoDocumentoBuilder setIdTipoDocumento(int idTipoDocumento) {
            this.idTipoDocumento = idTipoDocumento;
            return this;
        }

        public TipoDocumentoBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public TipoDocumentoBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public TipoDocumento build() {
            return new TipoDocumento(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles del tipo de documento:\n" +
                "ID Tipo Documento: " + idTipoDocumento + "\n" +
                "Nombre: " + nombre + "\n" +
                "Estado: " + (estado ? "Activo" : "Inactivo");
    }
}