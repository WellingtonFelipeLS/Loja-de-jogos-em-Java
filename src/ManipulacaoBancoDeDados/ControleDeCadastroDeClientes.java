package ManipulacaoBancoDeDados;

import ExceptionsCustomizadas.CadastroException;

import java.io.IOException;
import java.util.function.Function;

import ClassesUtilitarias.CharIOMaster;
import RegrasDeNegocio.Cliente;

public class ControleDeCadastroDeClientes {
	private String caminhoBancoDeDados;
	private String caminhoBancoDeDadosTemp;
	private String separadorDeinformacoes = "/";

	public ControleDeCadastroDeClientes(String caminhoPastaBancoDeDados) {
		this.caminhoBancoDeDados = caminhoPastaBancoDeDados + System.getProperty("file.separator") + "RegistroDeClientes.txt";
		this.caminhoBancoDeDadosTemp = caminhoPastaBancoDeDados + System.getProperty("file.separator") + "RegistroDeClientesTemp.txt";
	}

	public void cadastrarCliente(Cliente novoCliente) throws IOException{
		CharIOMaster dadosDosClientes = new CharIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp, separadorDeinformacoes);

		String cliente;
		String[] informacoesNovoCliente = {novoCliente.getNome(), novoCliente.getCPF(), 
			novoCliente.getCEP(), novoCliente.getId(), String.valueOf(1)};
		String[] informacoesClienteNoBanco;

		try{
			while((cliente = (String)dadosDosClientes.ler()) != null){
				informacoesClienteNoBanco = cliente.split(separadorDeinformacoes);
		
				if(informacoesClienteNoBanco[1].equals(novoCliente.getCPF()))
					if(Integer.valueOf(informacoesClienteNoBanco[4]) == 1)
						throw new CadastroException("Cliente com CPF " + novoCliente.getCPF() + " já cadastrado.");
				
				dadosDosClientes.escrever(informacoesClienteNoBanco);
			}

			dadosDosClientes.escrever(informacoesNovoCliente);

			dadosDosClientes.fecharArquivos();

			CharIOMaster.renomearESobrescreverArquivo(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		}catch(CadastroException ce) {
			System.err.println("Falha no cadastramento: " + ce.getMessage());

			dadosDosClientes.fecharArquivos();

			CharIOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	public Cliente procurarCliente(String CPF) throws IOException {
		CharIOMaster dadosDosClientes = new CharIOMaster(caminhoBancoDeDados, 'r', separadorDeinformacoes);
		
		String cliente;
		String[] informacoesCliente;

		try {
			while((cliente = (String)dadosDosClientes.ler()) != null){
				informacoesCliente = cliente.split(separadorDeinformacoes);

				if(CPF.equals(informacoesCliente[1])) {
					if(Integer.valueOf(informacoesCliente[4]) == 1)
						return new Cliente(informacoesCliente[0], informacoesCliente[1], informacoesCliente[2]);
					else
						throw new CadastroException("Cliente com CPF " + CPF + " excluído.");
				}
			}

			dadosDosClientes.fecharArquivos();
		}catch(CadastroException ce) {
			System.err.println("Falha na busca: " + ce.getMessage());

			dadosDosClientes.fecharArquivos();

			CharIOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}

		return null;
	}

	public void excluirCliente(String CPF) throws IOException{
		CharIOMaster dadosDosClientes = new CharIOMaster(caminhoBancoDeDados, caminhoBancoDeDadosTemp, separadorDeinformacoes);

		String cliente;
		String[] informacoesCliente;

		try {
			while((cliente = (String)dadosDosClientes.ler()) != null){
				informacoesCliente = cliente.split(separadorDeinformacoes);
	
				if(informacoesCliente[1].equals(CPF)) {
					if(Integer.valueOf(informacoesCliente[4]).equals(0)){
						throw new CadastroException("Cliente com CPF " + CPF + " já excluído.");
					}else
						informacoesCliente[4] = String.valueOf(0);
				}
				
				dadosDosClientes.escrever(informacoesCliente);
			}

			dadosDosClientes.fecharArquivos();

			CharIOMaster.renomearESobrescreverArquivo(caminhoBancoDeDados, caminhoBancoDeDadosTemp);

		}catch(CadastroException ce){
			System.err.println("Falha na exclusão: " + ce.getMessage());
			
			dadosDosClientes.fecharArquivos();

			CharIOMaster.deletarArquivo(caminhoBancoDeDadosTemp);
		}
	}

	private String prototipoListarClientes(Function<Integer,Boolean> func) throws IOException{
		CharIOMaster ciomaster = new CharIOMaster(caminhoBancoDeDados, 'r', separadorDeinformacoes);

		String cliente;
		String[] informacoesCliente;

		StringBuilder listaClientes = new StringBuilder();

		while((cliente = (String)ciomaster.ler()) != null){
			informacoesCliente = cliente.split(separadorDeinformacoes);

			if(func.apply(Integer.valueOf(informacoesCliente[4])))
				listaClientes.append(String.format("Nome: %-20s		CPF: %-10s 		CEP:%-10s \n", 
													informacoesCliente[0], 
													informacoesCliente[1], 
													informacoesCliente[2]));
		}


		ciomaster.fecharArquivos();

		return listaClientes.toString();
	}

	public String listarClientesCadastrados() throws IOException{
		return prototipoListarClientes((cadastroAtivo) -> cadastroAtivo == 1);
	}

	public String listarClientesExcluidos() throws IOException{
		return prototipoListarClientes((cadastroInativo) -> cadastroInativo == 0);
	}

	public static void main(String[] args) {
		try{
			ControleDeCadastroDeClientes controleDeCadastroDeClientes = new ControleDeCadastroDeClientes("src" + System.getProperty("file.separator") + "BancoDeDados");
			controleDeCadastroDeClientes.cadastrarCliente(new Cliente("Jonas", "289.875.960-01", "69027-410"));
			controleDeCadastroDeClientes.cadastrarCliente(new Cliente("Maria", "606.981.120-83", "74990-725"));
			controleDeCadastroDeClientes.cadastrarCliente(new Cliente("Kalil", "087.532.840-70", "78085-778"));
			controleDeCadastroDeClientes.cadastrarCliente(new Cliente("Wellington Felipe", "424.844.250-74", "49090-073"));
			controleDeCadastroDeClientes.cadastrarCliente(new Cliente("Matheus Miller", "425.332.960-82", "68908-351"));
			controleDeCadastroDeClientes.listarClientesCadastrados();
			System.out.println("-------------------------");
			controleDeCadastroDeClientes.listarClientesExcluidos();
		}catch(IOException e){
			System.out.println("Falha na comunicação com o banco de dados.");
		}
	}
}
