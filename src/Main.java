import models.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // é obrigatório inserir uma categoria no banco manualmente antes de testar
        povoarBD();
    }

    // inserindo dados iniciais no Banco de Dados
    public static void povoarBD() {
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        ProdutoDAO produtoDAO = new ProdutoDAO();

        // inserir usuarios
        usuarioDAO.inserir("Fulano", "ciclano@email.com", "123");
        usuarioDAO.inserir("Ciclano", "fulano@email.com", "321");

        categoriaDAO.inserir

        // inserir Produtos
        produtoDAO.inserir("Placa de Video RTX 4070", 4500.00, 10, idCatHardware);
        produtoDAO.inserir("Processador Ryzen 7 7800X3D", 2800.00, 15, idCatHardware);
        produtoDAO.inserir("Mouse Gamer Logitech G502", 350.00, 30, idCatPerifericos);
        produtoDAO.inserir("Teclado Mecânico Redragon", 299.90, 20, idCatPerifericos);
        produtoDAO.inserir("Monitor Ultrawide 29'", 1100.00, 8, idCatEletronicos);
    }

}