package models.carrinho;

import jakarta.servlet.http.Cookie;
import models.produto.Produto;
import models.produto.ProdutoDAO;
import java.util.ArrayList;
import java.util.List;

import static config.Constants.CARRINHO_COMPRAS_SEPARADOR_CAMPOS;
import static config.Constants.CARRINHO_COMPRAS_SEPARADOR_ITENS;

public final class CarrinhoCompras {
    private CarrinhoCompras() {
    }

    public static List<CarrinhoItem> obter(Cookie cookie) {
        List<CarrinhoItem> resultado = new ArrayList<>();
        if (cookie != null) {
            ProdutoDAO produtoDAO = new ProdutoDAO();
            String itens = cookie.getValue();
            if (itens != null && itens.trim().length() > 0) {
                if (itens.contains(CARRINHO_COMPRAS_SEPARADOR_ITENS)) {
                    String[] itensArray = itens.split(CARRINHO_COMPRAS_SEPARADOR_ITENS);
                    for (int i = 0; i < itensArray.length; i++) {
                        String[] camposArray = itensArray[i].split(CARRINHO_COMPRAS_SEPARADOR_CAMPOS);
                        Produto produto = produtoDAO.obter(Integer.parseInt(camposArray[0]));
                        CarrinhoItem item = new CarrinhoItem();
                        item.setProduto(produto);
                        item.setQuantidade(Integer.parseInt(camposArray[1]));
                        resultado.add(item);
                    }
                } else {
                    String[] camposArray = itens.split(CARRINHO_COMPRAS_SEPARADOR_CAMPOS);
                    Produto produto = produtoDAO.obter(Integer.parseInt(camposArray[0]));
                    CarrinhoItem item = new CarrinhoItem();
                    item.setProduto(produto);
                    item.setQuantidade(Integer.parseInt(camposArray[1]));
                    resultado.add(item);
                }
            }
        }
        return resultado;
    }

    public static Cookie addItem(int produtoId, int quantidade, Cookie cookie) {
        String itens = cookie.getValue();
        // se não houver nada na string do cookie (nenhum item está armazenado)
        if (itens == null || itens.trim().length() == 0) {
            cookie.setValue(produtoId + CARRINHO_COMPRAS_SEPARADOR_CAMPOS + quantidade);
        } else {
            // se tiver algum registro na string do cookie (deve ter pelo menos um item armazenado)
            String novaString = "";
            // se tem mais de um item
            if (itens.contains(CARRINHO_COMPRAS_SEPARADOR_ITENS)) {
                String[] itensArray = itens.split(CARRINHO_COMPRAS_SEPARADOR_ITENS);
                boolean encontrou = false;
                for (int i = 0; i < itensArray.length; i++) {
                    String[] camposArray = itensArray[i].split(CARRINHO_COMPRAS_SEPARADOR_CAMPOS);
                    if (Integer.parseInt(camposArray[0]) == produtoId) {
                        int novaQuantidade = Integer.parseInt(camposArray[1]) + quantidade;
                        novaString = novaString + produtoId + CARRINHO_COMPRAS_SEPARADOR_CAMPOS + novaQuantidade + CARRINHO_COMPRAS_SEPARADOR_ITENS;
                        encontrou = true;
                    } else {
                        novaString = novaString + camposArray[0] + CARRINHO_COMPRAS_SEPARADOR_CAMPOS + camposArray[1] + CARRINHO_COMPRAS_SEPARADOR_ITENS;
                    }
                }
                if (!encontrou) {
                    novaString = novaString + produtoId + CARRINHO_COMPRAS_SEPARADOR_CAMPOS + quantidade + CARRINHO_COMPRAS_SEPARADOR_ITENS;
                }
            } else {
                // se só existe um item
                String[] camposArray = itens.split(CARRINHO_COMPRAS_SEPARADOR_CAMPOS);
                boolean encontrou = false;
                if (Integer.parseInt(camposArray[0]) == produtoId) {
                    int novaQuantidade = Integer.parseInt(camposArray[1]) + quantidade;
                    novaString = novaString + produtoId + CARRINHO_COMPRAS_SEPARADOR_CAMPOS + novaQuantidade + CARRINHO_COMPRAS_SEPARADOR_ITENS;
                    encontrou = true;
                } else {
                    novaString = novaString + camposArray[0] + CARRINHO_COMPRAS_SEPARADOR_CAMPOS + camposArray[1] + CARRINHO_COMPRAS_SEPARADOR_ITENS;
                }
                if (!encontrou) {
                    novaString = novaString + produtoId + CARRINHO_COMPRAS_SEPARADOR_CAMPOS + quantidade + CARRINHO_COMPRAS_SEPARADOR_ITENS;
                }
            }
            if (novaString.endsWith(CARRINHO_COMPRAS_SEPARADOR_ITENS)) {
                novaString = novaString.substring(0, novaString.length() - 1);
            }
            cookie.setValue(novaString);
        }
        return cookie;
    }

    public static Cookie removerItem(int produtoId, Cookie cookie) {
        String itens = cookie.getValue();
        if (itens == null || itens.trim().length() == 0) {
            cookie.setValue("");
        } else {
            // se tiver algum registro na string do cookie (deve ter pelo menos um item armazenado)
            String novaString = "";
            // se tem mais de um item
            if (itens.contains(CARRINHO_COMPRAS_SEPARADOR_ITENS)) {
                String[] itensArray = itens.split(CARRINHO_COMPRAS_SEPARADOR_ITENS);
                for (int i = 0; i < itensArray.length; i++) {
                    String[] camposArray = itensArray[i].split(CARRINHO_COMPRAS_SEPARADOR_CAMPOS);
                    if (Integer.parseInt(camposArray[0]) != produtoId) {
                        novaString = novaString + camposArray[0] + CARRINHO_COMPRAS_SEPARADOR_CAMPOS + camposArray[1] + CARRINHO_COMPRAS_SEPARADOR_ITENS;
                    }
                }
            } else {
                // se só existe um item
                String[] camposArray = itens.split(CARRINHO_COMPRAS_SEPARADOR_CAMPOS);
                if (Integer.parseInt(camposArray[0]) == produtoId) {
                    novaString = "";
                } else {
                    novaString = novaString + camposArray[0] + CARRINHO_COMPRAS_SEPARADOR_CAMPOS + camposArray[1] + CARRINHO_COMPRAS_SEPARADOR_ITENS;
                }
            }
            if (novaString.endsWith(CARRINHO_COMPRAS_SEPARADOR_ITENS)) {
                novaString = novaString.substring(0, novaString.length() - 1);
            }
            cookie.setValue(novaString);
        }
        return cookie;
    }

    public static Cookie atualizarQuantidade(int produtoId, int novaQuantidade, Cookie cookie) {
        if (novaQuantidade <= 0) {
            return removerItem(produtoId, cookie);
        }

        String itens = cookie.getValue();
        if (itens == null || itens.trim().isEmpty()) {
            return cookie;
        }

        String novaString = "";
        if (itens.contains(CARRINHO_COMPRAS_SEPARADOR_ITENS)) {
            String[] itensArray = itens.split(CARRINHO_COMPRAS_SEPARADOR_ITENS);
            for (int i = 0; i < itensArray.length; i++) {
                String[] camposArray = itensArray[i].split(CARRINHO_COMPRAS_SEPARADOR_CAMPOS);
                int id = Integer.parseInt(camposArray[0]);
                int qtd = Integer.parseInt(camposArray[1]);

                if (id == produtoId) {
                    qtd = novaQuantidade;
                }
                novaString = novaString + id + CARRINHO_COMPRAS_SEPARADOR_CAMPOS + qtd + CARRINHO_COMPRAS_SEPARADOR_ITENS;
            }
        } else {
            String[] camposArray = itens.split(CARRINHO_COMPRAS_SEPARADOR_CAMPOS);
            int id = Integer.parseInt(camposArray[0]);
            int qtd = Integer.parseInt(camposArray[1]);

            if (id == produtoId) {
                qtd = novaQuantidade;
            }
            novaString = id + CARRINHO_COMPRAS_SEPARADOR_CAMPOS + qtd + CARRINHO_COMPRAS_SEPARADOR_ITENS;
        }

        if (novaString.endsWith(CARRINHO_COMPRAS_SEPARADOR_ITENS)) {
            novaString = novaString.substring(0, novaString.length() - 1);
        }
        cookie.setValue(novaString);
        return cookie;
    }

}
