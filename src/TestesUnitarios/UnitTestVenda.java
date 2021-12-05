package TestesUnitarios;

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
	public void testeRetirarQntDoProdutoDoCarrinho() {
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("The Witcher 3", 5);
		novaVenda.retirarQntDoProdutoDoCarrinho("The Witcher 3", 3);

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
}
