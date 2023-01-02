package RegrasDeNegocio;

import ClassesUtilitarias.IdGenerator;
import ExceptionsCustomizadas.CadastroException;

import java.io.Serializable;
// Código para a validação de cpf adquirido em: https://github.com/caelum/caelum-stella/wiki/Downloads-do-caelum-stella
// Site explicado o processo: https://www.alura.com.br/artigos/validando-cpf-com-java-atraves-do-stella
public final class Cliente implements Serializable{
	private String nome;
	private String id;
	private String CPF;
	private String CEP;

	public Cliente() {
		this.nome = "Cliente Anonimo";
	}
	
	public Cliente(String nome, String CPF, String CEP) {
		this.nome = nome;
		/*if (CPF == null)
			throw CadastroException("CPF inválido");*/
		this.CPF = CPF;
		this.CEP = CEP;
		this.id = IdGenerator.gerarId();
	}

	public String getNome() {
		return nome;
	}

	public String getId() {
		return id;
	}

	public String getCPF() {
		return CPF;
	}

	public String getCEP() {
		return CEP;
	}

	public boolean equals(Cliente cliente) {
		if(cliente.getCPF() == null)
			return false;
		
		return this.CPF.equals(cliente.getCPF());
	}

	public String imprimirInformacoes() {
		if(this.CPF == null)
			return "Nome: " + nome + '\n';
		else
			return "Nome: " +  nome + '\n' + "CPF: " +  CPF + '\n' + "CEP: " +  CEP + '\n';
	}
}
