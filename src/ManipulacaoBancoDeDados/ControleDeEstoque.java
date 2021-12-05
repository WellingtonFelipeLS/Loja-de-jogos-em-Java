package ManipulacaoBancoDeDados;

import RegrasDeNegocio.Produtos.*;
import ExceptionsCustomizadas.CadastroException;
import ExceptionsCustomizadas.EstoqueException;

import ClassesUtilitarias.EOFIndicatorClass;
import ClassesUtilitarias.IOMaster;
import ClassesUtilitarias.ObjectIOMaster;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class ControleDeEstoque {
	private static final String caminhoPastaBancoDeDados = "src" + System.getProperty("file.separator") + "BancoDeDados";
	private static final String caminhoBancoDeDados = caminhoPastaBancoDeDados + System.getProperty("file.separator") + "Estoque.ser";
	private static final String caminhoBancoDeDadosTemp = caminhoPastaBancoDeDados + System.getProperty("file.separator") + "EstoqueTemp.ser";

	public static void cadastrarProdutoNoEstoque(Produto novoProduto) throws IOException {
		ObjectIOMaster.verificarArquivo(caminhoBancoDeDados);

		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		Object produto;

		try {
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)){
				if(novoProduto.equals((Produto)produto))
					throw new CadastroException();
				
				estoque.escrever(produto);
			}

			estoque.escrever(novoProduto);

			estoque.escrever(new EOFIndicatorClass());

			estoque.fecharArquivos();

			ObjectIOMaster.renomearESobrescreverArquivo(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		}catch(CadastroException ce) {
			System.err.println("Produto de nome " + novoProduto.getNome() + " já cadastrado.");
			
			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();

			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	public static void reCadastrarProdutoNoEstoque(Produto novoProduto) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		Object produto;

		try {
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)){
	
				if(novoProduto.equals((Produto)produto)) {
					if(!novoProduto.getCadastroAtivo()){
						estoque.escrever(novoProduto);
					}else
						throw new CadastroException("Produto já cadastrado.");
				}else
					estoque.escrever(produto);
			}

			estoque.escrever(new EOFIndicatorClass());

			estoque.fecharArquivos();

			ObjectIOMaster.renomearESobrescreverArquivo(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		}catch(CadastroException ce){
			System.err.println("Produto já cadastrado.");
			
			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();

			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	public static void excluirProduto(String nomeDoProduto) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		Object produto;

		try {
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)){

				if(((Produto)produto).getNome().equals(nomeDoProduto)) {
					if(((Produto)produto).getCadastroAtivo())
						throw new CadastroException("Produto já excluído.");
					else{
						((Produto)produto).setCadastroAtivo(false);
						estoque.escrever(produto);
					}
				}else
					estoque.escrever(produto);
			}

			estoque.escrever(new EOFIndicatorClass());

			estoque.fecharArquivos();

			ObjectIOMaster.renomearESobrescreverArquivo(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		}catch(CadastroException ce){
			System.err.println("Produto já excluído.");
			
			estoque.fecharArquivos();

			ObjectIOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();

			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	public static Produto procurarProdutoNoEstoque(String nomeDoProduto) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, 'r');

		Object produto;

		try {
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)){

				if(((Produto)produto).getNome().equals(nomeDoProduto)) {
					if(((Produto)produto).getCadastroAtivo()){
						estoque.fecharArquivos();
						return (Produto)produto;
					}else
						throw new CadastroException("Produto excluído.");
			}
		}
			throw new EstoqueException("Produto não encontrado no estoque.");

		}catch(CadastroException ce) {
			ce.printStackTrace();
		}catch(EstoqueException ee) {
			ee.printStackTrace();
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally {
			estoque.fecharArquivos();
		}

		return null;
	}

	public static void setQntNoEstoque(String nomeDoProduto, int qnt) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		Object produto;

		try {
			if(qnt < 0)
				throw new EstoqueException("A quantidade no estoque não pode ser negativa.");

			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)){

				if(((Produto)produto).getNome().equals(nomeDoProduto)) {
					if(((Produto)produto).getCadastroAtivo()) {
						((Produto)produto).setQntNoEstoque(qnt);
						estoque.escrever(produto);
					}
					else
						throw new CadastroException("Produto excluído. Recadastre o produto para modificar sua quantidade.");
				}else
					estoque.escrever(produto);
			}

			estoque.escrever(new EOFIndicatorClass());

			estoque.fecharArquivos();

			ObjectIOMaster.renomearESobrescreverArquivo(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		}catch(CadastroException ce) {
			ce.printStackTrace();
			
			estoque.fecharArquivos();

			ObjectIOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}catch(EstoqueException ee) {
			ee.printStackTrace();
			
			estoque.fecharArquivos();

			ObjectIOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();

			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	private static void modificarQntNoEstoque(String nomeDoProduto, int qnt) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		Object produto;

		try {

			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)){

				if(((Produto)produto).getNome().equals(nomeDoProduto)) {
					if(((Produto)produto).getCadastroAtivo()) {

						if(qnt + ((Produto)produto).getQntNoEstoque() < 0) 
							throw new EstoqueException("A quantidade no estoque não pode ser negativa.");

						((Produto)produto).setQntNoEstoque(qnt + ((Produto)produto).getQntNoEstoque());
						estoque.escrever(produto);
					}
					else
						throw new CadastroException("Produto excluído. Recadastre o produto para modificar sua quantidade.");
				}else
					estoque.escrever(produto);
			}

			estoque.escrever(new EOFIndicatorClass());

			estoque.fecharArquivos();

			ObjectIOMaster.renomearESobrescreverArquivo(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		}catch(CadastroException ce) {
			ce.printStackTrace();
			
			estoque.fecharArquivos();

			ObjectIOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}catch(EstoqueException ee) {
			ee.printStackTrace();
			
			estoque.fecharArquivos();

			ObjectIOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();

			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	public static void adicionarQntDoEstoque(String nomeDoProduto, int qnt) throws IOException {
		modificarQntNoEstoque(nomeDoProduto, qnt);
	}

	public static void retirarQntDoEstoque(String nomeDoProduto, int qnt) throws IOException {
		modificarQntNoEstoque(nomeDoProduto, -qnt);
	}

	private static void prototipoListarProdutos(boolean val) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, 'r');

		Collection<String> nomeProdutos = new ArrayList<String>();
		
		Object produto;

		try{
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)) {
				// Operador XOR agindo como negação.

				// Se val == true, a expressão no if é equivalente à 
				// "!(((Produto)produto).getCadastroAtivo() && ((Produto)produto).getQntNoEstoque() > 0)"
				// Que, utilizando simplificações lógicas, é equivalente à
				//"((!(Produto)produto).getCadastroAtivo() || ((Produto)produto).getQntNoEstoque() <= 0)"

				// Se val == false, a expressão no if é equivalente à "Integer.valueOf(informacoesCliente[4]) == 1"
				if((((Produto)produto).getCadastroAtivo() && ((Produto)produto).getQntNoEstoque() > 0) ^ val) {
					nomeProdutos.add(((Produto) produto).getNome());

					System.out.println(((Produto) produto).getNome() + "/" + ((Produto) produto).getQntNoEstoque());
				}
					
			}

		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally {
			estoque.fecharArquivos();
		}	
	}

	public static void listarProdutosDisponiveis() throws IOException {
		prototipoListarProdutos(false);
	}

	public static void listarProdutosExcluidosOuForaDoEstoque() throws IOException {
		prototipoListarProdutos(true);
	}

	public static void listarProdutosCadastrados() throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, 'r');

		Collection<String> nomeProdutos = new ArrayList<String>();
		
		Object produto;
		try{
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass))
				nomeProdutos.add(((Produto) produto).getNome());
		
			for(String s : nomeProdutos)
				System.out.println(s);

		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally {
			estoque.fecharArquivos();
		}
	}

	public static void listarProdutosPorCategoria(String categoria) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, 'r');

		Collection<String> nomeProdutos = new ArrayList<String>();
		
		Object produto;
		String categoriaDoProduto;
		try{
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)) {
				categoriaDoProduto = produto.getClass().getSimpleName();
				if((categoriaDoProduto.equals(categoria)))
					nomeProdutos.add(((Produto) produto).getNome());
			}
				
			for(String s : nomeProdutos)
				System.out.println(s);

		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally {
			estoque.fecharArquivos();
		}
	}

	public static void main(String[] args) {
		try{
			cadastrarProdutoNoEstoque(new Jogo("The Witcher 3", 30f, 100, "Jogo do Geralt", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Fantasia", "Acao", "Aventura")));
			cadastrarProdutoNoEstoque(new Jogo("Rainbow Six Siege", 60f, 100, "Jogo de Tiro", Set.of("PS4", "XBOX ONE", "PC"), Set.of("FPS", "Tatico")));
			cadastrarProdutoNoEstoque(new Jogo("The Last Of Us", 45f, 100, "Jogo de Zumbi", Set.of("PS4", "PS5"), Set.of("Drama", "Apocalipse", "Aventura")));
			cadastrarProdutoNoEstoque(new Jogo("God Of War", 50f, 100, "Jogo do Kratos", Set.of("PS4", "PS5"), Set.of("Mitologia", "Acao", "Aventura")));
			cadastrarProdutoNoEstoque(new Console("PS5", 4000f, 100, "É um PS5", Set.of("PS5"), "1TB"));
			cadastrarProdutoNoEstoque(new Console("XBOX Series X", 3500f, 150, "É um XBOX", Set.of("XBOX Series X"), "2TB"));
			cadastrarProdutoNoEstoque(new TecladoMecanico("Redragon Kumara", 219.9f, 35, "Teclado mecânico gamer", Set.of("PC"), true, false, "Blue"));
			cadastrarProdutoNoEstoque(new Mouse("Razer Viper Mini", 400f, 20, "Mouse bonito", Set.of("PC"), true, false, String.valueOf(8500)));
			cadastrarProdutoNoEstoque(new Fone("Warrior Kaden", 150f, 250, "Fone confortável", Set.of("PC"), true, false, "108DB", true));
			cadastrarProdutoNoEstoque(new Jogo("Xcom", 45f, 100, "Jogo de estratégia futurista", Set.of("PS3", "XBOX 360", "PC"), Set.of("FPS", "Estratégia")));
			cadastrarProdutoNoEstoque(new Jogo("Xcom 2", 60f, 100, "Continuação do Xcom", Set.of("PS4", "XBOX ONE", "PC"), Set.of("FPS", "Estratégia")));
			cadastrarProdutoNoEstoque(new Jogo("Red Dead Redemption", 30f, 100, "Jogo de velho oeste", Set.of("PS3", "XBOX 360", "PC"), Set.of("Velho Oeste", "Acao", "Aventura")));
			cadastrarProdutoNoEstoque(new Jogo("Civilization 6", 70f, 100, "Jogo de estratégia de tabuleiro online", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Estratégia")));
			cadastrarProdutoNoEstoque(new Jogo("Red Dead Redemption 2", 80f, 100, "Continuação do Red Dead Redemption", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Velho Oeste", "Acao", "Aventura")));

			System.out.println("======================");
			listarProdutosDisponiveis();
			System.out.println("======================");
			listarProdutosPorCategoria("Jogo");

		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Falha na comunicação como banco de dados");
		}
	}
}