package InterfaceGrafica.src;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import ManipulacaoBancoDeDados.ControleDeEstoque;
import ManipulacaoBancoDeDados.ControleDeVendas;

import RegrasDeNegocio.Venda;
import RegrasDeNegocio.Produtos.Produto;

public class JanelaDoCarrinho{

	private Venda novaVenda;   

	public JanelaDoCarrinho(Venda novaVenda) {
		this.novaVenda = novaVenda;

		JFrame janelaDoCarrinho = criarJanelaDoCarrinho("Carrinho");

		JPanel painelPrincipal = criarPainelPrincipal();
		janelaDoCarrinho.add(painelPrincipal);

		JTable tabelaDoCarrinho = criarTabelaDeItens();
		JScrollPane painelDeRolagem = criarPainelDeRolagem(tabelaDoCarrinho);
		painelPrincipal.add(painelDeRolagem, BorderLayout.SOUTH);

		JPanel painelSuperior = criarPainelSuperior();
		painelPrincipal.add(painelSuperior, BorderLayout.NORTH);

		//Label (Painel Superior)
		JLabel olaUsuario = new JLabel("Logado como Visitante.");
		olaUsuario.setFont(new Font("Serif", Font.PLAIN, 20));
		olaUsuario.setForeground(Color.WHITE);
		painelSuperior.add(olaUsuario);

		JPanel painelCentral = criarPainelCentral();
		painelPrincipal.add(painelCentral, BorderLayout.CENTER);

		JPanel painelSuperiorCentral = criarPainelCentralSuperior();
		painelCentral.add(painelSuperiorCentral, BorderLayout.NORTH);

		JPanel painelCentralEsquerdo = criarPainelCentralEsquerdo();
		painelCentral.add(painelCentralEsquerdo, BorderLayout.WEST);

		// Label Imagem produto
		JLabel imagemLabel = new JLabel();
		painelCentralEsquerdo.add(imagemLabel);

		JPanel painelCentralDireito = criarPainelCentralDireito();
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.NORTHWEST;
		gbc.gridx = 0;
		gbc.gridy = 0;
		painelCentral.add(painelCentralDireito, BorderLayout.EAST);

		//PriceLabel
		JLabel priceLabel = new JLabel("Preço: ");
		painelCentralDireito.add(priceLabel, gbc);

		//EstoqueLabel
		JLabel estoqueLabel = new JLabel("Estoque: ");
		gbc.gridx = 1;
		gbc.gridy = 0;
		painelCentralDireito.add(estoqueLabel, gbc);

		//PreçoTotalLabel
		JLabel totalpriceLabel = new JLabel("Preço total: ");
		gbc.gridx = 2;
		gbc.gridy = 0;
		painelCentralDireito.add(totalpriceLabel, gbc);

		//quantidadeLabel e Field
		JLabel produtoQuantidadeLabel = new JLabel("Quantidade: ");
		gbc.gridy = 1;
		gbc.gridx = 0;
		painelCentralDireito.add(produtoQuantidadeLabel, gbc);

		JTextField produtoQuantidadeField = new JTextField();
		produtoQuantidadeField.setColumns(7);
		produtoQuantidadeField.setHorizontalAlignment(SwingConstants.RIGHT);
		gbc.gridx = 1;
		painelCentralDireito.add(produtoQuantidadeField, gbc);

		//Label selecione produto
		JLabel selecioneProdutoLabel = new JLabel("Selecione um produto:");
		painelSuperiorCentral.add(selecioneProdutoLabel);

		JLabel[] legendas = {priceLabel, totalpriceLabel, imagemLabel, estoqueLabel};
		JComboBox<String> barraDeSelecaoDeProduto = criarBarraDeSelecaoDeProduto(produtoQuantidadeField, legendas);
		painelSuperiorCentral.add(barraDeSelecaoDeProduto);

		JButton botaoDeAlterarQuantidade = criarBotaoDeAlterarQuantidade(barraDeSelecaoDeProduto, produtoQuantidadeField, tabelaDoCarrinho, janelaDoCarrinho);
		gbc.gridy = 1;
		gbc.gridx = 2;
		painelCentralDireito.add(botaoDeAlterarQuantidade, gbc);
		JButton botaoDeRemover = criarBotaoDeRemover(barraDeSelecaoDeProduto, tabelaDoCarrinho, janelaDoCarrinho);
		painelSuperiorCentral.add(botaoDeRemover);

		JButton botaoDeCompra = criarBotaoDeCompra();

		JLabel space1 = new JLabel(" ");
		gbc.ipady = 80;
		gbc.gridy = 2;
		painelCentralDireito.add(space1, gbc);

		gbc.ipady = 0;
		gbc.gridx = 2;
		gbc.gridy = 3;

		painelCentralDireito.add(botaoDeCompra, gbc);

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

	private JFrame criarJanelaDoCarrinho(String nome) {
		JFrame janelaDoCarrinho = new JFrame(nome);
		janelaDoCarrinho.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		janelaDoCarrinho.setSize( 600, 600 );
		janelaDoCarrinho.setLocation(740,0);

		return janelaDoCarrinho;
	}

	private JPanel criarPainelPrincipal() {
		JPanel painelPrincipal = new JPanel();
		painelPrincipal.setLayout(new BorderLayout());
		painelPrincipal.setBackground(Color.GRAY);

		return painelPrincipal;
	}

	private JPanel criarPainelSuperior() {
		JPanel painelSuperior = new JPanel();
		painelSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));
		painelSuperior.setBackground(Color.DARK_GRAY);

		return painelSuperior;
	}

	private JPanel criarPainelCentral() {
		JPanel painelCentral = new JPanel();
		painelCentral.setLayout(new BorderLayout());
		painelCentral.setBackground(Color.GRAY);

		return painelCentral;
	}

	private JPanel criarPainelCentralSuperior() {
		JPanel painelCentralSuperior = new JPanel();
		painelCentralSuperior.setLayout(new FlowLayout(FlowLayout.LEFT));
		painelCentralSuperior.setBackground(Color.GRAY);

		return painelCentralSuperior;
	}

	private JPanel criarPainelCentralEsquerdo() {
		JPanel painelCentralEsquerdo = new JPanel();
		painelCentralEsquerdo.setBackground(Color.GRAY);
		painelCentralEsquerdo.setBorder(BorderFactory.createLoweredBevelBorder());
		painelCentralEsquerdo.setPreferredSize(new Dimension(300, 300));

		return painelCentralEsquerdo;
	}

	private JPanel criarPainelCentralDireito() {
		JPanel painelCentralDireito = new JPanel();
		painelCentralDireito.setBackground(Color.GRAY);
		painelCentralDireito.setPreferredSize(new Dimension(300, 300));
		painelCentralDireito.setLayout(new GridBagLayout());

		return painelCentralDireito;
	}

	private JTable criarTabelaDeItens() {
		String[] informacoes = {"Produto", "Quantidade", "Preço unitário", "Preço total"};
		JTable tabelaDoCarrinho = new JTable(novaVenda.getInfoParaOCarrinho(), informacoes);
		tabelaDoCarrinho.setFillsViewportHeight(true);
		tabelaDoCarrinho.setEnabled(false);
		tabelaDoCarrinho.setShowGrid(false);
		tabelaDoCarrinho.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tabelaDoCarrinho.setShowHorizontalLines(true);

		return tabelaDoCarrinho;
	}

	private JScrollPane criarPainelDeRolagem(JTable tabelaDoCarrinho) {
		JScrollPane painelDeRolagem = new JScrollPane(tabelaDoCarrinho);
		painelDeRolagem.setPreferredSize(new Dimension(600, 200));
		painelDeRolagem.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		painelDeRolagem.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

		return painelDeRolagem;
	}

	private JComboBox<String> criarBarraDeSelecaoDeProduto(JTextField produtoQuantidadeField, JLabel[] legendas){
		JComboBox<String> barraDeSelecaoDeProduto = new JComboBox<String>(novaVenda.getProdutosDoCarrinho());
		String[][] finalDataCarrinhoFinal = novaVenda.getInfoParaOCarrinho();
		barraDeSelecaoDeProduto.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String produtoNome = Objects.requireNonNull(barraDeSelecaoDeProduto.getSelectedItem()).toString();
				produtoQuantidadeField.setText(finalDataCarrinhoFinal[barraDeSelecaoDeProduto.getSelectedIndex()][1]);
				legendas[0].setText("Preço: " + finalDataCarrinhoFinal[barraDeSelecaoDeProduto.getSelectedIndex()][2]);
				legendas[1].setText("Preço total: " + finalDataCarrinhoFinal[barraDeSelecaoDeProduto.getSelectedIndex()][3]);

				try {
					legendas[2].setIcon(new ImageIcon(procurarImagemDoProduto(produtoNome)));
					Produto produto = ControleDeEstoque.procurarProdutoNoEstoque(produtoNome);
					assert produto != null;
					legendas[3].setText("Estoque: " + produto.getQntNoEstoque());
				} catch (IOException ex) {
					ex.printStackTrace();
				}

			}
		});

		return barraDeSelecaoDeProduto;
	}

	private JButton criarBotaoDeAlterarQuantidade(JComboBox<String> barraDeSelecaoDeProduto, JTextField produtoQuantidadeField, JTable tabelaDoCarrinho, JFrame janelaDoCarrinho) {
		JButton botaoDeAlterarQuantidade = new JButton("Alterar");
		botaoDeAlterarQuantidade.setPreferredSize(new Dimension(100, 20));
		botaoDeAlterarQuantidade.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				novaVenda.modificarQntDoProdutoNoCarrinho(barraDeSelecaoDeProduto.getSelectedItem().toString(), Integer.valueOf(produtoQuantidadeField.getText()));
				janelaDoCarrinho.dispatchEvent(new WindowEvent(janelaDoCarrinho, WindowEvent.WINDOW_CLOSING));
				new JanelaDoCarrinho(novaVenda);
			}
		});

		return botaoDeAlterarQuantidade;
	}
	private JButton criarBotaoDeRemover(JComboBox<String> barraDeSelecaoDeProduto, JTable tabelaDoCarrinho, JFrame janelaDoCarrinho) {
		JButton botaoDeRemover = new JButton("Remover Produto");
		botaoDeRemover.setPreferredSize(new Dimension(150, 25));
		botaoDeRemover.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// remover produto do carrinho
				//novaVenda.modificarQntDoProdutoNoCarrinho(barraDeSelecaoDeProduto.getSelectedItem().toString(), Integer.valueOf(produtoQuantidadeField.getText()));
				janelaDoCarrinho.dispatchEvent(new WindowEvent(janelaDoCarrinho, WindowEvent.WINDOW_CLOSING));
				new JanelaDoCarrinho(novaVenda);
			}
		});

		return botaoDeRemover;
	}

	private JButton criarBotaoDeCompra() {
		JButton botaoDeCompra = new JButton("Comprar");
		botaoDeCompra.setPreferredSize(new Dimension(100, 20));
		botaoDeCompra.addActionListener(new ActionListener(){
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

		return botaoDeCompra;
	}
}

