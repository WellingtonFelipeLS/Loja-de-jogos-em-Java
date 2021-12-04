package InterfaceGrafica.src;


import javax.imageio.ImageIO;
import javax.swing.*;

import ClassesUtilitarias.Venda;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import ManipulacaoBancoDeDados.ControleDeEstoque;
import Produtos.Produto;

public class ProductWindow {

    ProductWindow(String nome, Venda novaVenda) {
        
		JFrame interfaceDoProduto = criarInterfaceDoProduto(nome);
		JPanel painelPrincipal = criarPainelPrincipal();

        
        interfaceDoProduto.add(painelPrincipal, BorderLayout.CENTER);

		GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        //imageLabel
        try {
            JLabel imageLabel = new JLabel(new ImageIcon(procurarImagemDoProduto(nome)));
			painelPrincipal.add(imageLabel);

			gbc.gridx = 0;
			gbc.gridy = 0;

			Produto produto = ControleDeEstoque.procurarProdutoNoEstoque(nome);

			JTextArea espacoDaDescricao = criarEspacoParaADescricao(produto.getDescricao()); 

			gbc.gridy += 1;

			painelPrincipal.add(espacoDaDescricao, gbc);

			gbc.gridy += 1;

			/*//priceLabel1 e 2
        	JLabel textoPrecoUnitario = new JLabel("Preço unitário: ");
        	gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        	painelPrincipal.add(textoPrecoUnitario, gbc);
        	JLabel valorPrecoUnitario = new JLabel(String.valueOf(produto.getPreco()));
        	gbc.anchor = GridBagConstraints.LINE_END;
        	painelPrincipal.add(valorPrecoUnitario, gbc);
			gbc.gridy += 1;

			//estoqueProduto1 e 2
			JLabel estoqueLabel1 = new JLabel("Estoque:");
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
			painelPrincipal.add(estoqueLabel1, gbc);
			JLabel estoqueLabel2 = new JLabel(String.valueOf(produto.getQntNoEstoque()));
			gbc.anchor = GridBagConstraints.FIRST_LINE_END;
			painelPrincipal.add(estoqueLabel2, gbc);

			//quantityLabel
			JLabel textoQuantidade = new JLabel("Quantidade:");
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc.gridy += 1;
			painelPrincipal.add(textoQuantidade, gbc);

			//quantityField
			JTextField campoDaQuantidade = new JTextField();
			campoDaQuantidade.setColumns(5);

			gbc.anchor = GridBagConstraints.LINE_END;
			painelPrincipal.add(campoDaQuantidade, gbc);

			//priceProductLabel1 e 2
			JLabel textoPrecoTotal = new JLabel("Preço total:");
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
			gbc.gridy += 1;
			painelPrincipal.add(textoPrecoTotal, gbc);
			JLabel textoCampoDaQuantidade = new JLabel("Digite uma quantidade.");
			gbc.anchor = GridBagConstraints.FIRST_LINE_END;
			painelPrincipal.add(textoCampoDaQuantidade, gbc);*/

			JLabel textoPrecoUnitario = criarLegendaGenerica("Preço unitário: ", gbc, GridBagConstraints.FIRST_LINE_START);
			painelPrincipal.add(textoPrecoUnitario, gbc);

			JLabel valorPrecoUnitario = criarLegendaGenerica(String.valueOf(produto.getPreco()), gbc, GridBagConstraints.LINE_END);
        	painelPrincipal.add(valorPrecoUnitario, gbc);

			gbc.gridy += 1;

			JLabel estoqueLabel1 = criarLegendaGenerica("Estoque:", gbc, GridBagConstraints.FIRST_LINE_START);
			painelPrincipal.add(estoqueLabel1, gbc);

			JLabel estoqueLabel2 = criarLegendaGenerica(String.valueOf(produto.getQntNoEstoque()), gbc, GridBagConstraints.FIRST_LINE_END);
			painelPrincipal.add(estoqueLabel2, gbc);

			gbc.gridy += 1;

			JLabel textoQuantidade = criarLegendaGenerica("Quantidade:", gbc, GridBagConstraints.FIRST_LINE_START);
			painelPrincipal.add(textoQuantidade, gbc);

			JTextField campoDaQuantidade = new JTextField();
			campoDaQuantidade.setColumns(5);
			gbc.anchor = GridBagConstraints.LINE_END;
			painelPrincipal.add(campoDaQuantidade, gbc);

			gbc.gridy += 1;

			JLabel textoPrecoTotal = criarLegendaGenerica("Preço total:", gbc, GridBagConstraints.FIRST_LINE_START);
			painelPrincipal.add(textoPrecoTotal, gbc);

			JLabel textoCampoDaQuantidade = criarLegendaGenerica("Digite uma quantidade.", gbc, GridBagConstraints.FIRST_LINE_END);
			painelPrincipal.add(textoCampoDaQuantidade, gbc);

			gbc.gridy += 1;

			JButton botaoDeCancelar = criarBotaoDeCancelar(interfaceDoProduto);
			gbc.anchor = GridBagConstraints.FIRST_LINE_START;
			painelPrincipal.add(botaoDeCancelar, gbc);

			JButton botaoDeAdiconarAoCarrinho = criarBotaoDeAdicionarAoCarrinho(nome, campoDaQuantidade, novaVenda, interfaceDoProduto, valorPrecoUnitario.getText());
			gbc.anchor = GridBagConstraints.FIRST_LINE_END;
			painelPrincipal.add(botaoDeAdiconarAoCarrinho, gbc);

			campoDaQuantidade.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {

				}

				@Override
				public void keyPressed(KeyEvent e) {

				}

				@Override
				public void keyReleased(KeyEvent e) {
					try {

						int numero = Integer.parseInt(campoDaQuantidade.getText());
						if (numero > 0 ) {
							if (numero <= produto.getQntNoEstoque()) {
								textoCampoDaQuantidade.setText(Float.toString(numero * Float.parseFloat(valorPrecoUnitario.getText())));
								botaoDeAdiconarAoCarrinho.setEnabled(true);
							} else {
								textoCampoDaQuantidade.setText("Estoque insuficiente!");
								botaoDeAdiconarAoCarrinho.setEnabled(false);
							}
						}
					} catch (NumberFormatException ex) {
						textoCampoDaQuantidade.setText("Quantidade inválida!");
						campoDaQuantidade.setText("");
						botaoDeAdiconarAoCarrinho.setEnabled(false);
					}
				}
        });

			interfaceDoProduto.pack();
			interfaceDoProduto.setVisible(true);
		}catch(IOException ioe) {
			ioe.printStackTrace();
		}

	}

	private JFrame criarInterfaceDoProduto(String name) {
		JFrame interfaceDoProduto = new JFrame(name);
		interfaceDoProduto.setDefaultCloseOperation( JFrame.DISPOSE_ON_CLOSE);
		interfaceDoProduto.setLocation(740, 0);
		interfaceDoProduto.setPreferredSize(new Dimension(240, 480));

		return interfaceDoProduto;
	}

	private JPanel criarPainelPrincipal() {
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new GridBagLayout());
        painelPrincipal.setBackground(Color.GRAY);

		return painelPrincipal;
	}

	private JTextArea criarEspacoParaADescricao(String descricao) {
		JTextArea espacoParaADescricao = new JTextArea(descricao);
		espacoParaADescricao.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
		espacoParaADescricao.setEditable(false);
		espacoParaADescricao.setLineWrap(true);
		espacoParaADescricao.setWrapStyleWord(true);
		espacoParaADescricao.setColumns(20);
		espacoParaADescricao.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		return espacoParaADescricao;
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

	private JButton criarBotaoDeCancelar(JFrame interfaceDoProduto) {
		JButton botaoDeCancelar = new JButton("Cancelar");
		botaoDeCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				interfaceDoProduto.dispatchEvent(new WindowEvent(interfaceDoProduto, WindowEvent.WINDOW_CLOSING));
			}
		});

		return botaoDeCancelar;
	}

	private JButton criarBotaoDeAdicionarAoCarrinho(String name, JTextField quantityField, Venda novaVenda, JFrame interfaceDoProduto, String price) {
		JButton botaoDeAdicionarAoCarrinho = new JButton("Adicionar");
		botaoDeAdicionarAoCarrinho.setEnabled(false);
		botaoDeAdicionarAoCarrinho.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				novaVenda.adicionarProdutoAoCarrinho(name, Integer.parseInt(quantityField.getText()));
			}
		});

		return botaoDeAdicionarAoCarrinho;
	}

	private JLabel criarLegendaGenerica(String mensagem, GridBagConstraints gbc, int option) {
		JLabel legendaGenerica = new JLabel(mensagem);
        gbc.anchor = option;

		return legendaGenerica;
	}
}
