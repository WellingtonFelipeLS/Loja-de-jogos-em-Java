package RegrasDeNegocio;

import java.io.IOException;
import java.io.Serializable;

import java.util.Date;
import java.util.Map;

import ManipulacaoBancoDeDados.ControleDeEstoque;
import RegrasDeNegocio.Produtos.Produto;

import ClassesUtilitarias.IdGenerator;

import java.util.Hashtable;

public class Venda implements Serializable{
	private Cliente cliente;
	private String idVenda;
	private Date dataDaCompra;
	private Map<String, Integer> carrinho;

	public Venda() {
		this.cliente = new Cliente();
		this.carrinho = new Hashtable<String, Integer>();
		this.idVenda = IdGenerator.gerarId();
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setDataDaCompra(Date dataDaCompra) {
		this.dataDaCompra = dataDaCompra;
	}

	public void limparCarrinho() {
		this.carrinho.clear();
	}

	public void limparDadosDaVenda() {
		this.cliente = new Cliente();
		limparCarrinho();
		this.idVenda = IdGenerator.gerarId();
	}

	public String[][] getInfoParaOCarrinho() {
		Produto produto;
		String[] infoProduto;
		String[][] infoCarrinho = new String[carrinho.size()][];
		
		try{
			int cont = 0;
			for(String nome : carrinho.keySet()) {
				produto = ControleDeEstoque.procurarProdutoNoEstoque(nome);

				infoProduto = new String[4];
				infoProduto[0] = nome;
				infoProduto[1] = String.valueOf(carrinho.get(nome));
				infoProduto[2] = String.valueOf(produto.getPreco());
				infoProduto[3] = String.valueOf(carrinho.get(nome) * produto.getPreco());

				infoCarrinho[cont] = infoProduto;
				cont++;
			}
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
		
		return infoCarrinho;
	}

	public Map<String, Integer> getCarrinho() {
		return carrinho;
	}

	public void adicionarProdutoAoCarrinho(String nomeDoProduto, int qnt) {
		if(carrinho.keySet().contains(nomeDoProduto))
			carrinho.replace(nomeDoProduto, carrinho.get(nomeDoProduto) + qnt);
		else
			carrinho.put(nomeDoProduto, qnt);
	}

	public void retirarQntDoProdutoDoCarrinho(String nomeDoProduto, int qnt) {
		if(carrinho.keySet().contains(nomeDoProduto))
			carrinho.replace(nomeDoProduto, carrinho.get(nomeDoProduto) - qnt);
	}

	public void retirarProdutoDoCarrinho(String nomeDoProduto) {
		carrinho.remove(nomeDoProduto);
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
			float precoUnitario = ControleDeEstoque.procurarProdutoNoEstoque(nomeDoProduto).getPreco();
			System.out.printf("%-20s %5d %10.2f %10.2f\n", nomeDoProduto, qntVendidaDoProduto, precoUnitario, precoUnitario * qntVendidaDoProduto);

			qntTotal += qntVendidaDoProduto;
			precoTotal += precoUnitario * qntVendidaDoProduto;
		}

		imprimirSeparadorDeSecao(n);

		System.out.printf("%-20s %5d %10s %10.2f\n", "Total:", qntTotal, " ", precoTotal);

		imprimirSeparadorDeNotaFiscal(n);
	}
}