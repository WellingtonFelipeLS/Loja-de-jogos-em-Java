package TestesUnitarios;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import ManipulacaoBancoDeDados.ControleDeEstoque;
import RegrasDeNegocio.Produtos.*;
import ClassesUtilitarias.EOFIndicatorClass;
import ClassesUtilitarias.ObjectIOMaster;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.io.IOException;

public class UnitTestControleDeEstoque extends UnitTestClasseBase{

	@Override
	protected void limparBancoDeDados() throws IOException{
		ObjectIOMaster oim  = new ObjectIOMaster(
			super.getCaminhoParaPastaDoBancoDeDados() + super.getFileSeparator() + "Estoque.ser", 'w');
		oim.escrever(new EOFIndicatorClass());
		oim.fecharArquivos();
	}

	@Override
	protected void popularBancoDeDados() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());
		controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("The Witcher 3", 30f, 100, "Jogo do Geralt", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Fantasia", "Acao", "Aventura")));
		controleDeEstoque.cadastrarProdutoNoEstoque(new Console("PS5", 4000f, 100, "É um PS5", Set.of("PS5"), "1TB"));
		controleDeEstoque.excluirProduto("PS5");
	}

	@Test
	public void testeCadastrarProduto() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());
		controleDeEstoque.cadastrarProdutoNoEstoque(new Console("Nintendo Switch", 3500f, 150, "É um Nintendo Switch", Set.of("Nintendo Switch"), "128GB"));
		Produto produto = controleDeEstoque.procurarProdutoNoEstoque("Nintendo Switch");
		
		assertEquals(produto.getNome(), "Nintendo Switch");
		assertEquals(produto.getPreco(), 3500f);
		assertEquals(produto.getQntNoEstoque(), 150);
		assertEquals(produto.getDescricao(), "É um Nintendo Switch");
		assertEquals(produto.getPlataforma(), Set.of("Nintendo Switch"));
		assertEquals(((Console)produto).getMemoria(), "128GB");
	}

	@Test
	public void testeCadastrarProdutoJaCadastrado() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());
		controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("The Witcher 3", 30f, 100, "Jogo do Geralt", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Fantasia", "Acao", "Aventura")));
		
		assertTrue(super.getMensagemDeErro().toString().contains("Falha no cadastramento: Produto de nome The Witcher 3 já cadastrado."));
	}

	@Test
	public void testeRecadastrarProduto() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());		
		controleDeEstoque.recadastrarProdutoNoEstoque(new Console("PS5", 4000f, 100, "É um PS5", Set.of("PS5"), "1TB"));
		Produto produto = controleDeEstoque.procurarProdutoNoEstoque("PS5");
		
		assertEquals(produto.getNome(), "PS5");
	}

	@Test
	public void testeRecadastrarProdutoComCadastroAtivo() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());
		controleDeEstoque.recadastrarProdutoNoEstoque(new Jogo("The Witcher 3", 30f, 100, "Jogo do Geralt", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Fantasia", "Acao", "Aventura")));
		assertTrue(super.getMensagemDeErro().toString().contains("Falha no recadastramento: Produto The Witcher 3 com cadastro ativo."));
	}

	@Test
	public void testeExcluirProduto() throws IOException{
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());		
		controleDeEstoque.excluirProduto("The Witcher 3");
		
		assertNull(controleDeEstoque.procurarProdutoNoEstoque("The Witcher 3"));
	}

	@Test
	public void testeExcluirProdutoJaExcluido() throws IOException{
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());		
		controleDeEstoque.excluirProduto("PS5");

		assertTrue(super.getMensagemDeErro().toString().contains("Falha na exclusão: Produto PS5 já excluído.")); 
	}

	@Test
	public void testeProcurarProdutoPresenteNoEstoque() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());		
		Produto produto = controleDeEstoque.procurarProdutoNoEstoque("The Witcher 3");

		assertEquals(produto.getNome(), "The Witcher 3");
		assertEquals(produto.getPreco(), 30f);
		assertEquals(produto.getQntNoEstoque(), 100);
		assertEquals(produto.getDescricao(), "Jogo do Geralt");
		assertEquals(produto.getPlataforma(), Set.of("PS4", "XBOX ONE", "PC"));
		assertEquals(((Jogo)produto).getGeneros(), Set.of("Fantasia", "Acao", "Aventura"));
	}

	@Test
	public void testeProcurarProdutoNaoPresenteNoEstoque() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());		
		Produto produto = controleDeEstoque.procurarProdutoNoEstoque("PS5");
		
		assertNull(produto);
		assertTrue(super.getMensagemDeErro().toString().contains("Falha na busca: Produto PS5 excluído.")); 
	}

	@Test
	public void testeProcurarProdutoNaoCadastradoNoEstoque() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());		
		Produto produto = controleDeEstoque.procurarProdutoNoEstoque("PS3");
		
		assertNull(produto);
		assertTrue(super.getMensagemDeErro().toString().contains("Falha na busca: Produto PS3 não cadastrado."));
	}

	@Test
	public void testeSetQntNoEstoque() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());
		controleDeEstoque.setQntNoEstoque("The Witcher 3", 20);
		Produto produto = controleDeEstoque.procurarProdutoNoEstoque("The Witcher 3");
		
		assertEquals(produto.getQntNoEstoque(), 20);
	}

	@Test
	public void testeSetQntNegativaNoEstoque() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());
		controleDeEstoque.setQntNoEstoque("The Witcher 3", -20);
		
		assertTrue(super.getMensagemDeErro().toString().contains("Falha em inserir quantidade: A quantidade no estoque não pode ser negativa."));
	}

	@Test
	public void testeSetQntDeProdutoExcluidoNoEstoque() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());
		controleDeEstoque.setQntNoEstoque("PS5", 20);
		
		assertTrue(super.getMensagemDeErro().toString().contains("Falha em inserir quantidade: Produto PS5 excluído. Recadastre o produto para modificar sua quantidade."));
	}

	@Test
	public void testeAddQntNoEstoque() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());
		Produto produto = controleDeEstoque.procurarProdutoNoEstoque("The Witcher 3");
		int qntIncial = produto.getQntNoEstoque();
		controleDeEstoque.adicionarQntDoEstoque("The Witcher 3", 20);
		produto = controleDeEstoque.procurarProdutoNoEstoque("The Witcher 3");
		
		assertEquals(produto.getQntNoEstoque(), qntIncial + 20);
	}

	@Test
	public void testeRetirarQntDoEstoque() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());
		Produto produto = controleDeEstoque.procurarProdutoNoEstoque("The Witcher 3");
		int qntIncial = produto.getQntNoEstoque();
		controleDeEstoque.retirarQntDoEstoque("The Witcher 3", 20);
		produto = controleDeEstoque.procurarProdutoNoEstoque("The Witcher 3");
		
		assertEquals(produto.getQntNoEstoque(), qntIncial - 20);
	}

	@Test
	public void testeRetirarMaisUnidadesDoQueExisteNoEstoque() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());
		controleDeEstoque.retirarQntDoEstoque("The Witcher 3", 120);
		
		assertTrue(super.getMensagemDeErro().toString().contains("Falha em modificar quantidade: Não é possível retirar 120 unidades do produto The Witcher 3. Existem apenas 100 em estoque."));
	}

	@Test
	public void testeListarProdutosPorCategoria() throws IOException {
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());
		ArrayList<String> nomes = controleDeEstoque.listarProdutosPorCategoria("Jogo");

		assertEquals(nomes, Arrays.asList("The Witcher 3"));
	}
}
