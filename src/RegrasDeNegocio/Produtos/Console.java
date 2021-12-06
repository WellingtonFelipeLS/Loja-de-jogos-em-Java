package RegrasDeNegocio.Produtos;

import java.util.Set;

public class Console extends Produto{
	private String memoria;

	public Console() {}

	public Console(String nome, float preco, int qntNoEstoque, String descricao, Set<String> plataforma, String memoria) {
		super(nome, preco, qntNoEstoque,  descricao, plataforma);
		this.memoria = memoria;
	}

	public boolean equals(Produto outro) {
		if(!(outro instanceof Console))
			return false;
		
		if(super.equals(outro) && this.memoria.equals(((Console)outro).getMemoria()))
			return true;
		
		return false;
	}
	
	public void setMemoria(String memoria) {
		this.memoria = memoria;
	}

	public String getMemoria() {
		return memoria;
	}
}
