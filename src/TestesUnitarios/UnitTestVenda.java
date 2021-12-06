package TestesUnitarios;

import static org.junit.Assert.assertArrayEquals;

import java.util.Hashtable;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import RegrasDeNegocio.Venda;

public class UnitTestVenda {
	@Test
	public void testeAdicionarProdutoAoCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);

		Map<String, Integer> resultadoEsperado = new Hashtable<String, Integer>();
		resultadoEsperado.put("The Witcher 3", 5);

		Assert.assertEquals(resultadoEsperado, novaVenda.getCarrinho());
	}

	@Test
	public void testeModificarQntDoProdutoNoCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);
		novaVenda.modificarQntDoProdutoNoCarrinho("The Witcher 3", 2);

		Map<String, Integer> resultadoEsperado = new Hashtable<String, Integer>();
		resultadoEsperado.put("The Witcher 3", 2);

		Assert.assertEquals(resultadoEsperado, novaVenda.getCarrinho());
	}

	@Test
	public void testeRetirarProdutoDoCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);
		novaVenda.retirarProdutoDoCarrinho("The Witcher 3");

		Map<String, Integer> resultadoEsperado = new Hashtable<String, Integer>();

		Assert.assertEquals(resultadoEsperado, novaVenda.getCarrinho());
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

	@Test
	public void testeGetInfoParaOCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);
		novaVenda.adicionarProdutoAoCarrinho("The Last Of Us", 2);

		String[][] infoParaOCarrinho = novaVenda.getInfoParaOCarrinho();

		String[][] resultadoEsperado = {{}};
	}
}
