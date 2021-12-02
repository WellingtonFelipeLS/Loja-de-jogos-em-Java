package ClassesUtilitarias;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CharIOMaster extends IOMaster{
	private String separadorDeInformacoes;

	public CharIOMaster(String caminhoDoArquivo, char opcao, String separadorDeInformacoes) throws IOException{
		switch(opcao){
			case 'r':
				setArquivoDeLeitura(new BufferedReader(new FileReader(caminhoDoArquivo)));
				break;
			case 'a':
				setArquivoDeEscrita(new BufferedWriter(new FileWriter(caminhoDoArquivo, true)));
				break;
		}
		
		this.separadorDeInformacoes = separadorDeInformacoes;
	}

	public CharIOMaster(String caminhoDoArquivoParaLeitura, String caminhoDoArquivoParaEscrita, String separadorDeInformacoes) throws IOException{
		setArquivoDeLeitura(new BufferedReader(new FileReader(caminhoDoArquivoParaLeitura)));
		setArquivoDeEscrita(new BufferedWriter(new FileWriter(caminhoDoArquivoParaEscrita)));
		this.separadorDeInformacoes = separadorDeInformacoes;
	}

	public Object ler() throws IOException {
		return ((BufferedReader)getArquivoDeLeitura()).readLine();
	}

	public void escrever(Object objeto) throws IOException {
		((BufferedWriter)getArquivoDeEscrita()).write((String)objeto);
	}

	public void escrever(Object[] objeto) throws IOException {
		escrever(objeto[0] + separadorDeInformacoes
				 + objeto[1] + separadorDeInformacoes 
				 + objeto[2] + separadorDeInformacoes 
				 + objeto[3] + separadorDeInformacoes
				 + objeto[4] + '\n');
	}
}
