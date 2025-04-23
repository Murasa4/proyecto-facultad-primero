package ConexionDB;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection instance;
    private static Connection connection;
    private static final String url ="jdbc:postgresql://localhost:5432/asur";
    private static final String usuario ="proyecto";
    private static final String contrasenia ="admin";

    private DatabaseConnection(){
        try{
            connection = DriverManager.getConnection(url,usuario,contrasenia);
        }catch (SQLException sqlException){
            sqlException.printStackTrace();
        }
    }

    public static DatabaseConnection getInstance(){
        if(instance == null){
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public static Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, usuario, contrasenia);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        return connection;
    }

    public void cerrarConexion() throws SQLException {
        if(connection != null && !connection.isClosed()){
            connection.close();
        }
    }
}