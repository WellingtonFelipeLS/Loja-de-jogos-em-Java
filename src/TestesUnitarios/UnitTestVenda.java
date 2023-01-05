package TestesUnitarios;

import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ManipulacaoBancoDeDados.ControleDeEstoque;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import RegrasDeNegocio.Venda;
import RegrasDeNegocio.Produtos.Jogo;

public class UnitTestVenda {

	@Test
	public void testarAdicionarProdutoAoCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);

		Map<String, Integer> resultadoEsperado = new Hashtable<String, Integer>();
		resultadoEsperado.put("The Witcher 3", 5);

		assertEquals(resultadoEsperado, novaVenda.getCarrinho());
	}

	@Test
	public void testarModificarQntDoProdutoNoCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);
		novaVenda.modificarQntDoProdutoNoCarrinho("The Witcher 3", 2);

		Map<String, Integer> resultadoEsperado = new Hashtable<String, Integer>();
		resultadoEsperado.put("The Witcher 3", 2);

		assertEquals(resultadoEsperado, novaVenda.getCarrinho());
	}

	@Test
	public void testarRetirarProdutoDoCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);
		novaVenda.retirarProdutoDoCarrinho("The Witcher 3");

		Map<String, Integer> resultadoEsperado = new Hashtable<String, Integer>();

		assertEquals(resultadoEsperado, novaVenda.getCarrinho());
	}

	@Test
	public void testarGetProdutosDoCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);
		novaVenda.adicionarProdutoAoCarrinho("The Last Of Us", 2);

		String[] produtosDoCarrinho = novaVenda.getProdutosDoCarrinho();

		String[] resultadoEsperado = {"The Witcher 3", "The Last Of Us"};

		assertArrayEquals(resultadoEsperado, produtosDoCarrinho);
	}

	@Test
	public void testarLimparOCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);
		novaVenda.adicionarProdutoAoCarrinho("The Last Of Us", 2);

		novaVenda.limparCarrinho();

		Map<String, Integer> resultadoEsperado = new Hashtable<String, Integer>();

		assertEquals(resultadoEsperado, novaVenda.getCarrinho());
	}

	@Test
	public void testarGetInfoParaOCarrinho() throws IOException{
		String fs = System.getProperty("file.separator");
		ControleDeEstoque controleDeEstoque = new ControleDeEstoque("src" + fs + "TestesUnitarios" + fs + "BancoDeDados");
		controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("The Witcher 3", 30f, 100, "Jogo do Geralt", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Fantasia", "Acao", "Aventura")));
		controleDeEstoque.cadastrarProdutoNoEstoque(new Jogo("The Last Of Us", 40f, 50, "Jogo do Geralt", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Fantasia", "Acao", "Aventura")));


		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);
		novaVenda.adicionarProdutoAoCarrinho("The Last Of Us", 2);

		
		String[][] infos = novaVenda.getInfoParaOCarrinho(controleDeEstoque);

		String[][] resultadoEsperado = {{"The Witcher 3", String.valueOf(5), String.valueOf(30f), String.valueOf(5 * 30f)}, 
										{"The Last Of Us", String.valueOf(2), String.valueOf(40f), String.valueOf(2 * 40f)}};

		assertArrayEquals(resultadoEsperado, infos);
	}
}
