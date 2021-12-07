package RegrasDeNegocio.Produtos;

import java.util.Set;

public class Periferico extends Produto{
	private boolean temBluetooth;

	public Periferico() {}
	
    public Periferico(String nome, float preco, int qntNoEstoque, String descricao, Set<String> plataforma, boolean temBluetooth) {
        super(nome, preco, qntNoEstoque,  descricao, plataforma);
		this.temBluetooth = temBluetooth;
    }

	public void setTemBluetooth(boolean temBluetooth) {
		this.temBluetooth = temBluetooth;
	}

	public boolean getTemBluetooth() {
		return temBluetooth;
	}
}