package ClasesDAO;

import Clases.Departamento;
import ConexionDB.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartamentoDAO {

    private Connection connection;
    private static DepartamentoDAO instance;

    public DepartamentoDAO() {
        this.connection = DatabaseConnection.getInstance().getConnection();
    }

    public static DepartamentoDAO getInstance() {
        if (instance == null) {
            instance = new DepartamentoDAO();
        }
        return instance;
    }

    public void agregarDepartamento(Departamento departamento) throws SQLException {
        String sql = "INSERT INTO proyecto.departamentos (nombre) VALUES (?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, departamento.getNombre());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                departamento.setIdDepartamento(generatedKeys.getInt(1));
            }
        }
    }

    public void actualizarDepartamento(Departamento departamento) throws SQLException {
        String sql = "UPDATE proyecto.departamentos SET nombre = ? WHERE id_departamento = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, departamento.getNombre());
            stmt.setInt(2, departamento.getIdDepartamento());
            stmt.executeUpdate();
        }
    }

    public void eliminarDepartamento(int idDepartamento) throws SQLException {
        String sql = "DELETE FROM proyecto.departamentos WHERE id_departamento = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDepartamento);
            stmt.executeUpdate();
        }
    }

    public Departamento obtenerDepartamentoPorId(int idDepartamento) throws SQLException {
        String sql = "SELECT * FROM proyecto.departamentos WHERE id_departamento = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idDepartamento);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Departamento.DepartamentoBuilder()
                        .setIdDepartamento(rs.getInt("id_departamento"))
                        .setNombre(rs.getString("nombre"))
                        .build();
            } else {
                return null;
            }
        }
    }

    public List<Departamento> listarDepartamentos() throws SQLException {
        List<Departamento> departamentos = new ArrayList<>();
        String sql = "SELECT * FROM proyecto.departamentos";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Departamento departamento = new Departamento.DepartamentoBuilder()
                        .setIdDepartamento(rs.getInt("id_departamento"))
                        .setNombre(rs.getString("nombre"))
                        .build();
                departamentos.add(departamento);
            }
        }
        return departamentos;
    }
}

