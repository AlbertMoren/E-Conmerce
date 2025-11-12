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
    public List<Categoria> obterTodos() {
        List<Produto> resultado = new ArrayList<>();
        String sql = "SELECT id, nome FROM Categoria";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_PRODUTO);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                categoria = new Categoria();
                categoria.setId(resultSet.getInt("id"));
                categoria.setNome(resultSet.getString("nome"));
                resultado.add(categoria);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar categorias: " + ex.getMessage());
            ex.printStackTrace();

        }
        return resultado;
    }
}
