package ClassesUtilitarias;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public abstract class IOMaster {
	private Closeable arquivoDeLeitura = null;
	private Closeable arquivoDeEscrita = null;

	public abstract Object ler() throws Exception;

	public abstract void escrever(Object objeto) throws Exception;

	public void fecharArquivos() throws IOException{
		if(arquivoDeLeitura != null) {
			arquivoDeLeitura.close();
			arquivoDeLeitura = null;
		}
			
		
		if(arquivoDeEscrita != null) {
			arquivoDeEscrita.close();
			arquivoDeEscrita = null;
		}
			
	}

	public void setArquivoDeEscrita(Closeable arquivoDeEscrita) {
		this.arquivoDeEscrita = arquivoDeEscrita;
	}

	public void setArquivoDeLeitura(Closeable arquivoDeLeitura) {
		this.arquivoDeLeitura = arquivoDeLeitura;
	}

	public Closeable getArquivoDeLeitura() {
		return arquivoDeLeitura;
	}

	public Closeable getArquivoDeEscrita() {
		return arquivoDeEscrita;
	}

	public static void deletarArquivo(String caminhoDoArquivo) {
		File estoqueTemp = new File(caminhoDoArquivo);
		estoqueTemp.delete();
	}

	public static void renomearESobrescreverArquivo(String caminhoDoArquivoAntigo, String caminhoDoArquivoNovo) throws IOException{
		Files.move(Paths.get(caminhoDoArquivoNovo),
					Paths.get(caminhoDoArquivoAntigo),
					StandardCopyOption.REPLACE_EXISTING);
	}
}