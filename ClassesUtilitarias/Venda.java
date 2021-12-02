package ClassesUtilitarias;

import java.io.IOException;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

import ManipulacaoBancoDeDados.ControleDeEstoque;

import java.util.Hashtable;

public abstract class Venda implements Serializable{
	private Cliente cliente;
	private String idVenda;
	private Date dataDaCompra;
	private Map<String, Integer> carrinho;

	public Venda(Cliente cliente) {
		this.cliente = cliente;
		this.carrinho = new Hashtable<String, Integer>();
		this.idVenda = IdGenerator.gerarId();
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setDataDaCompra(Date dataDaCompra) {
		this.dataDaCompra = dataDaCompra;
	}

	/*private final float calcularValorTotal(){
		int valorTotal = 0;

		for(String nomeDoProduto : carrinho.keySet())
			valorTotal += carrinho.get(nomeDoProduto);
		
		return valorTotal;
	}*/

	public final void adicionarProdutoAoCarrinho(String nomeDoProduto, int qnt) {
		carrinho.put(nomeDoProduto, Integer.valueOf(qnt));
	}

	public final void retirarProdutoDoCarrinho(String nomeDoProduto, int qnt) {
		carrinho.remove(nomeDoProduto, Integer.valueOf(qnt));
	}

	private void imprimirSeparadorDeNotaFiscal(int n) {
		for(int i = 0; i < n; i++)
			System.out.print("=");
		System.out.println();
	}

	private void imprimirSeparadorDeSecao(int n) {
		for(int i = 0; i < n; i++)
			System.out.print("-");
		System.out.println();
	}

	public void imprimirNotaFiscal() throws IOException{
		int n = 60;
		imprimirSeparadorDeNotaFiscal(n);

		cliente.imprimirInformacoes();

		System.out.println("Data: " + dataDaCompra);

		System.out.println("ID: " + idVenda);

		imprimirSeparadorDeSecao(n);

		System.out.printf("%-20s %5s %10s %10s\n", "Nome", "Qnt.", "Preco Unit.", "Preco Tot.");

		int qntTotal = 0;
		float precoTotal = 0f;
		for(String nomeDoProduto : carrinho.keySet()) {
			int qntVendidaDoProduto = carrinho.get(nomeDoProduto).intValue();
			float precoUnitario = ControleDeEstoque.buscarPrecoNoEstoque(nomeDoProduto);
			System.out.printf("%-20s %5d %10.2f %10.2f\n", nomeDoProduto, qntVendidaDoProduto, precoUnitario, precoUnitario * qntVendidaDoProduto);

			qntTotal += qntVendidaDoProduto;
			precoTotal += precoUnitario * qntVendidaDoProduto;
		}

		imprimirSeparadorDeSecao(n);

		System.out.printf("%-20s %5d %10s %10.2f\n", "Total:", qntTotal, " ", precoTotal);

		imprimirSeparadorDeNotaFiscal(n);
	}
}