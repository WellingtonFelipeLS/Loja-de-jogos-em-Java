package ManipulacaoBancoDeDados;

import java.io.IOException;

import java.util.Map;

import RegrasDeNegocio.Venda;
import RegrasDeNegocio.Cliente;

import ClassesUtilitarias.EOFIndicatorClass;
import ClassesUtilitarias.IOMaster;
import ClassesUtilitarias.ObjectIOMaster;


public class ControleDeVendas {
	private String caminhoBancoDeDados;
	private String caminhoBancoDeDadosTemp;
	private ControleDeEstoque controleDeEstoque;
	
	public ControleDeVendas(ControleDeEstoque controleDeEstoque, String caminhoPastaBancoDeDados) {
		this.controleDeEstoque = controleDeEstoque;
		this.caminhoBancoDeDados = caminhoPastaBancoDeDados + System.getProperty("file.separator") + "RegistroDasVendas.ser";
		this.caminhoBancoDeDadosTemp = caminhoPastaBancoDeDados + System.getProperty("file.separator") + "RegistroDasVendasTemp.ser";
	}

	public void cadastrarVenda(Venda novaVenda) throws IOException {
		ObjectIOMaster.verificarArquivo(caminhoBancoDeDados);

		ObjectIOMaster registroDeVendas = new ObjectIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		Object venda;
		Map<String, Integer> carrinho = novaVenda.getCarrinho();

		for(String nomeDoProduto : carrinho.keySet())
			controleDeEstoque.retirarQntDoEstoque(nomeDoProduto, carrinho.get(nomeDoProduto));

		try{
			while(!((venda = registroDeVendas.ler()) instanceof EOFIndicatorClass))
				registroDeVendas.escrever(venda);

			novaVenda.setDataDaCompra();
			
			registroDeVendas.escrever(novaVenda);

			registroDeVendas.escrever(new EOFIndicatorClass());
			registroDeVendas.fecharArquivos();
			ObjectIOMaster.renomearESobrescreverArquivo(caminhoBancoDeDados, caminhoBancoDeDadosTemp);
				
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();

			registroDeVendas.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	public String listarVendas() throws IOException {
		ObjectIOMaster registroDeVendas = new ObjectIOMaster(caminhoBancoDeDados, 'r');

		Object venda;
		StringBuilder notasFiscais = new StringBuilder();

		try{
			while(!((venda = registroDeVendas.ler()) instanceof EOFIndicatorClass))
				notasFiscais.append(((Venda) venda).imprimirNotaFiscal(controleDeEstoque));
				
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally{
			registroDeVendas.fecharArquivos();
		}

		return notasFiscais.toString();
	}

	public String listarVendasPorCliente(String CPF) throws IOException{
		ObjectIOMaster registroDeVendas = new ObjectIOMaster(caminhoBancoDeDados, 'r');

		Object venda;
		StringBuilder notasFiscais = new StringBuilder();

		try{

			while(!((venda = registroDeVendas.ler()) instanceof EOFIndicatorClass)) 
				if(((Venda)venda).getCliente().getCPF().equals(CPF))
					notasFiscais.append(((Venda) venda).imprimirNotaFiscal(controleDeEstoque));
			
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally{
			registroDeVendas.fecharArquivos();
		}

		return notasFiscais.toString();
	}

	public static void main(String[] args) {
		try{
			ControleDeVendas controleDeVendas = new ControleDeVendas(new ControleDeEstoque("src" + System.getProperty("file.separator") + "BancoDeDados"), "src" + System.getProperty("file.separator") + "BancoDeDados");

			Venda venda1 = new Venda();
			venda1.adicionarProdutoAoCarrinho("XBOX Series X", 10);
			controleDeVendas.cadastrarVenda(venda1);

			Cliente cliente1 = new Cliente("Wellington Felipe", "424.844.250-74", "49090-073");
			Venda venda2 = new Venda();
			venda2.setCliente(cliente1);
			venda2.adicionarProdutoAoCarrinho("Razer Viper Mini", 1);
			venda2.adicionarProdutoAoCarrinho("Warrior Kaden", 1);
			venda2.adicionarProdutoAoCarrinho("Rainbow Six Siege", 1);
			controleDeVendas.cadastrarVenda(venda2);

			Cliente cliente2 = new Cliente("Matheus Miller", "425.332.960-82", "68908-351");
			Venda venda3 = new Venda();
			venda3.setCliente(cliente2);
			venda3.adicionarProdutoAoCarrinho("PS5", 1);
			venda3.adicionarProdutoAoCarrinho("God Of War", 1);
			venda3.adicionarProdutoAoCarrinho("The Last Of Us", 1);
			controleDeVendas.cadastrarVenda(venda3);

			Venda venda4 = new Venda();
			venda4.adicionarProdutoAoCarrinho("The Witcher 3", 20);
			venda4.adicionarProdutoAoCarrinho("Redragon Kumara", 20);
			controleDeVendas.cadastrarVenda(venda4);

			controleDeVendas.listarVendas();
			System.out.println("**********************************************");
			controleDeVendas.listarVendasPorCliente("42484425074");
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
