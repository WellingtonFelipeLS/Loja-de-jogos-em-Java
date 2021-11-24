package Produtos;

public class Jogo extends Produto{

    public Jogo(float preco, String nome, String tipo, int estoque, String IDProduto) {
        super(preco, nome, tipo, estoque, IDProduto);
    }

    @Override
    public void Venda(int quantidade) {
        if (estoque >= quantidade) {
            estoque -= quantidade;
        } else {
            System.out.println("Estoque inferior à quantidade informada. Estoque = " + estoque);
        }
    }

    @Override
    public void AumentarEstoque(int quantidade) {
        estoque += quantidade;
    }
}
