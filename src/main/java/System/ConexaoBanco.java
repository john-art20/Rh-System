package System;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConexaoBanco {
    private static final String URL = "jdbc:mysql://localhost:3306/mydb?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
    private static final String USUARIO = "root";
    private static final String SENHA = "@Joao1710";

    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Driver do MySQL não encontrado: " + e.getMessage());
        }
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
    
  
}
