package Produtos;

public abstract class Produto {
    protected float preco;
    protected String nome;
    protected String tipo;
    protected int estoque;
    protected String IDProduto;

    public Produto(float preco, String nome, String tipo, int estoque, String IDProduto) {
        this.preco = preco;
        this.nome = nome;
        this.tipo = tipo;
        this.estoque = estoque;
        this.IDProduto = IDProduto;
    }

    public abstract void Venda(int quantidade);
    public abstract void AumentarEstoque (int quantidade);

}
