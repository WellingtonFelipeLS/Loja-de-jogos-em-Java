package ManipulaçãoCadastro;

public final class CadastroVendasIdentificado extends CadastroVendas{
	private boolean verificarCadastroCliente(){
		return false;
	}

	@Override
	protected void registrarVenda() {}
}