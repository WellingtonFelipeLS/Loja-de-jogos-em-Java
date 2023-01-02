package TestesUnitarios;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;

import org.junit.jupiter.api.Test;

import ClassesUtilitarias.CharIOMaster;
import ManipulacaoBancoDeDados.ControleDeCadastroDeClientes;
import RegrasDeNegocio.Cliente;

public class UnitTestCadastroDeClientes extends UnitTestClasseBase {
 
	@Override
	protected void limparBancoDeDados() throws IOException{
		CharIOMaster oim  = new CharIOMaster(
			super.getCaminhoParaPastaDoBancoDeDados() + super.getFileSeparator() + "RegistroDeClientes.txt", 
			'w', "/");
		oim.fecharArquivos();
	}

	@Override
	protected void popularBancoDeDados() throws IOException {
		ControleDeCadastroDeClientes dadosDosClientes = new ControleDeCadastroDeClientes(super.getCaminhoParaPastaDoBancoDeDados());
		dadosDosClientes.cadastrarCliente(new Cliente("Wellington", "819.808.150-03", "49060-700"));
		dadosDosClientes.cadastrarCliente(new Cliente("Gustavo", "637.106.250-68", "72145-820"));
		dadosDosClientes.excluirCliente("637.106.250-68");
	}

	@Test
	public void testarCadastroDeClientes() throws IOException {
		ControleDeCadastroDeClientes dadosDosClientes = new ControleDeCadastroDeClientes(super.getCaminhoParaPastaDoBancoDeDados());
		Cliente novoCliente = new Cliente("Josefa", "288.628.320-79", "49095-310");
		dadosDosClientes.cadastrarCliente(novoCliente);
		Cliente clienteBuscado = dadosDosClientes.procurarCliente("288.628.320-79");

		assertTrue(novoCliente.equals(clienteBuscado));
	}

	@Test
	public void testarCadastrarClienteJaCadastrado() throws IOException {
		ControleDeCadastroDeClientes dadosDosClientes = new ControleDeCadastroDeClientes(super.getCaminhoParaPastaDoBancoDeDados());
		Cliente novoCliente = new Cliente("Wellington", "819.808.150-03", "49095-310");
		dadosDosClientes.cadastrarCliente(novoCliente);

		assertTrue(super.getMensagemDeErro().toString().contains("Falha no cadastramento: Cliente com CPF 819.808.150-03 já cadastrado."));
	}

	@Test
	public void testarProcuraDeClientes() throws IOException {
		ControleDeCadastroDeClientes dadosDosClientes = new ControleDeCadastroDeClientes(super.getCaminhoParaPastaDoBancoDeDados());
		Cliente clienteBuscado = dadosDosClientes.procurarCliente("819.808.150-03");

		assertEquals(clienteBuscado.getCPF(), "819.808.150-03");
	}
	
	@Test
	public void testarProcuraDeClienteExcluido() throws IOException {
		ControleDeCadastroDeClientes dadosDosClientes = new ControleDeCadastroDeClientes(super.getCaminhoParaPastaDoBancoDeDados());
		dadosDosClientes.procurarCliente("637.106.250-68");

		assertTrue(super.getMensagemDeErro().toString().contains("Falha na busca: Cliente com CPF 637.106.250-68 excluído."));
	}

	@Test
	public void testarExcluirCliente() throws IOException {
		ControleDeCadastroDeClientes dadosDosClientes = new ControleDeCadastroDeClientes(super.getCaminhoParaPastaDoBancoDeDados());
		dadosDosClientes.excluirCliente("819.808.150-03");
		dadosDosClientes.procurarCliente("819.808.150-03");

		assertTrue(super.getMensagemDeErro().toString().contains("Falha na busca: Cliente com CPF 819.808.150-03 excluído."));
	}

	@Test
	public void testarExcluirClienteJaExcluido() throws IOException {
		ControleDeCadastroDeClientes dadosDosClientes = new ControleDeCadastroDeClientes(super.getCaminhoParaPastaDoBancoDeDados());
		dadosDosClientes.excluirCliente("637.106.250-68");

		assertTrue(super.getMensagemDeErro().toString().contains("Falha na exclusão: Cliente com CPF 637.106.250-68 já excluído."));
	}

	@Test
	public void testarListarClientesCadastrados() throws IOException {
		ControleDeCadastroDeClientes dadosDosClientes = new ControleDeCadastroDeClientes(super.getCaminhoParaPastaDoBancoDeDados());
		String clientesCadastrados = dadosDosClientes.listarClientesCadastrados();

		assertEquals(clientesCadastrados, String.format("Nome: %-20s		CPF: %-10s 		CEP:%-10s \n", 
								"Wellington", 
										"819.808.150-03", 
										"49060-700"));
	}

	@Test
	public void testarListarClientesExcluidos() throws IOException {
		ControleDeCadastroDeClientes dadosDosClientes = new ControleDeCadastroDeClientes(super.getCaminhoParaPastaDoBancoDeDados());
		String clientesCadastrados = dadosDosClientes.listarClientesExcluidos();

		assertEquals(clientesCadastrados, String.format("Nome: %-20s		CPF: %-10s 		CEP:%-10s \n", 
								"Gustavo", 
										"637.106.250-68", 
										"72145-820"));
	}
}
