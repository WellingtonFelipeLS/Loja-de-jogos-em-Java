package ExceptionsCustomizadas;

public class ValorInvalidoException extends Exception{
	public ValorInvalidoException(String msg) {
		super(msg);
	}

	public ValorInvalidoException() {
		super();
	}
}
