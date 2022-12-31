package ManipulacaoBancoDeDados;

import RegrasDeNegocio.Produtos.*;
import ExceptionsCustomizadas.CadastroException;
import ExceptionsCustomizadas.EstoqueException;

import ClassesUtilitarias.EOFIndicatorClass;
import ClassesUtilitarias.IOMaster;
import ClassesUtilitarias.ObjectIOMaster;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Function;

public class ControleDeEstoque {
	private String caminhoBancoDeDados;
	private String caminhoBancoDeDadosTemp;
	private String formatoDeSaida = "Nome: %-20s		Quantidade No Estoque: %-10d		Preço: %-10.2f\n";

	public ControleDeEstoque(String caminhoPastaBancoDeDados) {
		this.caminhoBancoDeDados = caminhoPastaBancoDeDados + System.getProperty("file.separator") + "Estoque.ser";
		this.caminhoBancoDeDadosTemp = caminhoPastaBancoDeDados + System.getProperty("file.separator") + "EstoqueTemp.ser";
	}

	public void cadastrarProdutoNoEstoque(Produto novoProduto) throws IOException {
		ObjectIOMaster.verificarArquivo(caminhoBancoDeDados);

		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		Object produto;

		try {
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)){
				if(((Produto)produto).equals(novoProduto))
					throw new CadastroException("Produto de nome " + novoProduto.getNome() + " já cadastrado.");
				
				estoque.escrever(produto);
			}

			estoque.escrever(novoProduto);

			estoque.escrever(new EOFIndicatorClass());

			estoque.fecharArquivos();

			ObjectIOMaster.renomearESobrescreverArquivo(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		}catch(CadastroException ce) {
			System.err.println("Falha no cadastramento: " + ce.getMessage());
			
			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();

			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	public void recadastrarProdutoNoEstoque(Produto novoProduto) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		Object produto;

		try {
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)){
				if(((Produto)produto).equals(novoProduto)) {
					if(!((Produto)produto).getCadastroAtivo()){
						estoque.escrever(novoProduto);
					}else
						throw new CadastroException("Produto " + novoProduto.getNome() + " com cadastro ativo.");
				}else
					estoque.escrever(produto);
			}

			estoque.escrever(new EOFIndicatorClass());

			estoque.fecharArquivos();

			ObjectIOMaster.renomearESobrescreverArquivo(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		}catch(CadastroException ce){
			System.err.println("Falha no recadastramento: " + ce.getMessage());
			
			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();

			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	// Pedir, além do nome do produto, a plataforma.
	public void excluirProduto(String nomeDoProduto) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		Object produto;

		try {
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)){

				if(((Produto)produto).getNome().equals(nomeDoProduto)) {
					if(!((Produto)produto).getCadastroAtivo())
						throw new CadastroException("Produto " + ((Produto)produto).getNome() + " já excluído.");
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
			System.err.println("Falha na exclusão: " + ce.getMessage());
			
			estoque.fecharArquivos();

			ObjectIOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();

			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	public Produto procurarProdutoNoEstoque(String nomeDoProduto) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, 'r');

		Object produto;
		Produto produtoTemp;

		try {
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)){
				produtoTemp = (Produto) produto;

				if(produtoTemp.getNome().equals(nomeDoProduto)) {
					if(produtoTemp.getCadastroAtivo()){
						estoque.fecharArquivos();
						return produtoTemp;
					}else
						throw new CadastroException("Produto " + produtoTemp.getNome() + " excluído.");
			}
		}
			throw new EstoqueException("Produto " + nomeDoProduto + " não cadastrado.");

		}catch(CadastroException | EstoqueException ce) {
			System.err.println("Falha na busca: " + ce.getMessage());
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally {
			estoque.fecharArquivos();
		}

		return null;
	}

	public void setQntNoEstoque(String nomeDoProduto, int qnt) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		Object produto;
		Produto produtoTemp;

		try {
			if(qnt < 0)
				throw new EstoqueException("A quantidade no estoque não pode ser negativa.");

			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)){
				produtoTemp = (Produto) produto;

				if(produtoTemp.getNome().equals(nomeDoProduto)) {
					if(produtoTemp.getCadastroAtivo()) {
						produtoTemp.setQntNoEstoque(qnt);
						estoque.escrever(produtoTemp);
					}
					else
						throw new CadastroException("Produto " + nomeDoProduto + " excluído. Recadastre o produto para modificar sua quantidade.");
				}else
					estoque.escrever(produto);
			}

			estoque.escrever(new EOFIndicatorClass());

			estoque.fecharArquivos();

			ObjectIOMaster.renomearESobrescreverArquivo(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		}catch(CadastroException | EstoqueException ce) {
			System.err.println("Falha em inserir quantidade: " + ce.getMessage());
			
			estoque.fecharArquivos();

			ObjectIOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();

			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	private void modificarQntNoEstoque(String nomeDoProduto, int qnt) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		Object produto;

		try {

			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)){

				if(((Produto)produto).getNome().equals(nomeDoProduto)) {
					if(((Produto)produto).getCadastroAtivo()) {

						if(qnt + ((Produto)produto).getQntNoEstoque() < 0) 
							throw new EstoqueException("Não é possível retirar " + -qnt + " unidades. Existem apenas " + ((Produto)produto).getQntNoEstoque() + " em estoque.");

						((Produto)produto).setQntNoEstoque(qnt + ((Produto)produto).getQntNoEstoque());
						estoque.escrever(produto);
					}
					else
						throw new CadastroException("Produto " + nomeDoProduto + " excluído. Recadastre o produto para modificar sua quantidade.");
				}else
					estoque.escrever(produto);
			}

			estoque.escrever(new EOFIndicatorClass());

			estoque.fecharArquivos();

			ObjectIOMaster.renomearESobrescreverArquivo(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		}catch(CadastroException | EstoqueException ce) {
			System.err.println("Falha em modificar quantidade: " + ce.getMessage());
			
			estoque.fecharArquivos();

			ObjectIOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();

			estoque.fecharArquivos();

			IOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	public void adicionarQntDoEstoque(String nomeDoProduto, int qnt) throws IOException {
		modificarQntNoEstoque(nomeDoProduto, qnt);
	}

	public void retirarQntDoEstoque(String nomeDoProduto, int qnt) throws IOException {
		modificarQntNoEstoque(nomeDoProduto, -qnt);
	}

	public ArrayList<String> listarNomeDosProdutosDisponiveis() throws IOException{
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, 'r');
		
		Object produto;
		Produto produtoTemp;
		ArrayList<String> nomesDosProdutos = new ArrayList<String>();

		try{
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)) {
				produtoTemp = (Produto) produto;
				
				if(produtoTemp.getCadastroAtivo() && produtoTemp.getQntNoEstoque() > 0) 
					nomesDosProdutos.add(produtoTemp.getNome());
			}
				
			return nomesDosProdutos;

		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally {
			estoque.fecharArquivos();
		}

		return null;
	}

	private String prototipoListarProdutos(Function<Produto, Boolean> func) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, 'r');
		
		Object produto;
		StringBuilder listaDosProdutos = new StringBuilder();

		try{
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)) {
				if(func.apply((Produto)produto))
					listaDosProdutos.append(String.format(formatoDeSaida,
														 ((Produto) produto).getNome(),
														 ((Produto) produto).getQntNoEstoque(),
														 ((Produto) produto).getPreco()));
					
			}

			return listaDosProdutos.toString();

		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally {
			estoque.fecharArquivos();
		}

		return null;
	}

	public String listarProdutosDisponiveis() throws IOException {
		return prototipoListarProdutos((produto) -> 
		produto.getCadastroAtivo() && produto.getQntNoEstoque() > 0);
	}

	public String listarProdutosExcluidosOuForaDoEstoque() throws IOException {
		return prototipoListarProdutos((produto) -> 
		!(produto.getCadastroAtivo() && produto.getQntNoEstoque() > 0));

	}

	public String listarProdutosCadastrados() throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, 'r');
		
		Object produto;
		Produto produtoTemp;
		StringBuilder produtosCadastrados = new StringBuilder();

		try{
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)) {
				produtoTemp = (Produto) produto;
				produtosCadastrados.append(String.format(formatoDeSaida,
														 produtoTemp.getNome(),
														 produtoTemp.getQntNoEstoque(),
														 produtoTemp.getPreco()));
			}

			return produtosCadastrados.toString();	

		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally {
			estoque.fecharArquivos();
		}

		return null;
	}

	public ArrayList<String> listarProdutosPorCategoria(String categoria) throws IOException {
		ObjectIOMaster estoque = new ObjectIOMaster(caminhoBancoDeDados, 'r');

		ArrayList<String> nomeProdutos = new ArrayList<String>();
		
		Object produto;
		String categoriaDoProduto;
		try{
			while(!((produto = estoque.ler()) instanceof EOFIndicatorClass)) {
				categoriaDoProduto = produto.getClass().getSimpleName();
				if((categoriaDoProduto.equals(categoria)))
					nomeProdutos.add(((Produto) produto).getNome());
			}
				
			return nomeProdutos;

		}catch(ClassNotFoundException cnfe) {
			cnfe.printStackTrace();
		}finally {
			estoque.fecharArquivos();
		}

		return null;
	}

	public static void main(String[] args) {
		try{

			ControleDeEstoque controleDeEstoque = new ControleDeEstoque("src" + System.getProperty("file.separator") + "BancoDeDados");

			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("The Witcher 3", 30f, 100, "Jogo do Geralt", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Fantasia", "Acao", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Rainbow Six Siege", 60f, 100, "Jogo de Tiro", Set.of("PS4", "XBOX ONE", "PC"), Set.of("FPS", "Tatico")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("The Last Of Us", 45f, 100, "Jogo de Zumbi", Set.of("PS4", "PS5"), Set.of("Drama", "Apocalipse", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("God Of War", 50f, 100, "Jogo do Kratos", Set.of("PS4", "PS5"), Set.of("Mitologia", "Acao", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Console("PS5", 4000f, 100, "É um PS5", Set.of("PS5"), "1TB"));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Console("XBOX Series X", 3500f, 150, "É um XBOX", Set.of("XBOX Series X"), "2TB"));
			controleDeEstoque.cadastrarProdutoNoEstoque(new TecladoMecanico("Redragon Kumara", 219.9f, 35, "Teclado mecânico gamer", Set.of("PC"), false, "Blue"));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Mouse("Razer Viper Mini", 400f, 20, "Mouse bonito", Set.of("PC"), false, String.valueOf(8500)));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Fone("Warrior Kaden", 150f, 250, "Fone confortável", Set.of("PC"), false, "108DB", true));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Xcom", 45f, 100, "Jogo de estratégia futurista", Set.of("PS3", "XBOX 360", "PC"), Set.of("FPS", "Estratégia")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Xcom 2", 60f, 100, "Continuação do Xcom", Set.of("PS4", "XBOX ONE", "PC"), Set.of("FPS", "Estratégia")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Red Dead Redemption", 30f, 100, "Jogo de velho oeste", Set.of("PS3", "XBOX 360", "PC"), Set.of("Velho Oeste", "Acao", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Civilization 6", 70f, 100, "Jogo de estratégia de tabuleiro online", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Estratégia")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Red Dead Redemption 2", 80f, 100, "Continuação do Red Dead Redemption", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Velho Oeste", "Acao", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Dying Light", 145f, 1010, "Jogo de sobrevivência zumbi", Set.of("PS3", "XBOX 360", "PC"), Set.of("Sobrevivência", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Dying Light 2", 260f, 1100, "Continuação do Dying Light", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Sobrevivência", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("GTA 5", 130f, 1090, "Jogo de mundo aberto", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Acao", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("GTA 6", 700f, 1, "Jogo mais aguardado da década", Set.of("PS5", "XBOX Series X", "PC"), Set.of("Acao", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("While True Learn()", 80f, 100, "Jogo de desafios lógicos", Set.of("PC"), Set.of("Puzzle")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Fone("KZ ZSN", 200f, 2400, "Fone Musical", Set.of("PC"), false, "104Db", false));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Frostpunk", 130f, 1200, "Jogo de gestão aopcalíptica", Set.of("PC"), Set.of("Estrategia", "Gestao", "Apocalipse")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Frostpunk 2", 180f, 1200, "Jogo de gestão aopcalíptica", Set.of("PC"), Set.of("Estrategia", "Gestao", "Apocalipse")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("The Last Of Us 2", 145f, 10110, "Jogo de Zumbi", Set.of("PS4", "PS5"), Set.of("Drama", "Apocalipse", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Marvel Midnight Sons", 350f, 10, "Jogo do MCU de rpg tático", Set.of("PC"), Set.of("Estratégia", "Acao", "rpg")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Console("Steam Deck", 4000f, 100, "É um Steam Deck", Set.of("PC"), "1TB"));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Console("Nintendo Switch", 3500f, 150, "É um Nintendo Switch", Set.of("Nintendo Switch"), "128GB"));
			controleDeEstoque.cadastrarProdutoNoEstoque(new TecladoMecanico("Spk 8404", 500f, 35, "Teclado mecânico gamer", Set.of("PC"), false, "Blue"));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Mouse("Warrior MO262", 40f, 20, "Mouse bonito", Set.of("PC"), false, String.valueOf(3200)));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Diablo 3", 80f, 1200, "Jogo de rpg da Blizzard", Set.of("PC"), Set.of("Estrategia", "rpg", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("Diablo 4", 280f, 1200, "Jogo de rpg da Blizzard", Set.of("PC"), Set.of("Estrategia", "rpg", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("God Of War 2", 50f, 2100, "Jogo do Kratos", Set.of("PS4", "PS5"), Set.of("Mitologia", "Acao", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("God Of War 3", 50f, 1200, "Jogo do Kratos", Set.of("PS4", "PS5"), Set.of("Mitologia", "Acao", "Aventura")));
			controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("God Of War Ragnarok", 50f, 1020, "Jogo do Kratos", Set.of("PS4", "PS5"), Set.of("Mitologia", "Acao", "Aventura")));

		}catch(IOException e){
			e.printStackTrace();
			System.out.println("Falha na comunicação como banco de dados");
		}
	}
}