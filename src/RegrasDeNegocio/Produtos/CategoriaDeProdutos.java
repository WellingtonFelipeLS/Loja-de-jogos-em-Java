package RegrasDeNegocio.Produtos;

import java.util.Arrays;

public enum CategoriaDeProdutos {
	QUALQUER(""),
	JOGO("Jogo"), 
	CONSOLE("Console"), 
	FONE("Fone"), 
	TECLADO("Teclado"), 
	MOUSE("Mouse");

	private String nome;
	
	private CategoriaDeProdutos(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return this.nome;
	}

	public static String[] getCategoriasDeProduto() {
		return Arrays.stream(CategoriaDeProdutos.values()).map(nome -> nome.toString())
														  .toArray(String[]::new);
	}
}
