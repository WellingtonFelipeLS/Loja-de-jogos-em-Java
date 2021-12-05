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

import ManipulacaoBancoDeDados.ControleDeEstoque;
import ManipulacaoBancoDeDados.ControleDeVendas;

import ClassesUtilitarias.Venda;
import Produtos.Produto;

public class JanelaDoCarrinho{

	public JanelaDoCarrinho(Venda novaVenda, java.io.File carrinho) {
		JFrame janelaDoCarrinho = new JFrame("Carrinho");
		janelaDoCarrinho.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		janelaDoCarrinho.setSize( 600, 600 );
		janelaDoCarrinho.setLocation(740,0);

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
		final JTable[] carrinhoTable = {new JTable(dataCarrinhoFinal, columnNames)};
		carrinhoTable[0].setFillsViewportHeight(true);
		carrinhoTable[0].setEnabled(true);
		carrinhoTable[0].setShowGrid(false);
		carrinhoTable[0].setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		carrinhoTable[0].setShowHorizontalLines(true);
		JScrollPane scrollPane = new JScrollPane(carrinhoTable[0]);
		scrollPane.setPreferredSize(new Dimension(600, 200));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		mainPanel.add(scrollPane, BorderLayout.SOUTH);


		//Painel Superior
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		topPanel.setBackground(Color.DARK_GRAY);
		mainPanel.add(topPanel, BorderLayout.NORTH);

		//Label (Painel Superior)
		JLabel olaUsuario = new JLabel("Olá, Visitante.");
		olaUsuario.setFont(new Font("Serif", Font.PLAIN, 20));
		olaUsuario.setForeground(Color.WHITE);
		topPanel.add(olaUsuario);


		//Painel Central
		JPanel centralPanel = new JPanel();
		centralPanel.setLayout(new BorderLayout());
		centralPanel.setBackground(Color.GRAY);
		mainPanel.add(centralPanel, BorderLayout.CENTER);

		//Painel Central Superior
		JPanel centralSuperiorPanel = new JPanel();
		centralSuperiorPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		centralSuperiorPanel.setBackground(Color.GRAY);
		centralPanel.add(centralSuperiorPanel, BorderLayout.NORTH);

		//Painel Central Esquerdo (imagem)
		JPanel centralEsquerdoPanel = new JPanel();
		centralEsquerdoPanel.setBackground(Color.GRAY);
		centralEsquerdoPanel.setBorder(BorderFactory.createLoweredBevelBorder());
		centralEsquerdoPanel.setPreferredSize(new Dimension(300, 300));
		centralPanel.add(centralEsquerdoPanel, BorderLayout.WEST);

		// Label Imagem produto
		JLabel imagemLabel = new JLabel();
		centralEsquerdoPanel.add(imagemLabel);

		//Painel Central Direito
		JPanel centralDireitoPanel = new JPanel();
		centralDireitoPanel.setBackground(Color.GRAY);
		centralDireitoPanel.setPreferredSize(new Dimension(300, 300));
		centralDireitoPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		//GridBagConstraints gbc = new GridBagConstraints(0, 0, 1, 1, 1.0, 1.0,
		//GridBagConstraints.NORTHWEST, GridBagConstraints.NONE, new Insets(
		//0, 0, 0, 0), 0, 0);
		centralPanel.add(centralDireitoPanel, BorderLayout.EAST);


		//PriceLabel
		JLabel priceLabel = new JLabel("Preço: ");
		centralDireitoPanel.add(priceLabel, gbc);

		//EstoqueLabel
		JLabel estoqueLabel = new JLabel("Estoque: ");
		gbc.gridx = 1;
		gbc.gridy = 0;
		centralDireitoPanel.add(estoqueLabel, gbc);

		//PreçoTotalLabel
		JLabel totalpriceLabel = new JLabel("Preço total: ");
		gbc.gridx = 2;
		gbc.gridy = 0;
		centralDireitoPanel.add(totalpriceLabel, gbc);

		//quantidadeLabel e Field
		JLabel produtoQuantidadeLabel = new JLabel("Quantidade: ");
		gbc.gridy = 1;
		gbc.gridx = 0;
		centralDireitoPanel.add(produtoQuantidadeLabel, gbc);
		JTextField produtoQuantidadeField = new JTextField();
		produtoQuantidadeField.setColumns(7);
		produtoQuantidadeField.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 1;
		centralDireitoPanel.add(produtoQuantidadeField, gbc);



		//Label selecione produto
		JLabel selecioneProdutoLabel = new JLabel("Selecione um produto:");
		centralSuperiorPanel.add(selecioneProdutoLabel);

		//JComboBox selecione o produto
		JComboBox selecioneProdutoBox = new JComboBox(produtosCarrinho);
		String[][] finalDataCarrinhoFinal = dataCarrinhoFinal;
		selecioneProdutoBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String produtoNome = Objects.requireNonNull(selecioneProdutoBox.getSelectedItem()).toString();
				System.out.println(finalDataCarrinhoFinal[selecioneProdutoBox.getSelectedIndex()][1]);
				produtoQuantidadeField.setText(finalDataCarrinhoFinal[selecioneProdutoBox.getSelectedIndex()][1]);
				priceLabel.setText("Preço: " + finalDataCarrinhoFinal[selecioneProdutoBox.getSelectedIndex()][2]);
				totalpriceLabel.setText("Preço total: " + finalDataCarrinhoFinal[selecioneProdutoBox.getSelectedIndex()][3]);

				try {
					imagemLabel.setIcon(new ImageIcon(procurarImagemDoProduto(produtoNome)));
					Produto produto = ControleDeEstoque.procurarProdutoNoEstoque(produtoNome);
					assert produto != null;
					estoqueLabel.setText("Estoque: " + produto.getQntNoEstoque());
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		});
		centralSuperiorPanel.add(selecioneProdutoBox);

		//AlterarButton
		JButton alterarQuantidadeButton = new JButton("Alterar");
		alterarQuantidadeButton.setPreferredSize(new Dimension(100, 20));
		alterarQuantidadeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(1);
				finalDataCarrinhoFinal[selecioneProdutoBox.getSelectedIndex()][1] = produtoQuantidadeField.getText();


			}
		});
		gbc.gridy = 1;
		gbc.gridx = 2;
		centralDireitoPanel.add(alterarQuantidadeButton, gbc);














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
		JLabel space1 = new JLabel(" ");
		gbc.ipady = 80;
		gbc.gridy = 2;
		centralDireitoPanel.add(space1, gbc);

		gbc.ipady = 0;
		gbc.gridx = 2;
		gbc.gridy = 3;
		botao.setPreferredSize(new Dimension(100, 20));

		centralDireitoPanel.add(botao, gbc);

		//janelaDoCarrinho.pack();
		janelaDoCarrinho.setVisible(true);
	}
	private BufferedImage procurarImagemDoProduto(String name) throws IOException{
		BufferedImage image = ImageIO.read(Objects.requireNonNull(this.getClass().getResource("/InterfaceGrafica"
				+ System.getProperty("file.separator") + "img" + System.getProperty("file.separator") + name + ".png")));
		BufferedImage imageResized = new BufferedImage(280, 280, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = imageResized.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		g2.drawImage(image, 0, 0, 280, 280, null);
		g2.dispose();

		return imageResized;
	}

}

