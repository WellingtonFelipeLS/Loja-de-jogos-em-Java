package ManipulaçãoCadastro;

public abstract class CadastroVendas{
	public String IDVenda;
	public float valorTotal;
	// private tuple compras -> Guardar (produto, qnt comprada) numa lista com as compras

	private final String gerarIDVenda(){
		return null;
	}

	private final float calcularValorTotal(/*tuple compras*/){
		return 0;
	}

	protected abstract void registrarVenda();

	public final void vender(/*tuple compras*/){
		IDVenda = gerarIDVenda();
		valorTotal = calcularValorTotal();

		registrarVenda();
	}
}