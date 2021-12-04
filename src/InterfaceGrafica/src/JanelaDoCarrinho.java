package InterfaceGrafica.src;

import javax.imageio.ImageIO;
import javax.swing.*;


import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import ManipulacaoBancoDeDados.ControleDeVendas;

import ClassesUtilitarias.Venda;

public class JanelaDoCarrinho{
	
	public JanelaDoCarrinho(Venda novaVenda) {
		
		JFrame janelaDoCarrinho = criarJanelaDoCarrinho("Carrinho");
		JPanel painelPrincipal = criarPainelPrincipal();
		JScrollPane tabelaDeItensDoCarrinho = criarTabelaDeItens(novaVenda);

		painelPrincipal.add(tabelaDeItensDoCarrinho, BorderLayout.SOUTH);
		janelaDoCarrinho.add(painelPrincipal);

		//GridBagConstraints gbc = new GridBagConstraints();
		//gbc.insets = new Insets(5, 5, 5, 5);

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

		janelaDoCarrinho.pack();
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

	private JFrame criarJanelaDoCarrinho(String nome) {
		JFrame janelaDoCarrinho = new JFrame("Carrinho");
        janelaDoCarrinho.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        janelaDoCarrinho.setSize( 800, 800 );

		return janelaDoCarrinho;
	}

	private JPanel criarPainelPrincipal() {
		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());
		painelPrincipal.setBackground(Color.GRAY);

		return painelPrincipal;
	}

	private JScrollPane criarTabelaDeItens(Venda novaVenda) {
		String[] informacoes = {"Produto", "Quantidade", "Preço unitário", "Preço total"};
		JTable tabelaDoCarrinho = new JTable(novaVenda.getInfoParaOCarrinho(), informacoes);
		tabelaDoCarrinho.setFillsViewportHeight(true);
		tabelaDoCarrinho.setEnabled(false);
		tabelaDoCarrinho.setShowGrid(false);
		tabelaDoCarrinho.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaDoCarrinho.setShowHorizontalLines(true);

		JScrollPane painelDeRolagem = new JScrollPane(tabelaDoCarrinho);
		painelDeRolagem.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		painelDeRolagem.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		return painelDeRolagem;
	}
}
