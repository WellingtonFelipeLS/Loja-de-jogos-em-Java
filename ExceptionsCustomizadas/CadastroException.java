package ExceptionsCustomizadas;

public class CadastroException extends Exception{
	public CadastroException(String msg) {
		super(msg);
	}

	public CadastroException() {
		super();
	}
}
