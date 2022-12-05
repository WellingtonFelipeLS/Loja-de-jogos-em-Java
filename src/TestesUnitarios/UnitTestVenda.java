package TestesUnitarios;

import java.util.Hashtable;
import java.util.Map;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import RegrasDeNegocio.Venda;

public class UnitTestVenda {
	@Test
	public void testeAdicionarProdutoAoCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);

		Map<String, Integer> resultadoEsperado = new Hashtable<String, Integer>();
		resultadoEsperado.put("The Witcher 3", 5);

		assertEquals(resultadoEsperado, novaVenda.getCarrinho());
	}

	@Test
	public void testeModificarQntDoProdutoNoCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);
		novaVenda.modificarQntDoProdutoNoCarrinho("The Witcher 3", 2);

		Map<String, Integer> resultadoEsperado = new Hashtable<String, Integer>();
		resultadoEsperado.put("The Witcher 3", 2);

		assertEquals(resultadoEsperado, novaVenda.getCarrinho());
	}

	@Test
	public void testeRetirarProdutoDoCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);
		novaVenda.retirarProdutoDoCarrinho("The Witcher 3");

		Map<String, Integer> resultadoEsperado = new Hashtable<String, Integer>();

		assertEquals(resultadoEsperado, novaVenda.getCarrinho());
	}

	@Test
	public void testeGetProdutosDoCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);
		novaVenda.adicionarProdutoAoCarrinho("The Last Of Us", 2);

		String[] produtosDoCarrinho = novaVenda.getProdutosDoCarrinho();

		String[] resultadoEsperado = {"The Witcher 3", "The Last Of Us"};

		assertArrayEquals(resultadoEsperado, produtosDoCarrinho);
	}
}
