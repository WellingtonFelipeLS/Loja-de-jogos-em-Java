package InterfaceGrafica.src;

//import java.awt.event.MouseListener;
//import java.awt.event.MouseEvent;

import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

import ManipulacaoBancoDeDados.ControleDeVendas;

import ClassesUtilitarias.Venda;

public class JanelaDoCarrinho{
	
	public JanelaDoCarrinho(Venda novaVenda, java.io.File carrinho) {
		JFrame janelaDoCarrinho = new JFrame("Carrinho");
        janelaDoCarrinho.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janelaDoCarrinho.setSize( 800, 800 );

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BorderLayout());
		mainPanel.setBackground(Color.GRAY);
		//GridBagConstraints gbc = new GridBagConstraints();
		//gbc.insets = new Insets(5, 5, 5, 5);
		janelaDoCarrinho.add(mainPanel);

		String[][] dataCarrinho = new String[0][3];
		int ultimo = 0;
		try {
			Scanner carrinhoReader = new Scanner(carrinho);
			while (carrinhoReader.hasNextLine()) {
				if (dataCarrinho.length == ultimo) {
					String[][] listaAux = new String[ultimo + 1][3];
					System.arraycopy(dataCarrinho, 0, listaAux, 0, dataCarrinho.length);
					dataCarrinho = listaAux;
				}
				int contador = 0;
				for (String i:carrinhoReader.nextLine().split(", ")) {
					dataCarrinho[ultimo][contador] = i;
					contador += 1;
				}

				ultimo += 1;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		String[] produtosCarrinho = new String[0];
		String[][] dataCarrinhoFinal = new String[0][4];
		ultimo = 0;
		for (String[] linha: dataCarrinho) {
			if (!Arrays.asList(produtosCarrinho).contains(linha[0])) {
				if (ultimo == produtosCarrinho.length) {
					String[] listaAux = new String[ultimo + 1];
					System.arraycopy(produtosCarrinho, 0, listaAux, 0, produtosCarrinho.length);
					produtosCarrinho = listaAux;
				}
				produtosCarrinho[ultimo] = linha[0];
				if (dataCarrinhoFinal.length == ultimo) {
					String[][] listaAux = new String[ultimo + 1][4];
					System.arraycopy(dataCarrinhoFinal, 0, listaAux, 0, dataCarrinhoFinal.length);
					dataCarrinhoFinal = listaAux;
				}
				ultimo += 1;
				for (String[] linhaFinal: dataCarrinhoFinal) {
					if (linhaFinal[0] == null) {
						linhaFinal[0] = linha[0];
						linhaFinal[1] = linha[1];
						linhaFinal[2] = linha[2];
						//linhaFinal[3] = Float.toString(Integer.parseInt(linha[1]) * Float.parseFloat(linha[2]));
					}
				}
			} else {
				for (String[] linhaFinal: dataCarrinhoFinal) {
					if (Objects.equals(linha[0], linhaFinal[0])) {
						linhaFinal[1] = Integer.toString(Integer.parseInt(linhaFinal[1]) + Integer.parseInt(linha[1]));
					}
				}
			}
		}
		for (String[] linha: dataCarrinhoFinal){
			linha[3] = Float.toString(Integer.parseInt(linha[1]) * Float.parseFloat(linha[2]));
		}



		//carrinhoTable
		String[] columnNames = {"Produto", "Quantidade", "Preço unitário", "Preço total"};
		JTable carrinhoTable = new JTable(dataCarrinhoFinal, columnNames);
		carrinhoTable.setFillsViewportHeight(true);
		carrinhoTable.setEnabled(true);
		carrinhoTable.setShowGrid(false);
		carrinhoTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		carrinhoTable.setShowHorizontalLines(true);
		JScrollPane scrollPane = new JScrollPane(carrinhoTable);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);









		JButton botao = new JButton("Comprar");
		botao.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				try{
					ControleDeVendas.cadastrarVenda(novaVenda);
					ControleDeVendas.listarVendas();
					novaVenda.limparDadosDaVenda();
				}
				catch(IOException ioe) {
					ioe.printStackTrace();
				}
			}
		});
		janelaDoCarrinho.add(botao, BorderLayout.NORTH);

		//janelaDoCarrinho.pack();
		janelaDoCarrinho.setVisible(true);
	}
	private BufferedImage procurarImagemDoProduto(String name) throws IOException{
		BufferedImage image = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/InterfaceGrafica"
				+ System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + name + ".png")));
		BufferedImage imageResized = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = imageResized.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(image, 0, 0, 200, 200, null);
		g2.dispose();

		return imageResized;
	}

}
