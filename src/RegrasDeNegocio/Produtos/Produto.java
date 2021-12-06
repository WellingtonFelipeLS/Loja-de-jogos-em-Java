package RegrasDeNegocio.Produtos;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

import ClassesUtilitarias.IdGenerator;

public abstract class Produto implements Serializable{
    private String nome;
	private float preco;
    private String id;
    private String descricao;
    private Set<String> plataforma;
	private boolean cadastroAtivo;
	private int qntNoEstoque;

	public Produto(String nome, float preco, int qntNoEstoque, String descricao, Set<String> plataforma) {
		this.nome = nome;
		setPreco(preco);
		setQntNoEstoque(qntNoEstoque);
		this.descricao = descricao;
		this.plataforma = new TreeSet<String>(plataforma);
		this.id = IdGenerator.gerarId();
		this.cadastroAtivo = true;
	}

    public boolean equals(Produto outro) {
        if (nome.equals(outro.getNome()) && plataforma.equals(outro.getPlataforma()))
			return true;

        return false;
    }

	public void setPreco(float preco) {
		//Exception para valor negativo
		this.preco = preco;
	}

	public void setCadastroAtivo(boolean cadastroAtivo) {
		this.cadastroAtivo = cadastroAtivo;
	}

	public void setQntNoEstoque(int qntNoEstoque) {
		//Exception para valor negativo
		this.qntNoEstoque = qntNoEstoque;
	}

	public String getNome() {
        return nome;
    }

    public float getPreco() {
        return preco;
    }

    public String getId() {
        return id;
    }

	public String getDescricao() {
		return descricao;
	}

	public Set<String> getPlataforma() {
		return plataforma;
	}

	public boolean getCadastroAtivo() {
		return cadastroAtivo;
	}

	public int getQntNoEstoque() {
		return qntNoEstoque;
	}
}