package RegrasDeNegocio.Produtos;

import java.util.Set;

public class Mouse extends Periferico{
	private String dpi;

	public Mouse(String nome, float preco, int qntNoEstoque, String descricao, Set<String> plataforma, boolean temRGB, boolean temBluetooth, String dpi) {
		super(nome, preco, qntNoEstoque, descricao, plataforma, temRGB, temBluetooth);
		this.dpi = dpi;
	}

	public boolean equals(Produto outro) {
		if(!(outro instanceof Mouse))
			return false;
		
		if(super.equals(outro) && this.getDpi().equals(((Mouse)outro).getDpi()))
			return true;
		
		return false;
	}

	public String getDpi() {
		return dpi;
	}
}
