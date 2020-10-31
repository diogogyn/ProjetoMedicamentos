package conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FabricaConexao {

    private static Connection connection = null;

    public FabricaConexao() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/rfid", "root",
                "root");
    }

    public static Connection getConnection() throws SQLException {

        if (connection == null) {
            try {
                new FabricaConexao();
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(FabricaConexao.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }

        return connection;
    }
}
