package ClasesDAO;

import Clases.Departamento;
import Clases.Domicilio;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DomicilioDAO {

    private Connection connection;
    private static DomicilioDAO instance;

    private DomicilioDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static DomicilioDAO getInstance() {
        if (instance == null) {
            instance = new DomicilioDAO();
        }
        return instance;
    }

    public void agregarDomicilio(Domicilio domicilio) throws SQLException {
        String sql = "INSERT INTO proyecto.domicilios (id_departamento, ciudad, calle, num_puerta, piso, apartamento, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, domicilio.getDepartamento().getIdDepartamento());
            stmt.setString(2, domicilio.getCiudad());
            stmt.setString(3, domicilio.getCalle());
            stmt.setInt(4, domicilio.getNumeroPuerta());
            stmt.setInt(5, domicilio.getPiso());
            stmt.setString(6, domicilio.getApartamento());
            stmt.setBoolean(7, domicilio.getEstado());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                domicilio.setIdDomicilio(generatedKeys.getInt(1));
            }
        }
    }

    public void actualizarDomicilio(Domicilio domicilio) throws SQLException {
        String sql = "UPDATE proyecto.domicilios SET id_departamento = ?, ciudad = ?, calle = ?, num_puerta = ?, piso = ?, apartamento = ?, estado = ? WHERE id_domicilio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, domicilio.getDepartamento().getIdDepartamento());
            stmt.setString(2, domicilio.getCiudad());
            stmt.setString(3, domicilio.getCalle());
            stmt.setInt(4, domicilio.getNumeroPuerta());
            stmt.setInt(5, domicilio.getPiso());
            stmt.setString(6, domicilio.getApartamento());
            stmt.setBoolean(7, domicilio.getEstado());
            stmt.setInt(8, domicilio.getIdDomicilio());
            stmt.executeUpdate();
        }
    }

    public void eliminarDomicilio(int idDomicilio) throws SQLException {
        String sql = "UPDATE proyecto.domicilios SET estado = false WHERE id_domicilio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDomicilio);
            stmt.executeUpdate();
        }
    }

    public Domicilio obtenerDomicilioPorId(int idDomicilio) throws SQLException {
        String sql = "SELECT * FROM proyecto.domicilios WHERE id_domicilio = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDomicilio);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Departamento departamento = DepartamentoDAO.getInstance().obtenerDepartamentoPorId(rs.getInt("id_departamento"));
                return new Domicilio.DomicilioBuilder()
                        .setIdDomicilio(rs.getInt("id_domicilio"))
                        .setDepartamento(departamento)
                        .setCiudad(rs.getString("ciudad"))
                        .setCalle(rs.getString("calle"))
                        .setNumPuerta(rs.getInt("num_puerta"))
                        .setPiso(rs.getString("piso"))
                        .setApartamento(rs.getString("apartamento"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public List<Domicilio> listarDomicilios() throws SQLException {
        List<Domicilio> domicilios = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.domicilios";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Departamento departamento = DepartamentoDAO.getInstance().obtenerDepartamentoPorId(rs.getInt("id_departamento"));
                Domicilio domicilio = new Domicilio.DomicilioBuilder()
                        .setIdDomicilio(rs.getInt("id_domicilio"))
                        .setDepartamento(departamento)
                        .setCiudad(rs.getString("ciudad"))
                        .setCalle(rs.getString("calle"))
                        .setNumPuerta(rs.getInt("num_puerta"))
                        .setPiso(rs.getString("piso"))
                        .setApartamento(rs.getString("apartamento"))
                        .setEstado(rs.getBoolean("estado"))
                        .build();
                domicilios.add(domicilio);
            }
        }
        return domicilios;
    }
}

