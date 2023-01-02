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
	private final void prepararAmbiente() throws IOException {
		limparBancoDeDados();
		popularBancoDeDados();
	}

	@BeforeAll
	private final static void redirecionarMensagemDeErro() {
		mensagemDeErro = new ByteArrayOutputStream();
		System.setErr(new PrintStream(mensagemDeErro));
	}

	protected abstract void limparBancoDeDados() throws IOException;
	protected abstract void popularBancoDeDados() throws IOException;

	public final String getCaminhoParaPastaDoBancoDeDados() {
		return caminhoParaPastaDoBancoDeDados;
	}

	public final ByteArrayOutputStream getMensagemDeErro() {
		return mensagemDeErro;
	}

	public final String getFileSeparator() {
		return fileSeparator;
	}
	
}
