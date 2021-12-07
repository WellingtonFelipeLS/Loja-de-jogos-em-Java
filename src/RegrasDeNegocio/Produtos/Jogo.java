package RegrasDeNegocio.Produtos;

import java.util.Set;
import java.util.TreeSet;

public class Jogo extends Produto{
    private Set<String> generos;

	public Jogo() {}
	
    public Jogo(String nome, float preco, int qntNoEstoque, String descricao, Set<String> plataforma, Set<String> generos) {
        super(nome, preco, qntNoEstoque,  descricao, plataforma);
        this.generos = new TreeSet<String>(generos);
    }

	public void setGeneros(Set<String> generos) {
		this.generos = generos;
	}

    public Set<String> getGeneros() {
		return generos;
	}

}
