package Clases;

public class Departamento {

    private int idDepartamento;
    private String nombre;

    private Departamento(DepartamentoBuilder builder) {
        this.idDepartamento = builder.idDepartamento;
        this.nombre = builder.nombre;
    }

    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public static class DepartamentoBuilder {
        private int idDepartamento;
        private String nombre;

        public DepartamentoBuilder setIdDepartamento(int idDepartamento) {
            this.idDepartamento = idDepartamento;
            return this;
        }

        public DepartamentoBuilder setNombre(String nombre) {
            this.nombre = nombre;
            return this;
        }

        public Departamento build() {
            return new Departamento(this);
        }
    }

    @Override
    public String toString() {
        return "Detalles del departamento:\n" +
                "ID Departamento: " + idDepartamento + "\n" +
                "Nombre: " + nombre;
    }
}