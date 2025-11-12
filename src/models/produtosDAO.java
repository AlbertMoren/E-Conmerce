public class produtosDAO {
    static {
        try {

            Class.forName(config.Config.BD_DRIVER);
        } catch (ClassNotFoundException e) {
            System.err.println("### FALHA CRÍTICA: Driver JDBC do PostgreSQL não encontrado! ###");
            e.printStackTrace();
        }
    }

    public List<Produtos> obterTodos() {
        List<Produtos> resultado = new ArrayList<>();
        String sql = "SELECT * FROM produtos;";


        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Produtos produto = new Produto();
                produto.setId(resultSet.getInt("id"));
                produto.setNome(resultSet.getString("descricao"));
                produto.setEmail(resultSet.getString("preco"));
                produto.setSenha(resultSet.getString("quantidade"));
                resultado.add(produto);
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao listar usuários: " + ex.getMessage());
            ex.printStackTrace();

        }
        return resultado;
    }

    public Produtos obter(int id) {
        Produtos produto = null;
        String sql = "SELECT * FROM produtos WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    produto = new Produtos();
                    produto.setId(resultSet.getInt("id"));
                    produto.setNome(resultSet.getString("descricao"));
                    produto.setEmail(resultSet.getString("preco"));
                    produto.setSenha(resultSet.getString("quantidade"));
                    resultado.add(produto);
                }
            }

        } catch (SQLException ex) {
            System.err.println("Erro ao obter usuário: " + ex.getMessage());
            ex.printStackTrace();
        }
        return produto;
    }

    public boolean inserir(String descricao, Double preco, int quantidade) {
        String sql = "INSERT INTO produto (descricao, preco, quantidade) VALUES (?, ?, ?,?)";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, descricao);
            preparedStatement.setString(2, preco);
            preparedStatement.setString(3, quantidade);

            int linhasAfetadas = preparedStatement.executeUpdate();
            return linhasAfetadas == 1; 

        } catch (SQLException ex) {
            System.err.println("Erro ao inserir usuário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

    public static boolean remover(int id) {
        String sql = "DELETE FROM produto WHERE id = ?";

        try (Connection connection = DriverManager.getConnection(BD_URL, BD_USUARIO, BD_SENHA);
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, id);
            return (preparedStatement.executeUpdate() == 1);

        } catch (SQLException ex) {
            System.err.println("Erro ao remover usuário: " + ex.getMessage());
            ex.printStackTrace();
            return false;
        }
    }

}
