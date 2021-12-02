package ClassesUtilitarias;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ObjectIOMaster extends IOMaster{

	public ObjectIOMaster(String caminhoDoArquivo, char opcao) throws IOException{
		switch(opcao){
			case 'r':
				setArquivoDeLeitura(new ObjectInputStream(new FileInputStream(caminhoDoArquivo)));
				break;
			case 'w':
				setArquivoDeEscrita(new ObjectOutputStream(new FileOutputStream(caminhoDoArquivo)));
				break;
		}
	}

	public ObjectIOMaster(String caminhoDoArquivoParaLeitura, String caminhoDoArquivoParaEscrita) throws IOException{
		setArquivoDeLeitura(new ObjectInputStream(new FileInputStream(caminhoDoArquivoParaLeitura)));
		setArquivoDeEscrita(new ObjectOutputStream(new FileOutputStream(caminhoDoArquivoParaEscrita)));
	}

	public Object ler() throws IOException, ClassNotFoundException {
		return ((ObjectInputStream)getArquivoDeLeitura()).readObject();
	}

	public void escrever(Object objeto) throws IOException {
		((ObjectOutputStream)getArquivoDeEscrita()).writeObject(objeto);
	}
}
