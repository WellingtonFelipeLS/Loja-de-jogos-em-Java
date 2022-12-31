package TestesUnitarios;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

public abstract class UnitTestClasseBase {
	private String fileSeparator = System.getProperty("file.separator");
	private String caminhoParaPastaDoBancoDeDados = "src" + fileSeparator + "TestesUnitarios" + fileSeparator + "BancoDeDados";
	private static ByteArrayOutputStream mensagemDeErro;

	@BeforeEach
	private void prepararAmbiente() throws IOException {
		limparBancoDeDados();
		populaBancoDeDados();
	}

	@BeforeAll
	private static void redirecionarMensagemDeErro() {
		mensagemDeErro = new ByteArrayOutputStream();
		System.setErr(new PrintStream(mensagemDeErro));
	}

	protected abstract void limparBancoDeDados() throws IOException;
	protected abstract void populaBancoDeDados() throws IOException;

	public String getCaminhoParaPastaDoBancoDeDados() {
		return caminhoParaPastaDoBancoDeDados;
	}

	public ByteArrayOutputStream getMensagemDeErro() {
		return mensagemDeErro;
	}

	public String getFileSeparator() {
		return fileSeparator;
	}
	
}
