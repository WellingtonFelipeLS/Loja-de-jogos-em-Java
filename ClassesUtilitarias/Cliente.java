package ClassesUtilitarias;

import java.io.Serializable;
// Código para a validação de cpf adquirido em: https://github.com/caelum/caelum-stella/wiki/Downloads-do-caelum-stella
// Site explicado o processo: https://www.alura.com.br/artigos/validando-cpf-com-java-atraves-do-stella
public final class Cliente implements Serializable{
	private String nome;
	private String id;
	private String CPF;
	private String CEP;
	private boolean eAnonimo;

	public Cliente() {
		this.nome = "Cliente Anonimo";
		this.CPF = "";
		this.CEP = "";
		this.eAnonimo = true;
	}
	
	public Cliente(String nome, String CPF, String CEP) {
		this.nome = nome;
		this.CPF = CPF;
		this.CEP = CEP;
		this.id = IdGenerator.gerarId();
		this.eAnonimo = false;
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

	public void imprimirInformacoes() {
		if(eAnonimo)
			System.out.println("Nome: " + nome);
		else{
			System.out.println("Nome: " +  nome);
			System.out.println("CPF: " +  CPF);
			System.out.println("CEP: " +  CEP);
		}
			//System.out.printf("Nome: %s - CPF: %s - CEP: %s\n", nome, CPF, CEP);
	}
}
