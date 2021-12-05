package RegrasDeNegocio.Produtos;

import java.util.Set;
import java.util.TreeSet;

public class Jogo extends Produto{
    private Set<String> generos;

    public Jogo(String nome, float preco, int qntNoEstoque, String descricao, Set<String> plataforma, Set<String> generos) {
        super(nome, preco, qntNoEstoque,  descricao, plataforma);
        this.generos = new TreeSet<String>(generos);
    }

    @Override
    public boolean equals(Produto outro) {
		if(!(outro instanceof Jogo))
			return false;

		if(super.equals(outro) && generos.equals(((Jogo)outro).getGeneros()))
			return true;
		
		return false;
    }

    public Set<String> getGeneros() {
		return generos;
	}

}
