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

	public String[] getProdutosDoCarrinho() {
		String[] nomesDosProdutos = new String[carrinho.size()];
		carrinho.keySet().toArray(nomesDosProdutos);
		return nomesDosProdutos;
	}

	public Map<String, Integer> getCarrinho() {
		return carrinho;
	}

	public void modificarQntDoProdutoNoCarrinho(String nomeDoProduto, int qnt) {
		carrinho.replace(nomeDoProduto, qnt);
	}

	public void adicionarUnidade(String nomeDoProduto) {
		carrinho.replace(nomeDoProduto, carrinho.get(nomeDoProduto) + 1);
	}

	public void retirarUnidade(String nomeDoProduto) {
		carrinho.replace(nomeDoProduto, carrinho.get(nomeDoProduto) - 1);
	}

	public void adicionarProdutoAoCarrinho(String nomeDoProduto, int qnt) {
		if(carrinho.keySet().contains(nomeDoProduto))
			carrinho.replace(nomeDoProduto, carrinho.get(nomeDoProduto) + qnt);
		else
			carrinho.put(nomeDoProduto, qnt);
	}

	public void retirarProdutoDoCarrinho(String nomeDoProduto) {
		carrinho.remove(nomeDoProduto);
	}

	private String imprimirSeparadorDeNotaFiscal(int n) {
		StringBuilder separador = new StringBuilder(n);
		for(int i = 0; i < n; i++)
			separador.append('=');
		separador.append('\n');

		return separador.toString();
	}

	private String imprimirSeparadorDeSecao(int n) {
		StringBuilder separador = new StringBuilder(n);
		for(int i = 0; i < n; i++)
			separador.append('-');
		separador.append('\n');

		return separador.toString();
	}

	public String imprimirNotaFiscal() throws IOException{
		int n = 60;
		StringBuilder notaFiscal = new StringBuilder();
		notaFiscal.append(imprimirSeparadorDeNotaFiscal(n));

		notaFiscal.append(cliente.imprimirInformacoes());

		notaFiscal.append("Data: " + dataDaCompra + '\n');

		notaFiscal.append("ID: " + idVenda + '\n');

		notaFiscal.append(imprimirSeparadorDeSecao(n));

		notaFiscal.append(String.format("%-20s %5s %10s %10s\n", "Nome", "Qnt.", "Preco Unit.", "Preco Tot."));

		int qntTotal = 0;
		float precoTotal = 0f;
		for(String nomeDoProduto : carrinho.keySet()) {
			int qntVendidaDoProduto = carrinho.get(nomeDoProduto).intValue();
			float precoUnitario = ControleDeEstoque.procurarProdutoNoEstoque(nomeDoProduto).getPreco();
			notaFiscal.append(String.format("%-20s %5d %10.2f %10.2f\n", nomeDoProduto, qntVendidaDoProduto, precoUnitario, precoUnitario * qntVendidaDoProduto));

			qntTotal += qntVendidaDoProduto;
			precoTotal += precoUnitario * qntVendidaDoProduto;
		}

		notaFiscal.append(imprimirSeparadorDeSecao(n));

		notaFiscal.append(String.format("%-20s %5d %10s %10.2f\n", "Total:", qntTotal, " ", precoTotal));

		notaFiscal.append(imprimirSeparadorDeNotaFiscal(n));

		return notaFiscal.toString();
	}
}