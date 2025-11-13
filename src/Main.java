import models.Usuario;
import models.UsuarioDAO;
import models.Produto;
import models.ProdutoDAO;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ProdutoDAO produtoDAo = new ProdutoDAO();

        // inserir produto
        System.out.println("Inserindo novo produto...");
        boolean inserido = produtoDAo.inserir("");
        System.out.println("prduto inserido? " + produtoDAo);
         /*
        // listar os usuarios
        System.out.println("\nListando usuários...");
        List<Usuario> usuarios = usuarioDAO.obterTodos();
        for (Usuario u : usuarios) {
            System.out.println(u);
        }

        // obter um usuário especifico
        System.out.println("\nBuscando usuário com ID = 2...");
        Usuario usuario = usuarioDAO.obter(2);
        if (usuario != null) {
            System.out.println("Encontrado: " + usuario);
        } else {
            System.out.println("Usuário não encontrado.");
        }
        */

    }
}
