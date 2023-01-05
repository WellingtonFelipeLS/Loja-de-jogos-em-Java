package TestesUnitarios;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.Set;

import org.junit.jupiter.api.Test;

import ClassesUtilitarias.EOFIndicatorClass;
import ClassesUtilitarias.ObjectIOMaster;

import ManipulacaoBancoDeDados.ControleDeEstoque;
import ManipulacaoBancoDeDados.ControleDeVendas;

import RegrasDeNegocio.Venda;
import RegrasDeNegocio.Produtos.Jogo;
import RegrasDeNegocio.Produtos.Console;

public class UnitTestControleDeVendas extends UnitTestClasseBase{

	private ControleDeEstoque estoque = new ControleDeEstoque(super.getCaminhoParaPastaDoBancoDeDados());

	@Override
	protected void limparBancoDeDados() throws IOException{
		ObjectIOMaster oim  = new ObjectIOMaster(
			super.getCaminhoParaPastaDoBancoDeDados() + super.getFileSeparator() + "RegistroDasVendas.ser", 'w');
		oim.escrever(new EOFIndicatorClass());
		oim.fecharArquivos();
	}

	@Override
	protected void popularBancoDeDados() throws IOException {
		estoque.cadastrarProdutoNoEstoque(new Jogo("The Witcher 3", 30f, 100, "Jogo do Geralt", Set.of("PS4", "XBOX ONE", "PC"), Set.of("Fantasia", "Acao", "Aventura")));
		estoque.cadastrarProdutoNoEstoque(new Console("PS5", 4000f, 100, "É um PS5", Set.of("PS5"), "1TB"));

		ControleDeVendas controleDeVendas = new ControleDeVendas(estoque, getCaminhoParaPastaDoBancoDeDados());
		Venda venda = new Venda();
		venda.adicionarProdutoAoCarrinho("The Witcher 3", 20);
		controleDeVendas.cadastrarVenda(venda);
	}

	@Test
	public void testarCadastroDeVenda() throws IOException, ClassNotFoundException {
		ControleDeVendas controleDeVendas = new ControleDeVendas(estoque, getCaminhoParaPastaDoBancoDeDados());
		Venda novaVenda = new Venda();
		novaVenda.adicionarProdutoAoCarrinho("PS5", 1);
		controleDeVendas.cadastrarVenda(novaVenda);

		ObjectIOMaster oim  = new ObjectIOMaster(
			super.getCaminhoParaPastaDoBancoDeDados() + super.getFileSeparator() + "RegistroDasVendas.ser", 'r');

		// Passar a primeira venda, que já está cadastrada no sistema
		oim.ler();

		Venda vendaCadastrada = (Venda) oim.ler();

		assertTrue(vendaCadastrada.equals(novaVenda));
	}
}
