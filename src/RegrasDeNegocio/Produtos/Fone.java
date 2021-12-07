package RegrasDeNegocio.Produtos;

import java.util.Set;

public class Fone extends Periferico {
	private String sensibilidade;
	private boolean temMicrofone;


	public Fone() {}
	
	public Fone(String nome, float preco, int qntNoEstoque, String descricao, Set<String> plataforma, boolean temBluetooth,
	 String sensibilidade, boolean temMicrofone) {
		super(nome, preco, qntNoEstoque, descricao, plataforma, temBluetooth);
		this.sensibilidade = sensibilidade;
		this.temMicrofone = temMicrofone;
	}

	public void setSensibilidade(String sensibilidade) {
		this.sensibilidade = sensibilidade;
	}

	public void setTemMicrofone(boolean temMicrofone) {
		this.temMicrofone = temMicrofone;
	}

	public String getSensibilidade() {
		return sensibilidade;
	}

	public boolean getTemMicrofone() {
		return temMicrofone;
	}
}
