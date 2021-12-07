package RegrasDeNegocio.Produtos;

import java.util.Set;

public class Mouse extends Periferico{
	private String dpi;

	public Mouse() {}
	
	public Mouse(String nome, float preco, int qntNoEstoque, String descricao, Set<String> plataforma, boolean temBluetooth, String dpi) {
		super(nome, preco, qntNoEstoque, descricao, plataforma, temBluetooth);
		this.dpi = dpi;
	}

	public void setDpi(String dpi) {
		this.dpi = dpi;
	}

	public String getDpi() {
		return dpi;
	}
}
