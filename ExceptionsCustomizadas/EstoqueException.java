package ExceptionsCustomizadas;

public class EstoqueException extends Exception{
	public EstoqueException(String msg) {
		super(msg);
	}

	public EstoqueException() {
		super();
	}
}
