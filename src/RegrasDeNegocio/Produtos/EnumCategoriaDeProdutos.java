package RegrasDeNegocio.Produtos;

public enum EnumCategoriaDeProdutos {
	JOGO, CONSOLE, FONE, TECLADOMECANICO, MOUSE;

	public static String[] getCategoriasDeProduto() {
		String[] categorias = {"Jogo", "Console", "Fone", "TecladoMecanico", "Mouse"};
		return categorias;
	}
}
