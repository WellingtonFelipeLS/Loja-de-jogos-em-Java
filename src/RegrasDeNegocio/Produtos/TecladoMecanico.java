package RegrasDeNegocio.Produtos;

import java.util.Set;

public class TecladoMecanico extends Periferico{
	private String tipoDeSwitch;

	public TecladoMecanico() {}
	
	public TecladoMecanico(String nome, float preco, int qntNoEstoque, String descricao, Set<String> plataforma, boolean temBluetooth, String tipoDeSwitch) {
		super(nome, preco, qntNoEstoque, descricao, plataforma, temBluetooth);
		this.tipoDeSwitch = tipoDeSwitch;
	}

	public boolean equals(Produto outro) {
		if(!(outro instanceof TecladoMecanico))
			return false;
		
		if(super.equals(outro) && this.getTipoDeSwitch().equals(((TecladoMecanico)outro).getTipoDeSwitch()))
			return true;
		
		return false;
	}

	public void setTipoDeSwitch(String tipoDeSwitch) {
		this.tipoDeSwitch = tipoDeSwitch;
	}

	public String getTipoDeSwitch() {
		return tipoDeSwitch;
	}
}
