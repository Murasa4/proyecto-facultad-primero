package Clases;

public class Domicilio {

    private int idDomicilio;
    private Departamento departamento;
    private String ciudad;
    private String calle;
    private int numeroPuerta;
    private int piso;
    private String apartamento;
    private boolean estado;

    private Domicilio(DomicilioBuilder builder) {
        this.idDomicilio = builder.idDomicilio;
        this.departamento = builder.departamento;
        this.ciudad = builder.ciudad;
        this.calle = builder.calle;
        this.numeroPuerta = builder.numPuerta;
        try {
            this.piso = Integer.parseInt(builder.piso);
        } catch (NumberFormatException e) {
            this.piso = -1; // Valor por defecto en caso de error de conversión
        }

        // this.piso = Integer.parseInt(builder.piso);
        this.apartamento = builder.apartamento;
        this.estado = builder.estado;
    }

    public int getIdDomicilio() {
        return idDomicilio;
    }

    public void setIdDomicilio(int idDomicilio) {
        this.idDomicilio = idDomicilio;
    }

    public Departamento getDepartamento() {
        return departamento;
    }

    public String getCiudad() {
        return ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumeroPuerta() {
        return numeroPuerta;
    }

    public int getPiso() {
        return piso;
    }

    public String getApartamento() {
        return apartamento;
    }

    public boolean getEstado() {
        return estado;
    }

    public static class DomicilioBuilder {
        private int idDomicilio;
        private Departamento departamento;
        private String ciudad;
        private String calle;
        private int numPuerta;
        private String piso;
        private String apartamento;
        private boolean estado;

        public DomicilioBuilder setIdDomicilio(int idDomicilio) {
            this.idDomicilio = idDomicilio;
            return this;
        }

        public DomicilioBuilder setDepartamento(Departamento departamento) {
            this.departamento = departamento;
            return this;
        }

        public DomicilioBuilder setCiudad(String ciudad) {
            this.ciudad = ciudad;
            return this;
        }

        public DomicilioBuilder setCalle(String calle) {
            this.calle = calle;
            return this;
        }

        public DomicilioBuilder setNumPuerta(int numPuerta) {
            this.numPuerta = numPuerta;
            return this;
        }

        public DomicilioBuilder setPiso(String piso) {
            this.piso = piso;
            return this;
        }

        public DomicilioBuilder setApartamento(String apartamento) {
            this.apartamento = apartamento;
            return this;
        }

        public DomicilioBuilder setEstado(boolean estado) {
            this.estado = estado;
            return this;
        }

        public Domicilio build() {
            return new Domicilio(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles del domicilio:\n" +
                "ID Domicilio: " + idDomicilio + "\n" +
                "Departamento: " + departamento.getNombre() + "\n" +
                "Ciudad: " + ciudad + "\n" +
                "Calle: " + calle + "\n" +
                "Número de Puerta: " + numeroPuerta + "\n" +
                // Usando -1 como valor por defecto
                "Piso: " + (piso != -1 ? piso : "N/A") + "\n" +
                "Apartamento: " + (apartamento != null ? apartamento : "N/A") + "\n" +
                "Estado: " + (estado ? "Activo" : "Inactivo");
    }
}