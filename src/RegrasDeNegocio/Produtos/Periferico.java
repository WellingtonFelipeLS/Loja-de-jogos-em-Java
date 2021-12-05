package RegrasDeNegocio.Produtos;

import java.util.Set;

public class Periferico extends Produto{
	private boolean temRGB;
	private boolean temBluetooth;

    public Periferico(String nome, float preco, int qntNoEstoque, String descricao, Set<String> plataforma, boolean temRGB, boolean temBluetooth) {
        super(nome, preco, qntNoEstoque,  descricao, plataforma);
		this.temRGB = temRGB;
		this.temBluetooth = temBluetooth;
    }

	public boolean equals(Produto outro) {
		if(!(outro instanceof Periferico))
			return false;
		
		if(super.equals(outro) && (this.getTemRGB() && ((Periferico)outro).getTemRGB()) && (this.getTemRGB() && ((Periferico)outro).getTemRGB()))
			return true;
		
		return false;
	}


	public boolean getTemRGB() {
		return temRGB;
	}

	public boolean getTemBluetooth() {
		return temBluetooth;
	}
}