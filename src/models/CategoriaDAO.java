package models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static config.Config.*;

public class CategoriaDAO {
    static {
        try {

            Class.forName(config.Config.BD_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("### FALHA CRÍTICA: Driver JDBC do PostgreSQL não encontrado! ###");
            e.printStackTrace();
        }
    }

    //Busca categoria por id
    public Categoria obterPorId(int idCategoria) {
        Categoria categoria = null;
        String sql = "SELECT id_categoria, nome FROM categoria WHERE id_categoria = ?;";
        
        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setInt(1, idCategoria);
            
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    categoria = new Categoria();
                    categoria.setId_cat(resultSet.getInt("id_categoria"));
                    categoria.setNome(resultSet.getString("nome"));
                }
            }
        } catch (SQLException ex) {
            System.err.println("Erro ao obter categoria por ID: " + ex.getMessage());
        }
        return categoria;
    }

}
