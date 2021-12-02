package ManipulacaoBancoDeDados;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import ClassesUtilitarias.Venda;
import ClassesUtilitarias.VendaAnonima;
import ClassesUtilitarias.VendaIdentificada;
import ClassesUtilitarias.Cliente;
import ClassesUtilitarias.EOFIndicatorClass;
import ClassesUtilitarias.IOMaster;
import ClassesUtilitarias.ObjectIOMaster;

public class ControleDeVendas {
	private static final String caminhoPastaBancoDeDados = "src" + System.getProperty("file.separator") + "BancoDeDados";
	private static final String caminhoBancoDeDados = caminhoPastaBancoDeDados + System.getProperty("file.separator") + "RegistroDasVendas.ser";
	private static final String caminhoBancoDeDadosTemp = caminhoPastaBancoDeDados + System.getProperty("file.separator") + "RegistroDasVendasTemp.ser";

	private static void verificarRegistroDeVendas(String caminho) throws IOException{
		File verificarRegistro = new File(caminhoBancoDeDados);
		if(verificarRegistro.length() == 0) {
			ObjectIOMaster registroDeVendas = new ObjectIOMaster(caminhoBancoDeDados, 'w');
			registroDeVendas.escrever(new EOFIndicatorClass());
			registroDeVendas.fecharArquivos();
		}
	}
	
	public static void cadastrarVenda(Venda novaVenda) throws IOException {
		verificarRegistroDeVendas(caminhoBancoDeDados);

		ObjectIOMaster registroDeVendas = new ObjectIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		Object venda;

		try{
			while(!((venda = registroDeVendas.ler()) instanceof EOFIndicatorClass))
				registroDeVendas.escrever(venda);

			novaVenda.setDataDaCompra(new Date());
			
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

	public static void listarVendas() throws IOException {
		ObjectIOMaster registroDeVendas = new ObjectIOMaster(caminhoBancoDeDados, 'r');

		Collection<Venda> vendas = new ArrayList<Venda>();

		Object venda;

		try{
			while(!((venda = registroDeVendas.ler()) instanceof EOFIndicatorClass))
				vendas.add((Venda) venda);

			for(Venda v : vendas)
				v.imprimirNotaFiscal();
				
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally{
			registroDeVendas.fecharArquivos();
		}
	}

	public static void listarVendasPorCliente(String CPF) throws IOException{
		ObjectIOMaster registroDeVendas = new ObjectIOMaster(caminhoBancoDeDados, 'r');

		Collection<Venda> vendas = new ArrayList<Venda>();

		Object venda;

		try{
			while(!((venda = registroDeVendas.ler()) instanceof EOFIndicatorClass)) {
				if(((Venda)venda).getCliente().getCPF().equals(CPF))
					vendas.add((Venda)venda);
			}

			for(Venda v : vendas)
				v.imprimirNotaFiscal();
				
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally{
			registroDeVendas.fecharArquivos();
		}
	}

	public static void main(String[] args) {
		try{
			Venda venda1 = new VendaAnonima();
			venda1.adicionarProdutoAoCarrinho("XBOX Series X", 10);
			cadastrarVenda(venda1);

			Cliente cliente1 = new Cliente("Wellington Felipe", "424.844.250-74", "49090-073");
			Venda venda2 = new VendaIdentificada(cliente1);
			venda2.adicionarProdutoAoCarrinho("Razer Viper Mini", 1);
			venda2.adicionarProdutoAoCarrinho("Warrior Kaden", 1);
			venda2.adicionarProdutoAoCarrinho("Rainbow Six Siege", 1);
			cadastrarVenda(venda2);

			Cliente cliente2 = new Cliente("Matheus Miller", "425.332.960-82", "68908-351");
			Venda venda3 = new VendaIdentificada(cliente2);
			venda3.adicionarProdutoAoCarrinho("PS5", 1);
			venda3.adicionarProdutoAoCarrinho("God Of War", 1);
			venda3.adicionarProdutoAoCarrinho("The Last Of Us", 1);
			cadastrarVenda(venda3);

			Venda venda4 = new VendaAnonima();
			venda4.adicionarProdutoAoCarrinho("The Witcher 3", 20);
			venda4.adicionarProdutoAoCarrinho("Redragon Kumara", 20);
			cadastrarVenda(venda4);

			listarVendas();
			System.out.println("**********************************************");
			listarVendasPorCliente("42484425074");
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}
	}
}
